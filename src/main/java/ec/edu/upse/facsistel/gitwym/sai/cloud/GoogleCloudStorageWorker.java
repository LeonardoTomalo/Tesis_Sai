package ec.edu.upse.facsistel.gitwym.sai.cloud;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import javax.imageio.ImageIO;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Bucket.BlobTargetOption;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobGetOption;
import com.google.cloud.storage.StorageOptions;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class GoogleCloudStorageWorker {

	private static String bucketName = "guiamovilse_recursos_storage";
    //Ancho máximo
    public static int MAX_WIDTH=800;
    //Alto máximo
    public static int MAX_HEIGHT=800;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private HBox mediaBar;

    
	/**Guarda un byte[] contenido que lo convierte en BLOB para posteriormente almacenarlo
	 * en un Bucket de Google Cloud.
	 * @param blobId
	 * @param content[]
	 * @return linkDescarga
	 */
	public String saveMediaCR(String blobId, byte[] content) {
		try {
			Credentials c = GoogleCredentials.getApplicationDefault();
			StorageOptions.getDefaultInstance();
			Storage storage = (Storage) StorageOptions.newBuilder().setCredentials(c).build().getService();
			Bucket bucket = storage.get(bucketName);

			Blob blob = bucket.create(blobId, content, BlobTargetOption.doesNotExist());
			System.out.println(blob.getMediaLink());
			System.out.printf("Bucket %s created.%n", bucket.getName());

			return blob.getMediaLink();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String updateMediaCR(String blobId, byte[] content) {
		try {
			Storage storage = StorageOptions.getDefaultInstance().getService();
			Bucket bucket = storage.get(bucketName);
			Blob blob = bucket.get(blobId, BlobGetOption.generationMatch());
			WritableByteChannel channel = blob.writer();
			channel.write(ByteBuffer.wrap(content));
			channel.close();
			return blob.getMediaLink();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error al modificar medio Cloud");
			return null;
		}
	}
	
	/**Metodo privado que se conecta a Google Cloud y mediante el bucketName retorna 
	 * el contenido del BlobID, lo cual retorna un byteArray[].
	 * @param blobID
	 * @return byte[]
	 */
	private byte[] getByteMediaCR(String blobID) {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Bucket bucket = storage.get(bucketName);		
		return bucket.get(blobID).getContent();
	}
	
	/**Obtiene una imagen de Google Cloud Storage por medio del BLOBID
	 * y lo convierte en Image JavaFX.
	 * @param blobID
	 * @return Image
	 */
	public Image getImageMediaCR(String blobID) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(getByteMediaCR(blobID));			
			return SwingFXUtils.toFXImage(ImageIO.read(bis), null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**Retorna un Media de JavaFX obteniendo un contenido en byte array por medio 
	 * del BLOBID y lo convierte en Media de JAVAFX.
	 * @param blobID
	 * @return
	 */
	public Media getMediaFromMediaCR(String blobID) {
		try {			
			return byteArrayToFile(getByteMediaCR(blobID));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**Convierte una FILE en un byte array
	 * @param file
	 * @return
	 */
	public byte[] fileToByteArray(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); //no doubt here is 0
				//Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
			fis.close();
			return bos.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**Metodo privado que convierte un byte array en un archivo MP4 y a su vez lo obtiene y convierte en 
	 * un Media de JavaFX.
	 * @param byteArray
	 * @return
	 */
	private Media byteArrayToFile(byte [] byteArray) {
		File file = new File("D:\\newfile.mp4");
		try (FileOutputStream fop = new FileOutputStream("D:\\newfile.mp4")) {
			fop.write(byteArray);
			fop.flush();
			fop.close();
			
			File d = new File(file.getAbsolutePath().replace("\\","/"));
	        return new Media(d.toURI().toString());			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void showMediaInContenedor(Image image, AnchorPane contenedor) {
		if (!contenedor.getChildren().isEmpty()) {
			contenedor.getChildren().clear();
		}

		BorderPane bp = new BorderPane();
	    ImageView img = new ImageView(image);
	    if (image.getUrl() != null) {
	    	if (!image.getUrl().equals("file:/D:/WSPC_TEO_11/TESIS_SAI/target/classes/albums.png")) 
			    img.fitWidthProperty().bind(contenedor .widthProperty()); 
		}
//	    img.fitHeightProperty().bind(contenedor.heightProperty());
		img.fitHeightProperty().set(288);
	    img.setPreserveRatio(true);
	    bp.setCenter(img);
	    
	    AnchorPane.setBottomAnchor(bp, 00.0);
		AnchorPane.setLeftAnchor(bp, 00.0);
		AnchorPane.setTopAnchor(bp, 00.0);
		AnchorPane.setRightAnchor(bp, 00.0);	    
	    
	    contenedor.getChildren().add(bp);
	}
	
	public void showMediaInContenedor(Media media, AnchorPane contenedor) {
		if (!contenedor.getChildren().isEmpty()) {
			contenedor.getChildren().clear();
		}

		BorderPane bp = new BorderPane();
	    MediaPlayer mp = new MediaPlayer(media);	    
	    MediaView view = new MediaView(mp);
//	    view.fitWidthProperty().bind(contenedor .widthProperty()); 
//	    view.fitHeightProperty().bind(contenedor.heightProperty());
	    view.setFitWidth(380);
	    view.setPreserveRatio(true);
	    bp.setCenter(view);
	    
	    mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);	
 
        final Button playButton  = new Button(">");
        
        playButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {	
        		Status status = mp.getStatus();

        		if (status == Status.UNKNOWN  || status == Status.HALTED){
        			// don't do anything in these states
        			return;
        		}

        		if ( status == Status.PAUSED || status == Status.READY || status == Status.STOPPED)	{
        			// rewind the movie if we're sitting at the end
        			if (atEndOfMedia) {
        				mp.seek(mp.getStartTime());
        				atEndOfMedia = false;
        			}
        			mp.play();
        		} else {
        			mp.pause();
        		}
        	}
        });
        
        mp.currentTimeProperty (). addListener (new InvalidationListener () { 
        	public void invalidated (Observable ov) { 
        		updateValues(mp); 
        	} 
        }); 

        mp.setOnPlaying (new Runnable () { 
        	public void run () { 
        		if (stopRequested) { 
        			mp.pause (); 
        			stopRequested = false; 
        		} else { 
        			playButton.setText ("||"); 
        		} 
        	} 
        }); 

        mp.setOnPaused (new Runnable () { 
        	public void run () { 
        		System.out.println ("onPaused");
        		playButton.setText (">"); 
        	} 
        }); 

        mp.setOnReady (new Runnable () { 
        	public void run () { 
        		duration = mp.getMedia().getDuration(); 
        		updateValues(mp); 
        	} 
        }); 

        mp.setCycleCount (repeat ? MediaPlayer.INDEFINITE: 1); 
        mp.setOnEndOfMedia (new Runnable () { 
        	public void run () { 
        		if (! repeat) { 
        			playButton.setText (">"); 
        			stopRequested = true; 
        			atEndOfMedia = true; 
        		} 
        	} 
        });

        mediaBar.getChildren().add(playButton);
        
        // Add spacer
        Label spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);
         
        // Add Time label
        Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);
         
        // Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider,Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (timeSlider.isValueChanging()) {
               // multiply duration by percentage calculated by slider position
                  mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
               }
            }
        });
        
        mediaBar.getChildren().add(timeSlider);

        // Add Play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);
         
        // Add the volume label
        Label volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);
         
        // Add Volume slider
        volumeSlider = new Slider();        
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);       
        
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (volumeSlider.isValueChanging()) {
                   mp.setVolume(volumeSlider.getValue() / 100.0);
               }
            }
        });
        
        mediaBar.getChildren().add(volumeSlider);
        
        bp.setBottom(mediaBar);     
        
        AnchorPane.setBottomAnchor(bp, 00.0);
		AnchorPane.setLeftAnchor(bp, 00.0);
		AnchorPane.setTopAnchor(bp, 00.0);
		AnchorPane.setRightAnchor(bp, 00.0);	
		
	    contenedor.getChildren().add(bp);
	}
	
	protected void updateValues(MediaPlayer mp) {
		if (playTime != null && timeSlider != null && volumeSlider != null) {
			Platform.runLater(new Runnable() {
				public void run() {
					Duration currentTime = mp.getCurrentTime();
					playTime.setText(formatTime(currentTime, duration));
					timeSlider.setDisable(duration.isUnknown());
					if (!timeSlider.isDisabled() 
							&& duration.greaterThan(Duration.ZERO) 
							&& !timeSlider.isValueChanging()) {
						timeSlider.setValue(currentTime.divide(duration).toMillis()
								* 100.0);
					}
					if (!volumeSlider.isValueChanging()) {
						volumeSlider.setValue((int)Math.round(mp.getVolume() 
								* 100));
					}
				}
			});
		}
	}
	
	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int)Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
				- elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - 
					durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", 
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d",
						elapsedMinutes, elapsedSeconds,durationMinutes, 
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, 
						elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d",elapsedMinutes, 
						elapsedSeconds);
			}
		}
	}
	
	/*
    Este método se utiliza para redimensionar la imagen
    */
    private static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
	
	public BufferedImage redimensionarImagen(BufferedImage bimage) {
		if(bimage.getHeight()>bimage.getWidth()){
            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
            bimage = resize(bimage, MAX_WIDTH, heigt);
            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
            bimage = resize(bimage, width, MAX_HEIGHT);
        }else{
            int width = (bimage.getWidth() * MAX_HEIGHT) / bimage.getHeight();
            bimage = resize(bimage, width, MAX_HEIGHT);
            int heigt = (bimage.getHeight() * MAX_WIDTH) / bimage.getWidth();
            bimage = resize(bimage, MAX_WIDTH, heigt);
        }
		return bimage;
	}
	
	 /*
    Este método se utiliza para almacenar la imagen en disco
    */
    public void saveImageInDirectorio(BufferedImage bufferedImage, String pathName) {
        try {
            String format = (pathName.endsWith(".png")) ? "png" : "jpg";
            File file =new File(pathName);
            file.getParentFile().mkdirs();
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}

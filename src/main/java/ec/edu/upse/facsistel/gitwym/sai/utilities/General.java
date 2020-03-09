package ec.edu.upse.facsistel.gitwym.sai.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXPopup;

import ec.edu.upse.facsistel.gitwym.sai.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class General {

	/**
	 * Establece una imagen en un ImageView.
	 * @param nombreImagen 
	 * @param imgView
	 */
	public static void setImageView(String nombreImagen, ImageView imgView) {
		Image img = new Image(nombreImagen);
		imgView.setImage(img);
	}	
	
	public static byte[] converterImageToByteArray(BufferedImage bufferImage) {
		try {
			// get DataBufferBytes from Raster
//			 WritableRaster raster = bufferImage .getRaster();
//			 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
//			 return data.getData();
			
			BufferedImage bufferedImage = bufferImage;
			ByteArrayOutputStream s = new ByteArrayOutputStream();
			if(!ImageIO.write(bufferedImage, "png", s)) {
				System.err.println("Error al escribir imagen al byte Array Output Stream");
			}

			byte[] res = s.toByteArray();
			s.flush();
			s.close();
			//Metodo para convertir ByteArray en Archivo de imagen. Solo usado para pruebas.
			//convertirByteArrayEnImagenArchivo();
			return res;
			
		}catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al convertir imagen en Array.!!");
			return null;
		}
	}
	

	/**
	 * Establece la interfaz de la URI como contenido del anchorPane.
	 * @param uri
	 * @param contenedor
	 */
	public static void setContentParent(String uri, AnchorPane contenedor){
		try{
			if(!contenedor.equals(null))contenedor.getChildren().removeAll();
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uri));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			contenedor.getChildren().setAll(page);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Establece la interfaz de la URI como contenido del anchorPane.
	 * @param uri
	 * @param contenedor
	 */
	public static JFXPopup setContentPopover(String uri){
		try{
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uri));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			JFXPopup contenedor = new JFXPopup();
			contenedor.setPopupContent(page);
			contenedor.setAutoHide(false);	    
			contenedor.setHideOnEscape(true);	
			contenedor.show(Context.getInstance().getAnch_Contenido());	
			return contenedor;			
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void showModalWithParent(String uriParent) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uriParent));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			Stage stage = new Stage();
			Scene scene = new Scene(page, Color.WHITE);
//			stage.b
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Context.getInstance().getStage());
			stage.setScene(scene);
			Context.getInstance().setStageModalBase(stage);
			stage.showAndWait();						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showModalWithParentMAPA(String uriParent) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uriParent));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			Stage stage = new Stage();
			Scene scene = new Scene(page, Color.WHITE);
//			stage.b
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Context.getInstance().getStage());
			stage.setScene(scene);
			Context.getInstance().setStageModalBaseMAPA(stage);
			stage.showAndWait();						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showModalWithParentMAPAPOINT(String uriParent) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uriParent));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			Stage stage = new Stage();
			Scene scene = new Scene(page, Color.WHITE);
//			stage.b
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Context.getInstance().getStage());
			stage.setScene(scene);
			Context.getInstance().setStageModalBaseMAPAPOINT(stage);
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showModalWithParentSendero(String uriParent) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uriParent));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			Stage stage = new Stage();
			Scene scene = new Scene(page, Color.WHITE);
			stage.setMaximized(true);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Context.getInstance().getStage());
			stage.setScene(scene);
			Context.getInstance().setStageModalBaseSendero(stage);
			stage.showAndWait();						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showModalWithParentAtractivo(String uriParent) {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource(uriParent));
			AnchorPane page = (AnchorPane) loader.load();		
			AnchorPane.setBottomAnchor(page, 00.0);
			AnchorPane.setLeftAnchor(page, 00.0);
			AnchorPane.setTopAnchor(page, 00.0);
			AnchorPane.setRightAnchor(page, 00.0);
			Stage stage = new Stage();
			Scene scene = new Scene(page, Color.WHITE);
//			stage.b
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Context.getInstance().getStage());
			stage.setScene(scene);
			Context.getInstance().setStageModalBaseAtractivo(stage);
			stage.showAndWait();						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Establece el mapa como contenido del anchorPane enviado.
	 * @param mapa
	 * @param contenedor
	 */
	public static void setMapatoAnchorPane(MapView map, AnchorPane contenedor){
		try{
			if(!contenedor.equals(null))contenedor.getChildren().removeAll();	
			AnchorPane.setBottomAnchor(map, 00.0);
			AnchorPane.setLeftAnchor(map, 00.0);
			AnchorPane.setTopAnchor(map, 00.0);
			AnchorPane.setRightAnchor(map, 00.0);
			contenedor.getChildren().setAll(map);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Abre y cierra el JFXDrawer enviada como parametro. 
	 * @param drw
	 */
	public static void setAbriCerrarDRW(JFXDrawer drw) {
		if (drw.isOpened()) {
			drw.close();
		}else {
			drw.open();
		}
	}
	
	/**
	 * Establece el contenido en un JFXDrawer enviando por parametro la ruta 
	 * de la interfaz y el Drawer Contenedor.
	 * @param URI
	 * @param drw
	 */
	public static void setContentToJFXDrawer(String URI, JFXDrawer drw) {
		try {
			Parent v = FXMLLoader.load(App.class.getResource(URI));
			drw.setSidePane(v);	
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Establece el texto de un label.
	 * @param label
	 * @param text
	 */
	public static void setTexttoLabel(Label label, String text) {
		label.setText(text.toUpperCase());
	}
	
	
	/**
	 * Configura un spinner numerico.
	 * @param spinner
	 */
	public static void spinnerNumerico(Spinner<Integer> spinner) {
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); 
        spinner.setValueFactory(valueFactory);
	}
	
}

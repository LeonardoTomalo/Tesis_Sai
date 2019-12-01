package ec.edu.upse.facsistel.gitwym.sai.utilities;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Context {
	
	private final static Context instance = new Context();
	
	@FXML private AnchorPane anch_Contenido;
	
	
	public static Context getInstance() {
		return instance;
	}
	
	public AnchorPane getAnch_Contenido() {
		return anch_Contenido;
	}

	public void setAnch_Contenido(AnchorPane anch_Contenido) {
		this.anch_Contenido = anch_Contenido;
	}

	//creo mi pojo.
	String fff ;
	
	public String getFff() {
		return fff;
	}

	public void setFff(String fff) {
		this.fff = fff;
	} 
	
}

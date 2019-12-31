package ec.edu.upse.facsistel.gitwym.sai.utilities;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class Context {
	
	private final static Context instance = new Context();	
	private AnchorPane anch_Contenido;	
	private Stage stage;
	
	public static Context getInstance() {
		return instance;
	}	
}

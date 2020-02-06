package ec.edu.upse.facsistel.gitwym.sai.utilities;

import com.gluonhq.maps.MapView;

import ec.edu.upse.facsistel.gitwym.sai.models.Comodidades;
import ec.edu.upse.facsistel.gitwym.sai.models.Contacto;
import ec.edu.upse.facsistel.gitwym.sai.models.Costo;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.models.Usuario;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class Context {
	
	private final static Context instance = new Context();	
	
	//Controller
	private AnchorPane anch_Contenido;	
	private Stage stage;
	private MapView mapViewContext;
	private Stage stageModalBase;
	
	//POJOS
	private Contacto contactoP;
	private Usuario userLogged;
	private Recurso recursoContext;
	private MediaCloudResources mediaContext;
	private Costo costoContext;
	private Contacto contactoContext;
	private Comodidades comodidadContext;
	
	
	public static Context getInstance() {
		return instance;
	}	
}

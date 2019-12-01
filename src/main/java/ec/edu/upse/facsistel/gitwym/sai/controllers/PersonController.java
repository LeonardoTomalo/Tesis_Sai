package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXListView;

import ec.edu.upse.facsistel.gitwym.sai.models.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PersonController {

    @FXML private Button getPerson;
    @FXML private Button addPerson;
    @FXML private TextField txt_nombre;
    @FXML private TextField txt_apellido;
    @FXML private TextField txt_estado;
    @FXML private JFXListView<Person> lst_personas;
    
//    @Autowired PersonRepository personServices;
	private Person person;

    @FXML
    void addPerson(ActionEvent event) {
    	person = new Person();
    	person.setNombre(txt_nombre.getText());
    	person.setApellido(txt_apellido.getText());
    	person.setEstado(txt_estado.getText());
    	
    	RestTemplate rest = new RestTemplate();
    	final String uri = "http://localhost:8080/persons1/addPerson";
    	Person p = rest.postForObject(uri, person, Person.class);
    	System.out.println("Aqui empieza el POST FOR OBJECT" + p.toString());
    }

    @FXML
    void getPerson(ActionEvent event) {
    	RestTemplate rest = new RestTemplate();
    	final String uri = "http://localhost:8080/persons1";
    	ResponseEntity<List<Person>> rateResponse =
    	        rest.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() { });
    	List<Person> persons = rateResponse.getBody();
    	
    	lst_personas.getItems().addAll(persons);
    }
	
	
}

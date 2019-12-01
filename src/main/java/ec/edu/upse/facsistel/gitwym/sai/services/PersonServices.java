package ec.edu.upse.facsistel.gitwym.sai.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.upse.facsistel.gitwym.sai.models.Person;
import ec.edu.upse.facsistel.gitwym.sai.repositories.PersonRepository;

@Service
@RestController
@RequestMapping("/persons1")
public class PersonServices {
	
	@Autowired
	private PersonRepository personRepository;

	@GetMapping
	public @ResponseBody List<Person> getListPerson() {
		return (List<Person>) personRepository.findAll();
	}
	
	@PostMapping("/addPerson")
	public Person addPerson(@RequestBody Person p) {
		return personRepository.save(p);
	}
}

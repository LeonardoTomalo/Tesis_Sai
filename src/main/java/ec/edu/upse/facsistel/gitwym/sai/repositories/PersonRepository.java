package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Person;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "person", viewName = "all")
public interface PersonRepository extends CouchbaseRepository<Person, String>{

}

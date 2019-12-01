package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Sendero;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "sendero", viewName = "all")
public interface SenderoRepository extends CouchbaseRepository<Sendero, String>{

}

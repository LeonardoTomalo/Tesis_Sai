package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "recurso", viewName = "all")
public interface RecursoRepository extends CouchbaseRepository<Recurso, String>{

}

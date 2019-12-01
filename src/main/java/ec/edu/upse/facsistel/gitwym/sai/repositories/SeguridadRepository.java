package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Seguridad;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "seguridad", viewName = "all")
public interface SeguridadRepository extends CouchbaseRepository<Seguridad, String>{

}

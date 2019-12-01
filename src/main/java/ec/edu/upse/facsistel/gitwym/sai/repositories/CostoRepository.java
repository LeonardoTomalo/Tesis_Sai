package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Costo;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "costo", viewName = "all")
public interface CostoRepository extends CouchbaseRepository<Costo, String>{

}

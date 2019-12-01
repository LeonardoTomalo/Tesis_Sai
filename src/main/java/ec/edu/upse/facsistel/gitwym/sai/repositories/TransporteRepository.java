package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Transporte;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "transporte", viewName = "all")
public interface TransporteRepository extends CouchbaseRepository<Transporte, String>{

}

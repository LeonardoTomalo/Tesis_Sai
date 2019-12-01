package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.TipoAtractivo;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "tipoAtractivo", viewName = "all")
public interface TipoAtractivoRepository extends CouchbaseRepository<TipoAtractivo, String>{

}

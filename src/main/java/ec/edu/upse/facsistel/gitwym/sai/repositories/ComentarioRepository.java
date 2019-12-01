package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Comentario;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "comentario", viewName = "all")
public interface ComentarioRepository extends CouchbaseRepository<Comentario, String>{

}

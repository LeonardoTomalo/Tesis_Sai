package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Animacion3D;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "animacion", viewName = "all")
public interface Animacion3DRepository extends CouchbaseRepository<Animacion3D, String>{

}

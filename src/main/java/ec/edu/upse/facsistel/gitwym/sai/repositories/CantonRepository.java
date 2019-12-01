package ec.edu.upse.facsistel.gitwym.sai.repositories;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import ec.edu.upse.facsistel.gitwym.sai.models.Canton;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "canton", viewName = "all")
public interface CantonRepository extends CouchbaseRepository<Canton, String>{

}

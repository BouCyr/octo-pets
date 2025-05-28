package app.cbo.petgm.backend.out.repository;

import app.cbo.petgm.backend.out.model.Prestation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrestationRepository extends MongoRepository<Prestation, String> {
    Optional<Prestation> findByTechnicalId(String technicalId);
    // Basic CRUD operations are inherited
}

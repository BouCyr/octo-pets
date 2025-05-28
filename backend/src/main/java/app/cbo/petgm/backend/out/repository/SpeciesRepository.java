package app.cbo.petgm.backend.out.repository;

import app.cbo.petgm.backend.out.model.Species;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SpeciesRepository extends MongoRepository<Species, String> {
    Optional<Species> findByName(String name);
    // Basic CRUD operations are inherited
}

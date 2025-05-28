package app.cbo.petgm.backend.out.repository;

import app.cbo.petgm.backend.out.model.PerformedService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformedServiceRepository extends MongoRepository<PerformedService, String> {
    // Basic CRUD operations are inherited
    // Custom query methods can be added here later for filtering by date, customer, pet, status etc.
}

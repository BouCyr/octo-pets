package app.cbo.petgm.backend.out.repository;

import app.cbo.petgm.backend.out.model.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findByCustomerId(String customerId);
    // Basic CRUD operations are inherited
}

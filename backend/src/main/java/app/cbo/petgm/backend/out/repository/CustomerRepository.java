package app.cbo.petgm.backend.out.repository;

import app.cbo.petgm.backend.out.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Basic CRUD operations are inherited
    // Custom query methods can be added here later if needed
}

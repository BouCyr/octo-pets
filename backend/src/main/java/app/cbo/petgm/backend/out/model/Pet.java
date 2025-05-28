package app.cbo.petgm.backend.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// No model imports needed here as Gender is in the same package after move
// import app.cbo.petgm.backend.model.Gender; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pets")
public class Pet {

    @Id
    private String id;

    private String customerId; // References Customer's ID
    private String speciesName; // User-provided, e.g., "Dog", "Cat", "Parrot"
    private String breedName;   // User-provided, e.g., "Labrador", "Siamese", "Macaw"
    private Gender gender; // Gender is now in app.cbo.petgm.backend.out.model
    private String dateOfBirth; // Approximative, e.g., "2021-07" or "Spring 2020"
    private Double weight;      // Optional
}

package app.cbo.petgm.backend.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    private String name;
    private String phone; // Optional
    private String email; // Optional
    private String address; // Optional

    // If Lombok is not available, explicit getters, setters, 
    // and constructors would be needed here.
}

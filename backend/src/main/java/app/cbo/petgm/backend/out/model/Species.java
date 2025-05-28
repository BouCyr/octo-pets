package app.cbo.petgm.backend.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "species")
public class Species {

    @Id
    private String id;

    private String name; // e.g., "Dog", "Cat"
    private List<String> standardBreeds; // e.g., ["Labrador", "Poodle", "German Shepherd"] for Dog
}

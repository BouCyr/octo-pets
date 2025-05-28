package app.cbo.petgm.backend.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal; // Import BigDecimal
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "prestations")
public class Prestation {

    @Id
    private String id;

    private String technicalId; // User-defined unique ID, e.g., "CUT-DOG-M"
    private String label;       // e.g., "Dog Haircut - Medium"
    private BigDecimal unitPrice;
}

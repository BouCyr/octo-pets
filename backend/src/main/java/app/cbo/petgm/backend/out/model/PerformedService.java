package app.cbo.petgm.backend.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "performed_services")
public class PerformedService {

    @Id
    private String id;

    private LocalDateTime date;
    private String prestationId; // References Prestation's ID
    private String customerId;   // References Customer's ID
    private String petId;        // Optional, references Pet's ID
    private BillingStatus billingStatus;
    private LocalDateTime paymentDate; // Optional
}

package app.cbo.petgm.backend.in.dto;

import app.cbo.petgm.backend.out.model.BillingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformedServiceRequestDto {
    private LocalDateTime date; // Expect ISO format, e.g., "2023-10-26T10:15:30"
    private String prestationId;
    private String customerId;
    private String petId; // Optional
    private BillingStatus billingStatus;
    private LocalDateTime paymentDate; // Optional
}

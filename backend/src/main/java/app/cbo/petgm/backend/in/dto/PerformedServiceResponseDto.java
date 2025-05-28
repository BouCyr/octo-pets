package app.cbo.petgm.backend.in.dto;

import app.cbo.petgm.backend.out.model.BillingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformedServiceResponseDto {
    private String id;
    private LocalDateTime date;
    private String prestationId;
    private String customerId;
    private String petId;
    private BillingStatus billingStatus;
    private LocalDateTime paymentDate;
    
    // Could add DTOs for prestation, customer, pet details later
    // private CustomerResponseDto customer; 
    // private PetResponseDto pet;
    // private PrestationResponseDto prestation;
}

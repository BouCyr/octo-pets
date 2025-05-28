package app.cbo.petgm.backend.in.dto;

import app.cbo.petgm.backend.out.model.Gender; // Or use String and map in controller
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
    private String id;
    private String customerId;
    private String speciesName;
    private String breedName;
    private Gender gender; // Using the enum directly for response DTO
    private String dateOfBirth;
    private Double weight;
}

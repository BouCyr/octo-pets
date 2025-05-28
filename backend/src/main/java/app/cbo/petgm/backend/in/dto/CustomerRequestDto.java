package app.cbo.petgm.backend.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// Add validation annotations later (javax.validation.constraints or jakarta.validation.constraints)
// e.g., import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    // @NotBlank(message = "Name is mandatory") // Example for later
    private String name;
    private String phone;
    private String email; // Add @Email later
    private String address;
}

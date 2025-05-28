package app.cbo.petgm.backend.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestationRequestDto {
    private String technicalId;
    private String label;
    private BigDecimal unitPrice;
}

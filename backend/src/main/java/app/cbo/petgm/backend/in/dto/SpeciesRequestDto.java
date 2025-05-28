package app.cbo.petgm.backend.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesRequestDto {
    private String name;
    private List<String> standardBreeds;
}

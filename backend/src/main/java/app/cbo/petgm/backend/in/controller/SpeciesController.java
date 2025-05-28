package app.cbo.petgm.backend.in.controller;

import app.cbo.petgm.backend.in.dto.SpeciesRequestDto;
import app.cbo.petgm.backend.in.dto.SpeciesResponseDto;
import app.cbo.petgm.backend.middle.service.SpeciesService;
import app.cbo.petgm.backend.out.model.Species; // Entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1") // Base path, specific endpoint on methods or class
public class SpeciesController {

    private final SpeciesService speciesService;

    @Autowired
    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    // --- Mapper Methods ---
    private SpeciesResponseDto convertToDto(Species species) {
        return new SpeciesResponseDto(
            species.getId(),
            species.getName(),
            species.getStandardBreeds()
        );
    }

    private Species convertToEntity(SpeciesRequestDto dto) {
        Species species = new Species();
        species.setName(dto.getName());
        species.setStandardBreeds(dto.getStandardBreeds());
        return species;
    }
    // --- End Mapper Methods ---

    @PostMapping("/species")
    public ResponseEntity<SpeciesResponseDto> createSpecies(/*@Valid*/ @RequestBody SpeciesRequestDto speciesDto) {
        Species species = convertToEntity(speciesDto);
        Species createdSpecies = speciesService.createSpecies(species);
        return new ResponseEntity<>(convertToDto(createdSpecies), HttpStatus.CREATED);
    }

    @GetMapping("/species")
    public ResponseEntity<List<SpeciesResponseDto>> getAllSpecies() {
        List<SpeciesResponseDto> speciesDtos = speciesService.getAllSpecies().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(speciesDtos);
    }
    
    @GetMapping("/public/species-breeds") // Public endpoint
    public ResponseEntity<List<SpeciesResponseDto>> getPublicSpeciesAndBreeds() {
        List<SpeciesResponseDto> speciesDtos = speciesService.getAllSpecies().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(speciesDtos);
    }

    @GetMapping("/species/{id}")
    public ResponseEntity<SpeciesResponseDto> getSpeciesById(@PathVariable String id) {
        Species species = speciesService.getSpeciesById(id);
        return ResponseEntity.ok(convertToDto(species));
    }

    @PutMapping("/species/{id}")
    public ResponseEntity<SpeciesResponseDto> updateSpecies(@PathVariable String id, 
                                                          /*@Valid*/ @RequestBody SpeciesRequestDto speciesDto) {
        Species species = convertToEntity(speciesDto);
        Species updatedSpecies = speciesService.updateSpecies(id, species);
        return ResponseEntity.ok(convertToDto(updatedSpecies));
    }

    @DeleteMapping("/species/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable String id) {
        speciesService.deleteSpecies(id);
        return ResponseEntity.noContent().build();
    }
}

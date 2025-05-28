package app.cbo.petgm.backend.in.controller;

import app.cbo.petgm.backend.in.dto.PrestationRequestDto;
import app.cbo.petgm.backend.in.dto.PrestationResponseDto;
import app.cbo.petgm.backend.middle.service.PrestationService;
import app.cbo.petgm.backend.out.model.Prestation; // Entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/prestations")
public class PrestationController {

    private final PrestationService prestationService;

    @Autowired
    public PrestationController(PrestationService prestationService) {
        this.prestationService = prestationService;
    }

    // --- Mapper Methods ---
    private PrestationResponseDto convertToDto(Prestation prestation) {
        return new PrestationResponseDto(
            prestation.getId(),
            prestation.getTechnicalId(),
            prestation.getLabel(),
            prestation.getUnitPrice()
        );
    }

    private Prestation convertToEntity(PrestationRequestDto dto) {
        Prestation prestation = new Prestation();
        prestation.setTechnicalId(dto.getTechnicalId());
        prestation.setLabel(dto.getLabel());
        prestation.setUnitPrice(dto.getUnitPrice());
        return prestation;
    }
    // --- End Mapper Methods ---

    @PostMapping
    public ResponseEntity<PrestationResponseDto> createPrestation(/*@Valid*/ @RequestBody PrestationRequestDto prestationDto) {
        Prestation prestation = convertToEntity(prestationDto);
        Prestation createdPrestation = prestationService.createPrestation(prestation);
        return new ResponseEntity<>(convertToDto(createdPrestation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PrestationResponseDto>> getAllPrestations() {
        List<PrestationResponseDto> prestationDtos = prestationService.getAllPrestations().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prestationDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestationResponseDto> getPrestationById(@PathVariable String id) {
        Prestation prestation = prestationService.getPrestationById(id);
        return ResponseEntity.ok(convertToDto(prestation));
    }

    @GetMapping("/by-technical-id/{technicalId}")
    public ResponseEntity<PrestationResponseDto> getPrestationByTechnicalId(@PathVariable String technicalId) {
        Prestation prestation = prestationService.getPrestationByTechnicalId(technicalId);
        return ResponseEntity.ok(convertToDto(prestation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestationResponseDto> updatePrestation(@PathVariable String id, 
                                                                /*@Valid*/ @RequestBody PrestationRequestDto prestationDto) {
        Prestation prestation = convertToEntity(prestationDto);
        Prestation updatedPrestation = prestationService.updatePrestation(id, prestation);
        return ResponseEntity.ok(convertToDto(updatedPrestation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestation(@PathVariable String id) {
        prestationService.deletePrestation(id);
        return ResponseEntity.noContent().build();
    }
}

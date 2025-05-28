package app.cbo.petgm.backend.in.controller;

import app.cbo.petgm.backend.in.dto.PerformedServiceRequestDto;
import app.cbo.petgm.backend.in.dto.PerformedServiceResponseDto;
import app.cbo.petgm.backend.middle.service.PerformedServiceService;
import app.cbo.petgm.backend.out.model.PerformedService; // Entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/services")
public class PerformedServiceController {

    private final PerformedServiceService performedServiceService;

    @Autowired
    public PerformedServiceController(PerformedServiceService performedServiceService) {
        this.performedServiceService = performedServiceService;
    }

    // --- Mapper Methods ---
    private PerformedServiceResponseDto convertToDto(PerformedService service) {
        return new PerformedServiceResponseDto(
            service.getId(),
            service.getDate(),
            service.getPrestationId(),
            service.getCustomerId(),
            service.getPetId(),
            service.getBillingStatus(),
            service.getPaymentDate()
        );
    }

    private PerformedService convertToEntity(PerformedServiceRequestDto dto) {
        PerformedService service = new PerformedService();
        service.setDate(dto.getDate());
        service.setPrestationId(dto.getPrestationId());
        service.setCustomerId(dto.getCustomerId());
        service.setPetId(dto.getPetId());
        service.setBillingStatus(dto.getBillingStatus());
        service.setPaymentDate(dto.getPaymentDate());
        return service;
    }
    // --- End Mapper Methods ---

    @PostMapping
    public ResponseEntity<PerformedServiceResponseDto> recordService(/*@Valid*/ @RequestBody PerformedServiceRequestDto serviceDto) {
        PerformedService service = convertToEntity(serviceDto);
        PerformedService recordedService = performedServiceService.recordService(service);
        return new ResponseEntity<>(convertToDto(recordedService), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PerformedServiceResponseDto>> getAllPerformedServices(
            // Example: Add query parameters for filtering later
            // @RequestParam(required = false) String customerId,
            // @RequestParam(required = false) String dateFrom, // ISO Date
            // @RequestParam(required = false) String dateTo,   // ISO Date
            // @RequestParam(required = false) BillingStatus status
    ) {
        List<PerformedServiceResponseDto> serviceDtos = performedServiceService.getAllPerformedServices().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformedServiceResponseDto> getPerformedServiceById(@PathVariable String id) {
        PerformedService service = performedServiceService.getPerformedServiceById(id);
        return ResponseEntity.ok(convertToDto(service));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformedServiceResponseDto> updatePerformedService(@PathVariable String id, 
                                                                           /*@Valid*/ @RequestBody PerformedServiceRequestDto serviceDto) {
        PerformedService service = convertToEntity(serviceDto);
        PerformedService updatedService = performedServiceService.updatePerformedService(id, service);
        return ResponseEntity.ok(convertToDto(updatedService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformedService(@PathVariable String id) {
        performedServiceService.deletePerformedService(id);
        return ResponseEntity.noContent().build();
    }
}

package app.cbo.petgm.backend.in.controller;

import app.cbo.petgm.backend.in.dto.PetRequestDto;
import app.cbo.petgm.backend.in.dto.PetResponseDto;
import app.cbo.petgm.backend.middle.service.PetService;
import app.cbo.petgm.backend.middle.service.CustomerService; // To verify customer
import app.cbo.petgm.backend.out.model.Pet; // Entity
import app.cbo.petgm.backend.out.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Add @Valid for DTO validation later
// e.g., import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1") // Base path
public class PetController {

    private final PetService petService;
    private final CustomerService customerService; // To ensure customer exists

    @Autowired
    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    // --- Mapper Methods ---
    private PetResponseDto convertToDto(Pet pet) {
        return new PetResponseDto(
            pet.getId(),
            pet.getCustomerId(),
            pet.getSpeciesName(),
            pet.getBreedName(),
            pet.getGender(),
            pet.getDateOfBirth(),
            pet.getWeight()
        );
    }

    private Pet convertToEntity(PetRequestDto dto, String customerIdFromPath) {
        Pet pet = new Pet();
        // If customerIdFromPath is provided (e.g. from @PathVariable), it takes precedence.
        // Otherwise, dto.getCustomerId() could be used if the endpoint is not customer-specific.
        pet.setCustomerId(customerIdFromPath != null ? customerIdFromPath : dto.getCustomerId());
        pet.setSpeciesName(dto.getSpeciesName());
        pet.setBreedName(dto.getBreedName());
        pet.setGender(dto.getGender());
        pet.setDateOfBirth(dto.getDateOfBirth());
        pet.setWeight(dto.getWeight());
        return pet;
    }
     private Pet convertToEntity(PetRequestDto dto) { // Overload for contexts where customerId is from DTO
        return convertToEntity(dto, dto.getCustomerId());
    }
    // --- End Mapper Methods ---

    @PostMapping("/customers/{customerId}/pets")
    public ResponseEntity<PetResponseDto> registerPetForCustomer(
            @PathVariable String customerId,
            /*@Valid*/ @RequestBody PetRequestDto petDto) {
        // Ensure customer exists
        customerService.getCustomerById(customerId); // Throws ResourceNotFound if not found
        
        Pet pet = convertToEntity(petDto, customerId); // Pass customerId from path
        Pet registeredPet = petService.registerPet(pet);
        return new ResponseEntity<>(convertToDto(registeredPet), HttpStatus.CREATED);
    }

    @GetMapping("/customers/{customerId}/pets")
    public ResponseEntity<List<PetResponseDto>> getPetsForCustomer(@PathVariable String customerId) {
        // Ensure customer exists
        customerService.getCustomerById(customerId); // Throws ResourceNotFound if not found

        List<PetResponseDto> petDtos = petService.getPetsByCustomerId(customerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(petDtos);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<PetResponseDto> getPetById(@PathVariable String id) {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.ok(convertToDto(pet));
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<PetResponseDto> updatePet(@PathVariable String id, 
                                                    /*@Valid*/ @RequestBody PetRequestDto petDto) {
        // Ensure customerId in petDto is valid if it's allowed to be changed or set.
        if (petDto.getCustomerId() != null) {
             customerService.getCustomerById(petDto.getCustomerId()); 
        }
        Pet pet = convertToEntity(petDto); 
        // The service's updatePet method should fetch the existing pet by 'id'
        // and then update its fields from the 'pet' entity passed.
        Pet updatedPet = petService.updatePet(id, pet); 
        return ResponseEntity.ok(convertToDto(updatedPet));
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable String id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}

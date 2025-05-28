package app.cbo.petgm.backend.middle.service;

import app.cbo.petgm.backend.out.model.Pet;
import app.cbo.petgm.backend.out.repository.PetRepository;
// Assuming Customer existence is validated upstream or not strictly enforced here for now
// import app.cbo.petgm.backend.out.repository.CustomerRepository; 
import app.cbo.petgm.backend.middle.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    // private final CustomerRepository customerRepository; // Optional: for validating customerId

    @Autowired
    public PetService(PetRepository petRepository /*, CustomerRepository customerRepository */) {
        this.petRepository = petRepository;
        // this.customerRepository = customerRepository;
    }

    public Pet registerPet(Pet pet) {
        // Optional: Check if customerRepository.existsById(pet.getCustomerId())
        // For now, assume customerId is valid or handled by controller/DTO validation
        return petRepository.save(pet);
    }

    public List<Pet> getPetsByCustomerId(String customerId) {
        return petRepository.findByCustomerId(customerId);
    }

    public Pet getPetById(String id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + id));
    }
    
    public Optional<Pet> getPetByIdOptional(String id) { // To allow checking existence without throwing
        return petRepository.findById(id);
    }

    public Pet updatePet(String id, Pet petDetails) {
        Pet pet = getPetById(id); // Ensures pet exists

        // Update fields
        pet.setCustomerId(petDetails.getCustomerId()); // Potentially validate this customerId
        pet.setSpeciesName(petDetails.getSpeciesName());
        pet.setBreedName(petDetails.getBreedName());
        pet.setGender(petDetails.getGender());
        pet.setDateOfBirth(petDetails.getDateOfBirth());
        pet.setWeight(petDetails.getWeight());
        // ID remains the same

        return petRepository.save(pet);
    }

    public void deletePet(String id) {
        Pet pet = getPetById(id); // Ensures pet exists
        petRepository.delete(pet);
    }
}

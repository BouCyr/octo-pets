package app.cbo.petgm.backend.middle.service;

import app.cbo.petgm.backend.out.model.Species;
import app.cbo.petgm.backend.out.repository.SpeciesRepository;
import app.cbo.petgm.backend.middle.exception.ResourceNotFoundException;
import app.cbo.petgm.backend.middle.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    @Autowired
    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Species createSpecies(Species species) {
        speciesRepository.findByName(species.getName()).ifPresent(s -> {
            throw new DuplicateResourceException("Species with name '" + species.getName() + "' already exists.");
        });
        return speciesRepository.save(species);
    }

    public List<Species> getAllSpecies() {
        return speciesRepository.findAll();
    }

    public Species getSpeciesById(String id) {
        return speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found with id: " + id));
    }
    
    public Optional<Species> getSpeciesByIdOptional(String id) {
        return speciesRepository.findById(id);
    }

    public Species getSpeciesByName(String name) {
        return speciesRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Species not found with name: " + name));
    }

    public Species updateSpecies(String id, Species speciesDetails) {
        Species species = getSpeciesById(id); // Ensures species exists

        // Check if name is being changed and if the new name already exists for another species
        if (!species.getName().equals(speciesDetails.getName())) {
            speciesRepository.findByName(speciesDetails.getName()).ifPresent(s -> {
                if (!s.getId().equals(id)) { // If it's a different species record
                    throw new DuplicateResourceException("Another species with name '" + speciesDetails.getName() + "' already exists.");
                }
            });
        }
        
        species.setName(speciesDetails.getName());
        species.setStandardBreeds(speciesDetails.getStandardBreeds()); // Overwrites the list

        return speciesRepository.save(species);
    }

    public void deleteSpecies(String id) {
        Species species = getSpeciesById(id); // Ensures species exists
        speciesRepository.delete(species);
    }
}

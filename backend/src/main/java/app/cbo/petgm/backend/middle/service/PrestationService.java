package app.cbo.petgm.backend.middle.service;

import app.cbo.petgm.backend.out.model.Prestation;
import app.cbo.petgm.backend.out.repository.PrestationRepository;
import app.cbo.petgm.backend.middle.exception.ResourceNotFoundException;
import app.cbo.petgm.backend.middle.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrestationService {

    private final PrestationRepository prestationRepository;

    @Autowired
    public PrestationService(PrestationRepository prestationRepository) {
        this.prestationRepository = prestationRepository;
    }

    public Prestation createPrestation(Prestation prestation) {
        prestationRepository.findByTechnicalId(prestation.getTechnicalId()).ifPresent(p -> {
            throw new DuplicateResourceException("Prestation with technicalId '" + prestation.getTechnicalId() + "' already exists.");
        });
        return prestationRepository.save(prestation);
    }

    public List<Prestation> getAllPrestations() {
        return prestationRepository.findAll();
    }

    public Prestation getPrestationById(String id) {
        return prestationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestation not found with id: " + id));
    }
    
    public Optional<Prestation> getPrestationByIdOptional(String id) {
        return prestationRepository.findById(id);
    }

    public Prestation getPrestationByTechnicalId(String technicalId) {
        return prestationRepository.findByTechnicalId(technicalId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestation not found with technicalId: " + technicalId));
    }

    public Prestation updatePrestation(String id, Prestation prestationDetails) {
        Prestation prestation = getPrestationById(id); // Ensures prestation exists

        // Check if technicalId is being changed and if the new technicalId already exists for another prestation
        if (!prestation.getTechnicalId().equals(prestationDetails.getTechnicalId())) {
            prestationRepository.findByTechnicalId(prestationDetails.getTechnicalId()).ifPresent(p -> {
                if (!p.getId().equals(id)) { // If it's a different prestation record
                    throw new DuplicateResourceException("Another prestation with technicalId '" + prestationDetails.getTechnicalId() + "' already exists.");
                }
            });
        }
        
        prestation.setTechnicalId(prestationDetails.getTechnicalId());
        prestation.setLabel(prestationDetails.getLabel());
        prestation.setUnitPrice(prestationDetails.getUnitPrice());

        return prestationRepository.save(prestation);
    }

    public void deletePrestation(String id) {
        Prestation prestation = getPrestationById(id); // Ensures prestation exists
        prestationRepository.delete(prestation);
    }
}

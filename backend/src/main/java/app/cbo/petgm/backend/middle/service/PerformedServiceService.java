package app.cbo.petgm.backend.middle.service;

import app.cbo.petgm.backend.out.model.PerformedService;
import app.cbo.petgm.backend.out.model.BillingStatus; // Required for default status
import app.cbo.petgm.backend.out.repository.PerformedServiceRepository;
import app.cbo.petgm.backend.out.repository.CustomerRepository;
import app.cbo.petgm.backend.out.repository.PetRepository;
import app.cbo.petgm.backend.out.repository.PrestationRepository;
import app.cbo.petgm.backend.middle.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils; // For checking petId

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PerformedServiceService {

    private final PerformedServiceRepository performedServiceRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository; // Optional, only if petId is provided
    private final PrestationRepository prestationRepository;

    @Autowired
    public PerformedServiceService(PerformedServiceRepository performedServiceRepository,
                                   CustomerRepository customerRepository,
                                   PetRepository petRepository,
                                   PrestationRepository prestationRepository) {
        this.performedServiceRepository = performedServiceRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
        this.prestationRepository = prestationRepository;
    }

    public PerformedService recordService(PerformedService performedService) {
        // Validate Customer
        customerRepository.findById(performedService.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + performedService.getCustomerId()));

        // Validate Prestation
        prestationRepository.findById(performedService.getPrestationId())
                .orElseThrow(() -> new ResourceNotFoundException("Prestation not found with id: " + performedService.getPrestationId()));

        // Validate Pet if petId is provided
        if (StringUtils.hasText(performedService.getPetId())) {
            petRepository.findById(performedService.getPetId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id: " + performedService.getPetId()));
        } else {
            performedService.setPetId(null); // Ensure it's null if empty string or not provided
        }

        // Set defaults if not provided
        if (performedService.getDate() == null) {
            performedService.setDate(LocalDateTime.now());
        }
        if (performedService.getBillingStatus() == null) {
            performedService.setBillingStatus(BillingStatus.DUE);
        }

        return performedServiceRepository.save(performedService);
    }

    public List<PerformedService> getAllPerformedServices() {
        // Later, add filtering capabilities (e.g., by customer, date range, status)
        return performedServiceRepository.findAll();
    }

    public PerformedService getPerformedServiceById(String id) {
        return performedServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performed service record not found with id: " + id));
    }
    
    public Optional<PerformedService> getPerformedServiceByIdOptional(String id) {
        return performedServiceRepository.findById(id);
    }

    public PerformedService updatePerformedService(String id, PerformedService serviceDetails) {
        PerformedService existingService = getPerformedServiceById(id); // Ensures record exists

        // Validate Customer, Prestation, Pet if IDs are being changed (more complex logic, for now assume they are not or are valid)
        // Typically, one might not allow changing customerId, petId, or prestationId on an existing service record.
        // Focus on updatable fields like billingStatus, paymentDate.

        existingService.setDate(serviceDetails.getDate()); // Or disallow date changes post-creation
        existingService.setBillingStatus(serviceDetails.getBillingStatus());
        if (serviceDetails.getBillingStatus() == BillingStatus.PAYED && serviceDetails.getPaymentDate() == null) {
            existingService.setPaymentDate(LocalDateTime.now()); // Default payment date if marked PAYED and no date given
        } else if (serviceDetails.getBillingStatus() != BillingStatus.PAYED) {
            existingService.setPaymentDate(null); // Clear payment date if not PAYED
        } else {
            existingService.setPaymentDate(serviceDetails.getPaymentDate());
        }
        
        // Potentially re-validate foreign keys if they are allowed to change
        // existingService.setCustomerId(serviceDetails.getCustomerId());
        // existingService.setPrestationId(serviceDetails.getPrestationId());
        // existingService.setPetId(serviceDetails.getPetId());


        return performedServiceRepository.save(existingService);
    }

    public void deletePerformedService(String id) {
        PerformedService service = getPerformedServiceById(id); // Ensures record exists
        performedServiceRepository.delete(service);
    }
}

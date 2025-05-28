package app.cbo.petgm.backend.middle.service;

import app.cbo.petgm.backend.out.model.Customer;
import app.cbo.petgm.backend.out.repository.CustomerRepository;
import app.cbo.petgm.backend.middle.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        // Later, add validation or DTO mapping here
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByIdOptional(String id) { // Renamed to avoid clash if a direct getCustomerById is added later that throws
        return customerRepository.findById(id);
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer updateCustomer(String id, Customer customerDetails) {
        Customer customer = getCustomerById(id); // Ensures customer exists

        // Update fields - for now, direct update. Consider using a mapper or selective updates.
        customer.setName(customerDetails.getName());
        customer.setPhone(customerDetails.getPhone());
        customer.setEmail(customerDetails.getEmail());
        customer.setAddress(customerDetails.getAddress());
        // ID remains the same

        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        Customer customer = getCustomerById(id); // Ensures customer exists before attempting delete
        customerRepository.delete(customer);
    }
}

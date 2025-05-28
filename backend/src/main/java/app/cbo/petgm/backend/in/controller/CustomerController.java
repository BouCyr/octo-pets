package app.cbo.petgm.backend.in.controller;

import app.cbo.petgm.backend.in.dto.CustomerRequestDto;
import app.cbo.petgm.backend.in.dto.CustomerResponseDto;
import app.cbo.petgm.backend.middle.service.CustomerService;
import app.cbo.petgm.backend.out.model.Customer; // Entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Add @Valid for DTO validation later
// e.g., import jakarta.validation.Valid;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers") // Base path for customer APIs
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // --- Mapper Methods (temporary, consider a dedicated mapper class) ---
    private CustomerResponseDto convertToDto(Customer customer) {
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        return dto;
    }

    private Customer convertToEntity(CustomerRequestDto dto) {
        Customer customer = new Customer();
        // Note: ID is not set from request DTO for creation
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        return customer;
    }
    // --- End Mapper Methods ---

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(/*@Valid*/ @RequestBody CustomerRequestDto customerDto) {
        Customer customer = convertToEntity(customerDto);
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(convertToDto(createdCustomer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customerDtos = customerService.getAllCustomers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id); // Throws ResourceNotFoundException if not found
        return ResponseEntity.ok(convertToDto(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable String id, 
                                                              /*@Valid*/ @RequestBody CustomerRequestDto customerDto) {
        Customer customer = convertToEntity(customerDto);
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(convertToDto(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

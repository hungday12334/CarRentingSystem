package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findByAccountId(Long accountId) {
        return customerRepository.findByAccountId(accountId);
    }
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
    @Override
    public Customer findByCustomerId(Long customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Customer findByIdentityCard(String identityCard) {
        return customerRepository.findByIdentityCard(identityCard);
    }

    @Override
    public Customer findByLicenceNumber(String licenceNumber) {
        return customerRepository.findByLicenceNumber(licenceNumber);
    }

    @Override
    public List<Customer> findByCustomerIdAndCustomerName(String customerId, String customerName) {
        Long id;
        try{
            id = Long.parseLong(customerId);
        }catch(Exception e) {
            id= null;
        }
        String name= customerName;
        if(customerName == null || customerName.isEmpty()) {
            name = null;
        }
        return customerRepository.findByCustomerIdAndCustomerName(id, name);
    }
}

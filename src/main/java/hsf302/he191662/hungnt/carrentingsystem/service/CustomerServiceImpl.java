package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
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
}

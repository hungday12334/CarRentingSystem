package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerService {
    public Customer findByAccountId(Long accountId);
    public void save(Customer customer);
    public Customer findByCustomerId(Long customerId);
    public Customer findByIdentityCard(String identityCard);
    public Customer findByLicenceNumber(String licenceNumber);
}

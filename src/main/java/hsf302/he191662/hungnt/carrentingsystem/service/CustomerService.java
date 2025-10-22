package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;

public interface CustomerService {
    public Customer findByAccountId(Long accountId);
    public void save(Customer customer);
    public Customer findByCustomerId(Long customerId);
}

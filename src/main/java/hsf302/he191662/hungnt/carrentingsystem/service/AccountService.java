package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Account;

public interface AccountService {
    public boolean existsByUsername(String username);
    public Account findByUsername(String username);
    public boolean existsByEmail(String email);
    public void save(Account account);
    public boolean existsByUsernameAndPassword(String username, String password);
    public Account findByCustomerId(Long customerId);
}

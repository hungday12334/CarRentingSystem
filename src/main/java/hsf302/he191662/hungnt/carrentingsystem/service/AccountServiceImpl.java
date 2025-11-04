package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
import hsf302.he191662.hungnt.carrentingsystem.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public boolean existsByUsernameAndPassword(String username, String password) {
        return accountRepository.existsByUsernameAndPassword(username, password);
    }

    @Override
    public Account findByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}

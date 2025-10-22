package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    public boolean existsByUsername(String username);
    public Account findByUsername(String username);
    public boolean existsByEmail(String email);
    public Account findByEmail(String email);
    public boolean existsByUsernameAndPassword(String username, String password);
}

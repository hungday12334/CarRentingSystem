package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("select c from Customer c where c.account.accountId = :accountId")
    public Customer findByAccountId(@Param("accountId") Long accountId);
    public Customer findByCustomerId(Long customerId);

    @Query("select c from Customer c where c.identityCard = :identityCard")
    public Customer findByIdentityCard(@Param("identityCard") String identityCard);

    @Query("select c from Customer c where c.licenceNumber = :licenceNumber")
    public Customer findByLicenceNumber(@Param("licenceNumber") String licenceNumber);
    @Query("select c from Customer c where (:customerName is null or lower(c.customerName) like concat('%', :customerName,'%')) and(:customerId is null or c.customerId = :customerId)")
    public List<Customer> findByCustomerIdAndCustomerName(@Param("customerId") Long customerId,@Param("customerName") String customerName);
}

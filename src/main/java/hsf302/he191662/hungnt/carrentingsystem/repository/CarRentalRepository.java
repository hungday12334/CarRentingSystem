package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRentalRepository extends JpaRepository<CarRental,Long> {
    @Query("select c from CarRental c where c.customer.customerId = :customerId")
    public List<CarRental> findByUserId(@Param("customerId")Long customerId);
}

package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRentalRepository extends JpaRepository<CarRental,Long> {
    @Query("select c from CarRental c where c.customer.customerId = :customerId")
    public List<CarRental> findByUserId(@Param("customerId")Long customerId);

    @Query("""
            Select  c from CarRental c 
            where
            (:status is null or Lower(c.status) = lower(:status))
         and(c.customer.customerId = :customerId)   
         and(:rentalDateFrom is null or c.pickupDate = :rentalDateFrom)
         and(:rentalDateTo is null or c.returnDate = :rentalDateTo)
         and(:periodDay is null or c.periodDay = :periodDay)
         and(c.rentPrice >= :rentPriceMin)
         and(c.rentPrice <= :rentPriceMax)
            
""")
    public List<CarRental> filter(@Param("status") String status, @Param("customerId") Long customerId, @Param("rentalDateFrom") LocalDate rentalDateFrom, @Param("rentalDateTo") LocalDate rentalDateTo,@Param("periodDay") Integer periodDay, @Param("rentPriceMin") Double rentPriceMin, @Param("rentPriceMax") Double rentPriceMax);

    List<CarRental> findByStatus(String status);
}

package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review r where r.car.carId = :carId")
    public List<Review> findByCarId(@Param("carId") Long carId);
}

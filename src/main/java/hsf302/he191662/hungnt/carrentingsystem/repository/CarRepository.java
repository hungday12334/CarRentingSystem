package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    public List<Car> findTop5ByOrderByRentedDesc();

    @Query("SELECT DISTINCT c.color FROM Car c")
    public List<String> findDistinctByColor();

    //filter
    @Query("""
        SELECT c FROM Car c
        WHERE (:name IS NULL OR LOWER(c.carName) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:color IS NULL OR LOWER(c.color) = LOWER(:color))
          AND (:status IS NULL OR LOWER(c.status) = LOWER(:status))
          AND (:producerId IS NULL OR c.producer.producerId = :producerId)
          AND (:year IS NULL OR c.carModelYear = :year)
          AND (:priceFrom IS NULL OR c.rentPrice >= :priceFrom)
          AND (:priceTo IS NULL OR c.rentPrice <= :priceTo)
    """)
    List<Car> filter(
            @Param("name") String name,
            @Param("color") String color,
            @Param("status") String status,
            @Param("producerId") Long producerId,
            @Param("year") Integer year,
            @Param("priceFrom") Double priceFrom,
            @Param("priceTo") Double priceTo
    );

}

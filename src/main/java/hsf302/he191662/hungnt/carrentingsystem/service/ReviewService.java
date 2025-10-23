package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.dto.ReviewDTO;
import hsf302.he191662.hungnt.carrentingsystem.entity.Review;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewService {
    public List<ReviewDTO> findByCarId(Long carId);
    public void save(Review review);
    public Review findById(Long id);
    public void deleteById(Long id);
    public List<Review> findByCarIdAndStatus(@Param("carId") Long carId, @Param("status") String status);
    public List<Review> findByCarIdAndStatusAndUsername(@Param("carId") Long carId, @Param("status") String status, @Param("username") String username);
}

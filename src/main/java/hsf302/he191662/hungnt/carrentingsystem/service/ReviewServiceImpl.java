package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.dto.ReviewDTO;
import hsf302.he191662.hungnt.carrentingsystem.entity.Review;
import hsf302.he191662.hungnt.carrentingsystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CustomerService customerService;
    @Override
    public List<ReviewDTO> findByCarId(Long carId) {
        List<Review> reviews = reviewRepository.findByCarId(carId);
        List<ReviewDTO> reviewList = new ArrayList<>();
        for(Review review: reviews) {
            Long customerId= review.getCustomer().getCustomerId();
            Long carIdR= review.getCar().getCarId();
            String nameCus= customerService.findByCustomerId(customerId).getCustomerName();
            reviewList.add(new ReviewDTO(review.getReviewId(),customerId,nameCus,carIdR, review.getComment(), review.getReviewStar()));
        }
        return reviewList;
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> findByCarIdAndStatus(Long carId, String status) {
        return List.of();
    }

    @Override
    public List<Review> findByCarIdAndStatusAndUsername(Long carId, String status, String username) {
        return List.of();
    }

}

package hsf302.he191662.hungnt.carrentingsystem.dto;
public class ReviewDTO {
    private Long reviewId;
    private Long customerId;
    private String customerName;
    private Long carId;
    private String content;
    private int rating;

    public ReviewDTO() {
    }

    public ReviewDTO(Long reviewId, Long customerId, String customerName, Long carId, String content, int rating) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.carId = carId;
        this.content = content;
        this.rating = rating;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", carId=" + carId +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                '}';
    }
}

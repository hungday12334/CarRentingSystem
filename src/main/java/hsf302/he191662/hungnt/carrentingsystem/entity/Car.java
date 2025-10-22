package hsf302.he191662.hungnt.carrentingsystem.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "car")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "car_name", nullable = false)
    private String carName;

    @Column(name = "car_model_year", nullable = false)
    private Integer carModelYear;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "import_date", nullable = false)
    private LocalDate importDate;

    @ManyToOne
    @JoinColumn(name = "producer_id", referencedColumnName = "producer_id", nullable = false)
    private CarProducer producer;

    @Column(name = "rent_price", nullable = false)
    private Double rentPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarRental> rentals;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Car() {
    }

    public Car(String carName, Integer carModelYear, String color, Integer capacity, String description, LocalDate importDate, CarProducer producer, Double rentPrice, String status) {
        this.carName = carName;
        this.carModelYear = carModelYear;
        this.color = color;
        this.capacity = capacity;
        this.description = description;
        this.importDate = importDate;
        this.producer = producer;
        this.rentPrice = rentPrice;
        this.status = status;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Integer getCarModelYear() {
        return carModelYear;
    }

    public void setCarModelYear(Integer carModelYear) {
        this.carModelYear = carModelYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public CarProducer getProducer() {
        return producer;
    }

    public void setProducer(CarProducer producer) {
        this.producer = producer;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CarRental> getRentals() {
        return rentals;
    }

    public void setRentals(List<CarRental> rentals) {
        this.rentals = rentals;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

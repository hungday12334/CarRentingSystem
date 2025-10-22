package hsf302.he191662.hungnt.carrentingsystem.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Auto; tự chọn tùy vào database
    //Identity: dùng trong sql sever, my sql: làm cho id tự tăng
    //Sequence: dùng trong no sql
    //table: dùng 1 bangr riêng để lưu id.
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "identity_card", length = 12, unique = true)
    private String identityCard;

    @Column(name = "licence_number", length = 12, unique = true)
    private String licenceNumber;

    @Column(name = "licence_date")
    private LocalDate licenceDate;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Double balance;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CarRental> rentals;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Review> reviews;


    public Customer() {
    }

    public Customer(String customerName, String mobile, LocalDate birthday, String identityCard, String licenceNumber, LocalDate licenceDate, String email, String password, Double balance) {
        this.customerName = customerName;
        this.mobile = mobile;
        this.birthday = birthday;
        this.identityCard = identityCard;
        this.licenceNumber = licenceNumber;
        this.licenceDate = licenceDate;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public Customer(String customerName, String email, String password, Double balance) {
        this.customerName = customerName;
        this.email = email;
        this.password = password;
        this.balance = balance;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public LocalDate getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(LocalDate licenceDate) {
        this.licenceDate = licenceDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

package hsf302.he191662.hungnt.carrentingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"hsf302.he191662.hungnt.carrentingsystem.controller","hsf302.he191662.hungnt.carrentingsystem.service"})
@EnableJpaRepositories(basePackages = {"hsf302.he191662.hungnt.carrentingsystem.repository"})
@EntityScan(basePackages = {"hsf302.he191662.hungnt.carrentingsystem.entity"})

public class CarRentingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentingSystemApplication.class, args);
    }

}

package hsf302.he191662.hungnt.carrentingsystem.repository;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarProducer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarProducerRepository extends JpaRepository<CarProducer,Long> {
    public CarProducer findByProducerId(Long producerId);
}

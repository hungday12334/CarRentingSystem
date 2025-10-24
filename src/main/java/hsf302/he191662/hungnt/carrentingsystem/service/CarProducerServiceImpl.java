package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarProducer;
import hsf302.he191662.hungnt.carrentingsystem.repository.CarProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarProducerServiceImpl implements CarProducerService{
    @Autowired
    CarProducerRepository carProducerRepository;

    @Override
    public List<CarProducer> findAll() {
        return carProducerRepository.findAll();
    }

    @Override
    public CarProducer findByProducerId(Long producerId) {
        return carProducerRepository.findByProducerId(producerId);
    }
}

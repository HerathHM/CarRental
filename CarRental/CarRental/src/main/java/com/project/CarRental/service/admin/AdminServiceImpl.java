package com.project.CarRental.service.admin;

import com.project.CarRental.dto.BookACarDto;
import com.project.CarRental.dto.CarDto;
import com.project.CarRental.entity.BookACar;
import com.project.CarRental.entity.Car;
import com.project.CarRental.enums.BookCarStatus;
import com.project.CarRental.repository.BookACarRepository;
import com.project.CarRental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final CarRepository carRepository;

    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public boolean updateCar(Long carId, CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if(optionalCar.isPresent()){
            Car existingCar = optionalCar.get();
            if(carDto.getImage() != null)
                existingCar.setImage(carDto.getImage().getBytes());
            existingCar.setPrice(carDto.getPrice());
            existingCar.setYear(carDto.getYear());
            existingCar.setType(carDto.getType());
            existingCar.setDescription(carDto.getDescription());
            existingCar.setTransmission(carDto.getTransmission());
            existingCar.setColour(carDto.getColour());
            existingCar.setName(carDto.getName());
            existingCar.setBrand(carDto.getBrand());
            carRepository.save(existingCar);
            return true;
        }else {

            return false;
        }
    }

    @Override
    public List<BookACarDto> getBookings() {
        return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingId);
        if (optionalBookACar.isPresent()){
            BookACar existingBookACar = optionalBookACar.get();
            if(Objects.equals(status, "Approve"))
                existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
            else
                existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);
            bookACarRepository.save(existingBookACar);
        }
        return false;
    }

    @Override
    public boolean postCar(CarDto carDto) throws IOException {
     try {
         Car car = new Car();
         car.setName((carDto.getName()));
         car.setBrand(carDto.getBrand());
         car.setColour(carDto.getColour());
         car.setPrice(carDto.getPrice());
         car.setYear(carDto.getYear());
         car.setType(carDto.getType());
         car.setDescription(carDto.getDescription());
         car.setTransmission(carDto.getTransmission());
         car.setImage(carDto.getImage().getBytes());
         carRepository.save(car);
         return true;
     }catch (Exception e) {
         return false;
     }


    }
}

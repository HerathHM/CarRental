package com.project.CarRental.service.customer;

import com.project.CarRental.dto.BookACarDto;
import com.project.CarRental.dto.CarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookACarDto bookACarDto);

    CarDto getCarById(Long carId);

    List<BookACarDto> getBookingsByUserId(Long userId);
}

package com.project.CarRental.repository;

import com.project.CarRental.dto.BookACarDto;
import com.project.CarRental.entity.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar, Long> {


    List<BookACar> findAllByUserId(Long userId);
}

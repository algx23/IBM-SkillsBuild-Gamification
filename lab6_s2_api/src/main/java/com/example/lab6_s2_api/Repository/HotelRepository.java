package com.example.lab6_s2_api.Repository;

import com.example.lab6_s2_api.Domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    //JPA already handles 'find By' methods
}

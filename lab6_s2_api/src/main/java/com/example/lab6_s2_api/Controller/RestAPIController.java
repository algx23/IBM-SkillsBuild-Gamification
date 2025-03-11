package com.example.lab6_s2_api.Controller;

import com.example.lab6_s2_api.Domain.Hotel;
import com.example.lab6_s2_api.ErrorInfo;
import com.example.lab6_s2_api.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestAPIController {
    @Autowired
    HotelRepository repo;

    //Retrive single Hotel by ID
    @GetMapping("/hotels/{id}")
    public ResponseEntity<?> getHotel(@PathVariable("id") int id) {
        Hotel hotel = repo.findById(id).orElse(null);
        if (hotel == null) {
            return new ResponseEntity(new ErrorInfo("Hotel with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
    }

    //Retrieve all Hotels
    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> listAllHotels() {
        List<Hotel> hotels = repo.findAll();
        if (hotels.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
    }

    //Create a hotel
    @PostMapping("/hotels")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel, UriComponentsBuilder ucBuilder)
    {
        if (repo.existsById(hotel.getId())) {
            return new ResponseEntity(
                    new ErrorInfo("A hotel named " + hotel.getName() + " already exists."), HttpStatus.CONFLICT);
        }
        repo.save(hotel);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/hotels/{id}").buildAndExpand(hotel.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //Delete hotel by ID
    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") int id) {
        if (! repo.existsById(id)) {
            return new ResponseEntity(new ErrorInfo("Hotel with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        repo.deleteById(id);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    //Delete all hotels
    @DeleteMapping("/hotels")
    public ResponseEntity<?> deleteHotels(@RequestBody List<Hotel> hotels) {
        for (Hotel hotel : hotels) {
            repo.delete(hotel);
        }
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}

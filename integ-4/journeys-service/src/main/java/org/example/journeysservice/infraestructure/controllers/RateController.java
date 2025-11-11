package org.example.journeysservice.infraestructure.controllers;

import org.example.journeysservice.application.services.RateService;
import org.example.journeysservice.domain.dto.RateDTO;
import org.example.journeysservice.domain.entities.Rate;
import org.example.journeysservice.domain.exceptions.RateNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("")
    public ResponseEntity<List<RateDTO>> getAllRates() {
        List<RateDTO> rates = rateService.getAll();
        if (rates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRateById(@PathVariable Long id) {
        try {
            RateDTO rate = rateService.getRateById(id);
            return ResponseEntity.ok(rate);
        } catch (RateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<RateDTO> save(@RequestBody Rate rate) {
        RateDTO newRate = rateService.save(rate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Rate rate) {
        try {
            RateDTO updatedRate = rateService.update(id, rate);
            return ResponseEntity.ok(updatedRate);
        } catch (RateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            rateService.delete(id);
            return ResponseEntity.ok("Rate with ID " + id + " was deleted successfully.");
        } catch (RateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentRate() {
        try {
            RateDTO rate = rateService.getCurrentRate();
            return ResponseEntity.ok(rate);
        } catch (RateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> getRateByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        try {
            RateDTO rate = rateService.getRateByDate(date);
            return ResponseEntity.ok(rate);
        } catch (RateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


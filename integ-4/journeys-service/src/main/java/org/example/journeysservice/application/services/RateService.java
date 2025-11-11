package org.example.journeysservice.application.services;

import jakarta.transaction.Transactional;
import org.example.journeysservice.application.repositories.RateRepository;

import org.example.journeysservice.domain.dto.RateDto;
import org.example.journeysservice.domain.entities.Rate;
import org.example.journeysservice.domain.exceptions.RateNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {

    private final RateRepository rateRepository;

    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Transactional
    public List<RateDto> getAll() {
        return rateRepository.findAll().stream().map(RateDto::new).collect(Collectors.toList());
    }

    @Transactional
    public RateDto getRateById(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(id));
        return new RateDto(rate);
    }

    @Transactional
    public RateDto save(Rate rate) {
        Rate savedRate = rateRepository.save(rate);
        return new RateDto(savedRate);
    }

    @Transactional
    public RateDto update(Long id, Rate rateDetails) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(id));

        rate.setInit_date(rateDetails.getInit_date());
        rate.setFinish_date(rateDetails.getFinish_date());
        rate.setPrice(rateDetails.getPrice());

        Rate updatedRate = rateRepository.save(rate);
        return new RateDto(updatedRate);
    }

    @Transactional
    public void delete(Long id) {
        if (!rateRepository.existsById(id)) {
            throw new RateNotFoundException(id);
        }
        rateRepository.deleteById(id);
    }

    @Transactional
    public RateDto getCurrentRate() {
        return getRateByDate(LocalDateTime.now());
    }

    @Transactional
    public RateDto getRateByDate(LocalDateTime date) {
        Rate rate = rateRepository.findActiveRateForDate(date)
                .orElseThrow(() -> new RateNotFoundException("No active rate found for date: " + date));
        return new RateDto(rate);
    }
}


package org.example.journeysservice.application.services;

import jakarta.transaction.Transactional;
import org.example.journeysservice.application.repositories.RateRepository;
import org.example.journeysservice.domain.dto.RateDTO;
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
    public List<RateDTO> getAll() {
        return rateRepository.findAll().stream().map(RateDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public RateDTO getRateById(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(id));
        return new RateDTO(rate);
    }

    @Transactional
    public RateDTO save(Rate rate) {
        Rate savedRate = rateRepository.save(rate);
        return new RateDTO(savedRate);
    }

    @Transactional
    public RateDTO update(Long id, Rate rateDetails) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(id));

        rate.setInit_date(rateDetails.getInit_date());
        rate.setFinish_date(rateDetails.getFinish_date());
        rate.setPrice(rateDetails.getPrice());

        Rate updatedRate = rateRepository.save(rate);
        return new RateDTO(updatedRate);
    }

    @Transactional
    public void delete(Long id) {
        if (!rateRepository.existsById(id)) {
            throw new RateNotFoundException(id);
        }
        rateRepository.deleteById(id);
    }

    @Transactional
    public RateDTO getCurrentRate() {
        return getRateByDate(LocalDateTime.now());
    }

    @Transactional
    public RateDTO getRateByDate(LocalDateTime date) {
        Rate rate = rateRepository.findActiveRateForDate(date)
                .orElseThrow(() -> new RateNotFoundException("No active rate found for date: " + date));
        return new RateDTO(rate);
    }
}


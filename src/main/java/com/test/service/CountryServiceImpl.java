package com.test.service;

import com.test.dao.CountryRepository;
import com.test.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService{
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country findByName(String name) {
        return countryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Can't find country by name"));
    }
}

package uni.travelguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.travelguide.model.Country;
import uni.travelguide.repository.CountryRepository;

import java.util.List;

@Service("countryService")
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(long id) {
        return countryRepository.findById(id).get();
    }


}

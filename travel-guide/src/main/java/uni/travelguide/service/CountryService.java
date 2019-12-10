package uni.travelguide.service;

import uni.travelguide.model.Country;
import java.util.List;

public interface CountryService {

    List<Country> findAll();

    Country findById(long id);
}

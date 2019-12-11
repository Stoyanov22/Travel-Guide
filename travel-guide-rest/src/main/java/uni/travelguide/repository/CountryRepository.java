package uni.travelguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.travelguide.model.Country;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<Country, Long> {

}

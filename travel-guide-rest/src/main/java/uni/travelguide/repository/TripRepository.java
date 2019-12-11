package uni.travelguide.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uni.travelguide.model.Trip;

@Repository("tripRepository")
public interface TripRepository extends JpaRepository<Trip, Integer> {

}

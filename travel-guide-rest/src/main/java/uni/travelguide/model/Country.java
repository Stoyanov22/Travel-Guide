package uni.travelguide.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COUNTRY")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Trip> trips = new ArrayList<>();

    public String getName() {
        return name;
    }


    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}

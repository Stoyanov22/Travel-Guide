package uni.travelguide.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "trip")
//@SqlResultSetMapping(name="TripsByUserIdMapping", classes = {
//        @ConstructorResult(targetClass = Trip.class,
//                columns = {@ColumnResult(name="id"), @ColumnResult(name="name"), @ColumnResult(name="country_id"), @ColumnResult(name="user_id")})
//})
public class Trip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return name + " | " + country.getName();
    }

    public void setId(int id) {
        this.id = id;
    }
}

package uni.travelguide.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }
}

package net.loove.profiles.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@Entity
@AllArgsConstructor
public class Profile implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String bio;

    @Range(min = 18, max = 99)
    private int age;

    @Enumerated
    private Gender gender;

    @Enumerated
    private Objective objective;

    @Enumerated
    @ElementCollection
    private Set<Gender> preferences;

    @Enumerated
    @ElementCollection
    private Set<Interest> interests;

    public Profile() {
        this.name = "";
        this.bio = "";
        this.age = 18;
        this.gender = Gender.MALE;
        this.objective = Objective.RELATIONSHIP;
        this.preferences = new HashSet<>();
        this.interests = new HashSet<>();
    }

    public enum Objective {
        HOOKUP,
        RELATIONSHIP,
        FRIENDSHIP,
        OTHER
    }

    public enum Gender {
        FEMALE,
        MALE,
        OTHER
    }

    public enum Interest {
        SPORTS,
        MUSIC,
        MOVIES,
        BOOKS,
        TRAVEL,
        FOOD,
        GAMES,
        ART,
        SCIENCE,
        TECHNOLOGY,
        OTHER
    }

}

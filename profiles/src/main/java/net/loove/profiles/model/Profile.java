package net.loove.profiles.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    private String bio;

    private String name;

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

    public enum Objective {
        FRIENDS,
        DATING,
        RELATIONSHIP,
        HOOKUP,
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

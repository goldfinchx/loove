package net.loove.profiles.repository;

import net.loove.profiles.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilesRepository extends JpaRepository<Profile, Long> { }

package net.loove.auth;

import java.util.Optional;
import net.loove.auth.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUsersRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
    Optional<AuthUser> findByNickName(String nickName);

    void deleteByUsername(String username);

    Optional<AuthUser> findByPassword(String password);
}

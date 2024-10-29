package net.loove.chats.repository;

import java.util.Collection;
import java.util.Optional;
import net.loove.chats.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatsRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE :user1 MEMBER OF c.users AND :user2 MEMBER OF c.users")
    Optional<Chat> findByUsers(Long user1, Long user2);

    @Query("SELECT c FROM Chat c WHERE :userId MEMBER OF c.users")
    Collection<Chat> findByUser(Long userId);
}

package ru.practicum.ewm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE user_id IN (?1) ORDER BY user_id DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<User> getByIds(List<Long> ownerId, Integer size, Integer from);

}
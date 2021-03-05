package com.railways.booking.repository;

import com.railways.booking.entity.Sessions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Sessions,String> {
//    @Query(value = "update sessions set is_logged_in = ?1 where user_name = ?2",nativeQuery = true)
//    void updateSessionState(String login,String username);

    @Modifying
    @Query(value = "delete from sessions where user_name = ?1",nativeQuery = true)
    void deleteSession(String uname);
}

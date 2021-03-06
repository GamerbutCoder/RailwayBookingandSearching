package com.railways.booking.repository;

import com.railways.booking.entity.Sessions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Sessions,String> {
//    @Query(value = "update sessions set is_logged_in = ?1 where user_name = ?2",nativeQuery = true)
//    void updateSessionState(String login,String username);
    @Query(value = "select is_logged_in from sessions where sessionid = ?1",nativeQuery = true )
    String findbysessionid(String sessionid);

    @Modifying
    @Query(value = "delete from sessions where sessionid = ?1",nativeQuery = true)
    void deleteSession(String sessionid);

    @Query(value = "Select user_name from sessions where sessionid=?1",nativeQuery = true)
    String findUserNameBySessionId(String sessionid);
}

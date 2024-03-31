package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface UserDao {
    // Data Access Object
    Optional<User> findById(long id);
    User create(String username, String name,String password ,String surname, String email, String telephone, Boolean isProvider);
    void changeEmail(long userid,String value);
    void changeUsername(long userid,String value);

}

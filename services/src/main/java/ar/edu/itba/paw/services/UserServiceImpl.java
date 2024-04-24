package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    @Override
    public User create(final String username,final String name, final String surname, final String password, final String email, final String telephone) {
        User user = userDao.findByEmail(email).orElse(null);
        if (user!= null){
            throw new IllegalArgumentException("User already exists");
        }
        return userDao.create(username,name,surname, passwordEncoder.encode(password), email, telephone,false);
    }

    @Override
    public void changeUsername(long userid,String value){
        userDao.changeUsername(userid,value);
    }
    @Override
    public void changeEmail(long userid,String value){
        userDao.changeEmail(userid,value);
    }

    public List<User> getUsers(List<Long> ids) {
        List<User> users = new ArrayList<>();
        for (long id : ids) {
            if (findById(id).isPresent()) {
                users.add(findById(id).get());
            }
            else users.add(null);
        }
        return users;
    }

    @Override
    public void changePassword(String email,String password){
        userDao.changePassword(email,passwordEncoder.encode(password));
    }

    @Override
    public void changeUserType(long userid){
        userDao.changeUserType(userid);
    }
}
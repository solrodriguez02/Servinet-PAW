package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
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
    public User create(final String username, final String password ,final String name, final String surname, final String email, final String telephone) {
        return userDao.create(username,name, password, surname, email, telephone,false);
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

}
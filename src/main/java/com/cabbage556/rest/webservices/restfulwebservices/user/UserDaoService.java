package com.cabbage556.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {

    private static int usersCount = 0;
    private static List<User> users = new ArrayList<>();
    static {
        users.add(new User(++usersCount, "Kim", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Tak", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Choi", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll() {
        return UserDaoService.users;
    }

    public User findOne(int id) {
        return UserDaoService.users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        user.setId(++UserDaoService.usersCount);
        UserDaoService.users.add(user);
        return user;
    }

    public void deleteOneById(int id) {
        UserDaoService.users
                .removeIf(user -> user.getId().equals(id));
    }
}

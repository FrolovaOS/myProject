package org.example.db;

import org.example.User;

import java.util.List;

public interface UserDao {
    List<User> loadAllUser();

}

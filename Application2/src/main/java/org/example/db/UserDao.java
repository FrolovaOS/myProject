package org.example.db;

import org.example.Counter;
import org.example.User;

import java.util.List;

public interface UserDao {
    void insert(User user,Integer id);
    void insertStatics(Counter count);

}

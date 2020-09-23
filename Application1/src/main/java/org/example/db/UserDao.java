package org.example.db;

import org.example.User;


import java.util.concurrent.ConcurrentMap;

public interface UserDao {
    ConcurrentMap<Integer,User> loadAllUser();

}

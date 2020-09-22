package org.example.db;

import org.example.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao{

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO info " +
                "(id,firstName, lastName ,age,role) VALUES ( ?, ?, ?, ?,?)";
        getJdbcTemplate().update(sql, new Object[]{
                user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getRole()
        });
    }

    public void insertStatics(User user) {
        String sql = "INSERT INTO statics " +
                "(idU,count,timestamp) VALUES ( ?, ?, ?)" ;
        getJdbcTemplate().update(sql, new Object[]{
                user.getId(),user.getCount(),user.getTimestamp()
        });
    }
}

package org.example.db;

import org.example.Counter;
import org.example.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao{

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    @Override
    public void insert(User user,Integer id) {
        String sql = "INSERT INTO info " +
                "(id,firstName, lastName ,age,role) VALUES ( ?, ?, ?, ?,?)";
        getJdbcTemplate().update(sql, new Object[]{
                id, user.getFirstName(), user.getLastName(), user.getAge(), user.getRole()
        });
    }

    public void insertStatics(Counter count) {
        String sql = "INSERT INTO statics " +
                "(idU,count,startinterval) VALUES ( ?, ?, ?)" ;
        getJdbcTemplate().update(sql, new Object[]{
                count.getId(),count.getCount(),count.getStartinterval()
        });
    }
}

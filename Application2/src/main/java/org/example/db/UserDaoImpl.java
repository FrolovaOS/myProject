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
    @Override
    public List<User> loadAllCustomer() {
        String sql = "SELECT * FROM info";

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<User> result = new ArrayList<User>();
        for(Map<String, Object> row:rows){
            User user = new User();
            user.setId((Integer)row.get("id"));
            user.setFirstName((String)row.get("firstName"));
            user .setLastName((String) row.get("lastName"));
            user.setAge((Integer)row.get("age"));
            user.setRole((String)row.get("role"));
            result.add(user );
        }

        return result;
    }
}

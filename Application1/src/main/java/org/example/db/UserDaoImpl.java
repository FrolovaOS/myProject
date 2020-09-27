package org.example.db;

import org.example.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao{

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }


    @Override
    public ConcurrentMap<Integer,User> loadAllUser() {
        String sql = "SELECT * FROM info";

        ConcurrentMap<Integer,User> users =new ConcurrentHashMap<>(1);//убрала статик перепроверить

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        for(Map<String, Object> row:rows){
            User user = new User((String)row.get("firstName"),(String)row.get("lastName"),(Integer)row.get("age"),(String)row.get("role"));
            users.put((Integer)row.get("id"),user);
        }

        return users;
    }
}

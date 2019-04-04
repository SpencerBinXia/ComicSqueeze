package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProfileRepo {
    @Autowired
    JdbcTemplate jdbc;

    //Might need to change the table name, depending on the table name for user
    public User findByName(String username)
    {
        String findUser ="SELECT * FROM user WHERE user_name='" + username + "';";
        User tempuser = new User();
        try
        {
            jdbc.queryForObject(findUser, new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempuser.setUsername(rs.getString("user_name"));
                    return tempuser;
                }
            });
        }
        catch (Exception e)
        {
            return null;
        }
        return tempuser;
    }

    //Might need to change the table name, depending on the table name for user
    public void regUser(User newUser)
    {
        jdbc.update("INSERT INTO user(user_name)" + "VALUES(?,?)", newUser.getUsername());

    }


}

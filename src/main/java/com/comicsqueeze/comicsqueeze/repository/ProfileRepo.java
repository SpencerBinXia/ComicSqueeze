package com.comicsqueeze.comicsqueeze.repository;

import com.comicsqueeze.comicsqueeze.object.Member;

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
    public Member findByName(String username)
    {
        System.out.println("findbyName repo reached");
        String findMember ="SELECT * FROM \"Member\" WHERE username='" + username + "';";
        Member tempuser = new Member();
        try
        {
            jdbc.queryForObject(findMember, new RowMapper<Member>() {
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempuser.setUsername(rs.getString("username"));
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
    public void regMember(Member newMember)
    {
        System.out.println("regMember reached");
        jdbc.update("INSERT INTO \"Member\"(username, email, admin, bio, imgurl)" + "VALUES(?,?,?,?,?)", newMember.getUsername(), newMember.getEmail(), false, "", "");

    }
    public void setMemberBio(Member member)
    {
        jdbc.update("UPDATE MEMBER WHERE username = " + member.getUsername() + "SET bio = " + member.getBio());
        System.out.println("Updated User's Bio in DB");
    }

}

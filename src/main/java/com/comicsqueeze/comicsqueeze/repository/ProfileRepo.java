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
        System.out.println("namerepo" + username);
        String findMember ="SELECT * FROM \"Member\" WHERE username='" + username + "';";
        Member tempuser = new Member();
        try
        {
            jdbc.queryForObject(findMember, new RowMapper<Member>() {
                public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                    tempuser.setUsername(rs.getString("username"));
                    tempuser.setBio(rs.getString("bio"));
                    tempuser.setAdminStatus(rs.getBoolean("admin"));
                    tempuser.setEmail(rs.getString("email"));
                    tempuser.setImgUrl(rs.getString("imgurl"));
                    tempuser.setCreatedWeekly(rs.getBoolean("createdweekly"));
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
        jdbc.update("INSERT INTO \"Member\"(username, email, admin, bio, imgurl)" + "VALUES(?,?,?,?,?)", newMember.getUsername(), newMember.getEmail(), false,
                "Bio - Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut laboreet dolore magna aliqua. Ut enim ad minim veniam, quis nostrud", "images/icons/default_pro_icon.png");
    }
    public void setMemberBio(Member member, String bio)
    {
        jdbc.update("UPDATE \"Member\" SET bio = '" + bio +"' WHERE username = '" + member.getUsername() + "';");
        System.out.println("Updated User's Bio in DB");
    }
    public void setImgUrl(Member member, String url){
        jdbc.update("UPDATE \"Member\" SET imgurl = '" + url+"' WHERE username = '" + member.getUsername() + "';");
        System.out.println("Updated User's img in DB");
    }


}

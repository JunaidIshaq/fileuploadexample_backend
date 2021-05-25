package com.practice.fileuploadexample.dao;

import com.practice.fileuploadexample.model.UserDetail;

import java.util.List;

public interface UserDAO {

    public int saveUser(UserDetail userDetail);

    public UserDetail getUserDetail(int userId);

    public int updateProfileImage(String profileImage , int userID);

    List<UserDetail> getUsers();
}

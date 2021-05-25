package com.practice.fileuploadexample.service;

import com.practice.fileuploadexample.model.UserDetail;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    int saveUser(UserDetail userDetail);

    List<UserDetail> getUsers();

    UserDetail getUserDetail(int userId);

    int store(MultipartFile file, int userID, HttpSession session);

}

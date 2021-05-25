package com.practice.fileuploadexample.service;

import com.practice.fileuploadexample.dao.UserDAO;
import com.practice.fileuploadexample.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public int saveUser(UserDetail userDetail) {
        return userDAO.saveUser(userDetail);
    }

    @Transactional
    public List<UserDetail> getUsers() {
        return userDAO.getUsers();
    }

    @Transactional
    public UserDetail getUserDetail(int userId) {
        return userDAO.getUserDetail(userId);
    }

    @Transactional
    public int store(MultipartFile file, int userID, HttpSession session) {

        Path rootLocation = Paths.get(session.getServletContext().getRealPath("/resources/images"));

        System.out.println("rootLocation  ==  " + rootLocation);

        UserDetail userDetail = this.getUserDetail(userID);

        String nameExtension[] = file.getContentType().split("/");

        String profileImage = userID + "." + nameExtension[1];

        System.out.println("ProfileImage  :: " + profileImage);

        if(userDetail.getUserId() > 0 )
        {

            if(userDetail.getProfileImage() == null || userDetail.getProfileImage() == " " || userDetail.getProfileImage() == "" )
            {
                try
                {
                    Files.copy(file.getInputStream(),rootLocation.resolve(profileImage));
                    int result = userDAO.updateProfileImage(profileImage, userID);
                    if(result > 0)
                        return result;
                    else
                        return -5;
                }
                catch(Exception exception)
                {
                    System.out.println("error while uploading image catch:: " + exception.getMessage());
                    return -5;
                }
            }
            else
            {
                try
                {
                    //Files.delete(rootLocation.resolve(profileImage));

                    Files.delete(rootLocation.resolve(userDetail.getProfileImage()));

                    Files.copy(file.getInputStream(),rootLocation.resolve(profileImage));
                    int result = userDAO.updateProfileImage(profileImage, userID);
                    if(result > 0)
                        return result;
                    else
                        return -5;
                }
                catch(Exception exception)
                {
                    System.out.println("Error while uploading image when image is already Exists :: " + exception.getMessage());
                    return -5;
                }
            }
        }
        else {
            return 0;
        }
    }
}

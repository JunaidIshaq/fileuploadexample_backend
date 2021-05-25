package com.practice.fileuploadexample.controller;

import com.practice.fileuploadexample.model.UserDetail;
import com.practice.fileuploadexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public int saveUser(@RequestBody UserDetail userDetail)
    {
        return userService.saveUser(userDetail);
    }

    @PostMapping("/uploadImage/{userId}")
    public int handleFileUpload(@PathVariable int userId , @RequestParam("file") MultipartFile file, HttpSession session) {
        return userService.store(file, userId, session);
    }

    @GetMapping("/")
    public List<UserDetail> getUsers(){
        return userService.getUsers();
    }

}

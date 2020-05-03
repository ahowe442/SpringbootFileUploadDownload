package com.howe.SpringbootFileUploadDownload.Service;

import com.howe.SpringbootFileUploadDownload.Model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();
    User save(User user);
}

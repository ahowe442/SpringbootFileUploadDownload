package com.howe.SpringbootFileUploadDownload.Service;

import com.howe.SpringbootFileUploadDownload.Model.User;
import com.howe.SpringbootFileUploadDownload.Model.UserFiles;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User save(User user);

    User findById(Long userId);

    List<UserFiles> findFilesByUserId(Long userId);

    User update(User user);
}

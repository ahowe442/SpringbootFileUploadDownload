package com.howe.SpringbootFileUploadDownload.Service;

import com.howe.SpringbootFileUploadDownload.Model.User;
import com.howe.SpringbootFileUploadDownload.Model.UserFiles;
import com.howe.SpringbootFileUploadDownload.Repository.UserFileRepository;
import com.howe.SpringbootFileUploadDownload.Repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService  {

    @Autowired private UserRepository userRepository;
    @Autowired private UploadPathService uploadPathService;
    @Autowired private UserFileRepository userFileRepository;


    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user){
        user.setCreatedDate(new Date());
        User dbUser = userRepository.save(user);
        if(dbUser!=null && user.getFiles()!=null && user.getFiles().size()>0){
            for(MultipartFile file : user.getFiles()){
                String fileName = file.getOriginalFilename();
                String modifiedFileName = FilenameUtils.getBaseName(fileName)+" "+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
                File storeFile = uploadPathService.getFilePath(modifiedFileName, "images");
                if(storeFile!=null){
                    try{
                        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                UserFiles files = new UserFiles();
                files.setFileExtension(FilenameUtils.getExtension(fileName));
                files.setModifiedFileName(modifiedFileName);
                files.setUser(dbUser);
                userFileRepository.save(files);

            }
        }
        return dbUser;
    }
}

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

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService  {

    @Autowired private UserRepository userRepository;
    @Autowired private UploadPathService uploadPathService;
    @Autowired private UserFileRepository userFileRepository;
    @Autowired private ServletContext context;


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
                String modifiedFileName = FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
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

    @Override
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public List<UserFiles> findFilesByUserId(Long userId) {
        return userFileRepository.findFilesByUserId(userId);
    }

    @Override
    public User update(User user) {
        user.setUpdatedDate(new Date());
        User dbUser = userRepository.save(user);

        if(user!= null && user.getRemoveImages()!=null && user.getRemoveImages().size()>0){
            userFileRepository.deleteFilesByUserAndImageNames(user.getId(), user.getRemoveImages());
            for(String file : user.getRemoveImages()){
                File dbFile = new File(context.getRealPath("/images/"+File.separator+file));
                if(dbFile.exists()){
                    dbFile.delete();
                }
            }
        }

        if(dbUser!=null && user.getFiles()!=null && user.getFiles().size()>0){
            for(MultipartFile file : user.getFiles()){
                String fileName = file.getOriginalFilename();
                String modifiedFileName = FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
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

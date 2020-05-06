package com.howe.SpringbootFileUploadDownload.Controller;
import com.howe.SpringbootFileUploadDownload.Service.UserService;
import com.howe.SpringbootFileUploadDownload.Model.UserFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class SpringFileDownloadController {

    @Autowired private ServletContext context;

    @GetMapping(value = "/downloadfile/{fileName}/{modifiedFileName}")
    public void downloadfile(@PathVariable String fileName, @PathVariable String modifiedFileName, HttpServletResponse response){
        String fullPath = context.getRealPath("/images"+File.separator+modifiedFileName);
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if(file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeTye = context.getMimeType(fullPath);
                response.setContentType(mimeTye);
                response.setHeader("Content-disposition", "attachment; filename="+fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer))!= -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();

            }catch (Exception e){
                // TODO: handle exception
            }
        }
    }

    @GetMapping(value="/downloadfileszip/{userId}")
    public void downloadfilesaszip(@PathVariable Long userId, HttpServletResponse response) {
        List<UserFiles> userFiles = userService.findFilesByUserId(userId);
        if(userFiles!=null && userFiles.size()>0){
            downloadzipfiles(userFiles, "files.zip", response);
        }
    }
}



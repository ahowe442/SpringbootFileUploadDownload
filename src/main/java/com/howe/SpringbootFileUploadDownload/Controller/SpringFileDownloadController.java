package com.howe.SpringbootFileUploadDownload.Controller;
import com.howe.SpringbootFileUploadDownload.Service.UserService;
import com.howe.SpringbootFileUploadDownload.Model.UserFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class SpringFileDownloadController {

    @Autowired private ServletContext context;
    @Autowired private UserService userService;


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
                e.printStackTrace();
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

    private void downloadzipfiles(List<UserFiles> userFiles, String zipName, HttpServletResponse response) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);
            byte bytes[] = new byte[122048];
            for(UserFiles file : userFiles){
                if(file!=null && StringUtils.hasText(file.getModifiedFileName())){
                    FileInputStream fis = new FileInputStream(context.getRealPath("/images/"+File.separator+file.getModifiedFileName()));
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    zos.putNextEntry(new ZipEntry(file.getFilename()));
                    int bytesRead;
                    while((bytesRead = bis.read(bytes))!= -1){
                        zos.write(bytes, 0, bytesRead);
                    }
                    zos.closeEntry();
                    bis.close();
                    fis.close();
                }
            }
            zos.flush();
            baos.flush();
            zos.close();
            baos.close();

            byte[] zip = baos.toByteArray();
            ServletOutputStream sos = response.getOutputStream();
            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment; filename="+zipName);
            sos.write(zip);
            sos.flush();
            sos.close();


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}



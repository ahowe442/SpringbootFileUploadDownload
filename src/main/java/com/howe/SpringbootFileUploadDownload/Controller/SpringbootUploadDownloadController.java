package com.howe.SpringbootFileUploadDownload.Controller;

import com.howe.SpringbootFileUploadDownload.Model.User;
import com.howe.SpringbootFileUploadDownload.Model.UserFiles;
import com.howe.SpringbootFileUploadDownload.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SpringbootUploadDownloadController {

    @Autowired private UserService userService;

    @GetMapping(value = "/")
    public String users(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("userfiles", new ArrayList<UserFiles>());
        model.addAttribute("isAdd", true);
        return "view/user";
    }
    @PostMapping(value="/save")
    public String save(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model){
        User dbUser = userService.save(user);
        if(dbUser!=null){
            redirectAttributes.addFlashAttribute("successmessage", "User has been saved successfully");
            return "redirect:/";
        }else{
            model.addAttribute("errormessage", "User has not been not saved, Please try again");
            model.addAttribute("user", user);
            return "view/user";

        }
    }




}

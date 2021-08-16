package com.example.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/")
    public String getHome(){

        return "myhome.html";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String getSignIn(){
        return "signin.html";
    }

    @PostMapping("/signup")
    public RedirectView signUp(@RequestParam (value = "username") String username,@RequestParam(value = "password") String password,@RequestParam(value = "firstname") String firstname,@RequestParam(value = "lastname") String lastname,@RequestParam(value = "dateOfBirth") String dateOfBirth,@RequestParam(value = "bio") String bio){
        ApplicationUser newUser=new ApplicationUser(username,bCryptPasswordEncoder.encode(password),firstname,lastname,dateOfBirth,bio);
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        newUser.setDateOfBirth(dateOfBirth);
        newUser.setBio(bio);
        applicationUserRepository.save(newUser);
        return new RedirectView("/login");

    }

    @GetMapping("/profile/{id}")
    public String getHome(Model m, @PathVariable("id") Integer id){
        Object princpal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(princpal instanceof UserDetails){
            String username=((UserDetails)princpal).getUsername();
            m.addAttribute("username",username);
        }else {
            String username=princpal.toString();
        }
        m.addAttribute("username",applicationUserRepository.findById(id).get());
        return "home.html";
    }

    @GetMapping("/ownprofile")
    public String getprofile(Model m){
        Object princpal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(princpal instanceof UserDetails){
            String username=((UserDetails)princpal).getUsername();
            ApplicationUser pro=applicationUserRepository.findByUsername(username);
            m.addAttribute("username",username);
            m.addAttribute("pro",pro);
            List<PostModel> post=pro.getPosts();
            System.out.println(post);
//            List<PostModel>post= (List<PostModel>) postRepository.findAll();
//            List<PostModel> post =  postRepository.findAllByUser(pro);
//            System.out.println(post);
            m.addAttribute("posts",post);
        }else {
            String username=princpal.toString();
        }
        return "profile.html";
    }
}

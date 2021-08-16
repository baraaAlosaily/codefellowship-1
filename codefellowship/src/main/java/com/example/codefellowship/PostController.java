package com.example.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;
@Controller
public class PostController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/newpost")
    public RedirectView addpost(@RequestParam (value ="body") String body){
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//        Date date=new Date(System.currentTimeMillis());
//
//        PostModel postModel=new PostModel(body,simpleDateFormat.format(date),applicationUserRepository.findById(id).get());
//        postRepository.save(postModel);
        PostModel post = new PostModel(body);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setApplicationUser(applicationUserRepository.findByUsername(userDetails.getUsername()));
        postRepository.save(post);

        return new RedirectView("/ownprofile");

    }



}

package com.example.codefellowship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostModel,Integer> {
//    public List<PostModel> findByUsername(String username);
//     public List<PostModel> findByUsername(ApplicationUser applicationUser);
}

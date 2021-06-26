package com.example.demo.controller;
import com.example.demo.models.GitHubUsers;
import com.example.demo.services.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GithubController {

   private final GithubService githubService;


    @GetMapping("/getRepositories/{repoName}")
    public GitHubUsers getRepositories(@PathVariable String repoName){
       return githubService.getRepositories(repoName);
    }

    @GetMapping("/getUniqueCommiter/{base}/{repoName}")
    public Set<String> getUniqueCommiters(@PathVariable String base, @PathVariable String repoName){
        return githubService.getUniqueCommiters(base,repoName);
    }

    @GetMapping("/getCommiterImpact/{base}/{repoName}")
    public Map<String,Integer> getCommiterImpact(@PathVariable String base, @PathVariable String repoName){
        return githubService.getCommitterImpact(base,repoName);
    }


    @GetMapping("/getCommitTimeline/{base}/{repoName}")
    public Map<String,Integer> getCommitTimeline(@PathVariable String base, @PathVariable String repoName){
        return githubService.getCommitTimeline(base,repoName);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

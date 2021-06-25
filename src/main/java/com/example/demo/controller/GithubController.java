package com.example.demo.controller;

import com.example.demo.models.Commiters;
import com.example.demo.models.GitHubUsers;
import com.example.demo.services.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController()
@RequestMapping("/api")
public class GithubController {

    @Autowired
    GithubService githubService;


    @GetMapping("/getRepositories/{repoName}")
    public ResponseEntity<GitHubUsers> getRepositories(@PathVariable String repoName){
       return ResponseEntity.ok(githubService.getRepositories(repoName));
    }

    @GetMapping("/getUniqueCommiter/{base}/{repoName}")
    public ResponseEntity<Set<String>> getUniqueCommiters(@PathVariable String base, @PathVariable String repoName){
        return ResponseEntity.ok(githubService.getUniqueCommiters(base,repoName));
    }

    @GetMapping("/getCommiterImpact/{base}/{repoName}")
    public ResponseEntity<Map<String,Integer>> getCommiterImpact(@PathVariable String base, @PathVariable String repoName){
        return ResponseEntity.ok(githubService.getCommitterImpact(base,repoName));
    }


    @GetMapping("/getCommitTimeline/{base}/{repoName}")
    public ResponseEntity<Map<String,Integer>> getCommitTimeline(@PathVariable String base, @PathVariable String repoName){
        return ResponseEntity.ok(githubService.getCommitTimeline(base,repoName));
    }


}

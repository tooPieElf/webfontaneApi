package com.example.demo.services;

import com.example.demo.models.Author;
import com.example.demo.models.Commit;
import com.example.demo.models.Commiters;
import com.example.demo.models.GitHubUsers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GithubService {

    @Autowired
    RestTemplate restTemplate;

    public GitHubUsers getRepositories(String repoName){
        return restTemplate.getForObject("https://api.github.com/search/repositories?q="+repoName, GitHubUsers.class);
    }

    public Set<String> getUniqueCommiters(String base, String repoName){
        List<String> names = getCommitNames(base, repoName);
        Set<String> uniqueNames = new HashSet<>(names);
        return uniqueNames;
    }

    public Map<String,Integer> getCommitterImpact(String base, String repoName){
        Map<String, Integer> impacts = new HashMap<>();
        List<String> names = getCommitNames(base, repoName);

        PutInHashMap(impacts, names);

        return impacts;
    }

    public Map<String, Integer> getCommitTimeline(String base, String repoName){
        Map<String, Integer> commitTimeline = new HashMap<>();

        List<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>> commitersList =
                restTemplate.getForObject("https://api.github.com/repos/"+base+"/"+repoName+"/commits?per_page=100", List.class);
        List<String> commitDates = commitersList.stream()
                .map(object->object.get("commit"))
                .map(commit->commit.get("author"))
                .map(authors->authors.get("date").substring(0,10))
                .collect(Collectors.toList());

        PutInHashMap(commitTimeline, commitDates);

        return commitTimeline;


    }

    public void PutInHashMap(Map<String, Integer> commitTimeline, List<String> commitDates) {
        for(String s : commitDates){
            if(commitTimeline.containsKey(s)){
                Integer value =commitTimeline.get(s);
                value +=1;
                commitTimeline.put(s,value+1);
            }else{
                commitTimeline.put(s, 1);
            }
        }
    }


    public List<String> getCommitNames(String base, String repoName){
        List<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>> commitersList =
                restTemplate.getForObject("https://api.github.com/repos/"+base+"/"+repoName+"/commits?per_page=100", List.class);
        return commitersList.stream()
                .map(object->object.get("commit"))
                .map(commit->commit.get("author"))
                .map(authors->authors.get("name"))
                .collect(Collectors.toList());
    }

}


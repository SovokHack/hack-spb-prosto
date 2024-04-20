package com.hack.hackathon.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    public List<String> getGroups() { //TODO from API
        return List.of("1375", "1376", "1378", "1379");
    }
}

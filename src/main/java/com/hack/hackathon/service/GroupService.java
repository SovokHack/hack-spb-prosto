package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final PeterburgConfig peterburgConfig;
    private final RestTemplate restTemplate;

    public List<String> getGroups() { //TODO from API
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getGroupsUrl(), String.class);
        JSONArray json = new JSONArray(response.getBody());
        var result = new ArrayList<String>();
        for (int k = 0; k < Objects.requireNonNull(json).length(); k++) {
            var deps = Optional.ofNullable(json.getJSONObject(k)).map(it -> it.optJSONArray("departments"))
                    .orElse(new JSONArray());
            for (int i = 0; i < deps.length(); i++) {
                var groups = Optional.ofNullable(deps.optJSONObject(i))
                        .map(it -> it.optJSONArray("groups"))
                        .orElse(new JSONArray());
                for (int j = 0; j < groups.length(); j++) {
                    var group = Optional.ofNullable(groups.optJSONObject(j))
                            .map(it -> it.optString("number", null));
                    group.ifPresent(result::add);

                }
            }
        }
        return result;
    }

    public boolean groupExists(String group) {
        return true;
    }
}

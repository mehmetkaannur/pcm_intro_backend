package com.example.myapp.controller;

import com.example.myapp.model.Campaign;
import com.example.myapp.model.User;
import com.example.myapp.repository.CampaignRepository;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/merged")
public class MergedDataController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping
    public List<MergedData> getMergedData() {
        List<User> users = userRepository.findAll();
        List<Campaign> campaigns = campaignRepository.findAll();

        return users.stream()
                .flatMap(user -> campaigns.stream()
                        .filter(campaign -> user.getPoints() >= campaign.getMinPoints())
                        .map(campaign -> new MergedData(user.getName(), user.getId(), campaign.getName())))
                .collect(Collectors.toList());
    }

    public static class MergedData {
        private String userName;
        private Long userId;
        private String campaignName;

        public MergedData(String userName, Long userId, String campaignName) {
            this.userName = userName;
            this.userId = userId;
            this.campaignName = campaignName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getCampaignName() {
            return campaignName;
        }

        public void setCampaignName(String campaignName) {
            this.campaignName = campaignName;
        }

        @Override
        public String toString() {
            return "MergedData{" +
                    "userName='" + userName + '\'' +
                    ", userId=" + userId +
                    ", campaignName='" + campaignName + '\'' +
                    '}';
        }
    }
}

package com.example.myapp.controller;

import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.model.Campaign;
import com.example.myapp.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping("/all")
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @PostMapping("/new")
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id :" + id));
        return ResponseEntity.ok(campaign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaignDetails) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id :" + id));

        campaign.setName(campaignDetails.getName());
        campaign.setStartDate(campaignDetails.getStartDate());
        campaign.setEndDate(campaignDetails.getEndDate());
        campaign.setMinPoints(campaignDetails.getMinPoints());

        Campaign updatedCampaign = campaignRepository.save(campaign);
        return ResponseEntity.ok(updatedCampaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id :" + id));

        campaignRepository.delete(campaign);
        return ResponseEntity.noContent().build();
    }
}

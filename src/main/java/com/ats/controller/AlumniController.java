package com.ats.controller;

import com.ats.model.*;
import com.ats.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumni")
@CrossOrigin(origins = "http://localhost:3000")
public class AlumniController {

    @Autowired
    private AlumniService alumniService;

    @Autowired
    private JobService jobService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAlumni(@RequestBody Alumni alumni) {
        if (alumniService.isUsernameExists(alumni.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        if (alumniService.isEmailExists(alumni.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }
        alumni.setStatus("Pending");
        alumniService.save(alumni);
        return ResponseEntity.ok("Registration successful, waiting for admin approval.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> listAlumni() {
        List<Alumni> alumniList = alumniService.listAll();
        List<Map<String, Object>> result = alumniList.stream().map(alumni -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", alumni.getName());
            map.put("age", alumni.getAge());
            map.put("address", alumni.getAddress());
            map.put("email", alumni.getEmail());
            map.put("gender", alumni.getGender());
            map.put("department", alumni.getDepartment());
            map.put("graduationYear", alumni.getGraduationYear());
            map.put("mobilnumber", alumni.getMobilnumber());
            map.put("currentEmployment", alumni.getCurrentEmployment());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginAlumni(@RequestBody Alumni loginRequest) {
        Alumni alumni = alumniService.findByUsername(loginRequest.getUsername());
        if (alumni == null || !alumni.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        if (alumni.getStatus().equals("Pending")) {
            return ResponseEntity.status(403).body("Account is pending approval");
        } else if (alumni.getStatus().equals("Denied")) {
            return ResponseEntity.status(403).body("Account has been denied");
        }

        Map<String, Object> alumniDetails = new HashMap<>();
        alumniDetails.put("id", alumni.getId());
        alumniDetails.put("name", alumni.getName());
        alumniDetails.put("age", alumni.getAge());
        alumniDetails.put("gender", alumni.getGender());
        alumniDetails.put("mobilnumber", alumni.getMobilnumber());
        alumniDetails.put("address", alumni.getAddress());
        alumniDetails.put("currentEmployment", alumni.getCurrentEmployment());
        alumniDetails.put("graduationYear", alumni.getGraduationYear());
        alumniDetails.put("department", alumni.getDepartment());
        alumniDetails.put("email", alumni.getEmail());
        alumniDetails.put("status", alumni.getStatus());

        return ResponseEntity.ok(alumniDetails);
    }

    @PostMapping("/postjob")
    public ResponseEntity<String> createJobPost(@RequestParam("companyName") String companyName,
                                                @RequestParam("jobDescription") String jobDescription,
                                                @RequestParam("contactDetails") String contactDetails,
                                                @RequestParam("referralId") String referralId,
                                                @RequestParam("location") String location,
                                                @RequestParam("jobType") String jobType,
                                                @RequestParam("alumniId") Long alumniId) {
        if (alumniId == null || alumniId <= 0) {
            return ResponseEntity.badRequest().body("Invalid alumni ID");
        }
        Alumni alumni = alumniService.get(alumniId);
        if (alumni == null) {
            return ResponseEntity.badRequest().body("Invalid alumni ID");
        }

        JobPost jobPost = new JobPost();
        jobPost.setCompanyName(companyName);
        jobPost.setJobDescription(jobDescription);
        jobPost.setContactDetails(contactDetails);
        jobPost.setReferralId(referralId);
        jobPost.setLocation(location);
        jobPost.setJobType(jobType);
        jobPost.setStatus("Pending");
        jobPost.setAlumni(alumni);
        jobService.saveJobPost(jobPost);

        return ResponseEntity.ok("Job post created successfully");
    }

    @GetMapping("/jobposts")
    public ResponseEntity<List<Map<String, Object>>> listJobPosts() {
        List<JobPost> jobPostList = jobService.listAllJobPosts();
        List<Map<String, Object>> result = jobPostList.stream()
                .filter(jobPost -> !"Pending".equals(jobPost.getStatus()))
                .map(jobPost -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("companyName", jobPost.getCompanyName());
                    map.put("jobDescription", jobPost.getJobDescription());
                    map.put("contactDetails", jobPost.getContactDetails());
                    map.put("referralId", jobPost.getReferralId());
                    map.put("location", jobPost.getLocation());
                    map.put("jobType", jobPost.getJobType());
                    map.put("status", jobPost.getStatus());

                    Alumni alumni = jobPost.getAlumni();
                    if (alumni != null) {
                        map.put("alumniId", alumni.getId());
                        map.put("alumniName", alumni.getName());
                    } else {
                        map.put("alumniId", null);
                        map.put("alumniName", "Unknown");
                    }

                    return map;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/requestjob")
    public ResponseEntity<String> createJobRequest(@RequestParam() String name,
                                                   @RequestParam() String qualifications,
                                                   @RequestParam() String completedYear,
                                                   @RequestParam() String contactDetails,
                                                   @RequestParam() Long alumniId) {
        if (alumniId == null || alumniId <= 0) {
            return ResponseEntity.badRequest().body("Invalid alumni ID");
        }
        Alumni alumni = alumniService.get(alumniId);
        if (alumni == null) {
            return ResponseEntity.badRequest().body("Invalid alumni ID");
        }

        JobRequest jobRequest = new JobRequest();
        jobRequest.setName(name);
        jobRequest.setQualifications(qualifications);
        jobRequest.setCompletedYear(completedYear);
        jobRequest.setContactDetails(contactDetails);
        jobRequest.setStatus("Pending");
        jobRequest.setAlumni(alumni);

        jobService.saveJobRequest(jobRequest);
        return ResponseEntity.ok("Job request created successfully");
    }

    @GetMapping("/jobrequests")
    public ResponseEntity<List<Map<String, Object>>> listJobRequests(@RequestParam Long alumniId) {
        List<JobRequest> jobRequestList = jobService.listAllJobRequests();
        List<Map<String, Object>> result = jobRequestList.stream()
                .filter(jobRequest -> !"Pending".equals(jobRequest.getStatus()))
                .map(jobRequest -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", jobRequest.getName());
                    map.put("qualifications", jobRequest.getQualifications());
                    map.put("completedYear", jobRequest.getCompletedYear());
                    map.put("contactDetails", jobRequest.getContactDetails());
                    map.put("status", jobRequest.getStatus());

                    Alumni alumni = jobRequest.getAlumni();
                    if (alumni != null) {
                        map.put("alumniId", alumni.getId());
                        map.put("alumniName", alumni.getName());
                    } else {
                        map.put("alumniId", null);
                        map.put("alumniName", "Unknown");
                    }

                    return map;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/donate")
    public String makeDonation(@RequestParam double amount, @RequestParam String paymentId, @RequestParam String reason,
                               @RequestParam("proof") MultipartFile proofFile, @RequestParam Long alumniId) {
        if (alumniId == null || alumniId <= 0) {
            return "Invalid alumni ID.";
        }
        Alumni alumni = alumniService.get(alumniId);
        if (alumni == null) {
            return "Alumni not found.";
        }
        try {
            Donation donation = new Donation();
            donation.setAmount(amount);
            donation.setPaymentId(paymentId);
            donation.setReason(reason);
            donation.setProof(proofFile.getBytes());
            donation.setAlumni(alumni);
            donationService.save(donation);
            return "Donation made successfully.";
        } catch (IOException e) {
            return "Error uploading proof.";
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> listEvents() {
        List<Event> events = eventService.listAll();
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/galleries")
    public ResponseEntity<List<Gallery>> listGalleries() {
        List<Gallery> galleries = galleryService.listAll();
        if (galleries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(galleries);
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestBody Map<String, Object> feedbackRequest) {
        Long alumniId;
        try {
            alumniId = Long.parseLong(feedbackRequest.get("alumniId").toString());
        } catch (NumberFormatException e) {
            return "Invalid alumni ID.";
        }
        Alumni alumni = alumniService.get(alumniId);
        if (alumni == null) {
            return "Alumni not found.";
        }

        String message = (String) feedbackRequest.get("message");
        Feedback feedback = new Feedback();
        feedback.setMessage(message);
        feedback.setAlumni(alumni);
        feedbackService.save(feedback);

        return "Feedback submitted successfully.";
    }
}

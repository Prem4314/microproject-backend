package com.ats.controller;

import com.ats.model.*;
import com.ats.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AlumniService alumniService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private DonationService donationService;

    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        if (adminService.validateAdmin(admin.getUsername(), admin.getPassword())) {
            return "Admin logged in";
        } else {
            return "Invalid credentials.";
        }
    }

    @GetMapping("/alumni/pending")
    public List<Alumni> listPendingAlumni() {
        return alumniService.listAll().stream()
                .filter(alumni -> "Pending".equals(alumni.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("/jobPost/pending")
    public List<JobPost> listPendingJobPosts() {
        return jobService.listAllJobPosts().stream()
                .filter(jobPost -> "Pending".equals(jobPost.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("/jobRequest/pending")
    public List<JobRequest> listPendingJobRequests() {
        return jobService.listAllJobRequests().stream()
                .filter(jobRequest -> "Pending".equals(jobRequest.getStatus()))
                .collect(Collectors.toList());
    }

    @GetMapping("/alumni/approve/{id}")
    public String approveAlumni(@PathVariable Long id) {
        Alumni alumni = alumniService.get(id);
        if (alumni != null) {
            alumni.setStatus("Active");
            alumniService.save(alumni);
            emailService.sendEmail(alumni.getEmail(), "Account Approved", "Hi Alumni, Your account has been approved. Login and Eplore more abouut the Alumni Association");
            return "Alumni approved.";
        } else {
            return "Alumni not found.";
        }
    }

    @GetMapping("/alumni/deny/{id}")
    public String denyAlumni(@PathVariable Long id, @RequestParam String reason) {
        Alumni alumni = alumniService.get(id);
        if (alumni != null) {
            alumni.setStatus("Denied");
            alumniService.save(alumni);
            emailService.sendEmail(alumni.getEmail(), "Account Denied", "Your account has been denied.Please Contact College Adinistartion." + reason);
            return "Alumni denied.";
        } else {
            return "Alumni not found.";
        }
    }

    @GetMapping("/jobPost/approve/{id}")
    public String approveJobPost(@PathVariable Long id) {
        JobPost jobPost = jobService.getJobPost(id);
        if (jobPost != null) {
            jobPost.setStatus("Approved");
            jobService.saveJobPost(jobPost);
            return "Job post approved.";
        } else {
            return "Job post not found.";
        }
    }

    @GetMapping("/jobPost/deny/{id}")
    public String denyJobPost(@PathVariable Long id, @RequestParam String reason) {
        JobPost jobPost = jobService.getJobPost(id);
        if (jobPost != null) {
            jobPost.setStatus("Denied");
            jobService.saveJobPost(jobPost);
            return "Job post denied.";
        } else {
            return "Job post not found.";
        }
    }

    @GetMapping("/jobRequest/approve/{id}")
    public String approveJobRequest(@PathVariable Long id) {
        JobRequest jobRequest = jobService.getJobRequest(id);
        if (jobRequest != null) {
            jobRequest.setStatus("Approved");
            jobService.saveJobRequest(jobRequest);
            return "Job request approved.";
        } else {
            return "Job request not found.";
        }
    }

    @GetMapping("/jobRequest/deny/{id}")
    public String denyJobRequest(@PathVariable Long id, @RequestParam String reason) {
        JobRequest jobRequest = jobService.getJobRequest(id);
        if (jobRequest != null) {
            jobRequest.setStatus("Denied");
            jobService.saveJobRequest(jobRequest);
            return "Job request denied.";
        } else {
            return "Job request not found.";
        }
    }

    @PostMapping("/event/add")
    public String addEvent(@RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("date") String date,
                           @RequestParam("image") MultipartFile file) throws IOException {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setDate(date);
        event.setImageData(file.getBytes());
        event.setImageName(file.getOriginalFilename());
        eventService.save(event);
        return "Event added successfully.";
    }

    @PostMapping("/gallery/add")
    public String addImage(@RequestParam("image") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return "File not received.";
        }
        Gallery gallery = new Gallery();
        gallery.setImageData(file.getBytes());
        gallery.setImageName(file.getOriginalFilename());
        galleryService.save(gallery);
        return "Image added successfully.";
    }

    @PutMapping("/event/update/{id}")
    public String updateEvent(@PathVariable Long id,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("date") String date,
                              @RequestParam("image") MultipartFile file) throws IOException {
        Event event = eventService.get(id);
        if (event != null) {
            event.setName(name);
            event.setDescription(description);
            event.setDate(date);
            if (file != null && !file.isEmpty()) {
                event.setImageData(file.getBytes());
                event.setImageName(file.getOriginalFilename());
            }
            eventService.save(event);
            return "Event updated successfully.";
        } else {
            return "Event not found.";
        }
    }

    @DeleteMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        Event event = eventService.get(id);
        if (event != null) {
            eventService.delete(id);
            return "Event deleted successfully.";
        } else {
            return "Event not found.";
        }
    }

    @GetMapping("/galleries")
    public ResponseEntity<List<Gallery>> listGalleries() {
        List<Gallery> galleries = galleryService.listAll();
        if (galleries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(galleries);
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> listEvents() {
        List<Event> events = eventService.listAll();
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/gallery/delete/{id}")
    public String deleteImage(@PathVariable Long id) {
        Gallery gallery = galleryService.get(id);
        if (gallery != null) {
            galleryService.delete(id);
            return "Image deleted successfully.";
        } else {
            return "Image not found.";
        }
    }

    @GetMapping("/feedback/list")
    public List<Feedback> listFeedbacks() {
        return feedbackService.listAll();
    }

    @GetMapping("/donation/list")
    public List<Donation> listDonations() {
        return donationService.listAll();
    }
}

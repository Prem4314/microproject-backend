package com.ats.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;
    private long mobilnumber;
    private String address;
    private String currentEmployment;
    private int graduationYear;
    private String department;
    private String username;
    private String email;
    private String password;
    private String status;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"alumni", "hibernateLazyInitializer", "handler"})
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"alumni", "hibernateLazyInitializer", "handler"}) 
    private List<Donation> donations;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"alumni", "hibernateLazyInitializer", "handler"})
    private List<JobPost> jobPosts;

    @OneToMany(mappedBy = "alumni", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"alumni", "hibernateLazyInitializer", "handler"})
    private List<JobRequest> jobRequests;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getMobilnumber() {
        return mobilnumber;
    }

    public void setMobilnumber(long mobilnumber) {
        this.mobilnumber = mobilnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentEmployment() {
        return currentEmployment;
    }

    public void setCurrentEmployment(String currentEmployment) {
        this.currentEmployment = currentEmployment;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<JobPost> getJobPosts() {
        return jobPosts;
    }

    public void setJobPosts(List<JobPost> jobPosts) {
        this.jobPosts = jobPosts;
    }

    public List<JobRequest> getJobRequests() {
        return jobRequests;
    }

    public void setJobRequests(List<JobRequest> jobRequests) {
        this.jobRequests = jobRequests;
    }
}

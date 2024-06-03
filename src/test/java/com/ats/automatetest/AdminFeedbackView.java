package com.ats.automatetest;

import org.junit.jupiter.api.Test;
import java.awt.Dimension;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class AdminFeedbackView {

	
	WebDriver driver=new ChromeDriver();
	@Test
	  public void adminFeedbackView() {
	    driver.get("http://localhost:3000/admin/alumni");
	    driver.manage().window().maximize();
	    
	    driver.findElement(By.linkText("Feedbacks")).click();
	    driver.findElement(By.cssSelector("button:nth-child(4)")).click();
	    driver.findElement(By.cssSelector("button path")).click();
	  }
	}



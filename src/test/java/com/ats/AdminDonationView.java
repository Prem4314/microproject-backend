package com.ats;

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

public class AdminDonationView {
	
	WebDriver driver=new ChromeDriver();
	
	@Test
	  public void adminDonationView() {
	    driver.get("http://localhost:3000/admin/alumni");
	    driver.manage().window().maximize();
	    driver.findElement(By.linkText("Donations")).click();
	    {
	      WebElement element = driver.findElement(By.linkText("Donations"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).perform();
	    }
	    {
	      WebElement element = driver.findElement(By.tagName("body"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element, 0, 0).perform();
	    }
	    driver.findElement(By.cssSelector("tr:nth-child(1) button")).click();
	    driver.findElement(By.cssSelector("button:nth-child(6)")).click();
	  }
	}



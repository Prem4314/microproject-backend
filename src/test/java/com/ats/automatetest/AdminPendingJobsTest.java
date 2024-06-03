package com.ats.automatetest;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

public class AdminPendingJobsTest {

	private Map<String, Object> vars;
	JavascriptExecutor js;

	WebDriver driver = new ChromeDriver();

	public void setUp() {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	public void tearDown() {
		driver.quit();
	}

	@Test
	public void adminPendingJobs() {
		driver.get("http://localhost:3000/admin/alumni");
		driver.manage().window().maximize();
		driver.findElement(By.linkText("Pending Job Posts")).click();
		driver.close();
	}
}

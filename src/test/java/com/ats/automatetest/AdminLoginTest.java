package com.ats.automatetest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class AdminLoginTest {
	
	WebDriver driver=new ChromeDriver();
	
	@Test
	  public void adminLogin() {
	    driver.get("http://localhost:3000/");
	    driver.manage().window().maximize();
	    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".MuiMenuItem-root:nth-child(1)")).click();
	    driver.findElement(By.cssSelector("form")).click();
	    driver.findElement(By.id(":r1:")).click();
	    driver.findElement(By.id(":r1:")).sendKeys("admin");
	    driver.findElement(By.id(":r3:")).click();
	    {
	      WebElement element = driver.findElement(By.cssSelector(".MuiButton-contained"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).perform();
	    }
	    driver.findElement(By.id(":r3:")).sendKeys("admin1234");
	    driver.findElement(By.cssSelector(".MuiButton-contained")).click();
	    {
	      WebElement element = driver.findElement(By.tagName("body"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element, 0, 0).perform();
	    }
	    driver.findElement(By.cssSelector(".swal-button")).click();
	  }
	

}

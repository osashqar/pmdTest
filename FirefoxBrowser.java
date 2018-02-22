package au.edu.rmit.its.devops.browsers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;

import io.github.bonigarcia.wdm.FirefoxDriverManager;

/**
 * Firefox Browser currently doesn't work well when triggered from Jenkins.
 * We request to use ChromeBrowser for Jenkins test runs.
 *
 */
public class FirefoxBrowser {

	public static WebDriver getDriver() {
		FirefoxDriverManager.getInstance().setup();
		
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("-private");
		
		WebDriver driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
		return driver;
	}
	
	public static void takeScreenshot(WebDriver driver) {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
        File screenshot = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
        try {
        	FileUtils.copyFile(screenshot, new File("src/main/resources/screenshot.png"));
		} catch (IOException e) {
			
		}
	}
}

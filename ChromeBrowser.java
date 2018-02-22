package au.edu.rmit.its.devops.browsers;



import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * 
 * ChromBrowser returns a Chrome driver for running selenium scripts.
 *
 */
public class ChromeBrowser {
	
	public static WebDriver getDriver() throws Exception {
		DesiredCapabilities capabilities;
		WebDriver driver = null;
		
		ChromeDriverManager.getInstance().setup();
        capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--start-maximized");
        options.addArguments("no-sandbox");
        options.addArguments("incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        //Start the browser
        driver = new ChromeDriver(options);
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
	
	public static WebDriver restartBrowser(WebDriver driver) throws Exception {
		driver.quit();
		return getDriver();
	}

}

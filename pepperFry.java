package assignment;
 
 
 
//import static org.testng.Assert.assertEquals;
 
import java.io.File;	// handles files operation
import java.io.IOException;	//to handle IO Exceptions
import java.time.Duration;	//used for timeout settings(implicit & explicit wait)
//import java.util.List;
//import java.util.Set;

import org.apache.commons.io.FileUtils;	//helps  copy ss from temp to permanent locations
import org.openqa.selenium.By;//locate elements
import org.openqa.selenium.JavascriptExecutor;	//execute javascript executor
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;	//takes screenshots
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;//handle browser
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.edge.EdgeDriver;//handle browser
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;//perform mouse and other interactions
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;	//use wait conditions
import org.testng.annotations.AfterClass;
//import org.testng.Assert;
import org.testng.annotations.BeforeClass;	//denotes testNG annotations
import org.testng.annotations.Parameters;
//import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;	//used to support annotations like @test,@BeforeClass,@AfterClass
 
public class pepperFry {		//class declaration
	static WebDriver driver = null;	//driver controls the browser
	static WebDriverWait wait = null;	//used for explicit waits
	Actions action = null;	//used for complex interactions like hover
	
	@BeforeClass
	@Parameters({"browser"})
	public void driverSetup(String br) throws Exception {
		if(br.equalsIgnoreCase("chrome"))
			driver = new ChromeDriver();
		else if(br.equalsIgnoreCase("edge"))
			driver = new EdgeDriver();
		//driver=new EdgeDriver();
		driver.manage().window().maximize();	//maximise window
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));		//implicit wait for finding element
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));		//initiliase explicit wait
		//driver.get("https://www.pepperfry.com/");
 
		//We are using navigate() and refresh() to avoid sign in pop up.
		//We can also try with clicking somewhere else in the page.
 
		driver.navigate().to("https://www.pepperfry.com/");		//navigate to pepperfry using navigat.to() instead of .get() to
																	//handle sign in pop ups better
		//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//		wait.until(ExpectedConditions.alertIsPresent());
		Thread.sleep(5000);
//		driver.navigate().refresh();
	}
 
	@Test(priority = 0)
	public void validateTitle() {
		WebElement closeButton = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//a[@class='close-modal']")
						)
				);	//waits for sign in modal and closes it
		closeButton.click();	
		
		String title = driver.getTitle();
		System.out.println("Page Title : "+title);		//fetches and prints page title
		//		Assert.assertEquals (title, "PepperFry title", "Page title mismatched!");
		//		Buy Furniture & Home Decor Online – Up to 60% Off at Best Prices in India | Pepperfry
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
	}
 
	@Test (priority = 1)
	public void clickFurnitureAndClickSetteesAndBenches() throws Exception{
 
		//Other methods tried. But not worked.
 
		//		WebElement furniture = driver.findElement(By.xpath("//*[@id=\"Furniture\"]/a"));
		//		driver.findElement(By.xpath("//*[@id=\"Furniture\"]/a")).click();
		//		driver.findElement(By.xpath("//*[@id=\"meta-Furniture\"]/div/div/div/div/div[2]/ul[3]/li[1]")).click();
 
		//		Actions actions = new Actions(driver);
		//		actions.moveToElement(furniture).perform();
		//		WebElement setteesAndBenches = driver.findElement(By.xpath("//*[@id=\"meta-Furniture\"]/div/div/div/div/div[2]/ul[3]/li[1]/a"));
		//		setteesAndBenchesElement.click();
		//		actions.moveToElement(setteesAndBenches).click().build().perform();
 
		//Tried this because I suspected that the element is not clickable for a certain period.
 
		WebElement furniture = wait.until(
				ExpectedConditions.elementToBeClickable(By.linkText("Furniture"))
				);	//using explicit wait (wait until certain conditions are met)
		Actions actions = new Actions(driver);
		actions.moveToElement(furniture).perform();//hovers over furniture menu
 
		WebElement setteesAndBenches = wait.until(
				ExpectedConditions.elementToBeClickable(By.linkText("Settees & Benches"))
				);		//click on settees and benches subcategory using actions
		setteesAndBenches.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
 
 
	}
 
	@Test (priority = 2)
 
	public void getCountOfBenches() {
 
 
		//No need to click on the element, extract count string.
 
		//      WebElement benches = wait.until(
		//              ExpectedConditions.elementToBeClickable(
		//              		By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[3]/pf-clip-category-list/div/a/div/div[2]")
		//              		)
		//      		);
		//      benches.click();
		//*[@id="clip-heder-desktop"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[3]/pf-clip-category-list/div/a/div
 
		//Tried getting the list directly. Failed to get size properly.
		//      Thread.sleep(3000);
		//      List<WebElement> benchList = driver.findElements(By.xpath("//*[@id=\"scroller\"]/div"));
		//      
		//      System.out.println("-------"+benchList.size()+"---------");
 
		//again tried and failed.
 
		//      List<WebElement> benchElements = wait.until(
		//              ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.bench-item"))
		//      );
		//      System.out.println("Count of Benches: " + benchElements.size());
 
		//Realized that the does not have all the options. So need to extract number from text.
 
 
		WebElement benchesElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[3]/pf-clip-category-list/div/a/div/div[2]")
						)
				);
		String benchesText = benchesElement.getText();
		int benchesCount = Integer.parseInt(benchesText.replaceAll("[^0-9]", ""));//using regex to get digits
		System.out.println("Count of Benches: " + benchesCount);	//extract bench count
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
	}
 
	@Test (priority = 3)
 
	public void getCountOfSettees() {
		WebElement setteesElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[2]/pf-clip-category-list/div/a/div/div[2]")
						)
				);
		String setteesText = setteesElement.getText();
		int setteesCount = Integer.parseInt(setteesText.replaceAll("[^0-9]", ""));
		System.out.println("Count of Settees: " + setteesCount);	//same
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
	}
 
	@Test (priority = 4)
 
	public void getCountOfRecamiers() {
		WebElement recamiersElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[4]/pf-clip-category-list/div/a/div/div[2]")
						)
				);
		String recamiersText = recamiersElement.getText();
		int recamiersCount = Integer.parseInt(recamiersText.replaceAll("[^0-9]", ""));
		System.out.println("Count of Recamiers: " + recamiersCount);	//same
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
	}
 
	@Test (priority = 5)
	public void clickOnBenchesAndFilterByMaterials() {
 
		//Tried this but, it was not clickable.
 
		//		WebElement materialElement = wait.until(
		//        		ExpectedConditions.visibilityOfElementLocated(
		//        				By.xpath("//button[@id=\"Material\"]")
		//        				)
		//        		);
		//		materialElement.click();
 
 
		//		WebElement materialElement = wait.until(
		//        		ExpectedConditions.elementToBeClickable(
		//        				By.xpath("//button[@id=\"Material\"]")
		//        				)
		//        		);
		//		materialElement.click();
		//		action = new Actions(driver);
		//		action.moveToElement(materialElement).click();
 
		//Used scroll with JSE and clicked it.
 
		WebElement benchElement = driver.findElement(By.xpath("//div[normalize-space()='Benches']"));
		benchElement.click();	//clicks on benches category
		
 
		WebElement materialElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='Material']"))
				);	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", materialElement);	// scrolls to material
																											//filter using
																											//javascript executor
 
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
		materialElement.click();	//clicks on open material options
 
	}
 
	@Test(priority = 6)
	public void selectMetalAsMaterialType() {
 
		WebElement metalCheckBox = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//label[normalize-space()='Metal']"))		//selects metal checkbox
				);		
		//		driver.findElement(By.xpath("//label[normalize-space()='Metal']"));
		metalCheckBox.click();
 
		WebElement applyButtonElement = driver.findElement(By.xpath("//span[normalize-space()='APPLY']"));	//clicks apply to filtet
		applyButtonElement.click();		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
 
	}
 
	@Test(priority = 7)
	public void takingCountAndScreenshotOfMetalBenches() {
		WebElement countElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/main/app-category/pf-clip/div/div[2]/pf-clip-product-listing/div[1]/div/span[2]"))
				);	//waits for the filtered count element
		String countText = countElement.getText();
		int count = Integer.parseInt(countText.replaceAll("[^0-9]", ""));
		System.out.println("Count of metal benches : "+count);		//parses and prints the count
 
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countElement);	//scrolls to count
 
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileName = "outputss";
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);	//takes screenshot
		File destination = new File("C:\\Users\\2403568\\OneDrive - Cognizant\\screenshots" + fileName + ".png");	//saves it into dest using Apache fileUtils
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
		try {
			FileUtils.copyFile(screenshot, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
 
	}
	public void takeScreenshot() {
	}
 
 
	@AfterClass
	public void closeBrowser() {
		driver.quit();		//closes the browser
	}
 
	
}
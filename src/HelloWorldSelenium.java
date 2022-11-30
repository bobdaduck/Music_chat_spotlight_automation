import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class HelloWorldSelenium {

	public static void main(String[] args) {
		String last_entry = null;
		String spotlights_list_file = null;
		String next_spotlight = null;
		try{
			last_entry = new String(Files.readAllBytes(Paths.get("C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\last_entry.txt")));
		}catch(Exception e) {
			e.printStackTrace();
		}
		try{
			spotlights_list_file = new String(Files.readAllBytes(Paths.get("C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\spot_rotators.txt")));
		}catch(Exception e) {
			e.printStackTrace();
		}
		String[] spotlights_list = spotlights_list_file.split(","); //parse list file into code variables
		for (int i = 0; i < spotlights_list.length; i++) {
			if (spotlights_list[i].equals(last_entry)){ //find yesterday's spotlight in the list
				try{
					next_spotlight = spotlights_list[i+1]; //Set today's spotlight to the one just after it
					break;
				}catch(Exception e){ //"index out of range" means we're at the last person
					System.out.println(spotlights_list[i] + " was the last on the list. Today's spotlight will go back to " + spotlights_list[0]);
					next_spotlight = spotlights_list[0]; //So next person would be the fist one on the list
					break;
				}
			}
		}
		System.out.println(next_spotlight);
		//execute:

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:/Users/bobdaduck/AppData/Local/Google/Chrome/User Data/Default"); //Use system cookies so we don't have to login
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(35)); //twitter is SLOW on chromedriver

		for (int i = 0; i < 10; i++) {
			System.out.println(""); //clear the console
		}
		System.out.println("initializing");

		//driver.get("https://twitter.com/messages");
		driver.get("https://twitter.com/messages/1507969867118366727");

		//WebElement desired_conversation = null;
		//List<WebElement> conversations = new WebDriverWait(driver, Duration.ofSeconds(3))
		//		.until(x -> driver.findElements(By.xpath("//div[@data-testid='conversation']")));

		//List<WebElement> conversations = new WebDriverWait(driver, Duration.ofSeconds(15)).until(x ->
		//List<WebElement> conversations = driver.findElements(By.cssSelector("[data-testid='conversation']"));
		//System.out.println(conversations.toString());
		/*
		for (WebElement conversation : conversations) {
			System.out.println("checking conversation");
			String conversation_title = conversation.getText();
			System.out.println(conversation_title);
			if (conversation.findElement(By.cssSelector("data-testid=\"DMGroupConversationTitle\"")).getText() == "Duckstack Music | Spotlight Channel"){
				desired_conversation = conversation;
				//desired_conversation.click();
				break;
			}
		}
		*/

		WebElement input_field = driver.findElement(By.cssSelector("[data-testid='dmComposerTextInput']"));
		input_field.sendKeys("next spotlight is " + next_spotlight);
		WebElement send_btn = driver.findElement(By.cssSelector("[data-testid='dmComposerSendButton']"));
		send_btn.click();

		//set last entry text file to new value
		try {
			Files.writeString(Paths.get("C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\last_entry.txt"), next_spotlight);
			System.out.println("overwrote last spotlight file with " + next_spotlight);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

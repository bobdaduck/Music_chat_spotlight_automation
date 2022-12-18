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
import java.util.List;

public class RunMusicChat {
	public static void main(String[] args) {
		//Todo: break out script components into separate functions/files
		String last_entry = null;
		String spotlights_list_file = null;
		String next_spotlight = null;
		//Read files, figure out who is next in the spotlight
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
		String[] spotlights_list = spotlights_list_file.split(","); //parse list file into an array
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
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:/Users/bobdaduck/AppData/Local/Google/Chrome/User Data/Default"); //Neat trick, use system cookies so selenium doesn't have to login
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40)); //twitter is SLOW on chromedriver

		for (int i = 0; i < 10; i++) {
			System.out.println(""); //clear the console
		}
		System.out.println("initializing");

		driver.get("https://twitter.com/messages");
		//driver.get("https://twitter.com/messages/1507969867118366727"); //shortcut to conversation that skips most of the following manual searching

		WebElement desired_conversation = null;

		List<WebElement> conversations = driver.findElements(By.cssSelector("[data-testid='conversation']"));
		System.out.println(conversations.toString());
		for (WebElement conversation : conversations) {
			System.out.println("checking conversation");
			String conversation_title = conversation.getText();
			System.out.println(conversation_title);
			if (conversation.findElement(By.cssSelector("[data-testid='DMGroupConversationTitle']")).getText().contains("Duckstack Music | Spotlight Channel")){
				desired_conversation = conversation;
				desired_conversation.click();
				break;
			}
		}

		WebElement input_field = driver.findElement(By.cssSelector("[data-testid='dmComposerTextInput']"));
		input_field.sendKeys("next spotlight is " + next_spotlight);

		WebElement send_btn = driver.findElement(By.cssSelector("[data-testid='dmComposerSendButton']"));
		send_btn.click(); //clicks the send button to send DM to the chat

		//set the contents of "last entry" text file to the new value so that tomorrow's spotlight will pick up from today's
		try {
			Files.writeString(Paths.get("C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\last_entry.txt"), next_spotlight);
			System.out.println("overwrote last spotlight file with " + next_spotlight);
			driver.quit();
		} catch (IOException e) {
			e.printStackTrace();
			driver.quit();
		}
	}
}

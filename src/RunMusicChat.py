from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By

last_entry = None
next_spotlight = None
# Read files, figure out who is next in the spotlight
with open('last_entry.txt') as f: #C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\last_entry.txt
	last_entry = f.read() 

with open('spot_rotators.txt') as f:#C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\spot_rotators.txt
	spotlights_list = f.read() # parse list file into an array
	items = spotlights_list.split(",")
for i in range(len(items)):
	if (items[i] ==last_entry): # find yesterday's spotlight in the list
		try:
			next_spotlight = items[i+1] # Set today's spotlight to the one just after it
			break
		except Exception as e: # "index out of range" means we're at the last person
			print(items[i] + " was the last on the list. Today's spotlight will go back to " + spotlights_list[0])
			next_spotlight = items[0] # So next person would be the fist one on the list
			break
		
	

print(next_spotlight)
# execute:
# System.setProperty("webdriver.chrome.driver", "C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\chromedriver.exe")
options = webdriver.ChromeOptions()
options.add_argument("user-data-dir=C:/Users/bobdaduck/AppData/Local/Google/Chrome/User Data/Default") # Neat trick, use system cookies so selenium doesn't have to login
options.add_argument("start-maximized") #  open Browser in maximized mode
options.add_argument("disable-infobars") #  disabling infobars
options.add_argument("--remote-allow-origins=*")
options.add_argument("--disable-extensions") #  disabling extensions
options.binary_location = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), chrome_options=options)
driver.implicitly_wait(40) # twitter is SLOW on chromedriver

for i in range(10):
	print("") # clear the console

print("initializing")

driver.get("https://twitter.com/messages")
# driver.get("https:# twitter.com/messages/1507969867118366727") # shortcut to conversation that skips most of the following manual searching
# Better performance ofc but I'm trying to demonstrate one way you could find it manually
desired_conversation = None

conversations = driver.find_elements(By.CSS_SELECTOR, "[data-testid='conversation']")
for conversation in conversations: 
	conversation_title = conversation.text
	if ("Duckstack Music | Spotlight Channel" in conversation.find_element(By.CSS_SELECTOR, "[data-testid='DMGroupConversationTitle']").text):
		desired_conversation = conversation
		desired_conversation.click()
		break


driver.find_element(By.CSS_SELECTOR, "[data-testid='dmComposerTextInput']").send_keys("next spotlight is " + next_spotlight + " ") #space on end closes account info window which would block the send button

driver.find_element(By.CSS_SELECTOR, "[data-testid='dmComposerSendButton']").click() # clicks the send button to send DM to the chat


# sendBtn = WebDriverWait(driver, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "[data-testid='dmComposerSendButton']")))

# set the contents of "last entry" text file to the new value so that tomorrow's spotlight will pick up from today's
try:
	f = open("last_entry.txt", "w") # C:\\Users\\bobdaduck\\Desktop\\Hello-World-Selenium-master\\src\\last_entry.txt
	f.write(next_spotlight)
	print("overwrote last spotlight file with " + next_spotlight)
	driver.quit()
except:
	driver.quit()

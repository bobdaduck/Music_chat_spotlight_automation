# Music_chat_spotlight_automation
Simple selenium program in Java to tell everyone in the X.com music chat who's up next. Reads CSV, writes .txt, then posts to twitter. I output this as an executable .jar and run it in a task scheduler for daily consistency. 

Normally you wouldn't use Java for this sort of thing because Java is not really meant for bundling into executable scripts, but it was an interesting exercise. There is also an identical python script to demonstrate how the project works in that language.

I used https://github.com/rodrigo-rac2/Hello-World-Selenium as a base, started with some hard coded paths because the script only needed to do one thing, then expanded it to be generalizable. Demonstrates how to use webdriverManager to avoid needing to put chromedriver in the project directory. Demonstrates selecting group chat by matching on element text. Demonstrates chrome options using normal user cookies to avoid needing to sign in. Demonstrates reading and writing from files in order to avoid spotlight repetition.

**JAVA SETUP**
0) Add project directory to PATH for convenience
1) Open project in intellij
2) Use maven's package command- You shouldn't need to do anything, it should pull everything from the .pom file
3) in the project's "target" folder, there should now be a Run_music_chat-selenium-0-jar-with-dependencies.jar file.

**PYTHON SETUP**
0) Pip install selenium, pip install webdriver_manager
1) run the python file from command line.

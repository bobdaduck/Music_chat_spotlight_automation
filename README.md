# Music_chat_spotlight_automation
Simple selenium program in Java to tell everyone in the twitter music chat who's up next. Reads CSV, writes .txt, then posts to twitter. I output this as an executable .jar and run it with window's built in task scheduler to consistently let us know who should be sharing a song each day.

I used https://github.com/rodrigo-rac2/Hello-World-Selenium as a base, and took several shortcuts by hardcoding paths and URLs, because this program only needs to do one thing. However, I plan on updating it to be generalizable eventually.

**SETUP**
1) Open project in intellij
2) Use maven's package command- You shouldn't need to do anything, it should pull everything from the .pom file
3) in the project's "target" folder, there should now be a helloworld-selenium-0-jar-with-dependencies.jar file. Run it or schedule it.

0) You will need to update hardcoded paths in your filesystem to the project directory for it to work but unless you're in the group chat on twitter its useless for you anyway right? This program is not for you! So step zero would actually be to just rip out everything you don't need for your own usage. Hopefully this is a useful example for a little toy utility like this though.

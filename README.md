# MyDictionary

## What is MyDictionary?
My dictionary is ideal for learning foreign words. We should enter all words we learn and then to search the words and also tests our knowledge on the given words.
* The main screen is the dictionary with all the words already entered by us. From the menu we can choose either to modify word or to test our knowledge.
* Modify screen allows us to add, edit and delete words.
  * To add word we just type the word we want to add. Then The program searches the meaning in goolge translator and may suggest us a meaning. We can leave it, change it and then we can press the button to add the word. We can separate different meainngs of a word with commas or with new lines. Goolge translator works for english-bulgarian at the moment.
  * To edit word we type the word we want to edit. Then the current meaning of the word will appear. We just edit and press the button.
  * To delete word again we type the word and its meaning appears. Then we should clear the meaning and press the button.
* Test screen allows us to test our knowledge. We can select how many words do we want to test and then start the test. The program will select random words for us. If we guess a meaning the test will automatically move on to the next word. If we don't know the word we should press enter to continue to the next word.

## How to run MyDictionary

### Prerequisities
* mysql
* eclipse & java

### Setup
* Import javadic project in eclipse
* Create mydictionary database -> `mysql -u root -p -e "create database mydictionary"`
* Create the tables -> `mysql -u username -p mydictionary < mydictionary.sql`
* Change database connection settings in `DBConnection` class

### Run
* Start `Main` as java application.

## Technical Details

### Classes
* Main class, the program starts here
  * Tests the database connection
  * Loads the cache
  * Creates the start frame
  * Handles major program errors and logs them
* DBConnection - user to handle all queries to the database, should be used only by the cache
  * Connects to database
  * Handles all db related stuff
* WordsCache - stores all words info in-memory, offloads the database, should be used always, not to break synchronization with the db
  * Singleton instance of the local cache
  * Offload the database
* MyDictionary - this is the main dictionary frame
  * Allows to query for words
  * You can navigate to all frames from here
* ModifyWords - frame where we can add/edit/remove words
  * Use GoogleTranslator to find and suggest meaning of the new words
* GoogleTranslator - send get request to gootle translator in order to find a meaning of a word
* TestWord - the frame where we can test our knowledge
  * Use Test instance to store the current test progress
  * Checks user answer for correctness automatically on every update
  * On enter just change to next word
* Test - test current test progress
  * selects the words to be tested
  * keep information about current word
  * responsible to check for correctness
* Logger - logs any errors in the program

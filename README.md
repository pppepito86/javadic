# MyDictionary

## What is MyDictionary?
My dictinary is ideal for learning foreign words. The user should enter all words we learn and then to search the words and also tests our knowledge on the given words.
* The main screen is the dictionary with all the words already entered by the us. From the menu we can choose either to modify word or to test our knowledge.
* Modify screen allows us to add, edit and delete words.
  * To add word we just type the word we want to add. Then The program searches the meaning in goolge translator and may suggest us a meaning. We can leave it, change it and then we can press the button to add the word. We can separate different meainngs of a word with commas or with new lines.
  * To edit word we type the word we want to edit. Then the current meaning of the word will appear. We just edit and press the button.
  * To delete word again we type the word and its meaning appears. Then we should clear the meaning and press the button.
* Test screen allows us to test our knowledge. We can select how many words do we want to test and then start the test. The program will select random words for us. If we guess a meaning the test will automatically move on to the next word. If we don't know the word we should press enter to continue to the next word.

## How to run it

### Prerequisities
* mysql
* eclipse & java

### Setup
* Import javadic project in eclipse
* Create mydictionary database
* Create the tables -> `mysql -u username -p mydictionary < mydictionary.sql`
* Change database connection settings in `DBConnection` class

### Run
* Start `Main` as java application.


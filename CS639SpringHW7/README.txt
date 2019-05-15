Description

Your objective for this assignment is to extend the Room example that we did in class and convert it to a modifiable dictionary. The user should be able to add new words and their corresponding definition & parts of speech to the dictionary. Users should also be able to click on an existing word in the dictionary and modify its definition or part of speech.

Main Screen Requirements

- When a user starts the app, there should already be a list of words (with a definition and part of speech) ordered alphabetically in the RecyclerView. At the very minimum, the first screen should look exactly like the in-class example. The only difference should be that instead of simply displaying a word in the RecyclerView row, you should be displaying text in the following format: "word (part of speech abbreviation) - definition". See the attached screenshot.

- If the user clicks on the "Add" button, they should be taken to the Add New Word screen. If a new dictionary entry successfully gets added to the database, then the RecyclerView on the first screen should update itself and display the new dictionary entry in its proper alphabetical position. See the attached screenshot.

- Whenever a new dictionary entry is added to the database, a notification should appear on the status bar. The title of the notification should be "New Word Added!" and the body of the notification should be text in the following format: "word (part of speech abbreviation) - definition". Do not worry about dismissing the notification when a user taps on it; swiping to dismiss the notification is fine. See the attached screenshot.

- When a user clicks on a dictionary entry in the RecyclerView, they should be taken to the Modify Word screen. Any changes made to the word's definition or part of speech, should be reflected in the RecyclerView when the user returns to the first screen.

Add New Word/Modify Word Screen Requirements

- The user should be able to navigate to this screen by either clicking on the "Add" button or a RecyclerView row on the first screen. The screen should contain an EditText for the word, an EditText for the definition, four radio buttons for the four parts of speech and a button that the user can use to save the dictionary entry. All of these elements should be vertically stacked. At a minimum, there should be at least 4 parts of speech that the user should be able to choose from, but if you'd like to add more, you are free to. See the attached screenshot.

- If the user is adding a new word to the dictionary, then when they navigate to the Add New Word screen, all the fields should be blank and none of the radio buttons should be selected. If the user clicks on the "Save" button, you must verify that both the "Word" and "Definition" fields are not blank and that exactly one part of speech is selected. If any of these conditions are not met, you should display an error message to the user. If all of these conditions are met, then the new word should get added to the dictionary. See the attached screenshot. 

NOTE: If the user tries to add a word to the dictionary that already exists, the operation should be ignored and no changes should be made to the dictionary

- If the user is modifying an existing word in the dictionary, then when they navigate to the Modify New Word screen, the fields should be populated with the selected word and its definition. The radio button associated with the word's part of speech should also be selected. The word field should *not* be modifiable. The only fields that the user should be able to modify are the definition and the parts of speech. If the user clicks on the "Save" button, you must verify that the "Definition" field is not blank and that a part of speech is selected. If any of these conditions are not met, you should display an error message to the user. If all of these conditions are met, then update the existing dictionary entry with the new data. See the attached screenshot.

- Do not worry about adding an "Up" button at the top left of the screen. If a user would like to go back to the Main screen, they should just press the back button

Bonus Points (+15)

I will be assigning 15 additional bonus points to anyone who attempts to complete this assignment using a database migration. A database migration is usually performed after an application has already shipped and changes need to be made to the existing database (i.e. tables need to be added/removed, columns need to be added/removed/modified, etc). To complete this assignment using a migration, what you should do is fully implement the in-class example and install the app on your device. Once the app is installed, and you've verified that it is working properly, start making modifications to the database and adding in the new fields required for this assignment (definition & parts of speech) wherever needed. In order to receive credit, please follow the instructions provided here, so I can have proof that you actually performed the migration. 

Keep in mind that this is only an optional requirement, you're free to complete the assignment without performing a migration. In order to do that, just uninstall the app from your phone/emulator and make all the database changes you need before installing the app again.

Helpful Hints

Please keep in mind that the entities you write in Room must adhere to certain rules in order for Room to properly convert them to their corresponding tables. When creating an entity, it is important that your member variables, constructor parameters, and getters (if your member variables aren't public) all use the same naming convention. If you run into any issues, refer to this link and ensure that you've named your variables, constructor parameters, & getters using the approach suggested there.

When you are pre-populating your database with values, it is okay if the values that you enter are hard-coded inside the Java file. All other static strings used within the app, including those used in notifications, should be accessed from the strings.xml file

Android Asset Studio has a tool that can automatically generate notification icons of the correct size and color. Feel free to use it if you'd like: http://romannurik.github.io/AndroidAssetStudio/

When following Notifications tutorials online, they may suggest that you add implementation "com.android.support:support-compat:xx.x.x" to your dependencies. If you are not using the androidx libraries for Room, this is the correct dependency to add. If you are using androidx, ensure that this line is added to your dependencies instead: implementation "androidx.appcompat:appcompat:1.1.0-alpha04"

If you're going to be attempting the migration, here is a useful explanation of what migrations are and how they can be accomplished in Room: https://medium.com/google-developers/understanding-migrations-with-room-f01e04b07929


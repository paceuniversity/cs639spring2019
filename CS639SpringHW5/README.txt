Description:

Your objective for this assignment is to create a multi-screen application where tabs with arbitrary content can be dynamically generated. The first screen in your application will be used to generate the content for your tabbed layout. The second screen in your application will display your tabbed layout with the content generated from the first screen.

Screen One Requirements

- When a user starts the app, they should be presented with the first screen of your application. On the first screen should be an empty list (ListView or RecyclerView is fine to use) whose width spans the entire screen and whose height takes up all remaining screen space.

- Below the list should be a TextView with the text "Name:". To the right of this TextView should be an EditText that the user will use to enter in an animal's name. If the user tries to submit an empty name, show a toast notifying them of their error.

- Below the Name section should be a TextView with the text "Biography:". To the right of this TextView should be an EditText that the user will use to enter information about the animal with the specified name. If the user tries to submit an empty biography, show a toast notifying them of their error.

- Below the Biography section should be five animal images that the user can optionally select. The images must be stored in your res folder and have at least 4 different resolutions. All five images should have dimensions of 40dp by 40dp, be adjacent to each other and be horizontally centered. Tapping on an un-selected image should select it and tapping on an already selected image should de-select it.

- Below the Image section should be three buttons, all of which are adjacent to each other and horizontally centered.

- The first button should say "Add Tab". When the user clicks on this button, you should first perform validation on their input. While attaching an image to a row is optional, the name and biography fields are not. In addition to ensuring that the name and biography fields are not empty, you should also make sure that the user does not have more than 4 items in the list. Each list item represents a tab and, for this application, the user is not allowed to have more than 4 tabs. If the user is trying to add more than the allowed number of tabs, show a toast message notifying them of their error. If not, add a new row to the list. Be sure to clear both EditTexts and de-select the currently selected image (if selected) whenever a new row gets added.

- At minimum, each list row should contain two TextViews stacked on top of each other. The first TextView will display the animal's name and the second TextView will display the animal's biography. If the user selected an image to be added to the list row, then the image should appear to the left of the name and biography TextViews and a checkbox with the text "Include Image?" next to it should appear underneath the biography TextView. By default the "Include Image?" checkbox should be checked and when it is checked, the animal's image should be displayed. If the checkbox is unchecked, the image should be hidden from the list's row and not take up any available layout space. See the attached screenshot.

- The second button should say "Clear All". When the user clicks on this button, all rows in the ListView/RecyclerView should be removed from the list.

- The third button should say "Create Tabs". When the user clicks on this button, you should first ensure that they have created at least one tab. If they haven't, notify them of their error. If they have, take them to the next screen in your application. 

Screen Two Requirements

- Using what you've learned thus far about XML layouts, create a tabbed layout. For this assignment you should NOT use one of Android's built-in tabbed components like TabHost, TabWidget, or TabLayout. That will only make this assignment more difficult than it has to be. You also do not need to create a custom view for this assignment. Instead, your XML layout should have tabs at the bottom of the screen that are standard Android views and have a container view above these tabs that will serve as a container for your Fragment.

- When the user gets taken to your second screen, they should be presented with a tabbed layout that contains the data they entered on the first screen. Each tab should be labeled with the name of the animal specified on the first screen. For instance, if the user created three tabs on the first screen with the names "Spot", "Catty", & "Tweety", then on the second screen they should see three tabs at the bottom of the screen with the labels "Spot", "Catty", & "Tweety" that each take up the same amount of horizontal space and span the width of the screen. See the attached screenshot.

- By default, the first tab should be selected when the second screen launches and its content displayed in the tab's content area. In this case, the content area is just going to contain the animal's name with their biography underneath. If an image is associated with the animal AND the "Include Image?" checkbox for that animal was checked when the tabs were created, then it should appear above the animal's name. Whenever a user clicks on a tab, the tab's content area should be updated to reflect the name, biography and image (if available) of the newly selected tab. NOTE: In order to receive credit for this feature, you cannot simply update the TextViews within your Fragment. You MUST replace the fragment you're using for your tab's content every time the user selects a new tab. Any deviation from this will be penalized.

- Beneath the animal's biography in the tab content area should be a couple of buttons that appear only if necessary. These buttons should say "Next" & "Previous" and are to be used to provide additional* tabbed navigation. When the user clicks on the "Next" button, they are shown** the content of the next tab. When the user clicks on the "Previous" button, they are shown** the content of the previous tab. To receive full credit for this feature, these buttons should only appear when they're needed. For instance, if your application only has one tab, then neither button should appear. If your application has two tabs, then the "Next" button should only appear when the first tab is selected, and the "Previous" button should only appear when the second tab is selected. If your application has three tabs, then the "Next" button should only appear when the first tab is selected, the "Previous" button should only appear when the last tab is selected, and both buttons should appear when the second tab is selected. So on and so forth. See attached screenshots.

* The "Next" & "Previous" buttons are there for additional navigation. This means, that clicking on the tabs at the bottom of the screen is still going to be the primary way users switch between your tabbed content. This is just going to be an additional means for them to do so.

** Same rules as above apply when swapping out your tabbed content. To receive credit for this, you cannot simply update the TextViews in your Fragment. You MUST replace the fragment you're using for your tab's content every time the user navigates to a new tab. Any deviation from this will be penalized.

- Your second screen should contain an up navigation button so that the user can easily navigate to the first screen. 



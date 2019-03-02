Description:

Your objective for this assignment is to extend the Image Editor app from Homework 1. You're going to change the list of animals from a static list to a data-driven list that can be modified by the user.

Application Requirements:

- When a user starts your app, they should be presented with a list that contains 10 animals. It is okay if the rows in the list do not share the same height. Immediately below the list should be an EditText where users can add fun facts about a selected animal. To the right of the EditText should be a button that says "Add Fact". Below it, and at the bottom of the screen should be transformation buttons. The buttons used to rotate and flip the images should be aligned to the left and the buttons used to translate the images should be aligned to the right. (See Attached Screenshot)

- Each animal image should support the following densities (mdpi, hdpi, xhdpi and xxhdpi). This means that each image should have four different sizes. You can use Android Asset Studio to do this fairly easily.

- The size of the Rotate & Flip button can be as large as you like. The Translate buttons must all be 35dp x 35dp and positioned to resemble a directional pad. In the center of the directional pad should be a button used to reposition the selected image to its original location. (See Attached Screenshot)

- When a user clicks on a row, the animal's name, followed by a description of the selected animal should appear on the screen to the right of the animal image. To the right of the animal description, should be two buttons, stacked on top of each other: "Next Fact" and "Delete Fact". All descriptions and buttons in other rows should disappear. Clicking on a row that is already selected, should clear its selection and consequently make both its description and row buttons disappear.

- When a user clicks on "Next Fact", the next fun fact associated with the animal should replace the existing fun fact. For instance, if the first fun fact was "Fun Fact 1", the animal's description should say: "Animal Name\nFun Fact 1". After a user clicks on "Next Fact", the animal's description should say "Animal Name\nFun Fact 2". If there is only one fun fact associated with the selected animal, then the description should not change.

- When a user clicks on "Delete Fact", the currently selected fun fact should be deleted and the next fun fact in the list should be shown. If there is only one fun fact associated with the selected animal, then notify the user that they cannot delete the selected fun fact since an animal needs at least one fun fact.

- When an animal is selected, the user should be able to add more fun facts about the animal. They can do this by entering text into the EditText on the screen and clicking on "Add Fact". Whenever a fun fact is added to an animal, the text in the EditText should be cleared. There is no limit to the amount of fun facts that a user can add to a given animal. If no animal is selected, notify the user that they must select an animal before adding a fun fact. If there is no text in the EditText and the user tries to click on "Add Fact", notify them that they cannot add a fun fact with no text.

- To receive full credit for this assignment you are just required to implement the Rotate button's functionality. When a user clicks on the Rotate button, the selected animal's image should be rotated BY 90 degrees. In other words, 90 degrees should be added to the selected image's current rotation. For example, if the selected image is already rotated by 90 degrees, clicking the Rotate button again should make the selected image's rotation equal 180 degrees. The images of the other animals should remain unchanged. If no image is selected, present the user with a Toast message letting them know that they have to select an image before changing the rotation.
Extra Credit Requirements

- I've stressed the importance of online resourcefulness and being able to teach yourself things you haven't already learned. To further drive home that point, students will be awarded extra credit points for implementing this assignment using a RecyclerView instead of a ListView. 10 Additional Points.

- When a user clicks on the Flip button, the selected image should be flipped horizontally. Clicking on the Flip button while it's already flipped should unflip it. The images of the other animals should remain unchanged. If no image is selected, present the user with a Toast message letting them know that they have to select an image in order to flip it. 10 Additional Points.

- When one of the arrow buttons is clicked on, the image should move 10dp in the selected direction. For example, if the up arrow is clicked on, the selected image should be translated by 10dp upwards; if the left arrow is clicked on, the selected image should be translated by 10dp to the left, etc. If translating the image causes part of the image to be cut off, don't worry about it. When the button in the middle of the directional pad is clicked on, the selected image should have both its x-translation and y-translation set to 0. In other words, it should be moved back to its original position. During these translations, the images of the other animals should remain unchanged. If no image is selected, present the user with a Toast message letting them know that they have to select an image before changing the translation. 30 Additional Points.

Grading Distribution:
70% = Completion. An app that meets all of the above requirements will be considered code complete
10% = Use of RecyclerView. An additional 10 points if you're able to complete the assignment using a RecyclerView instead of a ListView.
10% = Implement Flip button functionality
30% = Implement all translation button functionality
10% = Code Clarity. Make sure your code is easy to follow and understand.
10% = Documentation/Comments. Provide occasional documentation to ensure that you fully grasp what your code is doing
10% = Efficiency/Number of Lines. The less lines of code you use, the better. Find a balance between readability and being concise.

Helpful Hints

- Call ListView.setItemsCanFocus(false) when configuring your Listview in Java

- Give both the "Next Fact" & "Delete Fact" button the following XML attributes: android:focusableInTouchMode="false" and android:focusable="false". These two hints enable Android to distinguish between a button click and a row click.

- If you decide to pursue extra credit, add the following method to your Activity or Fragment class and use it to convert dp into pixels in your code. For example, if you wanted to convert 10dp into pixels, you would call the method below like this: int tenDpAsPixels = dpToPx(10);

private int dpToPx(int dpValue) {
     return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
}
Description:

Your objective for this assignment is to create an Image Editor app that changes the rotation, scale and translation of a selected animal image on the screen.

Application Requirements
- The three animals in your app will be a bird, a cat and a dog and next to each animal image should be a brief description or a fun fact about it. The animals on the screen should be stacked vertically with the row containing each animal taking up the same amount of vertical space, regardless of what phone the user is using. To achieve full credit for this requirement, all three rows should occupy whatever screen space is not being used by the transformation buttons.
- When the app starts, all three animals should be visible to the user with their descriptions hidden. At the bottom of the screen should be transformation buttons. The buttons used to rotate and flip the images should be aligned to the left and the buttons used to translate the images should be aligned to the right. 
- The size of the Rotate & Flip button can be as large as you like. The Translate buttons must all be 35dp x 35dp and must be positioned to resemble a directional pad. In the center of the directional pad should be a button used to reposition the selected image. (See Attached Screenshot)
- When a user clicks on an animal image, the description of the selected animal should appear on the screen. All other animal descriptions should disappear.
- When a user clicks on the Rotate button, the selected image should be rotated BY 90 degrees. In other words, 90 degrees should be added to the selected image's current rotation. For example, if the selected image is already rotated by 90 degrees, clicking the Rotate button again should make the selected image's rotation equal 180 degrees.
- When a user clicks on the Flip button, the selected image should be flipped horizontally. Clicking on the Flip button while it's already flipped should unflip it.
- When one of the arrow buttons is clicked on, the image should move 10dp in the selected direction. For example, if the up arrow is clicked on, the selected image should be translated by 10dp upwards; if the left arrow is clicked on, the selected image should be translated by 10dp to the left, etc. If translating the image causes part of the image to be cut off, don't worry about it.
- When the button in the middle of the directional pad is clicked on, the selected image should have both its x-translation and y-translation set to 0. In other words, it should be moved back to its original position.
- Changing the rotation, flip, or translation of the selected image should have NO effect on any other images on screen. The buttons should have an effect on ONLY the selected image.
- If no image is selected, present a Toast message to the user letting them know that they have to select an image before choosing a transformation.

Grading Distribution:
70% = Completion. An app that meets all of the above requirements will be considered code complete
10% = Code Clarity. Make sure your code is easy to follow and understand.
10% = Documentation/Comments. Provide occasional documentation to ensure that you fully grasp what your code is doing
10% = Efficiency/Number of Lines. The less lines of code you use, the better. Find a balance between readability and being concise.

Helpful Hints
- Code examples for all of the image transformations specified in this assignment can be found online using a Google search. I'm aware that we have not covered how to flip, rotate or translate views in class; this is by design. This assignment is meant to gauge your problem-solving capabilities and online resourcefulness.
The method that you will use to for translating a view only accepts pixels as an argument but this homework assignment is asking you to translate the selected image by 10dp. To work around this, add the following method to your Activity or Fragment class and use it to convert dp into pixels in your code. For example, if you wanted to convert 10dp into pixels, you would call the method below like this: int tenDpAsPixels = dpToPx(10);

private int dpToPx(int dpValue) {
     return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
}
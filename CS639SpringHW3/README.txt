Description:

Your objective for this assignment is to create an application that receives attendance information (class date & number of students in attendance) from a user and plots the input data on a line graph.

Application Layout Requirements

- When a user starts the app, they should be presented with an empty graph (meaning just an x-axis & y-axis) whose width spans the entire screen and whose height is at least 200dp. The x-axis of the graph will represent time and the y-axis of the graph will represent the number of students who were in attendance on a given day. 

- Below the graph should be a TextView with the text "Date:". To the right of this TextView should be an EditText that the user will use to enter in a date. If the user tries to submit an empty or invalid date, show a toast notifying them of their error.

- Below the Date section should be a TextView with the text "Student Count:". To the right of this TextView should be an EditText that the user will use to enter the number of students present on the specified day. If the user tries to submit empty text or an invalid number, show a toast notifying them of their error.

- Below the Student Count section should be two buttons with the text "Add Data" and "Clear Data", respectively. Both buttons should be centered horizontally on the screen.

- When the user clicks on the "Add Data" button, the data that the user is trying to input should be validated (required validations mentioned above). Once validated, clear the EditTexts and add the parsed date & the student count to the LineGraphView.

- When the user clicks on the "Clear Data" button, all graph data should be cleared and the graph should be reset to its initial state.

- Below the "Add Data" and "Clear Data" buttons should be a TextView with the text "Show Lines". To the right of this TextView should be a checkbox. If the checkbox is checked, then each point on the graph should be connected with a line. If the checkbox is not checked, then the points on the graph should appear with no lines connecting them.

Custom View Requirements

- Create a custom view called LineGraphView. It should simply extend the View class.

- This view should accept three custom attributes: "pointColor", "maxColor", and "lineColor". The attribute pointColor will be used to specify the color of the points on the line graph in XML. If the user does not specify a pointColor in XML, then LineGraphView should use its own default. The attribute maxColor will be used to specify the color of the highest point on the line graph in XML. If the user does not specify a maxColor in XML, then LineGraphView should use its own default. Lastly, as its name implies, the attribute lineColor will be used to specify the color of the graph lines in XML. If the user does not specify a lineColor in XML, then LineGraphView should use its own default.

- The LineGraphView should have a method called addData() that takes in a date and a number of students as arguments. This data should be stored in some sort of data structure. If data is entered for a day that already exists, then the new data should replace the existing data.

- If there is no data to plot, LineGraphView should just show the x-axis and y-axis. See attached screenshot.

- If there is ONLY ONE data point to show, then LineGraphView should only draw one point with a radius of 5dp. The point should be the drawn with the color specified in XML or with a default if none was provided. Given the fact that there is only one data point, there is no need to highlight the point as the maximum value. Below the x-axis, centered horizontally with the point, should be the date associated with the data point. At the the bottom of the y-axis should be the number 0. At the top of the y-axis should be a maximum value for the graph. See attached screenshot.

- If there are two or more data points to show, then LineGraphView should draw each point on the graph with a radius of 5dp and with the color specified in XML or with a default if none was provided. The point with the maximum value should be drawn with the maxColor attribute specified in XML, or a default maximum color if none was provided. Each point must be drawn in the order of their dates. In other words, the point with the earliest date should be drawn furthest left on the graph. To the right of that point, should be the point with the second earliest date, so on and so forth. There should be equal spacing between each point and each point should have the same radius. See attached screenshot.

- If the "Show Lines" checkbox is checked, then lines should be drawn connecting the points of the graph. The line connecting each point should have a width of 3dp and should be drawn with the lineColor attribute specified in XML or with a default line color value if none was provided. See attached screenshot

- The y-position of each point on the graph should be proportional to the distance between the bottom of the y-axis and the top of the y-axis. Do NOT use fabricated heights. This means that if the top of the y-axis signifies 20 students, and you have a point that represents 10 students, then the y-position of the point should be half the distance between the bottom of the y-axis and the the top of the y-axis. 

- The line graph should only hold 5 data points. If you are at capacity and a user attempts to add an additional data point to the line graph, drop the data point with the oldest date. In other words, the line graph should only plot the five most recent dates that it is given. 

Extra Credit (40 points)

- Create an additional custom attribute named "pathColor". This attribute will be used to specify the color that the highlighted area underneath the graph should be in XML. If the user does not specify a pathColor in XML, then LineGraphView should use its own default.

- Below the "Show Lines" text and checkbox should be a TextView with the text "Highlight Integral". To the right of this TextView should be a checkbox. If the checkbox is checked, then highlight the area underneath the line graph. If the checkbox is not checked, then the area underneath the line graph should not be highlighted. See attached screenshot.

- Inside onDraw(), use the canvas' drawPath() method to create a path that highlights the area underneath the line graph. The path should start immediately underneath the first point and end immediately underneath the last point. The top of the path should follow the line graph and the bottom of the path should be the graph's x-axis. The path should be filled with the color specified in XML or a default if none was provided. See attached screenshot.

- Below the "Highlight Integral" text and checkbox should be a Seekbar that the user can use to modify the radius of the points drawn on the line graph. The minimum radius should be 5 and the maximum radius should be 10. Text should be included to the left and the right of the Seekbar that lets the user know what the minimum and maximum values are. As a user moves the seekbar from the left to right, the radius of the points on the graph should increase. Conversely, as they move the seekbar from right to left, the radius of the graph points should decrease. See attached screenshot.


Grading Distribution:
70% = Completion. An app that meets all of the above requirements will be considered code complete
25% = Highlight Integral Functionality
15% = Seekbar Functionality
10% = Code Clarity. Make sure your code is easy to follow and understand.
10% = Documentation/Comments. Provide occasional documentation to ensure that you fully grasp what your code is doing
10% = Efficiency/Number of Lines. The less lines of code you use, the better. Find a balance between readability and being concise.

Helpful Hints

- When drawing to the canvas, it's important to be aware of the draw order. Items that are drawn first to the canvas will appear behind items that are drawn later. This is known as z-order (https://en.wikipedia.org/wiki/Z-order). In other words, if you draw a red circle at one position and draw a green circle with the same radius at the same exact position, then the green circle will completely overlap the red circle. If you're drawing shapes to the canvas and you don't see them appearing, make sure you're a) not drawing outside of the canvas' bounds and b) not drawing over them with another shape.

- Call LineGraphView.invalidate() (https://developer.android.com/reference/android/view/View.html#invalidate()) whenever you do something that requires your line graph to be redrawn (i.e adding a new data point, removing all data points, showing or hiding lines, etc). invalidate() will notify the view that it should redraw itself.

- Place the method below in the LineGraphView class and use it to convert values from dp to pixels. Like I've mentioned in class, when dealing with views in Java, everything is done in pixels. What this means is that giving the point a radius of 5 in Java, will not guarantee that it will be the correct size on all devices. What you have to do is call the function below with a value of 5, and use the returned value as the radius of the point. i.e. int pointRadius = dpToPx(5);

private int dpToPx(int dpValue) {
     return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getContext().getResources().getDisplayMetrics());
}
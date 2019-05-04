Description

Your objective for this assignment is to interact with the Google Places API and the World Weather Online API to create an application that allows users to receive weather information for a specified place on a specific day.

Application Layout Requirements

- When a user starts the app, they should be presented with a search bar at the top of the screen. Below the search bar should be a TextView with the word "Date:" and an EditText that the user will use to enter a date of interest. Underneath the EditText, centered horizontally, should be a Search button. Centered horizontally beneath the Search button should be text that says "No Place Selected". See the attached screenshot.

- When the user taps on the search bar, the search bar should expand and overlay itself over the Activity. Typing letters into the search bar should query the Google Places API and return the names of matching places as an autocomplete list underneath the search bar. See the attached screenshot.

- When a user clicks on a place name from the list, the search bar and its accompanying autocomplete list should be dismissed. Once the overlaid search bar is dismissed, the screen should look as follows: The search bar on the screen should display the text of the selected place. Underneath the search bar should be an image of the selected place and the name of the selected place centered horizontally. If there is no image available for the place, then the image should not be displayed and the name of the selected place should just be centered horizontally. See the attached screenshot.

- Before any weather information can be displayed, the user must enter in a date no earlier than the current date and no later than 7 days into the future. The EditText in your app should have placeholder text that specifies what the required date format should be. See the attached screenshot.

- When the user clicks search, you must first verify that a place name and valid date has been supplied. You should also verify that the supplied date falls within the range specified above. If all of these conditions are met, then the user should be allowed to search for weather information; if not, the user should see a Toast message informing them of their error and not be allowed to search. 

- When valid weather information is returned from the World Weather Online API, the following elements should be added to your screen: Underneath the place name should be an image icon for the current weather, the current temperature in Fahrenheit, as well as a brief description of the current weather condition (e.g. Partly Cloud, Overcast, etc) all centered horizontally. If there is no weather icon available for the current weather, then do not display the icon and just display the current temperature and weather condition centered horizontally. See the attached screenshot.

- If a user taps on the search bar, they should be able to search for another place. Searching for another place should follow the aforementioned requirements. When a new place is selected with a valid date and the "Search" button is clicked, all place and weather information about a previously selected place should be replaced with the new place's information. See the attached screenshot.

Helpful Hints

This project requires you to interact with 3 Library APIs (Google Places, Picasso, Retrofit) and 1 Web API (World Weather Online). Below are helpful resources and tips for using Google Places, Retrofit & World Weather Online

Google Places
Getting Started. Be mindful that by default you are only allowed 1000 Google Places API calls a day. It should be very difficult to exceed that number for this project, but anything is possible.
Adding Autocomplete Place Picker. This explains how to add the Place Picker to your app. This is what you should use to search for places
Getting Place Photos. This explains how to retrieve photos about a particular place. You should use the method described here to load photos of a place instead of Picasso.

Retrofit
Retrofit Overview Page. This page gives short descriptions and code snippets of how to use some of Retrofit's most commonly used featurs. For this project, you should just need to make a GET request to the World Weather Online API. GET requests are covered under the "Request Method" & "URL Manipulation" sections.
Short Retrofit Tutorial. This page contains a short Retrofit tutorial that is somewhat similar to what we did in class. Ignore any mentions of Converters since they are not needed for this assignment. You should be working directly with the JSON that is returned from the request. In this tutorial they are also using a separate JSON library. You are free to use that if you'd like, but the default JSONObject API that we used in class can handle the response just as well. Keep in mind that this tutorial is NOT exactly like what we did in class. It is merely here as a reference. If you choose to follow it exactly, you do so at your own risk. The easiest way to do this assignment is to use the approach that we did in class.

World Weather Online
Developer Site. Login here. Create a developer account. This will give you trial access to their premium API for 60 Days. Use their Premium API URL to make all requests. Be mindful that you are only allowed to make 500 calls to their API per day
Local Weather API Documentation. Documentation for the Local Weather API. This is the API endpoint that you should be using for this assignment.

Postman
If you would like to examine the data you receive from the World Weather Online API, to help you better debug, you can do so by using Postman like we did in class. If you'd like to download Postman (it's free!), you can do so using this link: Postman Site

If you run into any issues connecting to the Internet in your app, make sure that you've added the Internet Permission to your application. See: Adding Permissions


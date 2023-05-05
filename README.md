
# EV Charging Station Finder for Catalonia
This Android app allows users to find the closest EV charging stations in Catalonia. It connects to an open data API provided by the Generalitat de Catalunya to get the locations of all the EV charging stations in the region and displays them on a map using the OpenStreetMap library. The app also shows the user's current location and provides directions to the closest charging station.

## Features
* View all EV charging stations in Catalonia on a map
* Get directions to the closest charging station from the user's current location
* Store all parsed charging points in a local database using Room
* View detailed information about each charging station (e.g. location, availability, cost)

## Installation
To install the app, follow these steps:

* Clone this repository to your local machine
* Open Android Studio and import the project
* Build the project and run it on an Android emulator or a physical device

## Usage
When you open the app, you will see a map of Catalonia with markers representing the locations of all the EV charging stations.

It automatically shows your current location on the map with a dark blue circle. Additionally, the closest EV charging station to your location is shown with a blue colored pin and a route line is displayed on the map showing you the directions for a car to get to it.

You can tap on the charging station pin to view detailed information about it, including its location, availability, and cost. To view the charging stations on the map, you can zoom in and out using pinch-to-zoom or double-tap gestures.

The app also uses Room to store all the parsed charging points in a local database. This allows for faster and more efficient access to the charging station information, even when the user is offline.

## API
This app uses the "EV charging stations in Catalonia" API provided by the Generalitat de Catalunya. The API returns a JSON object containing the location and other information about each charging station. The app uses the Retrofit library to make HTTP requests to the API and the Gson library to parse the JSON response.

## Libraries
This app uses the following libraries:

* OpenStreetMap for displaying the map and markers
* Retrofit for making HTTP requests to the API
* Gson for parsing the JSON response
* Room for storing the parsed charging points in a local database

## Planned Updates
The following features are planned for future releases:
* Filter charging stations by type (e.g. fast charging, slow charging)
* Save favorite charging stations for easy access
* Color-code markers based on the power output of the charging station (e.g. green for fast charging, yellow for slow charging)

## Contributors
This app was created by Marçal Fargas. Contributions are welcome! If you find a bug or have a feature request, please submit an issue or a pull request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

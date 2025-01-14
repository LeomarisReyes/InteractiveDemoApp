 ![version](https://img.shields.io/badge/version-1.0.0-pink)
 
## 🗒️ Interactive Android App

### Table of Contents:

- [General information](#general-information)
	 - [Description](#description)
	 - [Requeriments](#requeriments) 
- [Feature Demo](#feature-demo)

___

### General information

### Description 

The Interactive App, built with Kotlin and Compose for Android, displays a list of Colombian presidents from the API-Colombia. It includes search functionality, a paginated list, and the ability to view detailed information about each president. The app also features a customizable password generator, allowing users to define settings like length and inclusion of symbols or letter case.

Additionally, it supports both light and dark modes, providing an optimal user experience in any environment.

⚠ *This application was created specifically for technical testing purposes. All feature requirements are detailed below.* 

### Requeriments

Create an Android application with the following functionalities:

|No.| Description |
|--|--|
| 1. | **Api List:** Create a view that consumes a REST API (you can choose a free API). The view should display a paginated list of items. Additionally, it should have a search functionality that searches a specific parameter in the list of objects provided by the API. Upon selecting an item from the list, the application should open a screen to display the details of the selected item. |
| 2. | **Capitalizer generator:** Create a view with an input field. The user can enter a phrase in this field. The operation to perform on the phrase is as follows: convert the first letter of each word to uppercase and display it on the screen. Language-specific functions such as split, capitalize, filters, etc., cannot be used. | 
| 3. | **Password generator:** Create a view capable of generating random passwords. Passwords should be configurable with the following parameters: Length: Between 5 and 20 characters. - With or without uppercase letters , numbers and symbols - These parameters can be combined. Password generation should be done without using special language functions or external libraries. |

### Feature Demo 

In this session, you will explore visual resources designed to showcase each functionality of the app in a clear and engaging way.

The test requires three functionalities, so an intermediate menu was created within the app to provide a more user-friendly experience, allowing users to easily select each functionality as an option.


#### ➖ 1. Colombian Presidents: List, Search, and Details

To fulfill this requirement, a view was implemented that consumes the public Colombian data API, API-Colombia (https://api-colombia.com), specifically the presidents endpoint, which allowed me to display the list of presidents interactively. The features include:

* **Paginated List:**
The list of presidents is loaded in a paginated manner. Initially, only the first few presidents are shown, and as the user scrolls down, the next presidents are automatically loaded. This pagination optimizes data loading and visualization, preventing excessive memory consumption and improving the user experience.

* **Selecting a President:**
By selecting any president from the list, the user is taken to a screen that shows the full details of the selected president, such as their term, political party, and other relevant data.

* **Search Bar:**
The application includes a search bar that allows filtering the list of presidents by name. As the user types in the search field, the list automatically updates to show only the presidents whose names match the entered term.

**Public API Used:**
The data source used is API-Colombia, a public API with which I have collaborated on open-source contributions, providing information about Colombian presidents, among other Colombian-related data.	

#### ➖ 2. Capitalizer generator

A screen was developed where you can enter a phrase, and upon clicking the button, it converts the first letter of each word to uppercase while changing the remaining letters to lowercase, if necessary.

#### ➖ 3. Password generator

Finally, the Password Generator: This screen is designed to randomly generate a password, allowing you to customize the settings for the key. You can choose to include uppercase letters, lowercase letters, symbols, and set the desired password length.

### Built With

- [Compose](https://developer.android.com/jetpack/compose/) 
- [Compose navigation](https://developer.android.com/jetpack/compose/navigation?hl=es-419) 
- [Material](https://m3.material.io)
- [Coroutines](https://developer.android.com/kotlin/coroutines)  
- [Coil](https://github.com/coil-kt/coil)  
- [Retrofit](https://square.github.io/retrofit/)  
- [Dagger Hilt](https://developer.android.com/jetpack/androidx/releases/hilt?hl=es-419)  
- [Gson](https://github.com/google/gson)  

___

Thanks for reading! 💚💕 <br />

*@Composable<br />
fun Thanks(){<br />
    Text(text = "Leomaris Reyes")<br />
}<br />*


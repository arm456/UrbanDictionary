# UrbanDictionary
Android Application built using MVVM architecture for making a basic dictionary search.
The app displays a list of definitions for the word typed in the SearchView with number of thumbs up and thumbs down votes.

It allows the users to sort the definitions by either thumbs up or thumbs down using Android switch. The API used for getting result for the word searched is : https://market.mashape.com/community/urban-dictionary

# Assumptions:
•	Shows a progress indicator every time a search is being performed.

•	Searching any word, lists out all the definitions retrieved from the API without any sorting.

•	When the switch is checked it sorts the definitions based on the descending order of thumbsUp and vice versa.

•	Displays a SnackBar on receiving error response or due to network issues.


# Languages and Libraries used:
•	[Kotlin](https://kotlinlang.org/)

•	[Java](https://site.mockito.org/)

•	[Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) : ViewModel and LiveData

•	[RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)  -  Concurrency/Asynchronous Tasks

•	[Dagger 2](https://github.com/google/dagger)  -  Dependency Injection

•	[Retrofit](https://square.github.io/retrofit/) -  Network Calls

•	[Gson](https://github.com/google/gson) -  Serialization/Deserialization 

•	[Mockito](https://site.mockito.org/) - Unit Testing

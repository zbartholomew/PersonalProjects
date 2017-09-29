# Personal Projects

## SimpleCalculator
  - One of the first apps I created
  - Very simplistic calculator with only the bare necessities of operators and abilities
  
## Top10Downloaded
  - Utilizes Apple's RSS feed - https://www.apple.com/rss/ to retreive XML data
  - AsyncTask allows us to retrieve the data on a background thread
  - Custom ArrayAdapter - FeedAdapter - that utilizes generics and viewholder subclass for efficiency
  - Menus allow the user to specify between top 10 and 25 paid and free apps, as well as, songs.
  
## YouTubePlayer
  - Utilizes YouTube's API - https://developers.google.com/youtube/android/player/
  - Play videos and launch different activities

## FlickrBrowser
  - Utilizes Flickr's API - https://www.flickr.com/services/feeds/docs/photos_public/
  - Utilizes Picasso - image downloading and caching library - http://square.github.io/picasso/
  - Allows a user to search for tags to see relevent photos
  - Downloads data using AsyncTask
  - Parsing JSON data
  - Callback functions and defining Interfaces
  - RecyclerView and RecyclerViewAdapter
  - Uses GestureDetector - long click gets you to photo details
  - Follows Material Design guidelines
  
  ![classandflowdiagram](https://user-images.githubusercontent.com/16873263/27619889-894743be-5b7a-11e7-96dd-df54c947c94a.png)
  
## TaskTimer
  - Utilizes SQLite database to store task data
  - Two activities MainActivity and AddEditActivity
  - Two Fragments MainActivityFragment and AddEditActivityFragment
  - Support for two pain mode for landscape viewing on phone and better support for tablets
  - CursorRecyclerViewAdapter that utilizes a ViewHolder
  - The data package includes TasksContentProvider, TasksContract, and TasksDbHelper
  - Menus offer the add task functionality
  - Support for API 16 and beyond
  - Future goals:
    - Add better colors to UI
    - Add features for timing tasks and recording of durations
    
## RedditLurker
  - Follows the Model View ViewModel (MVVM) design 
  - The Libraries used:
    - **[RxJava 2](https://github.com/ReactiveX/RxJava)** - Mainly for databinding
    - **[Retrofit](http://square.github.io/retrofit/)** - Reddit API calls
    - **[Dagger 2](https://google.github.io/dagger/)** - Dependency Injection
    - **[Picasso](http://square.github.io/picasso/)** - Image Loading
    - **[AndroidAnnotations](http://androidannotations.org/)** - Reduce boilerplate code
    - **[Lombok](https://projectlombok.org/setup/android)** - Eliminate getter/setter boilerplate
  - Retrieves the top posts from Reddit and displays them in an endless scrollable RecyclerView.
  - Future goals:
    - Add clickable items that show comments sections
    - Update UI
    - Search features
    
## Inventory Management System
- Backend/Business logic to keep track of inventory levels
- Unit tests
- Support for multiple clients and utilizes thread safe picking and restocking of products
- Future goals:
  - Add front end, where users will be able to login as a picker or restocker to accomplish their job
  - Add SQL database to keep track of inventory levels
  
# eCommerceExample
- Full Stack Web App
- Utilizes ReactJS front end and NodeJS backend with MongoDB database
- First ReactJS app to demonstrate a simple eCommerce Website


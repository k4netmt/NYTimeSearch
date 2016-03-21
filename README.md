# NYTimeSearch
Spent time: 30 hours
* [x] User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results.
* [x] User can configure advanced search filters such as:
* [x] Begin Date (using a date picker)
* [x] News desk values (Arts, Fashion & Style, Sports)
* [x] Sort order (oldest or newest)
* [x] Subsequent searches will have any filters applied to the search results.
* [x] User can tap on any article in results to view the contents in an embedded browser. 
* [x] User can scroll down "infinitely" to continue loading more news articles. The maximum number of articles is limited by the API search. 
The following advanced user stories are optional but recommended:
* [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText. 
* [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* [x] Improve the user interface and experiment with image assets and/or styling and coloring (1 to 3 points depending on the difficulty of UI improvements)
* [x] Use the RecyclerView with the StaggeredGridLayoutManager to display improve the grid of image results (see Picasso guide too)
* [x] For different news articles that only have text or have text with thumbnails, use Heterogenous Layouts with RecyclerView.
* [x] Apply the popular ButterKnife annotation library to reduce view boilerplate.
* [x] Use Parcelable instead of Serializable using the popular Parceler library.

![Video Walkthrough](https://github.com/k4netmt/NYTimeSearch/blob/master/nytimes.gif)

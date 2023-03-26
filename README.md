#Description
Collection of several web-scraping practice projects.
Two dedicated sites that were made to be sites are from: http://toscrape.com/

Additionally, practice of Spring boot + React will be used. 

#Notes
Document is from the org.jsoup.nodes package
- is a class 
- Represents the HTML document
- represents the entire HTML or XML document --> provides access to the document data

Jsoup
- working with real-world HTML 
- has API for fetching URLs and extracting & manipulating data using HTML5 DOM methods and CSS selectors

Class Jsoup (documentation: https://jsoup.org/apidocs/org/jsoup/Jsoup.html)
- static methods ==> usability methods 

Class Connection 
- creates a new Connection (session) with the defined request URL.
- Used to fetch and parse a HTML page
- get() method which is used to execute a request as a GET and fetch the HTML document

Class Element
- the HTML element 
- can manipulate the node with methods similar to JS node manipulation in the DOM

Class Elements
- extends ArrayList<Element>
- hence Elements holds a collection of Element

## Anti-Scraping Systems
Some anti-scraping systems will block HTTP requests if they do not have HTTP headers.

How to avoid blocking techniques:
1) Always set the User-Agent header (identifies the applicaiton, OS, and vendor)


Garrett Krohn Next Steps

===Instructions===
main method is in the app file.  The user will be asked to select a guest, a company, 
and a template (or create your own).  The final message will be printed after all 
selections have been made.

===Design Decisions===
- I set up the packages to include: an app, a view, a controller, a service layer,
and a seperate dao for each type of object (Guest, Company, and template).  In each
of the Dao layers I read the JSON files and returned the data as a JSONArray to the
controller layer, displayed the options, and asked the user for the desired item.
For the message templates, I used StrSubtitution to enable the use of variables from
the data within a String.  This allowed me to have all the templates stored as Strings,
as well as the ability to have the user enter a new template.

===Language Decision===
I used Java, because it is the language I am most proficient in.

===Process for Verification===
Throughout the coding of this application, at each step along the way I tested the 
results to make sure that I was getting what I wanted.  So while I didn't write any
unit tests, I tested each method as I went along.

===Future Ideas===
I would like to have added a way to persist the new inputted template from the user.
For that matter, it would be nice to be able to have CRUD capability with the templates.





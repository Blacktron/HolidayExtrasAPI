# How to install the app
1. Download Apache Tomcat (32-bit/64-bit Windows Service Installer) from here - https://tomcat.apache.org/download-90.cgi
2. Install (you can follow the steps in this video https://www.youtube.com/watch?v=pKMgr8uNvGM).
3. Download the war file from the repository (HolidayExtrasAPI.war).
4. Configure user in order to be able to access the Manager App section.
  Open tomcat-users file with Notepad++ for example and add the following line 
  <user username="admin" password="admin" roles="manager-gui" /> in the tomcat-users section. Save the file and
  then you'll be able to access the Manager App section with the credentials provided in the line above.
5. Run the server.
6. Open Manager App section and login with the credentials.
7. In the WAR file to deploy section click Choose File and open the downloaded war file.
8. Click Deploy and HolidayExtrasAPI application will appear in the list above.
9. Click it in order to open the application.

# How to use the app
1. Enter value for user ID
2. Enter value for email
3. Enter value for given name
4. Enter value for family name
5. Click Add User (Note: if there is already a user in the system with the provided user ID, an error message will appear).
6. Click Show All - it will display a table with the data of all currently existing users in the system

Against each entry there is an Edit and Delete button. The Delete button will remove the user from the system and it's data will
be removed from the table as well. On clicking the Edit button the data of the user will populated in the form above. Please note
that user ID field will be disabled, all other fields are editable. Click Save Changes to save the changes and Cancel Edit if you
decide not to edit the data of the user.

You can also search for users by typing search criteria in the search field and click Search. All users which contain the provide
search criteria in their first or last name will be displayed in the table. For examlpe, you put vik in the search fields, users
with first name Viktor or last name Viktorov will appead in the table.

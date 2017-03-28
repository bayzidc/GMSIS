# SE14
EECS SoftEng 2016 Team 14 Repository


Login credentials: 

Admin: 
User ID: 10000 
Password: pass1

Day-to-day: 
User ID: 10001 
Password: pass2

Authentication:

-How to add a new user?
Enter firstname, surname, password and select the checkbox if you are creating an admin user otherwise leave the checkbox unchecked if it's a day-to-day user.

-How to edit an existing user?
Select a row on the table view and click the "Edit User" button, which will show the table view details on the text fields, after making the changes click the "Update Details" button.

-How to delete an existing user?
Select a row on the table view and click the "Delete User" button and then confirm the pop-up window.


Customer Account:


Vehicle Record:
-How to add a vehicle?
Once on the 'Vehicle Records' page, there will be a button named 'Add Vehicle'. Click on this button and you will be redirected to another page whereby the user fills in their vehicle details. Firstly, select the customer name from the customer names combo box. You should see your customer ID pop up alongside the customer name you have selected. Enter all of the vehicle details as shown on the page. In regards to the warranty, if you select yes, you must enter the name and address of the warranty company as well as the warranty expiry date. However, if you select no, you will not be able to fill in any of those details. Once you have completed all the fields correctly, you can proceed and add a vehicle by clicking the 'Add Vehicle Entry' button which will ask you for confirmation of adding the vehicle. If you have made mistakes in entering your details, simply click the 'Clear all' button which will clear all the details for you.

-How to edit a vehicle?
There is a table on the 'Vehicle Records' page which lists all the vehicles added onto the system. To edit a vehicle, simply select the row with your details and click on the 'Edit Vehicle' button. This will redirect you to the 'Vehicle Entry' page which will set the form with all of your previous details. However, you will notice that you cannot edit the customer name, vehicle ID and customer ID. Once you have made the changes you want, click on the 'Make Changes' button and will ask you for confirmation of editing the vehicle. This will update your details and redirect you to the 'Vehicle Records' Page. You can then see that on the tableview, the changes you have made will be shown. If you have made mistakes in entering your details, simply click the 'Clear all' button which will clear all the details for you except the customer name, vehicle ID and customer ID.

-How to delete a vehicle?
There is a table on the 'Vehicle Records' page which lists all the vehicles added onto the system. To edit a vehicle, simply select the row with your details and click on the 'Delete Vehicle' button. You will be alerted with a confirmation to delete the vehicle you have selected. Press 'OK' and your vehicle will be deleted from the system.

-How to search for a vehicle?
To search for a vehicle, there is a combo box and a search field on the 'Vehicle Records' page. To filter the list of vehicles in the table, simply select from the combo box what you want to search by and partially or fully enter what you want to search for in the list of vehicles. You can either search by make, full/partial registration number and vehicle type. Once you have made preferences on your search, the list of vehicles in the table will filter according to your search preference.

-How to view vehicle information?
On the 'Vehicle Records' page, to view the vehicle information of a vehicle, simply select the table row you wish from the list of vehicles, and above you should see a text area which automatically shows all the vehicle information as well as the customer it belongs to.

-How to view customer/booking information for a vehicle?
On the 'Vehicle Records' page, to view the customer/booking information of a vehicle, simply select the table row you wish from the list of vehicles and click the 'View Cust/Book Info' button. There is a table named 'Customer & Booking Info' and this will display the name of the customer, their vehicle reg number, their booking dates and the total cost of the booking.

-How to view parts information for a vehicle?
On the 'Vehicle Records' page, to view the parts information of a vehicle, simply select the table row you wish from the list of vehicles and click the 'View Parts Used' button. There is a table named 'Parts Used Info' and this will display the parts used on the vehicle and the quantity.

-How to filter the booking dates for a customer?
Underneath the 'Customer & Booking Info' table, there are 3 ways to filter the table:
"View All Bookings" check box is the default to show all the bookings, 
"View Past Bookings" check box shows all past bookings, 
"View Future Bookings" check box shows all the future bookings. 

If you want to view the past and future bookings for a particular customer, simply select the table row you wish from the list of vehicles and click the 'View Cust/Book Info' button. Once you have done that, select either the 'View Past Bookings' checkbox or 'View Future Bookings' checkbox. 

NOTE : Selecting 'View All Bookings' will view all the bookings of all customers.

-How to add a booking from vehicle records?
If you would like to add a booking from the 'Vehicle Records' page,simply select the table row you wish from the list of vehicles and click the 'Add Booking' button. This will redirect you to the 'Bookings' page and will set the customer name, vehicle registration number and the mileage for you. However, if you wish to start from scratch, you do not have to select the table row from the list of vehicles and you can simply navigate to the bookings page through the button bar at the top. 


Diagnosis and Repair Booking:
-How to add a booking?
Select a customer from the customer combo box, then select vehicle registration number from the vehicle combo box where all the specific vehicle registration numbers for that customer will load up. As soon as you select the vehicle registration number, the previous mileage recorded will show up on the text field, which can you updated. Now select a mechanic from the combo box and choose a date. As soon as you choose a date, the start time combo box will load up all the available start times, then after selecting a start time the end time combo box will load up all the available end times. After all the entries are complete click the "Sumbit Booking" button. 

-How to edit a booking?
Select a row on the table view and click the "Edit Booking" button, which will show the table view details on the text field and combo boxes, after making the changes click the "Update" button.

-How to exit the edit mode?
If you selected a row and clicked "Edit Booking" and you change your mind, you can click the "exit edit mode".

-How to delete a booking?
Select a row on the table view and click the "Delete Booking" button and then confirm the pop-up window.

-How to search for a booking?
You select from customer name, registration number or make from the combo box and then you start typing on the text field, on key release the booking table view will filter according to what you enter.

-How to filter booking dates in the table for customers?
Underneath the booking table view there are 9 ways to filter the table view:
"Show all" check box is the default to show all the bookings, 
"Past bookings" check box shows all past bookings, 
"Future bookings" check box shows all the future bookings. 
"Within next hour" check box shows bookings that starts within an hour from now. 
"Today's booking" check box shows all bookings for today.
"Bookings For This Month" check box shows all the bookings for this current month.
"Next closest booking date for each vehicle" check box shows the very next future booking date for each vehicle in the table view.
"Filter by a month" combo box shows months January-December which can be selected to filter the table view by any month.
"Filter by a date" this allows the table view to be filtered by a specific date selected.

-Add parts button:
You select a row from the table view and click "Add Parts", which will initiate add part on the "Parts Module". It will load up the booking id and install date on the parts used page.

-Clear all button:
Clears all the combo boxes and text fields.


Parts Record:

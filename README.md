## Billable Hour Tracker

**What will the application do?**
As the name suggests, the application will track time spent on tasks attributable to clients through time entries. 
Tasks can put under distinct categories, and each category can have its own billing rate.
Users will be able to add multiple clients, and multiple categories for each client.
Clients, billing categories, and time entries can be added, edited, and removed as desired.

By the end of the four phases of this project, my goal is to have a robust application that will be capable of recording time entries,
but also display comprehensive reports based on those time entries.

In the future roadmap after CPSC 210, my plan is to include automatic time tracking based on the user's active window, upon being able to upgrade the project to a more recent version of Java.


**Who will use it?**

- Public Accountants
- Lawyers
- Other professions that bill based on time spent on work done


**Why is this project of interest to you?**

I am a former accountant that used to work in the Big 4 providing services to clients. 
Tracking time was very tedious and time-consuming itself, and I've always wanted to create an application with more quality of life enhancements than what is currently available.



## User Stories
Phase 1
- As a user, I want to be able to create clients I want to track time entries for.
- As a user, I want to be able to view the clients I have created.
- As a user, I want to be able to edit clients I have already created.
- As a user, I want to be able to removed clients I am no longer working for.
- As a user, I want to be able to create multiple billing categories for each client.
- As a user, I want to be able to view the billing categories I have created.
- As a user, I want to be able to edit each billing category.
- As a user, I want to be able to remove a billing category.
- As a user, I want to be able to create time entries associated with each billing category.
- As a user, I want to be able to view the time entries I have created for each billing category.
- As a user, I want to be able to modify time entries I have already created.
- As a user, I want to be able to remove time entries I have created.

Phase 2
- As a user, I want to be able to save my client book.
- As a user, I want to be able to save my billing categories.
- As a user, I want to be able to save my time entries.
- As a user, I want to be able to load my client book.
- As a user, I want to be able to load my billing categories.
- As a user, I want to be able to load my time entries.

Phase 3
- As a user, I want to be able to view clients added, edited, removed in a GUI panel.
- As a user, I want to be able to view billing categories added, edited, removed in a GUI panel.
- As a user, I want to be able to view time entries added, edited, removed in a GUI panel.
- As a user, I want to be able to load and save the state of the application.

Phase 4: Task 2
- Option 1: Test and design a class in your model package that is robust
- BillingCategory was made robust: in particular - the constructor and setName(), and setRatePerHour() methods.
- The methods throwing checked exceptions are tested in BillingCategoryTest
    
Phase 4: Task 3

At present, it is clear from the UML diagram that there is a great deal of coupling. Part of this is due to the passing of dependencies between a number of classes that then in turn store them as fields, before passing them again. There are 3 primary data collections that are responsible for this, the ClientBook, BillingCategories, and MasterTimeLog ArrayLists that drive the entire application. Currently these live at the top level inside the MenuTabs object which then passes a reference to the ArrayLists down to every other part of the application that needs them. 

Instead, given more time, I would choose to implement a Model View Controller pattern with the Observer pattern as the basis. This would increase the separation of concerns between the model package and UI. It would also result in cleaner code such that MenuTab wouldn't need to pass around ClientBook, BillingCategories, and MasterTimeLog. Instead, these objects can be made into subjects that observers in the UI package can register themselves to listen to.

There is also a great deal of redundant code in many of the classes that behave similarly, but regarding different issues. This indicates low cohesion. Examples of this can be seen when looking at the different SplitPanes (Client, Billing, Time). As they have many similar operations, instead - they should all inherit from an Abstract super class that stores the common code, and declares abstract methods for anything the children need to override to provide unique behaviour. A similar issue is seen when looking at the MenuOptions for (Client, Billing, Time), and can be similarly solved using the same resolution.

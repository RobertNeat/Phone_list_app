# Phone_list_app
Android app that stores phone data such as:
- manufacturer,
- model,
- android version,
- website
inside room database.

Used features af android platform:
- Room Database - provides possibility for the application to store data permanently between application runs,
- DAO object - creates link between database and objects so the SQL will generate automatically,
- element repository - serves as the truth source for application, if there is need to edit/get data from database this class will be used for that,
- element view model - is used to present data longer thath the activity lifetime so there is less need for application to create new activities and data is persisted longet between specific activities,
- ElementListAdapter - is used for displaying all the elements from database, we dont know how many they are so the app will be efficient event with hundreds of records with reusing few rows in main activity,
- context menu - pulpet menu in right up side corner that hides option for deleting all elements (corrently the options are displayed in top bar without pulpet menu, but if there is no space they will hide under three vertical dots),
- Table Layout - so there will be learning with new layout to try (the Constraint Laoyut is used in previous project so now it's time for new one),
- FAB button - floating action button with plus icon on it, so there will be possibility to launch secons edit/add activity without need to create bottom navigation bar for only one option or cramb add functionality under cintext menu,
- Item Touch Helper - so all list elements in Recycler View will support swipe to delete (there isn't implemented visual indicator what swipe does so maybe somewhere in future I will add delete indicator with icon),
-  implicit intent - to launch website from second activity in default web browser.


Development environment:
- Java SE Runtime Environment (build 1.8.0_371-b11)
- Android Studio Electric Eel | 2022.1.1

# App features:
- EditText is validated to not be empty,
- the website EditText is validated to start with https (so the implicit intent to show website will run without problems), if there wouldn't be https there should be list of allowed websites declared in application strusture,
- the actions on the list of phones are as follows:
    - delete all phones - from context menu pulpet menu with "delete all", deletes all elements in list,
    - delete one phone - swipe left to delete one phone, the recycler view will refresh and will update Room Database,
    - add one phone - use plus Floating Action Button, the second activity with empty fields will be launched and then you can input new phone data and alongside test if yout provided ulr is indeed one you wanted to specify,
    - edit one phone - click on list element to edit phone information, the second activity will be launched with selected element data so you can update information about choosen phone, after saving new information the recycler view will be updated,


# How to compile and run
To run application:
1. Download zip package
2. Extract package and open using Android Studio
3. If there is error with versions (pre Electric Eel) you should change version of the IDE in one of the gradle files and rebuild
4. Build application and run (either on VM Android or physical device, the development device is Samsung A53)

# Screenshots:
1. Application showing in app tray with specific icon:
<img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/app_tray.png" width="200"/>
2. First application launch (by default the few example records are acreated to show user how to use application):
<img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/after_launch.png" width="200"/>
3. Adding new phone:


<table>
    <tr>
        <td>    
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/after_launch.png" width="200"/>
        </td>   
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/add%20activity.png" width="200"/>
        </td>   
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/list_added.png" width="200"/> 
        </td>
    </tr>
</table>
        
4. Deleting phone (of all phones):
<table>
    <tr>
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/delete_on_swipe.png" width="200"/>
        </td>
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/after_swipe.png" width="200"/>
        </td>  
    </td>
</table>


5. Modify the existing phone:
<table>
    <tr>
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/editadd%20activity.png" width="200"/>
        </td>      
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/implicit_intent_samsung.png" width="200"/>
        </td>
        <td>
            <img src="https://github.com/RobertNeat/Phone_list_app/blob/main/pistures_res/list_edited.png" width="200"/>
        </td>   
    </tr>
</table>


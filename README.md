

##  Product: Fantastic Elevator Control System

### About/Overview
This is a project of a multi-elevator control system. This system can be applied to any building that has elevators, just like what we have in Northeastern Vancouver. The system can be customized by entering three parameters of a specific building: number of floors, number of elevators, and the maximum capacity of one elevator.

After initiation of the building's control system, the system serves as an interface that interact with users. This can be accomplished by a friendly graphical user interface. The manager of the building can decide to start the building's elevator control system , or to stop it. When the elevator control system is started, our end users can add requests to the building, and they will be assigned to the available elevators following a simple and effective algorithm. Our building can generate building reports, which reflects the status of both the building and the elevators,  at any time of the service.

As the designer of the system, I followed the MVC design pattern in the project. I have a well tested model of the building, a user-friendly view and a reliable controller that connect them. I also studied the given implementation of the elevator class so I know how to use it.



### List of features

#### Initialize the building
* Define the number of floors of the building
* Define the number of elevators of the building
* Define the number of people (capacity) an elevator can take
* If no parameters are given, we have a default setting that can be used for demo purpose.
* If there is an invalid parameter given, we will give an error message as feedback.

#### Control the building
* Start the building system
* Stop the building system
* Add requests to the building system
* Remove all requests from the building system
* Step the building
* Quit the Graphical User Interface
* Display the building's status as well as all elevator's status
* Update the building's status as well as all elevator's status
* Display a system message as a feedback of user input


### How To Run
1. Open your terminal, navigate to the folder that contains the JAR file. 
For example, ```cd res```

2.  Use command
 ```java -jar final2jar.jar <number of floors> <number of elevators> <number of people>``` 
 to run the JAR file.
 
	 Or, use ```java -jar final2jar.jar``` to run the JAR file with default parameters.

3. parameters explained: 
	* number of floors : an integer in range [3, 30] (inclusive).
	* number of elevators : an integer in range [1, 10] (inclusive).
	* number of people : an integer in range [3, 20] (inclusive).

4. The three parameters should be separately by one empty space.

5. If invalid input is given, an error message will be displayed, indicating the range of the parameters or the other errors.

### How to Use the Program
* After initializing the building with parameters in the command line, a graphical user interface will show up on the screen.
* From the "Building Status" section , we can 
	* confirm the passed parameters are correct
	* see the current building status is "out of service"
	*  see the current up requests and down requests are empty
* From the "Elevators Status", we can see all elevators are out of service.
* Currently we can use the "Start Building" button and the "Quit" button in the "Elevator Control" section. Other buttons being pressed will generate an error message at the bottom, the "System Message" section.

* If we follow the system message and hit the "Start Building" button, the building is started, and we can add or remove requests, step the building, or stop it.
	* If the building is stopped, we can hit the "Start Building" button to restart it.
	* If the building is stopping (not taking requests , but not fully stopped), pressing the "Start Building" button will lead to an error message.

* When we add a request:
	* we insert the start floor in the start floor field, end floor in the end floor field
	* hit the "Add Request" button
	* the request will show in the "Building Status" section. 
	* if we hit "Step Building" button,  the request will be fetched by an elevator and the elevator will display its updated stop plan.
	* If invalid requests are given, there will be an error message at the "System Message" section telling why.
* After adding a request, press the "Step Building" button to see the elevator control system process the request, and see elevators move to the next state.
* When we hit "Remove Request" button, it simply empty the two requests list in the "Building Status" section. No elevators will take the emptied requests.
* Whenever a button is pressed, there will be a feedback confirming the action or an error message indicating what is wrong. Also, the building status will update after every button being pressed.
* When the user is done with the elevator control system , hit "Quit" button and the program will stop.

### Design/Model Changes
This is my final project part 2. The design is almost the same as my part 1 design, which can be found in ```res/final_design.pdf``` .

I added more error handling and provided useful message to my users in the GUI. This is improved, way better than my console output in the first part of this project.

### Assumptions
* I assume that each request has only one passenger, instead of multiple passengers when calculating the capacity of an elevator.
* I assume that the "stop elevator system" is used when an emergency is happening, so I choose to disregard all the unprocessed requests. It is possible that some people inserted their requests and got accepted at the time, but no elevators are going to pick them up(This user experience is not good, but I hope they can hear what's going on in the building and use the stairs to run away).
* Also I will empty all floor stop plans of the elevators so if there are people inside the elevator, they will be sent to the ground floor directly.
* I assumed that after pressing the "Stop Building" button and some steps, the building will take another step to realize all the elevators are out of service, then change its own status to "out of service". So the building is one step slower than all the elevators.

### Limitations
* Again, I have to assume each request has only one passenger for this project. So the capacity calculation is not optimal. Ideally, the elevator can count the people inside and alert if it is at capacity (send a boolean to the building system).
* This elevator system cannot display each person's "reachable floors" like our building in Northeastern Vancouver. All it takes are two integers(if they are the same, or they are out of bound, an error message will be displayed). If I can have a map of people and their "reachable floors", I can only display those floors as buttons, instead of text fields.
* About the algorithm that allocate the requests, currently it is not very efficient. I believe I can learn some elevator algorithms that minimize the wait time of the passengers, or minimize the traveling distance of elevators to save some electricity.
* Due to the time constraint and my "art ability", the GUI is functional and minimal. If possible, I wish to add some visual display of the positions of the elevators. And I realized that I love the models. In the future I need to put more efforts on the front end. 

### Citations
	Documentation of Package javax.swing
	https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html


package main;

import building.Building;
import java.util.Scanner;
import scanerzus.Request;

/**
 * The driver for the elevator system.
 * This class will create the elevator system and run it.
 * this is for testing the elevator system.
 * <p>
 * It provides a user interface to the elevator system.
 */
public class MainConsole {

  /**
   * The main method for the elevator system.
   * This method creates the elevator system with fixed parameters and runs it.
   * You can step it, add requests, remove requests and so on.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // the number of floors, the number of elevators, and the number of people.
    // These values are fixed for now.

    final int numFloors = 11;
    final int numElevators = 8;
    final int numPeople = 3;

    boolean keepWorking = true;


    String[] introText = {
        "Welcome to the Elevator System!",
        "This system will simulate the operation of an elevator system.",
        "The system will be initialized with the following parameters:",
        "Number of floors: " + numFloors,
        "Number of elevators: " + numElevators,
        "Number of people: " + numPeople,
        "The system will then be run and the results will be displayed.",
        "",
        "Press enter to continue."
    };

    for (String line : introText) {
      System.out.println(line);

    }

    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
    Building building = new Building(numFloors, numElevators, numPeople);


    while (keepWorking) {

      System.out.println("current building status is:\n");
      building.getBuildingReport().generateLog();


      String[] commandsText = {
          "Hint:",
          "Press 's' to start the building.",
          "Press 'h' to stop the building.",
          "Enter 'r startFloor endFloor' to add request.",
          "Press 'st' to step the building.",
          "Press 'rm' to remove all requests.",
          "Press 'q' to quit."
      };
      for (String line : commandsText) {
        System.out.println(line);

      }


      switch (scanner.next()) {
        case "s":
          try {
            System.out.println("Starting the building");
            building.startElevatorSystem();
          } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
          }
          break;


        case "h":
          System.out.println("Stopping the building");
          building.stopElevatorSystem();
          break;


        case "r":
          int startFloor = scanner.nextInt();
          if (startFloor < 1 || startFloor >= numFloors) {
            System.out.println(
                "Invalid start floor. Please enter a floor between 0 and " + (numFloors - 1));
            break;
          }
          int endFloor = scanner.nextInt();
          if (endFloor < 1 || endFloor >= numFloors) {
            System.out.println(
                "Invalid end floor. Please enter a floor between 0 and " + (numFloors - 1));
            break;
          }
          if (startFloor == endFloor) {
            System.out.println("Start floor and end floor cannot be the same.");
            break;
          }
          System.out.println("Adding request from floor " + startFloor + " to floor " + endFloor);
          building.addRequests(new Request(startFloor, endFloor));
          break;

        case "st":
          System.out.println("Steps the building:");
          building.step();
          break;

        case "rm":
          System.out.println("Removing all requests:");
          building.removeAllRequests();
          break;


        case "q":
          System.out.println("Quitting the system");
          keepWorking = false;
          System.out.println("Goodbye!");
          break;

        default:
          System.out.println("Invalid input");
      }
    }
  }
}
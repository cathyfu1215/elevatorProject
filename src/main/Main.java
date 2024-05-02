package main;

import building.Building;
import building.SwingBuildingController;
import building.SwingBuildingControllerInterface;
import building.SwingBuildingView;
import java.io.IOException;

/**
 * The driver for the elevator system with a GUI.
 * User can enter the number of floors, the number of elevators, and the number of people
 * and build the building system in the console.
 * Then, in the GUI, user can add requests, remove requests,
 * and step the system to see each elevator's status.
 */
public class Main {
  /**
   * Run a building system interactively on the console.
   *
   * @param args the command line arguments, which are the number of floors,
   *             the number of elevators, and the number of people
   */
  public static void main(String[] args) {

    //get parameters from the user's input from console
    //if we get no input or invalid input, use the default values

    int numFloors = args.length > 0 ? Integer.parseInt(args[0]) : 10;
    int numElevators = args.length > 1 ? Integer.parseInt(args[1]) : 8;
    int numPeople = args.length > 2 ? Integer.parseInt(args[2]) : 3;


    SwingBuildingView view = new SwingBuildingView("Fantastic Building System",
        numFloors, numElevators, numPeople);

    Building model = new Building(numFloors, numElevators, numPeople);

    SwingBuildingControllerInterface controller = new SwingBuildingController(model, view);
    controller.go();
  }
}

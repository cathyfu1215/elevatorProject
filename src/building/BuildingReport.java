package building;

import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.List;
import scanerzus.Request;


/**
 * This is the reporting class for the building.
 */
public class BuildingReport {
  int numFloors;
  int numElevators;

  int elevatorCapacity;

  ElevatorReport[] elevatorReports;

  List<Request> upRequests;

  List<Request> downRequests;

  ElevatorSystemStatus systemStatus;

  /**
   * This constructor is used to create a new BuildingReport object.
   *
   * @param numFloors        The number of floors in the building.
   * @param numElevators     The number of elevators in the building.
   * @param elevatorCapacity The capacity of the elevators.
   * @param elevatorsReports The status of the elevators.
   * @param upRequests       The up requests for the elevators.
   * @param downRequests     The down requests for the elevators.
   * @param systemStatus     The status of the elevator system.
   */
  public BuildingReport(int numFloors,
                        int numElevators,
                        int elevatorCapacity,
                        ElevatorReport[] elevatorsReports,
                        List<Request> upRequests,
                        List<Request> downRequests,
                        ElevatorSystemStatus systemStatus) {
    if (numFloors < 3 || numFloors > 30) {
      throw new IllegalArgumentException("Number of floors must be between 3 and 30");
    }
    if (numElevators < 1 || numElevators > 10) {
      throw new IllegalArgumentException("Number of elevators must be between 1 and 10");
    }
    if (elevatorCapacity < 3 || elevatorCapacity > 20) {
      throw new IllegalArgumentException("Elevator capacity must be between 1 and 20");
    }
    this.numFloors = numFloors;
    this.numElevators = numElevators;
    this.elevatorCapacity = elevatorCapacity;
    this.elevatorReports = elevatorsReports;
    this.upRequests = upRequests;
    this.downRequests = downRequests;
    this.systemStatus = systemStatus;
  }

  /**
   * This method is used to get the number of floors in the building.
   *
   * @return the number of floors in the building
   */
  public int getNumFloors() {
    return this.numFloors;
  }

  /**
   * This method is used to get the number of elevators in the building.
   *
   * @return the number of elevators in the building
   */
  public int getNumElevators() {
    return this.numElevators;
  }

  /**
   * This method is used to get the max occupancy of the elevator.
   *
   * @return the max occupancy of the elevator.
   */
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  /**
   * This method is used to get the status of the elevators.
   *
   * @return the status of the elevators.
   */
  public ElevatorReport[] getElevatorReports() {
    return this.elevatorReports;
  }

  /**
   * This method is used to get the up requests for the elevators.
   *
   * @return the requests for the elevators.
   */
  public List<Request> getUpRequests() {
    return this.upRequests;
  }

  /**
   * This method is used to get the down requests for the elevators.
   *
   * @return the requests for the elevators.
   */
  public List<Request> getDownRequests() {
    return this.downRequests;
  }

  /**
   * This method is used to get the status of the elevator system.
   *
   * @return the status of the elevator system.
   */
  public ElevatorSystemStatus getSystemStatus() {
    return this.systemStatus;
  }

  /**
   * This method is a string representation of the BuildingReport.
   *
   * @return the string representation of the BuildingReport.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Building Report\n");

    //show the number of floors
    sb.append("Number of Floors: ");
    sb.append(numFloors);
    sb.append("\n");
    //show the number of elevators
    sb.append("Number of Elevators: ");
    sb.append(numElevators);
    sb.append("\n");
    //show the elevator capacity
    sb.append("Elevator Capacity: ");
    sb.append(elevatorCapacity);
    sb.append("\n");

    //show the status of the elevator system
    sb.append("Elevator System Status: ");
    sb.append(systemStatus.toString());
    sb.append("\n");

    //show list of upRequests
    sb.append("Up Requests: [(").append(upRequests.size()).append(")]");
    for (Request request : upRequests) {
      sb.append(request.toString());
      sb.append(" ");
    }
    //show list of downRequests
    sb.append("\nDown Requests: [(").append(downRequests.size()).append(")]");
    for (Request request : downRequests) {
      sb.append(request.toString());
      sb.append(" ");
    }
    //show list of elevatorReports
    sb.append("\nElevator Reports: \n");
    for (ElevatorReport report : elevatorReports) {
      sb.append("elevator").append(report.getElevatorId()).append(": ").append(report.toString());
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * This method is used to generate a log for the building.
   * It will print the status of the building to the console.
   * @return the string representation of the BuildingReport.
   */
  public String generateLog() {
    System.out.println("****************************");
    System.out.println(this.toString());
    System.out.println("****************************");
    return "****************************\n" + this.toString() + "****************************\n";
  }

}

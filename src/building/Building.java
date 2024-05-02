package building;

import building.enums.Direction;
import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import scanerzus.Request;


/**
 * This class represents a building that has several elevators.
 * This building will accept requests and distribute them to the elevators.
 * The building will also update the building report.
 * The building will also start and stop the elevator system.
 */
public class Building implements BuildingInterface {


  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;
  private List<Request> upRequests = new ArrayList<>();
  private List<Request> downRequests = new ArrayList<>();
  private final List<Elevator> elevators = new ArrayList<>();
  private ElevatorSystemStatus elevatorSystemStatus;


  /**
   * The constructor for the building.
   *
   * @param numberOfFloors    the number of floors in the building.
   * @param numberOfElevators the number of elevators in the building.
   * @param elevatorCapacity  the capacity of the elevators in the building.
   * @throws IllegalArgumentException if the number of floors, number of elevators,
   *                                  or elevator capacity is invalid.
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity)
      throws IllegalArgumentException {
    if (numberOfFloors <= 2 || numberOfFloors > 30) {
      throw new IllegalArgumentException("Number of floors should be in [3,30].");
    }
    if (numberOfElevators <= 0 || numberOfElevators > 10) {
      throw new IllegalArgumentException("Number of elevators should be in [1,10].");
    }
    if (elevatorCapacity < 3 || elevatorCapacity > 20) {
      throw new IllegalArgumentException("Elevator capacity should be in [3,20].");
    }
    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;
    this.elevatorCapacity = elevatorCapacity;

    // we initialize the building's elevator system status to out of service
    this.elevatorSystemStatus = ElevatorSystemStatus.outOfService;

    // add elevators to the building
    for (int i = 0; i < this.numberOfElevators; i++) {
      elevators.add(new Elevator(this.numberOfFloors, this.elevatorCapacity));
    }



  }


  /**
   * This method is used when adding a new request to the building.
   * It will separate the requests to either the upRequests or downRequests list.
   *
   * @param request the request to be added.
   */
  private void distributingRequests(Request request) {

    //we assume the public add request method check the building's status
    //so this method just separate the valid requests
    if (request.getStartFloor() < request.getEndFloor()) {
      upRequests.add(request);

    } else if (request.getStartFloor() > request.getEndFloor()) {
      downRequests.add(request);

    }

  }

  /**
   * This method is used to add requests to the building.
   * It will check of the request floors are valid.
   * It will not succeed if the building is not in the running status.
   *
   * @param request the request to be added.
   * @return true if the request was added successfully, otherwise throw an exception.
   * @throws IllegalArgumentException if the request is invalid.
   * @throws IllegalStateException    if the building is not running.
   */
  @Override
  public boolean addRequests(Request request)
      throws IllegalArgumentException, IllegalStateException {
    //check the building's status, only in "running" status can accept requests
    if (elevatorSystemStatus == ElevatorSystemStatus.running) {
      //check if the request is valid
      // from the jar file, we know floors starts from 0
      if (request.getStartFloor() < 0 || request.getStartFloor() > this.numberOfFloors - 1
          || request.getEndFloor() < 0 || request.getEndFloor() > this.numberOfFloors - 1) {
        throw new IllegalArgumentException("Requests out of range.");
      } else {
        if (request.getStartFloor() == request.getEndFloor()) {
          throw new IllegalArgumentException("Start floor and end floor cannot be the same.");
        }
        distributingRequests(request);
        return true;
      }
    } else {
      //when the building is not running, it cannot accept requests
      throw new IllegalStateException("Elevator system is not running, cannot accept requests.");
    }

  }


  /**
   * This method is used to remove all requests from the building.
   *
   * @return true if all requests were removed successfully, false otherwise.
   */
  @Override
  public boolean removeAllRequests() {
    this.upRequests = new ArrayList<Request>();
    this.downRequests = new ArrayList<Request>();
    return true;
  }

  /**
   * This method is used to start the elevator system.
   * If the elevator system is already running, it will do nothing.
   * If the elevator system is stopping, it will throw an exception.
   * If the elevator system is out of service, it will start the elevators.
   *
   * @throws IllegalStateException if the elevator system is stopping.
   */
  @Override
  public void startElevatorSystem() throws IllegalStateException {
    if (this.elevatorSystemStatus == ElevatorSystemStatus.running) {
      return;
    }
    if (this.elevatorSystemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("Elevator system is stopping, cannot start.");

    }
    if (this.elevatorSystemStatus == ElevatorSystemStatus.outOfService) {
      this.elevatorSystemStatus = ElevatorSystemStatus.running;
      //for every elevator, start them
      //they should be in the ground floor , doors open
      for (Elevator elevator : elevators) {
        elevator.start();
      }
    }

  }

  /**
   * This method is used to stop the elevator system.
   * If the elevator system is already stopping, it will do nothing.
   * If the elevator system is out of service, it will do nothing.
   * If the elevator system is running, it will stop the elevators.
   * The elevators will go to the ground floor.
   * All requests will be removed.
   * The building status will be set to stopping.
   * The building status will be set to out of service when all elevators are at the ground floor.
   */
  @Override
  public void stopElevatorSystem() {

    //check the building's status, only in "running" status can stop
    if (this.elevatorSystemStatus != ElevatorSystemStatus.running) {
      return;
    }

    this.elevatorSystemStatus = ElevatorSystemStatus.stopping;
    this.removeAllRequests();
    for (Elevator elevator : elevators) {
      elevator.takeOutOfService();
    }
  }

  /**
   * This method is used to get all the elevator reports and put them in an array.
   *
   * @return an array of elevator reports.
   */

  private ElevatorReport[] getElevatorReports() {
    ElevatorReport[] elevatorReports = new ElevatorReport[this.numberOfElevators];
    for (int i = 0; i < this.numberOfElevators; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    return elevatorReports;
  }

  /**
   * This method is used to get the building report.
   *
   * @return a building report object.
   */
  @Override
  public BuildingReport getBuildingReport() {
    return new BuildingReport(numberOfFloors, numberOfElevators, elevatorCapacity,
        getElevatorReports(), upRequests, downRequests, elevatorSystemStatus);
  }

  /**
   * This method is used to step the building.
   */
  @Override
  public void step() {

    //use switch to check the building's status

    switch (this.elevatorSystemStatus) {
      case outOfService:
        //check the building's status, if it is out of service, do nothing
        return;
      case stopping:

        //if the elevators are on their way back to ground floor or the door is not open, step them
        for (Elevator elevator : elevators) {
          elevator.step();
        }

        //check if all elevators are at the ground floor and doors open
        boolean allElevatorsAtGroundFloorAndDoorsOpen = true;
        for (Elevator elevator : elevators) {
          if (elevator.getCurrentFloor() != 0 || elevator.isDoorClosed()) {
            allElevatorsAtGroundFloorAndDoorsOpen = false;
            break;
          }
        }
        //when all elevators are at the ground floor and doors open,
        // set the building status to out of service
        if (allElevatorsAtGroundFloorAndDoorsOpen) {
          this.elevatorSystemStatus = ElevatorSystemStatus.outOfService;
        }
        break;
      default:
        //if the building's status is running, we have to step the elevators
        //for every elevator, assign them requests, and step them
        for (Elevator elevator : elevators) {
          if (elevator.isTakingRequests() && elevator.getCurrentFloor() == 0
              && !upRequests.isEmpty()) {
            assignRequestsToElevator(elevator, upRequests);
            elevator.step();
          } else {
            if (elevator.isTakingRequests() && elevator.getCurrentFloor() == this.numberOfFloors - 1
                && !downRequests.isEmpty()) {
              assignRequestsToElevator(elevator, downRequests);
              elevator.step();
            } else {
              elevator.step();
            }
          }
        }


    }

  }

  /**
   * This method is used to assign requests to an elevator.
   * The requests will be assigned to the elevator until the elevator is full
   * or there are no more requests.
   * We will remove the requests from the list of requests after assigning them to the elevator.
   * The requests are to the right direction of the elevator.
   *
   * @param elevator the elevator to assign requests to.
   * @param requests the requests to assign to the elevator.
   */
  private void assignRequestsToElevator(Elevator elevator, List<Request> requests) {
    List<Request> requestsToElevator = new ArrayList<>();
    int requestCount = 0;
    while (requestCount < elevatorCapacity && !requests.isEmpty()) {
      Request request = requests.get(0);

      requestsToElevator.add(request);
      requestCount++;
      requests.remove(0);

    }
    elevator.processRequests(requestsToElevator);
  }
}




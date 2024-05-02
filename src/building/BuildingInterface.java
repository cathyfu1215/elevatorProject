package building;

import scanerzus.Request;

/**
 * This interface is used to represent a building.
 */
public interface BuildingInterface {

  /**
   * This method is used to add requests to the building.
   *
   * @param request the request to be added.
   * @return true if the request was added successfully, false otherwise.
   */
  public boolean addRequests(Request request);

  /**
   * This method is used to remove all requests from the building.
   *
   * @return true if all requests were removed successfully, false otherwise.
   */
  public boolean removeAllRequests();

  /**
   * This method is used to start the elevator system.
   */
  public void startElevatorSystem();

  /**
   * This method is used to stop the elevator system.
   */
  public void stopElevatorSystem();

  /**
   * This method is used to get the building report.
   * @return a building report object.
   */
  public BuildingReport getBuildingReport();

  /**
   * This method is used to step the building.
   */
  public void step();


}

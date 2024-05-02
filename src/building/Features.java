package building;


/**
 * Interface for the Features component in the MVC architecture of a building system simulation.
 * The controller will implement this interface to interact with the view.
 */

public interface Features {

  /**
   * A method that starts the building's elevator system.
   *
   */
  void startBuilding();

  /**
   * A method that stops the building's elevator system.
   */
  void stopBuilding();

  /**
   * A method that steps through the building simulation.
   */
  void stepBuilding();

  /**
   * A method that remove all the requests in the building.
   */
  void removeRequest();

  /**
   * A method that adds a request to the building.
   * @param startFloor The floor where the request starts.
   * @param endFloor The floor where the request ends.
   */
  void addRequest(int startFloor, int endFloor);

  /**
   * A method that exits the building simulation.
   */
  void exitBuilding();
}

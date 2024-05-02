package building;

/**
 * The interface for the building system view with a GUI.
 * This interface will allow the controller to interact with the view.
 * The view will display the building status, elevator status, and the elevator control panel.
 * The view will also display error messages and reset the request fields.
 */
public interface SwingBuildingViewInterface {

  /**
   * Add the features to the view of the building system.
   *
   * @param swingBuildingController the controller for the building system
   */

  void addFeatures(SwingBuildingController swingBuildingController);

  /**
   * Display the building status, elevator status and the elevator control panel.
   * @param buildingReport the building report from the model
   */

  void displayAll(BuildingReport buildingReport);

  /**
   * Update the building information in the view.
   * @param buildingReport the building report from the model
   */
  void updateBuildingInfo(BuildingReport buildingReport);

  /**
   * Update the elevator information in the view.
   * @param buildingReport the building report from the model
   */
  void updateElevatorInfo(BuildingReport buildingReport);

  /**
   * Display an error message.
   * @param errorMessage the error message from the model
   */
  void displayErrorMessage(String errorMessage);

  /**
   * Reset the request fields in the view.
   */
  void resetRequestFields();
}

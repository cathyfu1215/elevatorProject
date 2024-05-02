package building;

import scanerzus.Request;

/**
 * The controller for the building system with a GUI.
 * This class will communicate with the view and the model.
 * Also, it serves as a listener for the view. When user interacts with the view,
 * the controller will respond to the user's actions.
 */
public class SwingBuildingController implements SwingBuildingControllerInterface, Features {


  private final Building model;
  private final SwingBuildingView view;


  /**
   * Constructor for the SwingBuildingController class.
   *
   * @param model the Building model
   * @param view  the Building view
   */
  public SwingBuildingController(Building model, SwingBuildingView view) {

    this.model = model;
    this.view = view;
    this.view.addFeatures(this);
  }


  /**
   * Execute a single building system. When the simulation
   * is over, the go method ends.
   */
  @Override
  public void go() {
    this.view.displayAll(this.model.getBuildingReport());
  }


  /**
   * A method that starts the building's elevator system.
   * If the system is stopping, it cannot be restarted immediately,
   * an error message will be displayed.
   */
  @Override
  public void startBuilding() {
    try {
      this.model.startElevatorSystem();
      this.view.displayErrorMessage("Building is started.");
    } catch (IllegalStateException e) {
      this.view.displayErrorMessage(e.getMessage());
    }
    this.view.displayAll(this.model.getBuildingReport());

  }

  /**
   * A method that stops the building's elevator system.
   */
  @Override
  public void stopBuilding() {
    this.model.stopElevatorSystem();
    this.view.displayErrorMessage("Building is stopped.All requests are removed.");
    this.view.displayAll(this.model.getBuildingReport());
  }

  /**
   * A method that steps through the building simulation.
   */
  @Override
  public void stepBuilding() {
    this.model.step();
    this.view.displayAll(this.model.getBuildingReport());
    this.view.displayErrorMessage("Step is done.");
    this.view.displayAll(this.model.getBuildingReport());

  }

  /**
   * A method that remove all the requests in the building.
   */
  @Override
  public void removeRequest() {
    this.model.removeAllRequests();
    this.view.displayErrorMessage("All Requests are removed.");
    this.view.displayAll(this.model.getBuildingReport());
  }

  /**
   * A method that adds a request to the building.
   *
   * @param startFloor The floor where the request starts.
   * @param endFloor   The floor where the request ends.
   */
  @Override
  public void addRequest(int startFloor, int endFloor) {
    try {
      this.model.addRequests(new Request(startFloor, endFloor));
      this.view.resetRequestFields();
      this.view.displayErrorMessage("Request added.");
      this.view.displayAll(this.model.getBuildingReport());
    } catch (IllegalStateException | IllegalArgumentException e) {
      this.view.displayErrorMessage(e.getMessage());
      this.view.resetRequestFields();
      this.view.displayAll(this.model.getBuildingReport());
    }
  }

  /**
   * A method that exits the building simulation.
   */
  @Override
  public void exitBuilding() {
    System.exit(0);
  }
}

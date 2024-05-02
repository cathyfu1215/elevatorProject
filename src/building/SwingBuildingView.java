package building;

import elevator.ElevatorReport;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * The view for the building system with a GUI.
 * User can interact with the building system through this view.
 * They can start and stop the building system, add and remove requests,
 * and step through the building simulation.
 */
public class SwingBuildingView extends JFrame implements SwingBuildingViewInterface {

  private final int numFloors;
  private final int numElevators;
  private final int numPeople;

  private final JButton quitButton = new JButton("Quit");
  private final JLabel upRequests = new JLabel(" ");

  private final JLabel downRequests = new JLabel(" ");
  private final JLabel buildingStatusText = new JLabel(" ");
  private JLabel[] elevatorStatus;

  private final JButton startBuildingButton = new JButton("Start Building");
  private final JButton stopBuildingButton = new JButton("Stop Building");
  private final JButton stepBuildingButton = new JButton("Step Building");

  private final JLabel errorMessageLabel = new JLabel("Start the building to add requests.");
  private final JTextField startFloorField = new JTextField("Start Floor");
  private final JTextField endFloorField = new JTextField("End Floor");

  private final JButton addRequestButton = new JButton("Add Request");
  private final JButton removeRequestButton = new JButton("Remove Request");

  private final Font newFont = new Font("Serif", Font.BOLD, 14);



  /**
   * Constructor for the SwingConnectFourView class.
   *
   * @param title        the title of the building system
   * @param numFloors    the number of floors in the building
   * @param numElevators the number of elevators in the building
   * @param numPeople    the number of people in the building
   */
  public SwingBuildingView(String title, int numFloors,
                           int numElevators, int numPeople) {
    super(title);
    this.numFloors = numFloors;
    this.numElevators = numElevators;
    this.numPeople = numPeople;


    setSize(800, 600);
    setLocation(50, 50);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    JPanel buildingStatusPanel = getBuildingStatusPanel();
    JPanel elevatorStatusPanel = getElevatorStatusPanel();
    JPanel elevatorControlPanel = getElevatorControlPanel();
    JPanel errorMessagePanel = getErrorMessagePanel();

    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.add(buildingStatusPanel);
    this.add(elevatorStatusPanel);
    this.add(elevatorControlPanel);
    this.add(errorMessagePanel);

    buildingStatusPanel.setAlignmentX(LEFT_ALIGNMENT);
    elevatorStatusPanel.setAlignmentX(LEFT_ALIGNMENT);
    elevatorControlPanel.setAlignmentX(LEFT_ALIGNMENT);
    errorMessagePanel.setAlignmentX(LEFT_ALIGNMENT);
  }

  /**
   * A private method that creates the error message panel.
   * @return the error message panel
   */
  private JPanel getErrorMessagePanel() {
    JPanel errorMessagePanel = new JPanel(new FlowLayout());
    TitledBorder border = BorderFactory.createTitledBorder("System Message");
    border.setTitleFont(newFont);
    errorMessagePanel.setBorder(border);
    errorMessagePanel.add(errorMessageLabel);
    return errorMessagePanel;
  }

  /**
   * A private method that creates the elevator control panel.
   * @return the elevator control panel
   */
  private JPanel getElevatorControlPanel() {
    JPanel elevatorControlPanel = new JPanel();
    elevatorControlPanel.setLayout(new BoxLayout(elevatorControlPanel, BoxLayout.Y_AXIS));
    TitledBorder border = BorderFactory.createTitledBorder("Elevator Control");
    border.setTitleFont(newFont);
    elevatorControlPanel.setBorder(border);

    startBuildingButton.setMargin(new Insets(5, 10, 5, 10));
    elevatorControlPanel.add(startBuildingButton);

    stopBuildingButton.setMargin(new Insets(5, 10, 5, 10));
    elevatorControlPanel.add(stopBuildingButton);

    startFloorField.setMargin(new Insets(5, 10, 5, 10));
    endFloorField.setMargin(new Insets(5, 10, 5, 10));

    startFloorField.setHorizontalAlignment(JTextField.CENTER);
    endFloorField.setHorizontalAlignment(JTextField.CENTER);

    elevatorControlPanel.add(startFloorField);
    elevatorControlPanel.add(endFloorField);

    addRequestButton.setMargin(new Insets(5, 10, 5, 10));
    elevatorControlPanel.add(addRequestButton);

    removeRequestButton.setMargin(new Insets(5, 10, 5, 10));
    elevatorControlPanel.add(removeRequestButton);

    stepBuildingButton.setMargin(new Insets(5, 10, 5, 10));
    elevatorControlPanel.add(stepBuildingButton);

    quitButton.setMargin(new Insets(5, 10, 5, 10));
    elevatorControlPanel.add(quitButton);

    elevatorControlPanel.add(errorMessageLabel);

    return elevatorControlPanel;
  }

  /**
   * A private method that creates the building status panel.
   * @return the building status panel
   */
  private JPanel getBuildingStatusPanel() {
    JPanel buildingStatusPanel = new JPanel();
    buildingStatusPanel.setLayout(new BoxLayout(buildingStatusPanel, BoxLayout.Y_AXIS));

    TitledBorder border = BorderFactory.createTitledBorder("Building Status");
    border.setTitleFont(newFont);
    buildingStatusPanel.setBorder(border);

    JLabel buildingParameters =
        new JLabel("Building Parameters: " + " Number of Floors: " + numFloors + " "
            + ", Number of Elevators: " + numElevators + " "
            + ", Number of People: " + numPeople + " ");

    buildingStatusPanel.add(buildingParameters);

    JLabel upRequestsLabel = new JLabel("Up Requests:");
    buildingStatusPanel.add(upRequestsLabel);
    buildingStatusPanel.add(upRequests);

    JLabel downRequestsLabel = new JLabel("Down Requests:");
    buildingStatusPanel.add(downRequestsLabel);
    buildingStatusPanel.add(downRequests);

    JLabel buildingStatusTextLabel = new JLabel("Building Status:");
    buildingStatusPanel.add(buildingStatusTextLabel);
    buildingStatusPanel.add(buildingStatusText);
    return buildingStatusPanel;
  }

  /**
   * A private method that creates the elevator status panel.
   * @return the elevator status panel
   */
  private JPanel getElevatorStatusPanel() {
    JPanel elevatorStatusPanel = new JPanel();
    elevatorStatusPanel.setLayout(new BoxLayout(elevatorStatusPanel, BoxLayout.Y_AXIS));

    TitledBorder border = BorderFactory.createTitledBorder("Elevator Status");
    border.setTitleFont(newFont);
    elevatorStatusPanel.setBorder(border);

    elevatorStatus = new JLabel[numElevators];
    for (int i = 0; i < numElevators; i++) {
      elevatorStatus[i] = new JLabel("Elevator " + i + ": ");
      elevatorStatusPanel.add(elevatorStatus[i]);
    }

    return elevatorStatusPanel;
  }

  /**
   * Add the features to the view of the building system.
   *
   * @param swingBuildingController the controller for the building system
   */

  @Override
  public void addFeatures(SwingBuildingController swingBuildingController) {
    quitButton.addActionListener(e -> swingBuildingController.exitBuilding());

    startBuildingButton.addActionListener(e -> {
      swingBuildingController.startBuilding();
    });

    stopBuildingButton.addActionListener(e -> {
      swingBuildingController.stopBuilding();
    });

    stepBuildingButton.addActionListener(e -> {
      swingBuildingController.stepBuilding();
    });

    addRequestButton.addActionListener(e -> {
      try {
        int startFloor = Integer.parseInt(startFloorField.getText());
        int endFloor = Integer.parseInt(endFloorField.getText());
        swingBuildingController.addRequest(startFloor, endFloor);
      } catch (NumberFormatException ex) {
        displayErrorMessage("Invalid floor request");
      }
    });

    removeRequestButton.addActionListener(e -> {
      swingBuildingController.removeRequest();
    });
  }

  /**
   * Display the building status and elevator status.
   * @param buildingReport the building report from the model
   */
  @Override
  public void displayAll(BuildingReport buildingReport) {
    //parse information from the building report
    this.updateBuildingInfo(buildingReport);
    this.updateElevatorInfo(buildingReport);
    pack();
    this.setVisible(true);
  }

  /**
   * Update the building information in the view.
   * @param buildingReport the building report from the model
   */
  @Override
  public void updateBuildingInfo(BuildingReport buildingReport) {
    upRequests.setText(buildingReport.getUpRequests().toString());
    downRequests.setText(buildingReport.getDownRequests().toString());
    buildingStatusText.setText(buildingReport.getSystemStatus().toString());
    revalidate();
    repaint();
  }

  /**
   * Update the elevator information in the view.
   * @param buildingReport the building report from the model
   */
  @Override
  public void updateElevatorInfo(BuildingReport buildingReport) {
    ElevatorReport[] elevatorReports = buildingReport.getElevatorReports();
    for (int i = 0; i < elevatorReports.length; i++) {
      elevatorStatus[i].setText("Elevator " + i + ": " + elevatorReports[i].toString());
    }
    revalidate();
    repaint();
  }

  /**
   * Display an error message.
   * @param errorMessage the error message from the model
   */
  @Override
  public void displayErrorMessage(String errorMessage) {
    errorMessageLabel.setText(errorMessage);
  }

  /**
   * Reset the request fields in the view.
   */
  @Override
  public void resetRequestFields() {
    startFloorField.setText("Start Floor");
    endFloorField.setText("End Floor");
    revalidate();
    repaint();
  }
}

package building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * This is the unit test class for the Building class.
 */

public class BuildingTest {

  private Building building1;
  private Building building2;
  private Building building3;


  /**
   * Set up the test fixture.
   */
  @Before
  public void setUp() {
    this.building1 = new Building(10, 2, 3);
    this.building2 = new Building(3, 2, 3);
    this.building3 = new Building(3, 1, 3);
  }

  /**
   * Test that an exception is thrown when the number of floors is less than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFloorsLessThanThree() {
    Building building = new Building(2, 2, 3);
  }

  /**
   * Test that an exception is thrown when the number of floors is more than 30.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFloorsMoreThanThirty() {
    Building building = new Building(31, 2, 3);
  }

  /**
   * Test that an exception is thrown when the number of elevators is less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorsLessThanOne() {
    Building building = new Building(3, 0, 3);
  }

  /**
   * Test that an exception is thrown when the number of elevators is more than 10.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorsMoreThanTen() {
    Building building = new Building(3, 11, 3);
  }

  /**
   * Test that an exception is thrown when the capacity of elevators is less than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorCapacityLessThanThree() {
    Building building = new Building(3, 5, 2);
  }

  /**
   * Test that an exception is thrown when the capacity of elevators is more than 20.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorCapacityMoreThanTwenty() {
    Building building = new Building(3, 5, 21);
  }

  /**
   * Test the happy path of the constructor.
   */

  @Test
  public void testConstructor() {
    assertEquals(10, building1.getBuildingReport().getNumFloors());
    assertEquals(2, building1.getBuildingReport().getNumElevators());
    assertEquals(3, building1.getBuildingReport().getElevatorCapacity());
    assertEquals(0, building1.getBuildingReport().getUpRequests().size());
    assertEquals(0, building1.getBuildingReport().getDownRequests().size());
    assertEquals(
        ElevatorSystemStatus.outOfService, building1.getBuildingReport().getSystemStatus());
    assertEquals(2, building1.getBuildingReport().getElevatorReports().length);

  }

  /**
   * Test the happy path of the addRequest method.
   */
  @Test
  public void addRequests() {
    this.building1.startElevatorSystem();
    assertTrue(this.building1.addRequests(new Request(1, 2)));
    assertEquals(1, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals("1->2", this.building1.getBuildingReport().getUpRequests().get(0).toString());
    assertTrue(this.building1.addRequests(new Request(3, 6)));
    assertEquals(2, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals("3->6", this.building1.getBuildingReport().getUpRequests().get(1).toString());
    assertTrue(this.building1.addRequests(new Request(5, 2)));
    assertEquals(1, this.building1.getBuildingReport().getDownRequests().size());
    assertEquals("5->2", this.building1.getBuildingReport().getDownRequests().get(0).toString());
    assertTrue(this.building1.addRequests(new Request(9, 0)));
    assertEquals(2, this.building1.getBuildingReport().getDownRequests().size());
    assertEquals("9->0", this.building1.getBuildingReport().getDownRequests().get(1).toString());

  }

  /**
   * Test that when the Building status is OutOfService, the addRequest method throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestWhenOutOfService() {
    this.building1.addRequests(new Request(1, 2));
    //when initiated, our building is put in "OutOfService" status
  }

  /**
   * Test that when the Building status is Stopping, the addRequest method throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestWhenStopping() {
    this.building1.startElevatorSystem();
    this.building1.addRequests(new Request(4, 7)); //this can be added
    this.building1.stopElevatorSystem();
    this.building1.addRequests(new Request(1, 2));
  }

  /**
   * Test that when the building is running, but the request is invalid,
   * the addRequest method throws an exception.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestInvalidRequest() {
    this.building1.startElevatorSystem();
    this.building1.addRequests(new Request(1, 2));
    this.building1.addRequests(new Request(1, 20));  //we have only 10 floors
  }

  /**
   * Test that when a request has same start and end floor,
   * the addRequest method throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddRequestSameStartAndEndFloor() {
    this.building1.startElevatorSystem();
    this.building1.addRequests(new Request(1, 1));  //start and end floor are the same
  }

  /**
   * Test that the building add requests in the order that it receives them.
   */
  @Test
  public void testAddRequestOrder() {
    this.building1.startElevatorSystem();
    assertTrue(this.building1.addRequests(new Request(1, 2)));
    assertTrue(this.building1.addRequests(new Request(3, 6)));
    assertTrue(this.building1.addRequests(new Request(5, 2)));
    assertTrue(this.building1.addRequests(new Request(9, 0)));
    assertEquals(2, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(2, this.building1.getBuildingReport().getDownRequests().size());
    assertEquals("1->2", this.building1.getBuildingReport().getUpRequests().get(0).toString());
    assertEquals("3->6", this.building1.getBuildingReport().getUpRequests().get(1).toString());
    assertEquals("5->2", this.building1.getBuildingReport().getDownRequests().get(0).toString());
    assertEquals("9->0", this.building1.getBuildingReport().getDownRequests().get(1).toString());
  }


  /**
   * Test the request allocation extensively.
   * Add 1000 up requests and 1000 down requests.
   */
  @Test
  public void testAddRequestAllocationExtensively() {
    this.building1.startElevatorSystem();
    for (int i = 0; i < 1000; i++) {
      int startFloor = new Random().nextInt(9);
      int endFloor = startFloor + 1;
      // I have to do this because the request will be invalid
      // if the start and end floor are the same
      assertTrue(this.building1.addRequests(new Request(startFloor, endFloor)));
    }
    assertEquals(1000, this.building1.getBuildingReport().getUpRequests().size());

    for (int i = 0; i < 1000; i++) {
      int end = new Random().nextInt(9) + 1;
      int start = end - 1;
      // I have to do this because the request will be invalid
      // if the start and end floor are the same
      assertTrue(this.building1.addRequests(new Request(end, start)));
    }
    assertEquals(1000, this.building1.getBuildingReport().getDownRequests().size());

  }


  /**
   * Test the building's request allocation is no more than an elevator's capacity.
   */
  @Test
  public void testAddRequestAllocationMoreThanCapacity() {
    //this building has only one elevator and its capacity is 3
    this.building3.startElevatorSystem();
    for (int i = 0; i < 4; i++) {
      assertTrue(this.building3.addRequests(new Request(0, 2)));
    }
    this.building3.step();
    //since the capacity of the elevator is 3, we should have 1 request left un-assigned
    assertEquals(1, this.building3.getBuildingReport().getUpRequests().size());
    this.building3.step();
    this.building3.step();
    this.building3.step();
    //the elevator should be closing its door
    assertEquals("[0|^|C  ]< -- --  2>",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    this.building3.step();
    //the elevator should be on the 1st floor
    assertEquals("[1|^|C  ]< -- --  2>",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    for (int i = 0; i < 4; i++) {
      assertTrue(this.building3.addRequests(new Request(2, 0)));
    }
    // there should be 4 down requests
    assertEquals(4, this.building3.getBuildingReport().getDownRequests().size());
    this.building3.step();
    //the elevator should be on the 2nd floor
    assertEquals("[2|^|C  ]< -- --  2>",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    this.building3.step();
    //the elevator should be on the 2nd floor, opening its door
    assertEquals("[2|^|O 3]< -- -- -->",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    this.building3.step();
    //the elevator should be on the 2nd floor, opening its door
    assertEquals("[2|^|O 2]< -- -- -->",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    this.building3.step();
    //the elevator should be on the 2nd floor, opening its door
    assertEquals("[2|^|O 1]< -- -- -->",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    this.building3.step();
    //the elevator should close its door
    assertEquals("[2|^|C  ]< -- -- -->",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    this.building3.step();
    //the elevator should be waiting on the 2nd floor
    assertEquals("Waiting[Floor 2, Time 5]",
        this.building3.getBuildingReport().elevatorReports[0].toString());

    this.building3.step();
    //the elevator should take down requests
    assertEquals("[2|v|O 3]<  0 -- -->",
        this.building3.getBuildingReport().elevatorReports[0].toString());
    //there should be 1 down request left
    assertEquals(1, this.building3.getBuildingReport().getDownRequests().size());
    //still there should be 1 up request left as well
    assertEquals(1, this.building3.getBuildingReport().getUpRequests().size());


  }

  /**
   * Test the removeRequest method.
   */
  @Test
  public void removeAllRequests() {
    this.building1.startElevatorSystem();
    assertTrue(this.building1.addRequests(new Request(1, 2)));
    assertTrue(this.building1.addRequests(new Request(3, 6)));
    assertTrue(this.building1.addRequests(new Request(5, 2)));
    assertTrue(this.building1.addRequests(new Request(9, 0)));
    assertEquals(2, this.building1.getBuildingReport().getDownRequests().size());
    assertEquals(2, this.building1.getBuildingReport().getDownRequests().size());
    assertTrue(this.building1.removeAllRequests());
    assertEquals(0, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(0, this.building1.getBuildingReport().getDownRequests().size());
  }

  /**
   * Test the happy path for starting the elevator system. It is in out of service status.
   */
  @Test
  public void startElevatorSystemFromOutOfService() {
    assertEquals(ElevatorSystemStatus.outOfService,
        this.building1.getBuildingReport().getSystemStatus());
    this.building1.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());
  }

  /**
   * Test that if the building is already running, the startElevatorSystem will do nothing.
   */
  @Test
  public void startElevatorSystemFromRunning() {
    this.building1.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());
    this.building1.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());
  }

  /**
   * Test that if the building is stopping, the startElevatorSystem will throw an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void startElevatorSystemFromStopping() {
    this.building1.startElevatorSystem();
    this.building1.addRequests(new Request(1, 2));
    this.building1.addRequests(new Request(3, 6));
    this.building1.step();
    this.building1.step();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());

    this.building1.stopElevatorSystem();
    //the above step will take more than one step to complete
    this.building1.step();

    // the building is in stopping status
    //so we cannot start the system immediately
    assertEquals(ElevatorSystemStatus.stopping,
        this.building1.getBuildingReport().getSystemStatus());
    this.building1.startElevatorSystem();
  }

  /**
   * Test the happy path for stopping the elevator system.
   */
  @Test
  public void stopElevatorSystemFromRunning() {
    this.building1.startElevatorSystem();
    this.building1.step();
    this.building1.step();
    this.building1.addRequests(new Request(1, 2));
    this.building1.addRequests(new Request(3, 6));
    this.building1.step();
    this.building1.step();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());
    this.building1.stopElevatorSystem();

    //building status should be stopping
    assertEquals(ElevatorSystemStatus.stopping,
        this.building1.getBuildingReport().getSystemStatus());
    //all requests should be removed
    assertEquals(0, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(0, this.building1.getBuildingReport().getDownRequests().size());

    //all the elevators should be taken out of service
    for (ElevatorReport elevatorReport : this.building1.getBuildingReport().getElevatorReports()) {
      assertTrue(elevatorReport.isOutOfService());
      assertFalse(elevatorReport.isTakingRequests());
    }
  }

  /**
   * Test that if the building is already stopping, the stopElevatorSystem will do nothing.
   */

  @Test
  public void stopElevatorSystemFromStopping() {
    this.building1.startElevatorSystem();
    this.building1.step();
    this.building1.step();
    this.building1.addRequests(new Request(1, 2));
    this.building1.addRequests(new Request(3, 6));
    this.building1.step();
    this.building1.step();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());
    this.building1.stopElevatorSystem();

    //building status should be stopping
    assertEquals(ElevatorSystemStatus.stopping,
        this.building1.getBuildingReport().getSystemStatus());
    //all requests should be removed
    assertEquals(0, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(0, this.building1.getBuildingReport().getDownRequests().size());

    //all the elevators should be taken out of service
    for (ElevatorReport elevatorReport : this.building1.getBuildingReport().getElevatorReports()) {
      assertTrue(elevatorReport.isOutOfService());
      assertFalse(elevatorReport.isTakingRequests());
    }
    //stop it again
    this.building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping,
        this.building1.getBuildingReport().getSystemStatus());

  }

  /**
   * Test that if the building is out of service the stopElevatorSystem will do nothing.
   */

  @Test
  public void stopElevatorSystemFromOutOfService() {

    //building status should be out of service
    assertEquals(ElevatorSystemStatus.outOfService,
        this.building1.getBuildingReport().getSystemStatus());
    //stop it, nothing will change
    this.building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.outOfService,
        this.building1.getBuildingReport().getSystemStatus());

  }

  /**
   * Test the happy path for the getBuildingReport method.
   */

  @Test
  public void getBuildingReport() {
    this.building1.startElevatorSystem();
    assertEquals(10, this.building1.getBuildingReport().getNumFloors());
    assertEquals(2, this.building1.getBuildingReport().getNumElevators());
    assertEquals(3, this.building1.getBuildingReport().getElevatorCapacity());
    assertEquals(0, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(0, this.building1.getBuildingReport().getDownRequests().size());
    assertEquals(
        ElevatorSystemStatus.running, this.building1.getBuildingReport().getSystemStatus());
    assertEquals(2, this.building1.getBuildingReport().getElevatorReports().length);

    this.building1.addRequests(new Request(3, 6));
    this.building1.addRequests(new Request(2, 1));
    this.building1.addRequests(new Request(3, 0));
    assertEquals(1, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(2, this.building1.getBuildingReport().getDownRequests().size());

    this.building1.step();
    assertEquals(0, this.building1.getBuildingReport().elevatorReports[1].getCurrentFloor());
    assertEquals(0, this.building1.getBuildingReport().elevatorReports[1].getCurrentFloor());
    assertEquals("[1|^|C  ]< -- -- --  3 -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 4]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[2|^|C  ]< -- -- --  3 -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 3]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|^|C  ]< -- -- --  3 -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 2]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|^|O 3]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 1]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
  }

  /**
   * A comprehensive test for the step method.
   */

  @Test
  public void step() {
    this.building1.startElevatorSystem();
    assertEquals(
        ElevatorSystemStatus.running, this.building1.getBuildingReport().getSystemStatus());
    assertEquals(2, this.building1.getBuildingReport().getElevatorReports().length);
    this.building1.addRequests(new Request(3, 6));
    this.building1.addRequests(new Request(2, 1));
    this.building1.addRequests(new Request(3, 0));
    assertEquals(1, this.building1.getBuildingReport().getUpRequests().size());
    assertEquals(2, this.building1.getBuildingReport().getDownRequests().size());

    //first elevator goes up and pick up the upRequest
    this.building1.step();
    assertEquals(0, this.building1.getBuildingReport().elevatorReports[1].getCurrentFloor());
    assertEquals(0, this.building1.getBuildingReport().elevatorReports[1].getCurrentFloor());
    assertEquals("[1|^|C  ]< -- -- --  3 -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 4]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[2|^|C  ]< -- -- --  3 -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 3]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|^|C  ]< -- -- --  3 -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 2]",
        this.building1.getBuildingReport().elevatorReports[1].toString());

    //fist elevator arrives at the stop, open
    this.building1.step();
    assertEquals("[3|^|O 3]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 1]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|^|O 2]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());

    //second elevator goes up
    assertEquals("[0|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|^|O 1]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[1|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|^|C  ]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[2|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[4|^|C  ]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[3|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[5|^|C  ]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[4|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[6|^|C  ]< -- -- -- -- -- --  6 -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[5|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[6|^|O 3]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[6|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());

    this.building1.step();
    assertEquals("[6|^|O 2]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[7|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[6|^|O 1]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[8|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[6|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[9|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[7|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 9, Time 5]",
        this.building1.getBuildingReport().elevatorReports[1].toString());

    //second elevator pick up the downRequests
    this.building1.step();
    assertEquals("[8|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[8|v|C  ]<  0  1  2  3 -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[9|^|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[7|v|C  ]<  0  1  2  3 -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());

    //call stop system here and all the elevators will be out of service
    //stop plan is emptied
    //all the requests will be removed
    //the system status will be stopping
    this.building1.stopElevatorSystem();
    assertEquals(ElevatorSystemStatus.stopping,
        this.building1.getBuildingReport().getSystemStatus());
    assertEquals("[9|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[7|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[8|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[6|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[7|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[5|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[6|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[4|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[5|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[3|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[4|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[2|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[3|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("[1|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    this.building1.step();
    assertEquals("[2|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());

    //second elevator reach the ground floor and opens its door
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    assertFalse(this.building1.getBuildingReport().elevatorReports[1].isTakingRequests());
    assertTrue(this.building1.getBuildingReport().elevatorReports[1].isOutOfService());
    this.building1.step();
    assertEquals("[1|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        this.building1.getBuildingReport().elevatorReports[0].toString());


    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    assertFalse(this.building1.getBuildingReport().elevatorReports[1].isDoorClosed());
    this.building1.step();
    //first elevator reach the ground floor and opens its door
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    assertEquals(ElevatorSystemStatus.stopping,
        this.building1.getBuildingReport().getSystemStatus());

    //after one more step when all elevators are out of service,
    // the building will be out of service
    this.building1.step();
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    assertEquals(ElevatorSystemStatus.outOfService,
        this.building1.getBuildingReport().getSystemStatus());

    //one more step, and it won't change
    this.building1.step();
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Out of Service[Floor 0]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
    assertEquals(ElevatorSystemStatus.outOfService,
        this.building1.getBuildingReport().getSystemStatus());

    //if we start the building again, it will be running
    this.building1.startElevatorSystem();
    assertEquals(ElevatorSystemStatus.running,
        this.building1.getBuildingReport().getSystemStatus());
    assertEquals("Waiting[Floor 0, Time 5]",
        this.building1.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Waiting[Floor 0, Time 5]",
        this.building1.getBuildingReport().elevatorReports[1].toString());
  }


  /**
   * Test the take out of service method for the building when one elevator has an open door.
   */
  @Test
  public void testTakeOutOfServiceWhenElevatorHasOpenDoor() {
    this.building2.startElevatorSystem();
    this.building2.addRequests(new Request(0, 2));
    this.building2.addRequests(new Request(2, 1));
    this.building2.addRequests(new Request(1, 2));
    assertEquals(2, this.building2.getBuildingReport().getUpRequests().size());
    assertEquals(1, this.building2.getBuildingReport().getDownRequests().size());


    this.building2.step();
    //elevator 1 should have floor request 0, 1, and 2
    //it should go to the 1st floor and opens its door
    assertEquals("[0|^|O 3]< --  1  2>",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should be waiting for 4 steps
    assertEquals("Waiting[Floor 0, Time 4]",
        this.building2.getBuildingReport().elevatorReports[1].toString());
    this.building2.step();
    assertEquals("[0|^|O 2]< --  1  2>",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should be waiting for 3 steps
    assertEquals("Waiting[Floor 0, Time 3]",
        this.building2.getBuildingReport().elevatorReports[1].toString());

    this.building2.step();
    //elevator 1 should have 1 second left to close its door
    assertEquals("[0|^|O 1]< --  1  2>",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should have 2 seconds left to wait
    assertEquals("Waiting[Floor 0, Time 2]",
        this.building2.getBuildingReport().elevatorReports[1].toString());


    this.building2.step();
    //elevator 1 should be closing its door and head up
    assertEquals("[0|^|C  ]< --  1  2>",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should have 1 seconds left to wait
    assertEquals("Waiting[Floor 0, Time 1]",
        this.building2.getBuildingReport().elevatorReports[1].toString());
    this.building2.step();
    //elevator 1 should be on the 1st floor
    assertEquals("[1|^|C  ]< --  1  2>",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should head up
    assertEquals("[0|^|C  ]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[1].toString());


    this.building2.step();
    //elevator 1 should open its door
    assertEquals("[1|^|O 3]< -- --  2>",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should be on the 1st floor
    assertEquals("[1|^|C  ]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[1].toString());

    //stop the system now

    this.building2.stopElevatorSystem();
    //the building should in in stopping mode
    assertEquals(ElevatorSystemStatus.stopping,
        this.building2.getBuildingReport().getSystemStatus());
    //elevator 1 should still wait for closing its door
    assertEquals("[1|v|O 3]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should switch direction and go down
    assertEquals("[1|v|C  ]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[1].toString());
    this.building2.step();
    //elevator 1 should still wait for closing its door
    assertEquals("[1|v|O 2]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should reach the ground floor and opens its door
    assertEquals("Out of Service[Floor 0]",
        this.building2.getBuildingReport().elevatorReports[1].toString());

    this.building2.step();
    //elevator 1 should still wait for closing its door
    assertEquals("[1|v|O 1]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[0].toString());

    this.building2.step();
    //elevator 1 should close its door and go down
    assertEquals("[1|v|C  ]< -- -- -->",
        this.building2.getBuildingReport().elevatorReports[0].toString());

    this.building2.step();
    //elevator 1 should be on ground floor and opens its door
    assertEquals("Out of Service[Floor 0]",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    //elevator 2 should be on ground floor and opens its door as well
    assertEquals("Out of Service[Floor 0]",
        this.building2.getBuildingReport().elevatorReports[1].toString());
    //building status is still stopping
    assertEquals(ElevatorSystemStatus.stopping,
        this.building2.getBuildingReport().getSystemStatus());

    this.building2.step();
    //both elevators are out of service
    //building is also out of service
    assertEquals("Out of Service[Floor 0]",
        this.building2.getBuildingReport().elevatorReports[0].toString());
    assertEquals("Out of Service[Floor 0]",
        this.building2.getBuildingReport().elevatorReports[1].toString());
    assertEquals(ElevatorSystemStatus.outOfService,
        this.building2.getBuildingReport().getSystemStatus());


  }

}
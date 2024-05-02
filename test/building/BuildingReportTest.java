package building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * This is the test class for the BuildingReport class.
 */
public class BuildingReportTest {

  private BuildingReport buildingReport01;

  /**
   * This method is used to set up the test fixture.
   */
  @Before
  public void setUp() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 2; i++) {
      elevators.add(new Elevator(10, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[2];
    for (int i = 0; i < 2; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(10, 2, 10, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * Test that an exception is thrown when the number of floors is less than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFloorsLessThanThree() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 5; i++) {
      elevators.add(new Elevator(10, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[10];
    for (int i = 0; i < 5; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(2, 5, 10, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * Test that an exception is thrown when the number of floors is more than 30.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFloorsMoreThanThirty() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 5; i++) {
      elevators.add(new Elevator(10, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[10];
    for (int i = 0; i < 5; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(31, 5, 10, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * Test that an exception is thrown when the number of elevators is less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorLessThanOne() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 0; i++) {
      elevators.add(new Elevator(10, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[10];
    for (int i = 0; i < 0; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(4, 0, 10, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * Test that an exception is thrown when the number of elevators is more than 10.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorMoreThanTen() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 11; i++) {
      elevators.add(new Elevator(4, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[11];
    for (int i = 0; i < 11; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(4, 11, 10, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * Test that an exception is thrown when the elevator capacity is less than 3.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorCapacityLessThanThree() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 10; i++) {
      elevators.add(new Elevator(10, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[10];
    for (int i = 0; i < 10; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(4, 5, 2, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }

  /**
   * Test that an exception is thrown when the elevator capacity is more than 20.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorElevatorCapacityMoreThanTwenty() {
    List<Elevator> elevators = new ArrayList<Elevator>();
    for (int i = 0; i < 5; i++) {
      elevators.add(new Elevator(10, 10));
    }
    ElevatorReport[] elevatorReports = new ElevatorReport[10];
    for (int i = 0; i < 5; i++) {
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }
    List<Request> upRequests = new ArrayList<Request>();
    upRequests.add(new Request(1, 2));
    upRequests.add(new Request(3, 4));
    List<Request> downRequests = new ArrayList<Request>();
    downRequests.add(new Request(5, 4));
    downRequests.add(new Request(7, 6));
    this.buildingReport01 = new BuildingReport(4, 5, 21, elevatorReports,
        upRequests, downRequests, ElevatorSystemStatus.running);
  }


  /**
   * Test the getNumFloors method.
   */
  @Test
  public void getNumFloors() {
    assertEquals(10, this.buildingReport01.getNumFloors());
  }

  /**
   * Test the getNumElevators method.
   */
  @Test
  public void getNumElevators() {
    assertEquals(2, this.buildingReport01.getNumElevators());
  }

  /**
   * Test the getElevatorCapacity method.
   */
  @Test
  public void getElevatorCapacity() {
    assertEquals(10, this.buildingReport01.getElevatorCapacity());
  }

  /**
   * Test the getElevatorReports method.
   */
  @Test
  public void getElevatorReports() {
    assertEquals(2, this.buildingReport01.getElevatorReports().length);
    assertEquals(0, this.buildingReport01.getElevatorReports()[0].getElevatorId());
    assertEquals(0, this.buildingReport01.getElevatorReports()[0].getCurrentFloor());
    assertEquals(0, this.buildingReport01.getElevatorReports()[0].getDoorOpenTimer());
    assertEquals(0, this.buildingReport01.getElevatorReports()[0].getEndWaitTimer());
    assertTrue(this.buildingReport01.getElevatorReports()[0].isDoorClosed());
    //out of service, doors should be open
    assertEquals(10, this.buildingReport01.getElevatorReports()[0].getFloorRequests().length);
    assertFalse(this.buildingReport01.getElevatorReports()[0].getFloorRequests()[0]);
  }

  /**
   * Test the getUpRequests method.
   */
  @Test
  public void getUpRequests() {
    assertEquals(2, this.buildingReport01.getUpRequests().size());
    assertEquals(1, this.buildingReport01.getUpRequests().get(0).getStartFloor());
    assertEquals(2, this.buildingReport01.getUpRequests().get(0).getEndFloor());
    assertEquals(3, this.buildingReport01.getUpRequests().get(1).getStartFloor());
    assertEquals(4, this.buildingReport01.getUpRequests().get(1).getEndFloor());
  }

  /**
   * Test the getDownRequests method.
   */
  @Test
  public void getDownRequests() {
    assertEquals(2, this.buildingReport01.getDownRequests().size());
    assertEquals(5, this.buildingReport01.getDownRequests().get(0).getStartFloor());
    assertEquals(4, this.buildingReport01.getDownRequests().get(0).getEndFloor());
    assertEquals(7, this.buildingReport01.getDownRequests().get(1).getStartFloor());
    assertEquals(6, this.buildingReport01.getDownRequests().get(1).getEndFloor());
  }

  /**
   * Test the getSystemStatus method.
   */
  @Test
  public void getSystemStatus() {
    assertEquals(ElevatorSystemStatus.running, this.buildingReport01.getSystemStatus());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {

    int elevatorId1 = this.buildingReport01.getElevatorReports()[0].getElevatorId();
    int elevatorId2 = this.buildingReport01.getElevatorReports()[1].getElevatorId();

    assertEquals("Building Report\n"
            + "Number of Floors: 10\n"
            + "Number of Elevators: 2\n"
            + "Elevator Capacity: 10\n"
            + "Elevator System Status: Running\n"
            + "Up Requests: [(2)]1->2 3->4 \n"
            + "Down Requests: [(2)]5->4 7->6 \n"
            + "Elevator Reports: \n"
            + "elevator" + elevatorId1 + ": Out of Service[Floor 0]\n"
            + "elevator" + elevatorId2 + ": Out of Service[Floor 0]\n",
        this.buildingReport01.toString());

  }

  /**
   * Test the generateLog method. It will both return a string and print to the console.
   */

  @Test
  public void testGenerateLog() {
    int elevatorId1 = this.buildingReport01.getElevatorReports()[0].getElevatorId();
    int elevatorId2 = this.buildingReport01.getElevatorReports()[1].getElevatorId();

    assertEquals("****************************\n"
            + "Building Report\n"
            + "Number of Floors: 10\n"
            + "Number of Elevators: 2\n"
            + "Elevator Capacity: 10\n"
            + "Elevator System Status: Running\n"
            + "Up Requests: [(2)]1->2 3->4 \n"
            + "Down Requests: [(2)]5->4 7->6 \n"
            + "Elevator Reports: \n"
            + "elevator" + elevatorId1 + ": Out of Service[Floor 0]\n"
            + "elevator" + elevatorId2 + ": Out of Service[Floor 0]\n"
            + "****************************\n",
        this.buildingReport01.generateLog());
  }

}
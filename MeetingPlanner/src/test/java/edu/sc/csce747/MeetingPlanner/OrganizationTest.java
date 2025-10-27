package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

/**
 * Unit tests for the Organization class.
 * Tests the functionality of managing people and rooms in an organization.
 */
public class OrganizationTest {
    private Organization organization;
    
    @Before
    public void setUp() {
        organization = new Organization();
    }
    
    // Positive test cases
    @Test
    public void testOrganizationDefaultConstructor() {
        assertNotNull("Organization should be created", organization);
    }
    
    @Test
    public void testGetEmployees() {
        ArrayList<Person> employees = organization.getEmployees();
        assertNotNull("Employees list should not be null", employees);
        assertTrue("Should have at least 5 employees", employees.size() >= 5);
        
        // Check that specific employees exist
        boolean hasGreg = false, hasManton = false, hasJohn = false;
        for (Person employee : employees) {
            if ("Greg Gay".equals(employee.getName())) hasGreg = true;
            if ("Manton Matthews".equals(employee.getName())) hasManton = true;
            if ("John Rose".equals(employee.getName())) hasJohn = true;
        }
        
        assertTrue("Should have Greg Gay", hasGreg);
        assertTrue("Should have Manton Matthews", hasManton);
        assertTrue("Should have John Rose", hasJohn);
    }
    
    @Test
    public void testGetRooms() {
        ArrayList<Room> rooms = organization.getRooms();
        assertNotNull("Rooms list should not be null", rooms);
        assertTrue("Should have at least 5 rooms", rooms.size() >= 5);
        
        // Check that specific rooms exist
        boolean has2A01 = false, has2A02 = false, has2A03 = false;
        for (Room room : rooms) {
            if ("2A01".equals(room.getID())) has2A01 = true;
            if ("2A02".equals(room.getID())) has2A02 = true;
            if ("2A03".equals(room.getID())) has2A03 = true;
        }
        
        assertTrue("Should have room 2A01", has2A01);
        assertTrue("Should have room 2A02", has2A02);
        assertTrue("Should have room 2A03", has2A03);
    }
    
    @Test
    public void testGetRoom_ValidRoom() {
        try {
            Room room = organization.getRoom("2A01");
            assertNotNull("Room should be found", room);
            assertEquals("Room ID should match", "2A01", room.getID());
        } catch(Exception e) {
            fail("Should find existing room: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetRoom_AnotherValidRoom() {
        try {
            Room room = organization.getRoom("2A05");
            assertNotNull("Room should be found", room);
            assertEquals("Room ID should match", "2A05", room.getID());
        } catch(Exception e) {
            fail("Should find existing room: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetEmployee_ValidEmployee() {
        try {
            Person employee = organization.getEmployee("Greg Gay");
            assertNotNull("Employee should be found", employee);
            assertEquals("Employee name should match", "Greg Gay", employee.getName());
        } catch(Exception e) {
            fail("Should find existing employee: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetEmployee_AnotherValidEmployee() {
        try {
            Person employee = organization.getEmployee("Csilla Farkas");
            assertNotNull("Employee should be found", employee);
            assertEquals("Employee name should match", "Csilla Farkas", employee.getName());
        } catch(Exception e) {
            fail("Should find existing employee: " + e.getMessage());
        }
    }
    
    // Negative test cases
    @Test
    public void testGetRoom_NonExistentRoom() {
        try {
            organization.getRoom("NonExistentRoom");
            fail("Should throw exception for non-existent room");
        } catch(Exception e) {
            assertTrue("Exception message should indicate room doesn't exist", 
                      e.getMessage().contains("Requested room does not exist"));
        }
    }
    
    @Test
    public void testGetRoom_NullID() {
        try {
            organization.getRoom(null);
            fail("Should throw exception for null room ID");
        } catch(Exception e) {
            assertNotNull("Exception should have a message", e.getMessage());
        }
    }
    
    @Test
    public void testGetRoom_EmptyID() {
        try {
            organization.getRoom("");
            fail("Should throw exception for empty room ID");
        } catch(Exception e) {
            assertTrue("Exception message should indicate room doesn't exist", 
                      e.getMessage().contains("Requested room does not exist"));
        }
    }
    
    @Test
    public void testGetEmployee_NonExistentEmployee() {
        try {
            organization.getEmployee("NonExistentEmployee");
            fail("Should throw exception for non-existent employee");
        } catch(Exception e) {
            assertTrue("Exception message should indicate employee doesn't exist", 
                      e.getMessage().contains("Requested employee does not exist"));
        }
    }
    
    @Test
    public void testGetEmployee_NullName() {
        try {
            organization.getEmployee(null);
            fail("Should throw exception for null employee name");
        } catch(Exception e) {
            assertNotNull("Exception should have a message", e.getMessage());
        }
    }
    
    @Test
    public void testGetEmployee_EmptyName() {
        try {
            organization.getEmployee("");
            fail("Should throw exception for empty employee name");
        } catch(Exception e) {
            assertTrue("Exception message should indicate employee doesn't exist", 
                      e.getMessage().contains("Requested employee does not exist"));
        }
    }
    
    // Edge case tests
    @Test
    public void testGetRoom_CaseSensitive() {
        try {
            organization.getRoom("2a01"); // lowercase
            fail("Should be case sensitive");
        } catch(Exception e) {
            assertTrue("Should be case sensitive", 
                      e.getMessage().contains("Requested room does not exist"));
        }
    }
    
    @Test
    public void testGetEmployee_CaseSensitive() {
        try {
            organization.getEmployee("greg gay"); // lowercase
            fail("Should be case sensitive");
        } catch(Exception e) {
            assertTrue("Should be case sensitive", 
                      e.getMessage().contains("Requested employee does not exist"));
        }
    }
    
    @Test
    public void testGetRoom_WithSpaces() {
        try {
            organization.getRoom("2A01 "); // with trailing space
            fail("Should not find room with trailing space");
        } catch(Exception e) {
            assertTrue("Should not find room with trailing space", 
                      e.getMessage().contains("Requested room does not exist"));
        }
    }
    
    @Test
    public void testGetEmployee_WithSpaces() {
        try {
            organization.getEmployee("Greg Gay "); // with trailing space
            fail("Should not find employee with trailing space");
        } catch(Exception e) {
            assertTrue("Should not find employee with trailing space", 
                      e.getMessage().contains("Requested employee does not exist"));
        }
    }
}

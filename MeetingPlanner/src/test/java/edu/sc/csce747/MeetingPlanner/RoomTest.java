package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class RoomTest {
    private Room room;
    
    @Before
    public void setUp() {
        room = new Room("Conference Room A");
    }
    
    // Positive test cases
    @Test
    public void testRoomDefaultConstructor() {
        Room defaultRoom = new Room();
        assertNotNull("Room should be created", defaultRoom);
        assertEquals("Default ID should be empty", "", defaultRoom.getID());
    }
    
    @Test
    public void testRoomConstructorWithID() {
        Room namedRoom = new Room("Meeting Room 101");
        assertEquals("ID should be set correctly", "Meeting Room 101", namedRoom.getID());
    }
    
    @Test
    public void testAddMeeting_validMeeting() {
        try {
            Meeting meeting = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            room.addMeeting(meeting);
            assertTrue("Room should be busy during meeting time", 
                      room.isBusy(6, 15, 9, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testIsBusy_validTime() {
        try {
            Meeting meeting = new Meeting(7, 20, 10, 12, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            room.addMeeting(meeting);
            
            assertTrue("Should be busy during meeting", room.isBusy(7, 20, 10, 12));
            assertTrue("Should be busy during meeting start", room.isBusy(7, 20, 10, 10));
            assertTrue("Should be busy during meeting end", room.isBusy(7, 20, 12, 12));
            assertFalse("Should not be busy before meeting", room.isBusy(7, 20, 9, 9));
            assertFalse("Should not be busy after meeting", room.isBusy(7, 20, 13, 13));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testPrintAgenda_month() {
        try {
            Meeting meeting = new Meeting(8, 10, 14, 15, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            room.addMeeting(meeting);
            
            String agenda = room.printAgenda(8);
            assertNotNull("Agenda should not be null", agenda);
            assertTrue("Agenda should contain month", agenda.contains("8"));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testPrintAgenda_day() {
        try {
            Meeting meeting = new Meeting(9, 25, 11, 12, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            room.addMeeting(meeting);
            
            String agenda = room.printAgenda(9, 25);
            assertNotNull("Agenda should not be null", agenda);
            assertTrue("Agenda should contain date", agenda.contains("9/25"));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetMeeting() {
        try {
            Meeting meeting = new Meeting(10, 5, 9, 10, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            room.addMeeting(meeting);
            
            Meeting retrieved = room.getMeeting(10, 5, 0);
            assertNotNull("Retrieved meeting should not be null", retrieved);
            assertEquals("Meeting month should match", 10, retrieved.getMonth());
            assertEquals("Meeting day should match", 5, retrieved.getDay());
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRemoveMeeting() {
        try {
            Meeting meeting = new Meeting(11, 15, 13, 14, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            room.addMeeting(meeting);
            assertTrue("Room should be busy", room.isBusy(11, 15, 13, 14));
            
            room.removeMeeting(11, 15, 0);
            assertFalse("Room should not be busy after removal", room.isBusy(11, 15, 13, 14));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    // Negative test cases
    @Test
    public void testAddMeeting_timeConflict() {
        try {
            Meeting firstMeeting = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Room A"), "First Meeting");
            Meeting conflictingMeeting = new Meeting(6, 15, 10, 12, new ArrayList<>(), new Room("Room B"), "Conflicting Meeting");
            
            room.addMeeting(firstMeeting);
            room.addMeeting(conflictingMeeting);
            fail("Should throw TimeConflictException for conflicting meetings");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain conflict information", 
                      e.getMessage().contains("Conflict for room"));
            assertTrue("Exception message should contain room ID", 
                      e.getMessage().contains("Conference Room A"));
        }
    }
    
    @Test
    public void testAddMeeting_invalidTime() {
        try {
            Meeting invalidMeeting = new Meeting(6, 35, 9, 11);
            room.addMeeting(invalidMeeting);
            fail("Should throw TimeConflictException for invalid day");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain conflict information", 
                      e.getMessage().contains("Conflict for room"));
        }
    }
    
    @Test
    public void testIsBusy_invalidParameters() {
        try {
            room.isBusy(0, 15, 9, 11);
            fail("Should throw TimeConflictException for invalid month");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid month", "Month does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testMultipleRoomsDifferentSchedules() {
        // Test that different rooms can have different schedules
        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");
        
        try {
            Meeting meeting1 = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Room 1"), "Meeting 1");
            Meeting meeting2 = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Room 2"), "Meeting 2");
            
            room1.addMeeting(meeting1);
            room2.addMeeting(meeting2);
            
            assertTrue("Room 1 should be busy", room1.isBusy(6, 15, 9, 11));
            assertTrue("Room 2 should be busy", room2.isBusy(6, 15, 9, 11));
            
            // Both rooms can have the same meeting time without conflict
            assertTrue("Both rooms should be able to have meetings at same time", 
                      room1.isBusy(6, 15, 9, 11) && room2.isBusy(6, 15, 9, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}
package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class CalendarTest {
    private Calendar calendar;
    
    @Before
    public void setUp() {
        calendar = new Calendar();
    }
    
    // Positive test cases - valid inputs
	@Test
	public void testAddMeeting_holiday() {
		// Create Midsommar holiday
		try {
			Meeting midsommar = new Meeting(6, 26, "Midsommar");
			calendar.addMeeting(midsommar);
			// Verify that it was added.
			Boolean added = calendar.isBusy(6, 26, 0, 23);
            assertTrue("Midsommar should be marked as busy on the calendar", added);
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_validTimeSlot() {
        // Test adding a meeting in a valid time slot
        try {
            Meeting meeting = new Meeting(3, 15, 9, 11, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Calendar should be busy during meeting time", calendar.isBusy(3, 15, 9, 11));
            assertTrue("Calendar should be busy during meeting start", calendar.isBusy(3, 15, 9, 9));
            assertTrue("Calendar should be busy during meeting end", calendar.isBusy(3, 15, 11, 11));
            assertFalse("Calendar should not be busy before meeting", calendar.isBusy(3, 15, 8, 8));
            assertFalse("Calendar should not be busy after meeting", calendar.isBusy(3, 15, 12, 12));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testAddMultipleMeetingsSameDay() {
        // Test adding multiple meetings on the same day
        try {
            Meeting morning = new Meeting(4, 10, 9, 10, new ArrayList<>(), new Room("Room A"), "Morning Meeting");
            Meeting afternoon = new Meeting(4, 10, 14, 15, new ArrayList<>(), new Room("Room B"), "Afternoon Meeting");
            
            calendar.addMeeting(morning);
            calendar.addMeeting(afternoon);
            
            assertTrue("Morning meeting should be scheduled", calendar.isBusy(4, 10, 9, 10));
            assertTrue("Afternoon meeting should be scheduled", calendar.isBusy(4, 10, 14, 15));
            assertFalse("Time between meetings should be free", calendar.isBusy(4, 10, 11, 13));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testIsBusy_validTimeRange() {
        // Test checking busy status for valid time ranges
        try {
            Meeting meeting = new Meeting(5, 20, 10, 12, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            calendar.addMeeting(meeting);
            
            // Test various time ranges
            assertTrue("Should be busy during exact meeting time", calendar.isBusy(5, 20, 10, 12));
            assertTrue("Should be busy when start overlaps", calendar.isBusy(5, 20, 9, 11));
            assertTrue("Should be busy when end overlaps", calendar.isBusy(5, 20, 11, 13));
            assertTrue("Should be busy when completely contains meeting", calendar.isBusy(5, 20, 9, 13));
            assertTrue("Should be busy when meeting contains time range", calendar.isBusy(5, 20, 10, 11));
            
            assertFalse("Should not be busy before meeting", calendar.isBusy(5, 20, 8, 9));
            assertFalse("Should not be busy after meeting", calendar.isBusy(5, 20, 13, 14));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testPrintAgenda_month() {
        // Test printing agenda for a month
        try {
            Meeting meeting1 = new Meeting(6, 5, 9, 10, new ArrayList<>(), new Room("Room 1"), "Meeting 1");
            Meeting meeting2 = new Meeting(6, 15, 14, 15, new ArrayList<>(), new Room("Room 2"), "Meeting 2");
            
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            
            String agenda = calendar.printAgenda(6);
            assertNotNull("Agenda should not be null", agenda);
            assertTrue("Agenda should contain Meeting 1", agenda.contains("Meeting 1"));
            assertTrue("Agenda should contain Meeting 2", agenda.contains("Meeting 2"));
            assertTrue("Agenda should contain month number", agenda.contains("6"));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testPrintAgenda_day() {
        // Test printing agenda for a specific day
        try {
            Meeting meeting = new Meeting(7, 25, 10, 11, new ArrayList<>(), new Room("Conference Room"), "Daily Standup");
            calendar.addMeeting(meeting);
            
            String agenda = calendar.printAgenda(7, 25);
            assertNotNull("Agenda should not be null", agenda);
            assertTrue("Agenda should contain meeting description", agenda.contains("Daily Standup"));
            assertTrue("Agenda should contain date", agenda.contains("7/25"));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testClearSchedule() {
        // Test clearing schedule for a day
        try {
            Meeting meeting = new Meeting(8, 30, 9, 10, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Calendar should be busy", calendar.isBusy(8, 30, 9, 10));
            
            calendar.clearSchedule(8, 30);
            assertFalse("Calendar should not be busy after clearing", calendar.isBusy(8, 30, 9, 10));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetMeeting() {
        // Test getting a specific meeting
        try {
            Meeting meeting = new Meeting(9, 10, 11, 12, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            calendar.addMeeting(meeting);
            
            Meeting retrieved = calendar.getMeeting(9, 10, 0);
            assertNotNull("Retrieved meeting should not be null", retrieved);
            assertEquals("Meeting month should match", 9, retrieved.getMonth());
            assertEquals("Meeting day should match", 10, retrieved.getDay());
            assertEquals("Meeting start time should match", 11, retrieved.getStartTime());
            assertEquals("Meeting end time should match", 12, retrieved.getEndTime());
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRemoveMeeting() {
        // Test removing a meeting
        try {
            Meeting meeting = new Meeting(10, 15, 13, 14, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Calendar should be busy", calendar.isBusy(10, 15, 13, 14));
            
            calendar.removeMeeting(10, 15, 0);
            assertFalse("Calendar should not be busy after removal", calendar.isBusy(10, 15, 13, 14));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
}
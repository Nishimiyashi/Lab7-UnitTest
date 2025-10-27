package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

/**
 * Edge case tests to find additional bugs in the Meeting Planner system.
 * These tests focus on boundary conditions, edge cases, and unusual scenarios.
 */
public class EdgeCaseTest {
    private Calendar calendar;
    private Person person;
    private Room room;
    
    @Before
    public void setUp() {
        calendar = new Calendar();
        person = new Person("Test Person");
        room = new Room("Test Room");
    }
    
    // Test edge cases for Calendar class
    
    @Test
    public void testCalendar_LeapYearFebruary29() {
        // Test February 29 in a leap year (should be valid)
        try {
            Meeting meeting = new Meeting(2, 29, 10, 11, new ArrayList<>(), new Room("Room A"), "Leap Day Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Should be able to schedule meeting on February 29", 
                      calendar.isBusy(2, 29, 10, 11));
        } catch(TimeConflictException e) {
            fail("February 29 should be valid in leap years: " + e.getMessage());
        }
    }
    
    @Test
    public void testCalendar_NonLeapYearFebruary29() {
        // Test February 29 in a non-leap year (should be invalid)
        try {
            Meeting meeting = new Meeting(2, 29, 10, 11, new ArrayList<>(), new Room("Room A"), "Invalid Leap Day");
            calendar.addMeeting(meeting);
            fail("February 29 should be invalid in non-leap years");
        } catch(TimeConflictException e) {
            assertTrue("Should throw exception for invalid date", 
                      e.getMessage().contains("Day does not exist") || 
                      e.getMessage().contains("Month does not exist"));
        }
    }
    
    @Test
    public void testCalendar_MidnightMeeting() {
        // Test meeting at midnight (hour 0)
        try {
            Meeting meeting = new Meeting(6, 15, 0, 1, new ArrayList<>(), new Room("Room A"), "Midnight Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Should be able to schedule meeting at midnight", 
                      calendar.isBusy(6, 15, 0, 1));
        } catch(TimeConflictException e) {
            fail("Midnight meetings should be valid: " + e.getMessage());
        }
    }
    
    @Test
    public void testCalendar_LateNightMeeting() {
        // Test meeting ending at 23:59 (last hour of day)
        try {
            Meeting meeting = new Meeting(6, 15, 22, 23, new ArrayList<>(), new Room("Room A"), "Late Night Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Should be able to schedule late night meeting", 
                      calendar.isBusy(6, 15, 22, 23));
        } catch(TimeConflictException e) {
            fail("Late night meetings should be valid: " + e.getMessage());
        }
    }
    
    @Test
    public void testCalendar_AllDayMeeting() {
        // Test meeting spanning entire day (0 to 23)
        try {
            Meeting meeting = new Meeting(6, 15, 0, 23, new ArrayList<>(), new Room("Room A"), "All Day Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Should be able to schedule all-day meeting", 
                      calendar.isBusy(6, 15, 0, 23));
        } catch(TimeConflictException e) {
            fail("All-day meetings should be valid: " + e.getMessage());
        }
    }
    
    @Test
    public void testCalendar_ZeroDurationMeeting() {
        // Test meeting with zero duration (start == end)
        try {
            Meeting meeting = new Meeting(6, 15, 10, 10, new ArrayList<>(), new Room("Room A"), "Zero Duration");
            calendar.addMeeting(meeting);
            assertTrue("Should be able to schedule zero-duration meeting", 
                      calendar.isBusy(6, 15, 10, 10));
        } catch(TimeConflictException e) {
            fail("Zero-duration meetings should be valid: " + e.getMessage());
        }
    }
    
    @Test
    public void testCalendar_MaximumMeetingsPerDay() {
        // Test adding maximum number of meetings per day
        try {
            // Add meetings every hour from 0 to 22 (23 meetings total)
            for (int hour = 0; hour < 23; hour++) {
                Meeting meeting = new Meeting(6, 15, hour, hour + 1, new ArrayList<>(), new Room("Room A"), "Meeting " + hour);
                calendar.addMeeting(meeting);
            }
            
            // Verify all meetings are scheduled
            for (int hour = 0; hour < 23; hour++) {
                assertTrue("Meeting at hour " + hour + " should be scheduled", 
                          calendar.isBusy(6, 15, hour, hour + 1));
            }
        } catch(TimeConflictException e) {
            fail("Should be able to schedule maximum meetings per day: " + e.getMessage());
        }
    }
    
    @Test
    public void testCalendar_ConsecutiveMeetings() {
        // Test consecutive meetings (one ends when next starts)
        try {
            Meeting meeting1 = new Meeting(6, 15, 9, 10, new ArrayList<>(), new Room("Room A"), "Meeting 1");
            Meeting meeting2 = new Meeting(6, 15, 10, 11, new ArrayList<>(), new Room("Room B"), "Meeting 2");
            
            calendar.addMeeting(meeting1);
            calendar.addMeeting(meeting2);
            
            assertTrue("First meeting should be scheduled", calendar.isBusy(6, 15, 9, 10));
            assertTrue("Second meeting should be scheduled", calendar.isBusy(6, 15, 10, 11));
        } catch(TimeConflictException e) {
            fail("Consecutive meetings should be valid: " + e.getMessage());
        }
    }
    
    // Test edge cases for Meeting class
    
    @Test
    public void testMeeting_NullDescription() {
        // Test meeting with null description
        try {
            Meeting meeting = new Meeting(6, 15, 9, 10, new ArrayList<>(), new Room("Room A"), null);
            assertNull("Description should be null", meeting.getDescription());
        } catch(Exception e) {
            fail("Null description should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testMeeting_EmptyDescription() {
        // Test meeting with empty description
        try {
            Meeting meeting = new Meeting(6, 15, 9, 10, new ArrayList<>(), new Room("Room A"), "");
            assertEquals("Description should be empty", "", meeting.getDescription());
        } catch(Exception e) {
            fail("Empty description should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testMeeting_VeryLongDescription() {
        // Test meeting with very long description
        String longDescription = "A".repeat(1000); // 1000 character description
        try {
            Meeting meeting = new Meeting(6, 15, 9, 10, new ArrayList<>(), new Room("Room A"), longDescription);
            assertEquals("Long description should be preserved", longDescription, meeting.getDescription());
        } catch(Exception e) {
            fail("Long description should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testMeeting_SpecialCharactersInDescription() {
        // Test meeting with special characters in description
        String specialDescription = "Meeting with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?";
        try {
            Meeting meeting = new Meeting(6, 15, 9, 10, new ArrayList<>(), new Room("Room A"), specialDescription);
            assertEquals("Special characters should be preserved", specialDescription, meeting.getDescription());
        } catch(Exception e) {
            fail("Special characters should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testMeeting_NullRoom() {
        // Test meeting with null room
        try {
            Meeting meeting = new Meeting(6, 15, 9, 10, new ArrayList<>(), null, "Test Meeting");
            assertNull("Room should be null", meeting.getRoom());
        } catch(Exception e) {
            fail("Null room should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testMeeting_NullAttendees() {
        // Test meeting with null attendees list
        try {
            Meeting meeting = new Meeting(6, 15, 9, 10, null, new Room("Room A"), "Test Meeting");
            assertNull("Attendees should be null", meeting.getAttendees());
        } catch(Exception e) {
            fail("Null attendees should be handled gracefully: " + e.getMessage());
        }
    }
    
    // Test edge cases for Person class
    
    @Test
    public void testPerson_EmptyName() {
        // Test person with empty name
        Person emptyNamePerson = new Person("");
        assertEquals("Empty name should be preserved", "", emptyNamePerson.getName());
    }
    
    @Test
    public void testPerson_NullName() {
        // Test person with null name
        try {
            Person nullNamePerson = new Person(null);
            assertNull("Null name should be preserved", nullNamePerson.getName());
        } catch(Exception e) {
            fail("Null name should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testPerson_VeryLongName() {
        // Test person with very long name
        String longName = "A".repeat(1000); // 1000 character name
        try {
            Person longNamePerson = new Person(longName);
            assertEquals("Long name should be preserved", longName, longNamePerson.getName());
        } catch(Exception e) {
            fail("Long name should be handled gracefully: " + e.getMessage());
        }
    }
    
    // Test edge cases for Room class
    
    @Test
    public void testRoom_EmptyID() {
        // Test room with empty ID
        Room emptyIdRoom = new Room("");
        assertEquals("Empty ID should be preserved", "", emptyIdRoom.getID());
    }
    
    @Test
    public void testRoom_NullID() {
        // Test room with null ID
        try {
            Room nullIdRoom = new Room(null);
            assertNull("Null ID should be preserved", nullIdRoom.getID());
        } catch(Exception e) {
            fail("Null ID should be handled gracefully: " + e.getMessage());
        }
    }
    
    @Test
    public void testRoom_VeryLongID() {
        // Test room with very long ID
        String longId = "A".repeat(1000); // 1000 character ID
        try {
            Room longIdRoom = new Room(longId);
            assertEquals("Long ID should be preserved", longId, longIdRoom.getID());
        } catch(Exception e) {
            fail("Long ID should be handled gracefully: " + e.getMessage());
        }
    }
    
    // Test edge cases for Calendar.isBusy method
    
    @Test
    public void testIsBusy_ExactBoundaryTimes() {
        // Test isBusy with exact boundary times
        try {
            Meeting meeting = new Meeting(6, 15, 10, 12, new ArrayList<>(), new Room("Room A"), "Test Meeting");
            calendar.addMeeting(meeting);
            
            // Test exact start time
            assertTrue("Should be busy at exact start time", calendar.isBusy(6, 15, 10, 10));
            // Test exact end time
            assertTrue("Should be busy at exact end time", calendar.isBusy(6, 15, 12, 12));
            // Test just before start
            assertFalse("Should not be busy just before start", calendar.isBusy(6, 15, 9, 9));
            // Test just after end
            assertFalse("Should not be busy just after end", calendar.isBusy(6, 15, 13, 13));
        } catch(TimeConflictException e) {
            fail("Boundary time tests should work: " + e.getMessage());
        }
    }
    
    @Test
    public void testIsBusy_SingleHourRange() {
        // Test isBusy with single hour range
        try {
            Meeting meeting = new Meeting(6, 15, 10, 11, new ArrayList<>(), new Room("Room A"), "Test Meeting");
            calendar.addMeeting(meeting);
            
            assertTrue("Should be busy during single hour", calendar.isBusy(6, 15, 10, 11));
            assertFalse("Should not be busy before", calendar.isBusy(6, 15, 9, 10));
            assertFalse("Should not be busy after", calendar.isBusy(6, 15, 11, 12));
        } catch(TimeConflictException e) {
            fail("Single hour range tests should work: " + e.getMessage());
        }
    }
}

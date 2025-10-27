package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class CalendarNegativeTest {
    private Calendar calendar;
    
    @Before
    public void setUp() {
        calendar = new Calendar();
    }
    
    // Negative test cases - invalid inputs and edge cases
    
    @Test
    public void testAddMeeting_invalidDay_negative() {
        // Test adding meeting with negative day
        try {
            Meeting meeting = new Meeting(6, -1, 9, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for negative day");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid day", "Day does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidDay_zero() {
        // Test adding meeting with day 0
        try {
            Meeting meeting = new Meeting(6, 0, 9, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for day 0");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid day", "Day does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidDay_tooLarge() {
        // Test adding meeting with day > 31
        try {
            Meeting meeting = new Meeting(6, 35, 9, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for day > 31");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid day", "Day does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidMonth_negative() {
        // Test adding meeting with negative month
        try {
            Meeting meeting = new Meeting(-1, 15, 9, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for negative month");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid month", "Month does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidMonth_zero() {
        // Test adding meeting with month 0
        try {
            Meeting meeting = new Meeting(0, 15, 9, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for month 0");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid month", "Month does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidMonth_tooLarge() {
        // Test adding meeting with month > 12
        try {
            Meeting meeting = new Meeting(13, 15, 9, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for month > 12");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid month", "Month does not exist.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidStartTime_negative() {
        // Test adding meeting with negative start time
        try {
            Meeting meeting = new Meeting(6, 15, -1, 11);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for negative start time");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate illegal hour", "Illegal hour.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidStartTime_tooLarge() {
        // Test adding meeting with start time > 23
        try {
            Meeting meeting = new Meeting(6, 15, 24, 25);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for start time > 23");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate illegal hour", "Illegal hour.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidEndTime_negative() {
        // Test adding meeting with negative end time
        try {
            Meeting meeting = new Meeting(6, 15, 9, -1);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for negative end time");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate illegal hour", "Illegal hour.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_invalidEndTime_tooLarge() {
        // Test adding meeting with end time > 23
        try {
            Meeting meeting = new Meeting(6, 15, 9, 24);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException for end time > 23");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate illegal hour", "Illegal hour.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_startTimeEqualsEndTime() {
        // Test adding meeting where start time equals end time
        try {
            Meeting meeting = new Meeting(6, 15, 9, 9);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException when start time equals end time");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate meeting starts before it ends", 
                        "Meeting starts before it ends.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_startTimeAfterEndTime() {
        // Test adding meeting where start time is after end time
        try {
            Meeting meeting = new Meeting(6, 15, 11, 9);
            calendar.addMeeting(meeting);
            fail("Should throw TimeConflictException when start time is after end time");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate meeting starts before it ends", 
                        "Meeting starts before it ends.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_timeConflict() {
        // Test adding overlapping meetings
        try {
            Meeting firstMeeting = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Room A"), "First Meeting");
            Meeting conflictingMeeting = new Meeting(6, 15, 10, 12, new ArrayList<>(), new Room("Room B"), "Conflicting Meeting");
            
            calendar.addMeeting(firstMeeting);
            calendar.addMeeting(conflictingMeeting);
            fail("Should throw TimeConflictException for overlapping meetings");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain overlap information", 
                      e.getMessage().contains("Overlap with another item"));
        }
    }
    
    @Test
    public void testAddMeeting_exactTimeConflict() {
        // Test adding meetings with exact same time
        try {
            Meeting firstMeeting = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Room A"), "First Meeting");
            Meeting exactConflict = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Room B"), "Exact Conflict");
            
            calendar.addMeeting(firstMeeting);
            calendar.addMeeting(exactConflict);
            fail("Should throw TimeConflictException for exact time conflict");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain overlap information", 
                      e.getMessage().contains("Overlap with another item"));
        }
    }
    
    @Test
    public void testAddMeeting_partialOverlap() {
        // Test adding meetings with partial overlap
        try {
            Meeting firstMeeting = new Meeting(6, 15, 9, 12, new ArrayList<>(), new Room("Room A"), "First Meeting");
            Meeting partialOverlap = new Meeting(6, 15, 11, 14, new ArrayList<>(), new Room("Room B"), "Partial Overlap");
            
            calendar.addMeeting(firstMeeting);
            calendar.addMeeting(partialOverlap);
            fail("Should throw TimeConflictException for partial overlap");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain overlap information", 
                      e.getMessage().contains("Overlap with another item"));
        }
    }
    
    @Test
    public void testIsBusy_invalidParameters() {
        // Test isBusy with invalid parameters
        try {
            calendar.isBusy(0, 15, 9, 11);
            fail("Should throw TimeConflictException for invalid month");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid month", "Month does not exist.", e.getMessage());
        }
        
        try {
            calendar.isBusy(6, 0, 9, 11);
            fail("Should throw TimeConflictException for invalid day");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid day", "Day does not exist.", e.getMessage());
        }
        
        try {
            calendar.isBusy(6, 15, -1, 11);
            fail("Should throw TimeConflictException for invalid start time");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate illegal hour", "Illegal hour.", e.getMessage());
        }
        
        try {
            calendar.isBusy(6, 15, 9, 24);
            fail("Should throw TimeConflictException for invalid end time");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate illegal hour", "Illegal hour.", e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_nonExistentDays() {
        // Test adding meetings to non-existent days (February 30, April 31, etc.)
        try {
            Meeting feb30 = new Meeting(2, 30, 9, 11);
            calendar.addMeeting(feb30);
            fail("Should throw TimeConflictException for February 30");
        } catch(TimeConflictException e) {
            assertTrue("Should indicate day does not exist or conflict", 
                      e.getMessage().contains("Day does not exist") || 
                      e.getMessage().contains("Overlap"));
        }
        
        try {
            Meeting apr31 = new Meeting(4, 31, 9, 11);
            calendar.addMeeting(apr31);
            fail("Should throw TimeConflictException for April 31");
        } catch(TimeConflictException e) {
            assertTrue("Should indicate day does not exist or conflict", 
                      e.getMessage().contains("Day does not exist") || 
                      e.getMessage().contains("Overlap"));
        }
    }
    
    @Test
    public void testEdgeCase_boundaryValues() {
        // Test boundary values
        try {
            // Test minimum valid values
            Meeting minValid = new Meeting(1, 1, 0, 1, new ArrayList<>(), new Room("Test Room"), "Min Valid");
            calendar.addMeeting(minValid);
            assertTrue("Should be able to add meeting with minimum valid values", 
                      calendar.isBusy(1, 1, 0, 1));
        } catch(TimeConflictException e) {
            fail("Should not throw exception for minimum valid values: " + e.getMessage());
        }
        
        try {
            // Test maximum valid values
            Meeting maxValid = new Meeting(12, 31, 22, 23, new ArrayList<>(), new Room("Test Room"), "Max Valid");
            calendar.addMeeting(maxValid);
            assertTrue("Should be able to add meeting with maximum valid values", 
                      calendar.isBusy(12, 31, 22, 23));
        } catch(TimeConflictException e) {
            fail("Should not throw exception for maximum valid values: " + e.getMessage());
        }
    }
}

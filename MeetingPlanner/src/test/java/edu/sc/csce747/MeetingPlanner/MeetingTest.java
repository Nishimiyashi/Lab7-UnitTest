package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class MeetingTest {
    
    @Test
    public void testMeetingDefaultConstructor() {
        // Test default constructor
        Meeting meeting = new Meeting();
        assertNotNull("Meeting should be created", meeting);
        assertEquals("Default month should be 0", 0, meeting.getMonth());
        assertEquals("Default day should be 0", 0, meeting.getDay());
        assertEquals("Default start time should be 0", 0, meeting.getStartTime());
        assertEquals("Default end time should be 0", 0, meeting.getEndTime());
    }
    
    @Test
    public void testMeetingConstructorWithMonthAndDay() {
        // Test constructor with month and day
        Meeting meeting = new Meeting(6, 15);
        assertEquals("Month should be 6", 6, meeting.getMonth());
        assertEquals("Day should be 15", 15, meeting.getDay());
        assertEquals("Start time should be 0", 0, meeting.getStartTime());
        assertEquals("End time should be 23", 23, meeting.getEndTime());
    }
    
    @Test
    public void testMeetingConstructorWithMonthDayAndDescription() {
        // Test constructor with month, day and description
        Meeting meeting = new Meeting(6, 15, "Team Meeting");
        assertEquals("Month should be 6", 6, meeting.getMonth());
        assertEquals("Day should be 15", 15, meeting.getDay());
        assertEquals("Description should be 'Team Meeting'", "Team Meeting", meeting.getDescription());
        assertEquals("Start time should be 0", 0, meeting.getStartTime());
        assertEquals("End time should be 23", 23, meeting.getEndTime());
    }
    
    @Test
    public void testMeetingConstructorWithTimes() {
        // Test constructor with month, day, start and end times
        Meeting meeting = new Meeting(6, 15, 9, 11);
        assertEquals("Month should be 6", 6, meeting.getMonth());
        assertEquals("Day should be 15", 15, meeting.getDay());
        assertEquals("Start time should be 9", 9, meeting.getStartTime());
        assertEquals("End time should be 11", 11, meeting.getEndTime());
    }
    
    @Test
    public void testMeetingFullConstructor() {
        // Test full constructor with all parameters
        ArrayList<Person> attendees = new ArrayList<>();
        attendees.add(new Person("John"));
        attendees.add(new Person("Jane"));
        
        Room room = new Room("Conference Room A");
        Meeting meeting = new Meeting(6, 15, 9, 11, attendees, room, "Project Review");
        
        assertEquals("Month should be 6", 6, meeting.getMonth());
        assertEquals("Day should be 15", 15, meeting.getDay());
        assertEquals("Start time should be 9", 9, meeting.getStartTime());
        assertEquals("End time should be 11", 11, meeting.getEndTime());
        assertEquals("Description should be 'Project Review'", "Project Review", meeting.getDescription());
        assertEquals("Room should match", room, meeting.getRoom());
        assertEquals("Attendees should match", attendees, meeting.getAttendees());
    }
    
    @Test
    public void testSettersAndGetters() {
        // Test setters and getters
        Meeting meeting = new Meeting();
        
        meeting.setMonth(7);
        meeting.setDay(20);
        meeting.setStartTime(10);
        meeting.setEndTime(12);
        meeting.setDescription("Weekly Standup");
        
        Room room = new Room("Room 101");
        meeting.setRoom(room);
        
        assertEquals("Month should be 7", 7, meeting.getMonth());
        assertEquals("Day should be 20", 20, meeting.getDay());
        assertEquals("Start time should be 10", 10, meeting.getStartTime());
        assertEquals("End time should be 12", 12, meeting.getEndTime());
        assertEquals("Description should be 'Weekly Standup'", "Weekly Standup", meeting.getDescription());
        assertEquals("Room should match", room, meeting.getRoom());
    }
    
    @Test
    public void testAddAndRemoveAttendee() {
        // Test adding and removing attendees
        ArrayList<Person> attendees = new ArrayList<>();
        Meeting meeting = new Meeting(6, 15, 9, 11, attendees, new Room(), "Test Meeting");
        
        Person john = new Person("John");
        Person jane = new Person("Jane");
        
        meeting.addAttendee(john);
        assertEquals("Should have 1 attendee", 1, meeting.getAttendees().size());
        assertTrue("Should contain John", meeting.getAttendees().contains(john));
        
        meeting.addAttendee(jane);
        assertEquals("Should have 2 attendees", 2, meeting.getAttendees().size());
        assertTrue("Should contain Jane", meeting.getAttendees().contains(jane));
        
        meeting.removeAttendee(john);
        assertEquals("Should have 1 attendee after removal", 1, meeting.getAttendees().size());
        assertFalse("Should not contain John", meeting.getAttendees().contains(john));
        assertTrue("Should still contain Jane", meeting.getAttendees().contains(jane));
    }
}
package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class PersonTest {
    private Person person;
    
    @Before
    public void setUp() {
        person = new Person("John Doe");
    }
    
    // Positive test cases
    @Test
    public void testPersonDefaultConstructor() {
        Person defaultPerson = new Person();
        assertNotNull("Person should be created", defaultPerson);
        assertEquals("Default name should be empty", "", defaultPerson.getName());
    }
    
    @Test
    public void testPersonConstructorWithName() {
        Person namedPerson = new Person("Jane Smith");
        assertEquals("Name should be set correctly", "Jane Smith", namedPerson.getName());
    }
    
    @Test
    public void testAddMeeting_validMeeting() {
        try {
            Meeting meeting = new Meeting(6, 15, 9, 11, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            person.addMeeting(meeting);
            assertTrue("Person should be busy during meeting time", 
                      person.isBusy(6, 15, 9, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testIsBusy_validTime() {
        try {
            Meeting meeting = new Meeting(7, 20, 10, 12, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            person.addMeeting(meeting);
            
            assertTrue("Should be busy during meeting", person.isBusy(7, 20, 10, 12));
            assertTrue("Should be busy during meeting start", person.isBusy(7, 20, 10, 10));
            assertTrue("Should be busy during meeting end", person.isBusy(7, 20, 12, 12));
            assertFalse("Should not be busy before meeting", person.isBusy(7, 20, 9, 9));
            assertFalse("Should not be busy after meeting", person.isBusy(7, 20, 13, 13));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testPrintAgenda_month() {
        try {
            Meeting meeting = new Meeting(8, 10, 14, 15, new ArrayList<>(), new Room("Test Room"), "Test Meeting");
            person.addMeeting(meeting);
            
            String agenda = person.printAgenda(8);
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
            person.addMeeting(meeting);
            
            String agenda = person.printAgenda(9, 25);
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
            person.addMeeting(meeting);
            
            Meeting retrieved = person.getMeeting(10, 5, 0);
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
            person.addMeeting(meeting);
            assertTrue("Person should be busy", person.isBusy(11, 15, 13, 14));
            
            person.removeMeeting(11, 15, 0);
            assertFalse("Person should not be busy after removal", person.isBusy(11, 15, 13, 14));
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
            
            person.addMeeting(firstMeeting);
            person.addMeeting(conflictingMeeting);
            fail("Should throw TimeConflictException for conflicting meetings");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain conflict information", 
                      e.getMessage().contains("Conflict for attendee"));
            assertTrue("Exception message should contain person name", 
                      e.getMessage().contains("John Doe"));
        }
    }
    
    @Test
    public void testAddMeeting_invalidTime() {
        try {
            Meeting invalidMeeting = new Meeting(6, 35, 9, 11);
            person.addMeeting(invalidMeeting);
            fail("Should throw TimeConflictException for invalid day");
        } catch(TimeConflictException e) {
            assertTrue("Exception message should contain conflict information", 
                      e.getMessage().contains("Conflict for attendee"));
        }
    }
    
    @Test
    public void testIsBusy_invalidParameters() {
        try {
            person.isBusy(0, 15, 9, 11);
            fail("Should throw TimeConflictException for invalid month");
        } catch(TimeConflictException e) {
            assertEquals("Exception message should indicate invalid month", "Month does not exist.", e.getMessage());
        }
    }
}
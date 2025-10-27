## See comprehensive test result's report from **REPORT.HTML** file

# Meeting Planner - Unit Testing Project

toA comprehensive unit testing suite for a Java-based Meeting Planner application using JUnit 4.13.2 and Apache Ant build system.

## Project Overview

This project implements a complete unit testing framework for the Meeting Planner system, consisting of **94 test methods** across **7 test classes**. The testing suite systematically validates all components of the meeting planning system and identifies bugs through comprehensive test coverage.

## Test Classes

### CalendarTest.java (9 tests)
Tests positive scenarios for Calendar class functionality including:
- Adding meetings in valid time slots
- Multiple meetings on the same day
- Busy status checking
- Agenda printing
- Meeting retrieval and removal

### CalendarNegativeTest.java (18 tests)
Tests negative scenarios and error conditions:
- Invalid month/day/time values
- Time conflicts and overlaps
- Boundary value testing
- Non-existent dates
- Error handling validation

### EdgeCaseTest.java (22 tests)
Comprehensive edge case testing:
- Leap year date validation
- Midnight and late night meetings
- Zero-duration meetings
- Maximum meetings per day
- Consecutive meetings
- Null and empty value handling
- Special characters and long strings

### MeetingTest.java (7 tests)
Meeting class functionality testing:
- Constructor variations
- Setter and getter methods
- Attendee management
- Data structure validation

### PersonTest.java (11 tests)
Person class and calendar management:
- Person creation and naming
- Meeting scheduling
- Busy status checking
- Agenda management
- Time conflict handling

### RoomTest.java (12 tests)
Room class and calendar management:
- Room creation and identification
- Meeting scheduling
- Multiple room scenarios
- Calendar operations

### OrganizationTest.java (17 tests)
Organization class functionality:
- Employee and room management
- Search operations
- Case sensitivity testing
- Error handling

## Test Results Summary

| Test Class | Tests | Passed | Failed | Errors | Success Rate |
|------------|-------|--------|--------|--------|--------------|
| CalendarTest | 9 | 6 | 2 | 1 | 67% |
| CalendarNegativeTest | 18 | 16 | 2 | 0 | 89% |
| EdgeCaseTest | 22 | 16 | 6 | 0 | 73% |
| MeetingTest | 7 | 7 | 0 | 0 | 100% |
| PersonTest | 11 | 10 | 1 | 0 | 91% |
| RoomTest | 12 | 11 | 1 | 0 | 92% |
| OrganizationTest | 17 | 17 | 0 | 0 | 100% |
| **TOTAL** | **94** | **75** | **12** | **7** | **80%** |

## Bugs Identified

The testing suite successfully identified **12 critical bugs** in the main source code:

### High Severity Bugs
1. **Calendar Month Validation Error** - December (month 12) incorrectly rejected
2. **Meeting Time Validation Too Strict** - Same start/end time meetings rejected
3. **NullPointerException in Meeting.toString()** - Crashes when room is null
4. **NullPointerException in Meeting.toString()** - Crashes when attendees is null

### Medium Severity Bugs
5. **Missing Date Validation** - Invalid dates like February 30 accepted
6. **Calendar.isBusy() Logic Error** - Incorrect overlap detection
7. **Calendar Overlap Detection Too Strict** - Consecutive meetings rejected
8. **Calendar Maximum Meetings Limitation** - Cannot schedule max meetings per day
9. **Calendar Boundary Time Detection Error** - Incorrect boundary time handling

### Low Severity Bugs
10. **Calendar Leap Year Validation Missing** - February 29 accepted in non-leap years
11. **Calendar Zero-Duration Meeting Rejection** - Instant meetings rejected
12. **Calendar Boundary Time Meeting Rejection** - Boundary time meetings rejected

## Getting Started

### Prerequisites
- Java 17 or higher
- Apache Ant 1.10.14 or higher

### Installation
1. Clone or download the project
2. Navigate to the MeetingPlanner directory
3. Ensure Java and Ant are installed

### Running Tests
```bash
# Navigate to project directory
cd MeetingPlanner

# Compile main source code
ant compile

# Compile test code
ant compile-tests

# Run all tests
ant test

# Run tests with detailed output
ant test-detailed

# Generate HTML test report
ant test-report
```

### Available Ant Targets
- `clean` - Clean build directories
- `init` - Initialize build directories
- `compile` - Compile main source code
- `compile-tests` - Compile test source code
- `test` - Run unit tests
- `test-detailed` - Run unit tests with detailed report
- `javadoc` - Generate Javadoc documentation
- `run` - Run the main application
- `jar` - Create JAR file
- `build` - Full build (clean, compile, test, javadoc, jar)
- `help` - Display help message

## Test Coverage

The testing suite provides comprehensive coverage across:

### Functional Testing
- Core functionality validation
- Data structure operations
- Business logic verification

### Negative Testing
- Invalid input handling
- Error condition testing
- Exception validation

### Edge Case Testing
- Boundary value testing
- Extreme scenarios
- Null and empty value handling

### Integration Testing
- Component interactions
- Cross-class functionality
- System behavior validation

## Testing Methodology

### Test Design Principles
1. **Comprehensive Coverage** - Test all public methods and scenarios
2. **Boundary Testing** - Test edge cases and boundary values
3. **Error Testing** - Test error conditions and exception handling
4. **Integration Testing** - Test interactions between components
5. **Regression Testing** - Ensure fixes don't break existing functionality

### Test Naming Convention
- `test[MethodName]_[Scenario]` - e.g., `testAddMeeting_validTimeSlot`
- `test[MethodName]_[NegativeScenario]` - e.g., `testAddMeeting_invalidTime`
- `test[Class]_[EdgeCase]` - e.g., `testCalendar_MidnightMeeting`

## Generated Files

After running tests, the following files are generated:
- **Test Reports**: `build/test-reports/index.html`
- **Compiled Classes**: `build/classes/` and `build/test-classes/`
- **Javadoc**: `build/javadoc/`
- **JAR File**: `build/MeetingPlanner.jar`

## Documentation

- **README.md** - This file (project overview and setup)
- **report.html** - Detailed HTML test report with full analysis
- **Javadoc** - Generated API documentation

---

**Project**: Meeting Planner Unit Testing  
**Framework**: JUnit 4.13.2 + Apache Ant  
**Total Tests**: 94  
**Success Rate**: 80%  
**Bugs Found**: 12  
**Last Updated**: October 27, 2025
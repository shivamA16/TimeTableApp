# **Time-Table Application**

## *CPSC 210 Personal Project*

**What is it?** 
 
 The time-table app is a Java application which creates time-tables to be filled in and used by users. 
Time slots can be set by the users for any subject in a day and then the application *reserves* those time slots for 
those subject and repeats the time-table every **week**. The user will be reminded a set time before each class that 
they have class in a few minutes. Short-term things such as tests, or reminders can also be added on specific days 
(note: for one-time things like tests, they would need to be removed after they happen of the test will show up for 
each week.)


**Who is it for?**
- Students in high school or college/university.
- People with jobs who have to follow a schedule every day.
- General people who are trying to strictly schedule their day.

Even though almost anyone can use the Time-Table application, it is most suited for students in school or college
as the format of the application suits school/college timetables with some subjects with classes on multiple days of 
the week.

**Why did I make this?** 

As a student, I find it especially hard sometimes to keep track of all my classes, specially with online classes. This 
becomes exponentially important when it comes to tests and exams since missing them can severely affect the overall 
course marks. Having an app that records my class times and even tests can be very helpful since I can be reminded
before the class and thus, I wouldn't miss it. I am sure there are a lot of students, especially now in times of online
classes, that could benefit from this application.

(Note - the programming for the reminder feature will be added in later phases on the project)



### *User Stories*

- As a user, I want to be able to add multiple classes/subjects to my timetable
- As a user, I want to be able to select if I have a given subject on each day of the week
- As a user, I want to be able to change the timings of a subject already in the timetable
- As a user, I want to be able to remove classes/subjects from my timetable
- As a user, I want to be able to view my current time-table
- As a user, I want to be able to save my timetable to file.
- As a user, I want to be able to load my timetable from file.


**Phase 4: Task 2**
Added extra implementation in the Subject.setTime method that checks whether the timings submitted are valid, i.e. the
start time is lower than the end time. The method throws an InvalidTimeException if that isn't the case. 


**Phase 4: Task 3**
One refactoring that could be done is that the code related to 'days', and listOfDays can be extracted from the various
methods that it was used in (AddSubjectGUI.checkIfSlotFree(), ViewTimeTableGUI.view(), etc) and be made into a separate
method, and the methods that use it can then just call that method. Abstracting duplicate code will reduce semantic 
coupling and make the code less prone to bugs.
Also, code in AddSubjectGUI and ChangeSubjectGUI2 is very similar with some minor changes, so some abstraction can be 
done there with either an interface, or an abstract class. Extracting the similar code will reduce coupling. Another 
refactoring could be to extract the music methods (playSuccessMusic and playUnSuccessMusic) can be extracted either into 
one method that can be called from different classes or into a separate music class. 

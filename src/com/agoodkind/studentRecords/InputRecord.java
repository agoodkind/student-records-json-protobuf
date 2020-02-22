package com.agoodkind.studentRecords;

import java.util.*;

public class InputRecord {
    // <id>,<LastName>,<FirstName>,[<email>]:<Course1>,<Marks1>:<Course2>,<Mark s2>:...:<CourseN1>,<MarksN>
    private Integer id;
    private String LastName;
    private String FirstName;
    private String email;
    private HashMap<String, Integer> courseMarkDict = new HashMap<>();

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Integer> getCourseMarkDict() {
        return courseMarkDict;
    }

    public Integer getMarkOfCourse(String courseName) {
        return courseMarkDict.get(courseName);
    }

    public boolean hasId() {
        return this.id != null;
    }

    public boolean hasLastName() {
        return this.LastName != null;
    }

    public boolean hasFirstName() {
        return this.FirstName != null;
    }

    public boolean hasEmail() {
        return this.email != null;
    }

    public boolean hasCourseMarks() {
        return this.courseMarkDict.size() > 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourseMarkDict(HashMap<String, Integer> courseMarkDict) {
        this.courseMarkDict = courseMarkDict;
    }

    public void setMarkForCourse(String courseName, Integer courseMark) {
        this.courseMarkDict.put(courseName, courseMark);
    }

    @Override
    public String toString() {
        return "InputRecord{" +
                "id=" + id + '}';
    }
}

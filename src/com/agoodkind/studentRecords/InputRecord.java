package com.agoodkind.studentRecords;

import com.google.gson.annotations.SerializedName;

import java.util.*;

class CourseMark {

    private Integer CourseScore;
    private String CourseName;

    public CourseMark(String CourseName, Integer CourseScore) {
        this.CourseName = CourseName;
        this.CourseScore = CourseScore;
    }

    public Integer getCourseScore() {
        return CourseScore;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setCourseScore(Integer courseScore) {
        CourseScore = courseScore;
    }


}


public class InputRecord {
    // <id>,<LastName>,<FirstName>,[<email>]:<Course1>,<Marks1>:<Course2>,<Mark s2>:...:<CourseN1>,<MarksN>

    private Integer id;
    private String LastName;
    private String FirstName;
    private String email;
    private ArrayList<CourseMark> CourseMarks = new ArrayList<>();

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

    public ArrayList<CourseMark> getCourseMarks() {
        return this.CourseMarks;
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
        return this.CourseMarks.size() > 0;
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

    public void setMarkForCourse(String courseName, Integer courseMark) {
        this.CourseMarks.add(new CourseMark(courseName, courseMark));
    }
}

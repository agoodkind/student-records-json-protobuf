package com.agoodkind.studentRecords;
import com.agoodkind.studentRecords.RecordProto.Student;
import com.agoodkind.studentRecords.RecordProto.CourseMarks;
import com.google.gson.annotations.SerializedName;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class InputRecordFromStudentRecordProto {
    public static InputRecord convertRecord(Student studentRecordProto) {
        InputRecord parsed = new InputRecord();
        parsed.setId(Integer.parseInt(studentRecordProto.getId()));
        parsed.setFirstName(studentRecordProto.getFirstname());
        parsed.setLastName(studentRecordProto.getLastname());

        if (studentRecordProto.hasEmail()) {
            parsed.setEmail(studentRecordProto.getEmail());
        }

        if (studentRecordProto.getMarksCount() > 0) {
            for (RecordProto.CourseMarks course : studentRecordProto.getMarksList()) {
                parsed.setMarkForCourse(course.getName(), course.getScore());
            }
        }

        return parsed;
    }
}

class InputRecordListFromResultProto {

    public static InputRecordList InputRecordsFromResult(RecordProto.Result inputResult) {
        InputRecordList inputRecordList = new InputRecordList();
        for (RecordProto.Student studentRecord : inputResult.getStudentList()) {
            inputRecordList.addRecord(InputRecordFromStudentRecordProto.convertRecord(studentRecord));
        }

        return inputRecordList;
    }

}

class InputRecordList {

    private List<InputRecord> inputRecordList;

    public InputRecordList() {
        this.inputRecordList = new ArrayList<>();
    }

    public List<InputRecord> getInputRecordList() {
        return inputRecordList;
    }

    public void addRecord(InputRecord record) {
        this.inputRecordList.add(record);
    }

    public void setInputRecordList(List<InputRecord> inputRecordList) {
        this.inputRecordList = inputRecordList;
    }

    public void setInputRecordList(InputRecord[] inputRecordList) {
        this.inputRecordList = Arrays.asList(inputRecordList);
    }

    public void writeToAndFlushAndClose(FileWriter fileWriter) throws IOException {

        fileWriter.write(this.toString());
        fileWriter.flush();
        fileWriter.close();
        // write out file here

    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        for (InputRecord inputRecord : this.inputRecordList) {
            toStringBuilder.append(inputRecord.toString());
            toStringBuilder.append("\n");

        }

        return toStringBuilder.toString();
    }
}

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
    @SerializedName(value = "lastname")
    private String LastName;
    @SerializedName(value = "firstname")
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

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append(this.id);
        toStringBuilder.append(",");
        toStringBuilder.append(this.LastName);
        toStringBuilder.append(",");
        toStringBuilder.append(this.FirstName);

        if (this.hasEmail()) {
            toStringBuilder.append(",");
            toStringBuilder.append(this.email);
        }

        if (this.hasCourseMarks()) {

            for (CourseMark courseMark : this.CourseMarks) {
                toStringBuilder.append(":");
                toStringBuilder.append(courseMark.getCourseName());
                toStringBuilder.append(",");
                toStringBuilder.append(courseMark.getCourseScore());
            }
        }

        return toStringBuilder.toString();
    }


}

package com.agoodkind.studentRecords;
import com.agoodkind.studentRecords.RecordProto.Student;
import com.agoodkind.studentRecords.RecordProto.CourseMarks;

public class SingleProtoBuilder {

    private InputRecord studentInputRecord;
    private Student.Builder studentRecordProtoBuilder;
    private Student studentRecordProto;

    public SingleProtoBuilder(InputRecord record) {
        this.studentInputRecord = record;
        this.studentRecordProtoBuilder = Student.newBuilder();
    }

    public Student buildProto() {
        if (this.studentInputRecord.hasId()) {
            this.studentRecordProtoBuilder.setId(this.studentInputRecord.getId());
        } else {
            this.studentRecordProtoBuilder.setId(Integer.toString(0));
        }
        if (this.studentInputRecord.hasLastName()) {
            this.studentRecordProtoBuilder.setLastname(this.studentInputRecord.getLastName());
        } else {
            this.studentRecordProtoBuilder.setLastname("null");
        }
        if (this.studentInputRecord.hasFirstName()) {
            this.studentRecordProtoBuilder.setFirstname(this.studentInputRecord.getFirstName());
        } else {
            this.studentRecordProtoBuilder.setFirstname("null");
        }
        if (this.studentInputRecord.hasEmail()) {
            this.studentRecordProtoBuilder.setEmail(this.studentInputRecord.getEmail());
        }
        if (this.studentInputRecord.hasCourseMarks()) {
            for (CourseMark course : this.studentInputRecord.getCourseMarks()) {
                String courseName = course.getCourseName();
                Integer courseMark = course.getCourseScore();
                this.studentRecordProtoBuilder.addMarks(CourseMarks.newBuilder().setName(courseName).setScore(courseMark).build());
            }
        }
        this.studentRecordProto = this.studentRecordProtoBuilder.build();
        return this.studentRecordProto;
    }

    public Student getStudentRecordProto() {
        return studentRecordProto;
    }
}

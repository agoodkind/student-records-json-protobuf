package com.agoodkind.studentRecords;
import com.agoodkind.studentRecords.RecordProto.Student;
import com.agoodkind.studentRecords.RecordProto.CourseMarks;

import java.util.Map;

public class SingleProtoBuilder {

    private InputRecord studentRecord;
    private Student.Builder studentRecordProtoBuilder;
    private Student studentRecordProto;

    public SingleProtoBuilder(InputRecord record) {
        this.studentRecord = record;
        this.studentRecordProtoBuilder = Student.newBuilder();
    }

    public Student buildProto() {
        if (this.studentRecord.hasId()) {
            this.studentRecordProtoBuilder.setId(Integer.toString(this.studentRecord.getId()));
        }
        if (this.studentRecord.hasLastName()) {
            this.studentRecordProtoBuilder.setLastname(this.studentRecord.getLastName());
        }
        if (this.studentRecord.hasFirstName()) {
            this.studentRecordProtoBuilder.setFirstname(this.studentRecord.getFirstName());
        }
        if (this.studentRecord.hasEmail()) {
            this.studentRecordProtoBuilder.setEmail(this.studentRecord.getEmail());
        }
        if (this.studentRecord.hasCourseMarks()) {
            for (Map.Entry<String, Integer> course : this.studentRecord.getCourseMarkDict().entrySet()) {
                String courseName = course.getKey();
                Integer courseMark = course.getValue();
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

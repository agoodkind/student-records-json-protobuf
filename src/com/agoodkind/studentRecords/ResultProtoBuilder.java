package com.agoodkind.studentRecords;
import com.agoodkind.studentRecords.InputRecord;
import com.agoodkind.studentRecords.RecordProto.Result;
import com.agoodkind.studentRecords.SingleProtoBuilder;
import java.util.List;

public class ResultProtoBuilder {

    private List<InputRecord> recordList;
    private Result.Builder resultProtoBuilder;
    private Result resultProto;

    public ResultProtoBuilder(List<InputRecord> recordList) {
        this.recordList = recordList;
        resultProtoBuilder = Result.newBuilder();
    }

    public Result buildResultProto() {
        for (InputRecord record : this.recordList) {
            SingleProtoBuilder current = new SingleProtoBuilder(record);
            resultProtoBuilder.addStudent(current.buildProto());
        }
        this.resultProto = this.resultProtoBuilder.build();
        return this.resultProto;
    }

}

// program source can be found under src/com/agoodkind/studentRecords
// the program is invoked via run.sh, to compile run `./run.sh -c`
// the program can also be invoked by running `java com.agoodkind.studentRecords arg1 arg2 arg3`
package com.agoodkind.studentRecords;

import com.google.gson.Gson;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args[0].equalsIgnoreCase("-s")) {
            Parser serializeParser = new Parser(args[2]);
            if (args[1].equalsIgnoreCase("-p")) {
                // serialize to Protobuf
                ResultProtoBuilder resultProtoBuilder = new ResultProtoBuilder(serializeParser.getInputRecords());
                FileOutputStream resultProtoOutFile = new FileOutputStream("result_protobuf");
                resultProtoBuilder.buildResultProto().writeTo(resultProtoOutFile);
                resultProtoOutFile.flush();
                resultProtoOutFile.close();
            } else if (args[1].equalsIgnoreCase("-j")) {
                // Serialize to Json
                Gson gson = new Gson();
                FileWriter gsonSerializedOutFile =  new FileWriter("result.json");
                gson.toJson(serializeParser.getInputRecords(), gsonSerializedOutFile);
                gsonSerializedOutFile.flush();
                gsonSerializedOutFile.close();
            }
        } else if (args[0].equalsIgnoreCase("-d")) {
            if (args[1].equalsIgnoreCase("-p")) {
                // de-Serialize from Protobuf

                RecordProto.Result parsedResult =
                        RecordProto.Result
                                .parseFrom(new FileInputStream(args[2]));

                InputRecordListFromResultProto
                        .InputRecordsFromResult(parsedResult)
                        .writeToAndFlushAndClose(new FileWriter("output_protobuf.txt"));

            } else if (args[1].equalsIgnoreCase("-j")) {
                // String test = "[{\"id\":409603335,\"lastname\":\"Elwira\",\"firstname\":\"Anscombe\",\"CourseMarks\":[{\"CourseScore\":48,\"CourseName\":\"IN393\"},{\"CourseScore\":94,\"CourseName\":\"LC315\"},{\"CourseScore\":74,\"CourseName\":\"LE254\"},{\"CourseScore\":40,\"CourseName\":\"LO474\"},{\"CourseScore\":76,\"CourseName\":\"AA169\"},{\"CourseScore\":88,\"CourseName\":\"FY244\"},{\"CourseScore\":80,\"CourseName\":\"EC319\"},{\"CourseScore\":85,\"CourseName\":\"IR128\"},{\"CourseScore\":72,\"CourseName\":\"AR453\"},{\"CourseScore\":75,\"CourseName\":\"AL400\"}]},{\"id\":890929754,\"lastname\":\"Sherm\",\"firstname\":\"Drewry\",\"CourseMarks\":[{\"CourseScore\":72,\"CourseName\":\"LI139\"},{\"CourseScore\":59,\"CourseName\":\"AL391\"},{\"CourseScore\":52,\"CourseName\":\"QU306\"},{\"CourseScore\":48,\"CourseName\":\"AL105\"},{\"CourseScore\":100,\"CourseName\":\"LO238\"},{\"CourseScore\":45,\"CourseName\":\"EC223\"},{\"CourseScore\":92,\"CourseName\":\"EN328\"},{\"CourseScore\":58,\"CourseName\":\"BI310\"},{\"CourseScore\":79,\"CourseName\":\"HI153\"},{\"CourseScore\":53,\"CourseName\":\"AL466\"},{\"CourseScore\":78,\"CourseName\":\"LM237\"},{\"CourseScore\":69,\"CourseName\":\"BB201\"},{\"CourseScore\":80,\"CourseName\":\"AA499\"},{\"CourseScore\":44,\"CourseName\":\"AA246\"},{\"CourseScore\":78,\"CourseName\":\"HI234\"},{\"CourseScore\":62,\"CourseName\":\"AH271\"},{\"CourseScore\":68,\"CourseName\":\"LS120\"},{\"CourseScore\":95,\"CourseName\":\"IP102\"},{\"CourseScore\":60,\"CourseName\":\"EI152\"},{\"CourseScore\":47,\"CourseName\":\"LS217\"},{\"CourseScore\":68,\"CourseName\":\"CI218\"},{\"CourseScore\":57,\"CourseName\":\"AI163\"},{\"CourseScore\":97,\"CourseName\":\"LF460\"},{\"CourseScore\":81,\"CourseName\":\"AN371\"},{\"CourseScore\":80,\"CourseName\":\"AH116\"},{\"CourseScore\":55,\"CourseName\":\"IR298\"},{\"CourseScore\":73,\"CourseName\":\"ID165\"}]}]";
                InputRecord[] inputJSONToRecordList = new Gson()
                        .fromJson(
                                new BufferedReader(
                                        new FileReader(args[2])
                                ),
                                InputRecord[].class);
                // de-Serialize from Json
                InputRecordList inputRecordList = new InputRecordList();
                inputRecordList.setInputRecordList(inputJSONToRecordList);

                inputRecordList
                        .writeToAndFlushAndClose(new FileWriter("output_json.txt"));

                System.out.println(inputRecordList);
            }
        } else if (args[0].equalsIgnoreCase("-t")) {
            if (args[1].equalsIgnoreCase("-j")) {

                // start measuring
                long totalJsonTime = 0;
                long serializeJsonStartTime = System.currentTimeMillis();

                // read in records
                Parser serializeParser = new Parser(args[2]);

                // Serialize to Json
                Gson gson = new Gson();
                String serializedJsonOut = gson.toJson(serializeParser.getInputRecords());

                long serializeJsonEndTime = System.currentTimeMillis();

                FileWriter gsonSerializedOutFile =  new FileWriter("result.json");
                gsonSerializedOutFile.write(serializedJsonOut);
                gsonSerializedOutFile.flush();
                gsonSerializedOutFile.close();

                long serializeJsonTimeElapsed = serializeJsonEndTime - serializeJsonStartTime;
                totalJsonTime += serializeJsonTimeElapsed;


                BufferedReader inputJsonTimingFile = new BufferedReader(new FileReader("result.json"));

                long deSerializeJsonStartTime = System.currentTimeMillis();

                InputRecord[] inputJSONToRecordList = new Gson()
                        .fromJson(inputJsonTimingFile,
                                InputRecord[].class);
                // de-Serialize from Json
                InputRecordList inputRecordList = new InputRecordList();
                inputRecordList.setInputRecordList(inputJSONToRecordList);

                long deSerializeJsonEndTime = System.currentTimeMillis();
                long deSerializeJsonTimeElapsed = deSerializeJsonEndTime - deSerializeJsonStartTime;
                totalJsonTime += deSerializeJsonTimeElapsed;
                inputRecordList
                        .writeToAndFlushAndClose(new FileWriter("output_json.txt"));

                System.out.println(totalJsonTime);
                System.out.println(serializeJsonTimeElapsed);
                System.out.println(deSerializeJsonTimeElapsed);

            } else if (args[1].equalsIgnoreCase("-p")) {

                long totalProtoTime = 0;
                long serializeProtoStartTime = System.currentTimeMillis();
                // read in records
                Parser serializeParser = new Parser(args[2]);

                ResultProtoBuilder resultProtoBuilder = new ResultProtoBuilder(serializeParser.getInputRecords());
                FileOutputStream resultProtoOutFile = new FileOutputStream("result_protobuf");
                RecordProto.Result outputResultProto = resultProtoBuilder.buildResultProto();


                long serializeProtoEndTime = System.currentTimeMillis();

                outputResultProto.writeTo(resultProtoOutFile);
                resultProtoOutFile.flush();
                resultProtoOutFile.close();



                long serializeProtoTimeElapsed = serializeProtoEndTime - serializeProtoStartTime;
                totalProtoTime += serializeProtoTimeElapsed;


                long deSerializeProtoStartTime = System.currentTimeMillis();

                RecordProto.Result parsedResult =
                        RecordProto.Result
                                .parseFrom(new FileInputStream("result_protobuf"));

                InputRecordList outputRecordList = InputRecordListFromResultProto
                        .InputRecordsFromResult(parsedResult);

                long deSerializeProtoEndTime = System.currentTimeMillis();
                long deSerializeProtoTimeElapsed = deSerializeProtoEndTime - deSerializeProtoStartTime;

                outputRecordList.writeToAndFlushAndClose(new FileWriter("output_protobuf.txt"));

                totalProtoTime += deSerializeProtoTimeElapsed;

                System.out.println(totalProtoTime);
                System.out.println(serializeProtoTimeElapsed);
                System.out.println(deSerializeProtoTimeElapsed);
            }
        }
    }
}

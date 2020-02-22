package com.agoodkind.studentRecords;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class Parser {
    private List<InputRecord> inputRecords;
    Reader fileReader;
    private int __test_size = 0;

    public Parser(String filePathName) throws FileNotFoundException {
        inputRecords = new ArrayList<>();
        fileReader = new FileReader(filePathName);
    }

    private List<String> split(String str, String token){
        return Stream.of(str.split(token))
                .map (String::new)
                .collect(Collectors.toList());
    }

    private Boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern compiledEmailRegex = Pattern.compile(emailRegex);
        return compiledEmailRegex.matcher(email).matches();
    }

    private InputRecord parse(String line) {

        List<String> unprocessedSubTokens = split(line, ":");
        // <id>,<LastName>,<FirstName>,[<email>]:<Course1>,<Marks1>:<Course2>,<Mark s2>:...:<CourseN1>,<MarksN>

        InputRecord currentRecord = new InputRecord();

        String identifyingInfoString = unprocessedSubTokens.get(0);
        List<String> identifyingInfo = split(identifyingInfoString, ",");

        if (identifyingInfo.size() >= 1) {
            currentRecord.setId(Integer.parseInt(identifyingInfo.get(0).replaceAll("[\\D]", "")));
            if (identifyingInfo.size() >= 2) {
                currentRecord.setLastName(identifyingInfo.get(1));
                if (identifyingInfo.size() >= 3) {
                    currentRecord.setFirstName(identifyingInfo.get(2));
                    if (identifyingInfo.size() >= 4) {
                        currentRecord.setEmail(identifyingInfo.get(3));
                    }
                }
            }
        }

        for (int i = 1; i <  unprocessedSubTokens.size(); i++) {
            String currentCourseAndMark = unprocessedSubTokens.get(i);
            List<String> courseAndMark = split(currentCourseAndMark, ",");
            String courseName = courseAndMark.get(0);
            Integer courseMark = Integer.parseInt(courseAndMark.get(1));
            currentRecord.setMarkForCourse(courseName, courseMark);
        }

        return currentRecord;
    }

    private void readLines() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(this.fileReader);
            String line = reader.readLine();
            while (line != null) {
                inputRecords.add(parse(line));
                __test_size += 1;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<InputRecord> getInputRecords() {
        return this.inputRecords;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < inputRecords.size(); i++) {
            out += inputRecords.get(i).toString();
            out += "\n";
        }

        return out;
    }

    public void test() {
        this.readLines();
        System.out.println(this.toString());
        System.out.println(this.__test_size);
    }
}

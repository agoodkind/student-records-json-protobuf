package com.agoodkind.studentRecords;

import java.io.FileNotFoundException;
import com.agoodkind.studentRecords.RecordProto;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	    Parser parseTest = new Parser("/Users/alex/Desktop/input.txt");
	    parseTest.test();
	    System.out.println(parseTest.getInputRecords().size());
    }
}

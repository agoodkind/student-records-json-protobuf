package com.agoodkind.studentRecords;

import com.google.gson.Gson;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	    Parser parseTest = new Parser("/Users/alex/Desktop/input.txt");
//	    parseTest.test();
		InputRecord re = parseTest.getInputRecords().get(0);
		Gson gson = new Gson();

		String json = gson.toJson(re);

		System.out.println(json.toString());

	    System.out.println(parseTest.getInputRecords().size());
    }
}

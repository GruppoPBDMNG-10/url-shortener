package it.datatoknowledge.pbdmng.urlShortener.test;

import static org.junit.Assert.assertTrue;
import it.datatoknowledge.pbdmng.urlShortener.logic.TinyGenerator;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTinyGenerator {
	
	private TinyGenerator gen;
	private ArrayList<String> stringToTest;
	private ArrayList<String> stringToTestTiny;
	private String testString;
	private ArrayList<String> testStringTiny;
	
	private final static int TEST_CASE = 10000;

	@Before
	public void setUp() throws Exception {
		gen = new TinyGenerator();
		stringToTest = new ArrayList<String>(TEST_CASE);
		stringToTest.add("");
		stringToTest.add(" ");
		Random r = new Random();
		for (int i = 0; i < TEST_CASE - 2; i++) {
			int stringLength = r.nextInt(TEST_CASE) + 1;
			String text = "";
			for (int j = 0; j < stringLength; j++) {
				char c = ((char) (r.nextInt(90)+32));
				text += c;
			}
			stringToTest.add(text);
		}
		testString = "abcdefg1234";
		testStringTiny = new ArrayList<String>(TEST_CASE);
		stringToTestTiny = new ArrayList<String>(TEST_CASE);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetTiny() {
		for (String s : stringToTest) {
			String tiny = gen.getTiny(s);
			stringToTestTiny.add(tiny);
		}
		
		for (int i = 0; i < TEST_CASE; i++) {
			String tiny = gen.getTiny(testString);
			testStringTiny.add(tiny);
		}
		
		for (int i = 0; i < stringToTestTiny.size(); i++) {
			String stringToCheck = stringToTestTiny.get(i);
			assertTrue("Random String test case " + (i+1), !stringToTestTiny.subList(i + 1, stringToTestTiny.size()).contains(stringToCheck));
		}
		
		for (int i = 0; i < testStringTiny.size(); i++) {
			String stringToCheck = testStringTiny.get(i);
			assertTrue("Fixed String test case " + (i+1), !testStringTiny.subList(i + 1, testStringTiny.size()).contains(stringToCheck));
		}
	}

}

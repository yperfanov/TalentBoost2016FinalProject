package com.vmware.tb2016.finalproject.validators;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * <code>ValidatorTest</code>
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class CommandValidatorTest {

	private CommandValidator v;

	@Before
	public void setUpBeforeClass() {
		v = new CommandValidator();
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void invalidCommand() throws Exception {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage("Inknown command: create");
		v.validate("create suv model=q7-hatchback engine=P-484hp-T-euro6 transmission=auto-5");
	}
	
	@Test
	public void invalidDisassembleCommandValidationTest() throws Exception {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage("Invalid VIN length: [7]. It must 17.");
		v.validate("disassemble bg08dhj");
	}
	
	@Test
	public void invalidDisassembleCommandValidationTest_1() throws Exception {
		expectedEx.expect(ParseException.class);
		expectedEx.expectMessage("Cannot parse: all of them");
		v.validate("disassemble all of them");
	}
	
	@Test
	public void validDisassembleCommandValidationTest() throws ValidationException, ParseException {
		String expectedResult = v.validate("disassemble bg015s8d8tqd9541a");
		assertEquals("disassemble bg015s8d8tqd9541a", expectedResult);
	}
	
	@Test
	public void validAssembleCommandValidationTest() throws ValidationException, ParseException {
		String expectedResult = v.validate("assemble suv model=q7-hatchback engine=P-484hp-T-euro6 transmission=auto-5");
		assertEquals("assemble Q7-HATCHBACK-253-EURO6-P-4000-T-AUTO-5", expectedResult);
	}

	@Test
	public void validFindCommandValidationTest() throws ValidationException, ParseException {
		String expectedResult = v.validate("find engine euro3");
		assertEquals("find engine euro3", expectedResult);
	}

	@Test(expected = ParseException.class)
	public void invalidFindCommandValidationTest() throws ValidationException, ParseException {
		v.validate("find engine diesel euro3");
	}

	@Test
	public void validUpdateCommandValidationTest() throws ValidationException, ParseException {
		String expectedResult = v.validate("update BG01sg5a5gw7q5sc9 euro3");
		assertEquals("update BG01sg5a5gw7q5sc9 euro3", expectedResult);
	}

	@Test
	public void invalidUpdateCommandValidationTest() throws Exception {
		expectedEx.expect(ParseException.class);
		expectedEx.expectMessage("Cannot parse: emission standart BG01sg5a5gw7q5sc9 euro3");
		v.validate("update emission standart BG01sg5a5gw7q5sc9 euro3");
	}
	
	@Test
	public void invalidUpdateCommandValidationTest_1() throws Exception {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage("Invalid VIN length: [15]. It must 17.");
		v.validate("update BG01sga5gw75sc9 euro3");
	}
	
	@Test
	public void invalidUpdateCommandValidationTest_2() throws Exception {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage("Invalid emission standart: euro1");
		v.validate("update BG01sga5s1gw75sc9 euro1");
	}
	
	@Test
	public void validDisplayCommandValidationTest() throws ValidationException, ParseException {
		String expectedResult = v.validate("display BG01sga5s1gw75sc9");
		assertEquals("display BG01sga5s1gw75sc9", expectedResult);
	}
	
	@Test
	public void validDisplayCommandValidationTest_1() throws ValidationException, ParseException {
		String expectedResult = v.validate("display all");
		assertEquals("display all", expectedResult);
	}
	
	@Test
	public void invalidDisplayCommandValidationTest() throws Exception {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage("Invalid VIN length: [16]. It must 17.");
		v.validate("display BG01sa5s1gw75sc9");
	}
	
	@Test
	public void invalidDisplayCommandValidationTest_1() throws Exception {
		expectedEx.expect(ValidationException.class);
		expectedEx.expectMessage("Invalid VIN length: [9]. It must 17.");
		v.validate("display fdafadfas");
	}
	
	@Test
	public void invalidDisplayCommandValidationTest_2() throws Exception {
		expectedEx.expect(ParseException.class);
		expectedEx.expectMessage("Cannot parse: all of them");
		v.validate("display all of them");
	}
}

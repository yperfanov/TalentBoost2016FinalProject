package com.vmware.tb2016.finalproject.validators;


import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.Test;

import com.vmware.tb2016.finalproject.validators.AssembleCommandValidator;

/**
 *<code>AssembleCommandValidatorTest</code>
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class AssembleCommandValidatorTest {

	private AssembleCommandValidator acv;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		acv = new AssembleCommandValidator();
	}
	
	@Test(expected = ParseException.class)
	public void invalidTest_1() throws ParseException, ValidationException {
		acv.validateAssembleCommand("invalid test");
	}
	
	@Test(expected = ParseException.class)
	public void invalidTest_2() throws ParseException, ValidationException {
		acv.validateAssembleCommand("invalid test sdashd sdhaas fa sdf ag sdf sa da s");
	}
	
	@Test
	public void validTest_1() throws ParseException, ValidationException {
		String expectedResult = acv.validateAssembleCommand("car model=A5-hatchback engine=P");
			assertEquals("A5-HATCHBACK-74-EURO3-P-1000-*-MANUAL-4", expectedResult);
	}
	
	@Test
	public void validTest_2() throws ParseException, ValidationException {
		String expectedResult = acv.validateAssembleCommand("suv model=q1-combi engine=D-180hp-euro4");
		assertEquals("Q1-COMBI-134-EURO4-D-2000-*-MANUAL-4", expectedResult);
	}
	
	@Test
	public void validTest_3() throws ParseException, ValidationException {
		String expectedResult = acv.validateAssembleCommand("suv model=q7-hatchback engine=P-484hp-T-euro6 transmission=auto-5");
		assertEquals("Q7-HATCHBACK-253-EURO6-P-4000-T-AUTO-5", expectedResult);
	}
	
	@Test
	public void validTest_4() throws ParseException, ValidationException {
		String expectedResult = acv.validateAssembleCommand("suv model=q7 engine=E");
		assertEquals("Q7-SEDAN-535-EURO6-E-*-*-", expectedResult);
	}
}

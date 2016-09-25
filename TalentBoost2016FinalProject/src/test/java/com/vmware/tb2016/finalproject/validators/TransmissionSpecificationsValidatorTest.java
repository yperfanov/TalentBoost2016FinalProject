package com.vmware.tb2016.finalproject.validators;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.Test;

import com.vmware.tb2016.finalproject.validators.TransmissionSpecificationsValidator;

/**
 *<code>TransmissionSpecificationsValidatorTest</code> tests several cases for transmission specifications.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class TransmissionSpecificationsValidatorTest {

	private TransmissionSpecificationsValidator tsv;

	@Before
	public void setUp() {
		tsv = new TransmissionSpecificationsValidator();
	}

	@Test(expected = ValidationException.class)
	public void invalidTransmissionSpecificationsTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("invalid-test");
	}

	@Test
	public void setDefaultTransmissionTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("default");
		assertEquals("MANUAL", tsv.transmissionType);
		assertEquals(4, tsv.gearsNumber);
	}
	
	@Test
	public void twoOptionsTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("transmission=auto");
		assertEquals("AUTO", tsv.transmissionType);
		assertEquals(4, tsv.gearsNumber);
	}
	
	@Test(expected = ValidationException.class)
	public void twoOptionsFailureTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("transmission=hybrid");
	}
	
	@Test(expected = ValidationException.class)
	public void invalidManualGearTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("transmission=manual-8");
	}
	
	@Test
	public void manualGearTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("transmission=manual-5");
		assertEquals("MANUAL", tsv.transmissionType);
		assertEquals(5, tsv.gearsNumber);
	}
	
	@Test(expected = ValidationException.class)
	public void invalidlGearTest() throws ParseException, ValidationException {
		tsv.validateTransmissionSpecifications("transmission=manual-15");
	}
	
}

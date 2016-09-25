package com.vmware.tb2016.finalproject.validators;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.Test;

import com.vmware.tb2016.finalproject.validators.ModelSpecificationsValidator;
/**
 * <code>ModelSpecificationsValidatorTest</code> tests several cases for model specifications.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class ModelSpecificationsValidatorTest {

	private ModelSpecificationsValidator msv;

	@Before
	public void setUp() {
		msv = new ModelSpecificationsValidator();
	}

	@Test(expected = ParseException.class)
	public void invalidModelSpecificationsTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("invalid-test");
	}
	
	@Test
	public void twoOptionsTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("suv model=q3");
		assertEquals("SUV", msv.vehicleType);
		assertEquals("Q3", msv.modelName);
		assertEquals("SEDAN", msv.modelType);
	}
	
	@Test(expected = ValidationException.class)
	public void twoOptionsFailuireTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("suv model=a3");
	}
	
	@Test
	public void threeOptionsSuvTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("suv model=q5-hatchback");
		assertEquals("SUV", msv.vehicleType);
		assertEquals("Q5", msv.modelName);
		assertEquals("HATCHBACK", msv.modelType);
	}
	
	@Test
	public void threeOptionsCarTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("CAr model=A8-combi");
		assertEquals("CAR", msv.vehicleType);
		assertEquals("A8", msv.modelName);
		assertEquals("COMBI", msv.modelType);
	}
	
	@Test(expected = ValidationException.class)
	public void threeOptionsFailureSuvTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("suv model=a1-combi");
	}
	
	@Test(expected = ValidationException.class)
	public void threeOptionsFailureCarTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("CAr model=q8-combi");
	}
	
	@Test(expected = ValidationException.class)
	public void modelTypeFailureTest() throws ParseException, ValidationException {
		msv.validateModelSpecifications("CAr model=q8-truck");
	}
	
	

}

package com.vmware.tb2016.finalproject.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.Test;

import com.vmware.tb2016.finalproject.validators.EngineSpecificationsValidator;

/**
 * <code>EngineSpecificationsValidatorTest</code> tests a lot of cases for
 * engine specifications.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class EngineSpecificationsValidatorTest {

	private EngineSpecificationsValidator esv;

	@Before
	public void setUp() {
		esv = new EngineSpecificationsValidator();
	}

	@Test(expected = ParseException.class)
	public void invalidEngineSpecificationsTest() throws ValidationException, ParseException {
		esv.validateEngineSpecifications("invalidtest");
	}

	@Test(expected = ValidationException.class)
	public void invalidEngineTypeTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=invalid_type");
	}

	@Test
	public void twoOptionsElectricEngineTest() throws ValidationException, ParseException {
		esv.validateEngineSpecifications("engine=e");
		assertEquals("E", esv.engineType);
		assertEquals(535, esv.enginePowerInKw);
		assertEquals("EURO6", esv.emissionStandart);
		assertFalse(esv.turbocharger);
	}

	@Test
	public void twoOptionsDieselEngineTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=d");
		assertEquals("D", esv.engineType);
		assertEquals(134, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(2000, esv.engineDisplacementInCubicCentimeters);
		assertFalse(esv.turbocharger);
	}

	@Test
	public void twoOptionsPetrolEngineTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P");
		assertEquals("P", esv.engineType);
		assertEquals(74, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(1000, esv.engineDisplacementInCubicCentimeters);
		assertFalse(esv.turbocharger);
	}

	@Test
	public void enginePowerWithLitersTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-5l");
		assertEquals("P", esv.engineType);
		assertEquals(331, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(5000, esv.engineDisplacementInCubicCentimeters);
		assertFalse(esv.turbocharger);
	}

	@Test
	public void enginePowerWithHpTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=D-339hp");
		assertEquals("D", esv.engineType);
		assertEquals(253, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(4000, esv.engineDisplacementInCubicCentimeters);
		assertFalse(esv.turbocharger);
	}

	@Test(expected = ValidationException.class)
	public void enginePowerWithLitersFailureTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-50l");
	}

	@Test(expected = ValidationException.class)
	public void enginePowerWithHpFailureTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=D-500hp");
	}

	/**
	 * 253kW + 30% = 329kW = 441hp-> "engine=P-441hp-T" should be valid
	 * @throws ValidationException
	 */
	@Test
	public void engineHpTurboTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-441hp-T");
		assertEquals("P", esv.engineType);
		assertEquals(329, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(4000, esv.engineDisplacementInCubicCentimeters);
		assertTrue(esv.turbocharger);
	}

	/**
	 * 134kW + 30% = 174kW = 233hp -> "engine=P-233hp-T" should be valid
	 * @throws ValidationException
	 */
	@Test
	public void engineHpTurboTest_1() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-233hp-T");
		assertEquals("P", esv.engineType);
		assertEquals(174, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(2000, esv.engineDisplacementInCubicCentimeters);
		assertTrue(esv.turbocharger);
	}

	/**
	 * Same input as the test above but without Turbo. The command should fail
	 * because without T 233hp is not valid engine power.
	 * 
	 * @throws ValidationException
	 */
	@Test(expected = ValidationException.class)
	public void invalidEngineHpTurboTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=D-233hp");
	}

	@Test(expected = ValidationException.class)
	public void invalidEngineHpTurboTest_1() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-500hp-T");
	}

	/**
	 * 3l and T -> 245kw + 30% = 318kw
	 * @throws ParseException
	 * @throws ValidationException
	 */
	@Test
	public void engineLitersTurboTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-3l-T");
		assertEquals("P", esv.engineType);
		assertEquals(318, esv.enginePowerInKw);
		assertEquals("EURO3", esv.emissionStandart);
		assertEquals(3000, esv.engineDisplacementInCubicCentimeters);
		assertTrue(esv.turbocharger);
	}

	@Test
	public void standartEmissionTestWithLiters() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-5l-euro4");
		assertEquals("P", esv.engineType);
		assertEquals(331, esv.enginePowerInKw);
		assertEquals("EURO4", esv.emissionStandart);
		assertEquals(5000, esv.engineDisplacementInCubicCentimeters);
		assertFalse(esv.turbocharger);
	}

	@Test
	public void standartEmissionTestWithHp() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-987hp-euro4");
		assertEquals("P", esv.engineType);
		assertEquals(736, esv.enginePowerInKw);
		assertEquals("EURO4", esv.emissionStandart);
		assertEquals(8000, esv.engineDisplacementInCubicCentimeters);
		assertFalse(esv.turbocharger);
	}

	@Test
	public void standartEmissionWithTurboTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-5l-T-euro4");
		assertEquals("P", esv.engineType);
		assertEquals(430, esv.enginePowerInKw);
		assertEquals("EURO4", esv.emissionStandart);
		assertEquals(5000, esv.engineDisplacementInCubicCentimeters);
		assertTrue(esv.turbocharger);
	}

	@Test(expected = ValidationException.class)
	public void invalidStandartEmissionTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-5l-T-euro10");
	}

	@Test(expected = ValidationException.class)
	public void invalidTurboCharTest() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=P-5l-v-euro10");
	}

	@Test(expected = ValidationException.class)
	public void dieselEngineLimitationTest_1() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=d-8l-t-euro4");
	}

	@Test(expected = ValidationException.class)
	public void dieselEngineLimitationTest_2() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=d-1l-t-euro3");
	}

	@Test(expected = ValidationException.class)
	public void dieselEngineLimitationTestWithHp_1() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=d-99hp-t-euro4");
	}

	@Test(expected = ValidationException.class)
	public void dieselEngineLimitationTestWithHp_2() throws ParseException, ValidationException {
		esv.validateEngineSpecifications("engine=d-987hp-t-euro3");
	}
}

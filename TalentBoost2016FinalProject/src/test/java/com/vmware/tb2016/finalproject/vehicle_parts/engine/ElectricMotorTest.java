package com.vmware.tb2016.finalproject.vehicle_parts.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class ElectricMotorTest {

	private ElectricMotor e;

	@Test
	public void getPowerInHptest() {
		e = new ElectricMotor(100, "euro6");
		assertEquals("EURO6", e.getEmissionStandart());
		assertEquals(134, e.getEnginePowerInHp());
	}
	
	@Test
	public void getPowerInHptest_1() {
		e = new ElectricMotor(200, "euro6");
		assertEquals("EURO6", e.getEmissionStandart());
		assertEquals(268, e.getEnginePowerInHp());
	}
	
	@Test
	public void getPowerInHptest_2() {
		e = new ElectricMotor(200, "euro6");
		assertEquals("EURO6", e.getEmissionStandart());
		assertNotEquals(258, e.getEnginePowerInHp());
	}

}

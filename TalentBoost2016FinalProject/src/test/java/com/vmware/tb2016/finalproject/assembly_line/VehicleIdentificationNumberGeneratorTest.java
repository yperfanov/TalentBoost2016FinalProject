package com.vmware.tb2016.finalproject.assembly_line;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Generates 1 000 000 vehicle identification numbers
 * and checks if each of them has length different
 * than 17.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */

public class VehicleIdentificationNumberGeneratorTest {

	private Set<String> set;
	private int vinLength;

	@Before
	public void setUp() {
		set = new HashSet<String>();
		vinLength = 17;
		for (int i = 0; i < 100_000; i++) {
			String vin = VehicleIdentificationNumberGenerator.generateVin("BG1");
			set.add(vin);
			if (vin.length() != vinLength) {
				vinLength = vin.length();
			}
		}
	}
	
	/**
	 * Test generated vins randomness.
	 */
	@Test
	public void randomnessTest() {
		assertEquals(100_000, set.size());
	}

	/**
	 * Test generated vins length.
	 */
	@Test
	public void vinLengthTest() {
		assertEquals(17, vinLength);
	}
}

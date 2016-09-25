package com.vmware.tb2016.finalproject.assembly_line;

import java.security.SecureRandom;

/**
 * <code>VehicleIdentificationNumberGenerator</code>
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class VehicleIdentificationNumberGenerator {

	private static final String ALLOWED_CHARS = "0123456789abcdefghjklmnprstuvwxyzABCDEFGHJKLMNPRSTUVWXYZ";

	/**
	 * Generates 14 random characters from the allowed symbols and appends them
	 * to the factory code.
	 * 
	 * @param factoryCode - {@link java.lang.Object.String String}
	 * @return - {@link java.lang.Object.String String} Vehicle Identification Number of 17 chars
	 */
	public static synchronized String generateVin(String factoryCode) {
		SecureRandom rnd = new SecureRandom();
		StringBuffer sb = new StringBuffer();
		sb.append(factoryCode);
		for (int i = 1; i <= 14; i++) {
			int randomIndex = rnd.nextInt(ALLOWED_CHARS.length());
			char randomChar = ALLOWED_CHARS.charAt(randomIndex);
			sb.append(randomChar);
		}
		return sb.toString();
	}
}

package com.vmware.tb2016.finalproject.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <code>VehicleSpecificationsConstants</code> keeps all project specifications in constant fields.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 *
 */
public class VehicleSpecificationsConstants {

	public static final String DEFAULT_EMISSION_STANDART = "EURO3";
	public static final int DEFAULT_PETROL_ENGINE_DISPLACEMENT = 1000;
	public static final int DEFAULT_PETROL_ENGINE_POWER_KW = 74;
	public static final int DEFAULT_DIESEL_ENGINE_DISPLACEMENT = 2000;
	public static final int DEFAULT_DIESEL_ENGINE_POWER_KW = 134;
	public static final int DEFAULT_ELECTRIC_MOTOR_POWER_KW = 535;
	public static final String DEFAULT_ELECTRIC_MOTOR_EMISSION_STANDART = "EURO6";
	public static final String DEFAULT_MODEL_TYPE = "SEDAN";
	public static final String DEFAULT_TRANSMISSION_TYPE = "MANUAL";
	public static final int DEFAULT_GEARS_NUMBER = 4;
	

	public static final List<Integer> ENGINE_POWER_OPTIONS_IN_KW = Collections.unmodifiableList(
			new ArrayList<Integer>(Arrays.asList(74, 134, 245, 253, 331, 510, 736)));
	
	public static final List<Integer> ENGINE_POWER_OPTIONS_IN_CC = Collections.unmodifiableList(
			new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 8)));

	public static final Set<String> ENGINE_TYPE_OPTIONS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("P", "D", "E")));
	
	public static final Set<String> MODEL_TYPE_OPTIONS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("HATCHBACK", "COMBI", "SEDAN")));

	public static final Set<String> EMISSION_TYPE_OPTIONS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("EURO3", "EURO3", "EURO4", "EURO5", "EURO6")));
	
	public static final Set<String> CAR_MODELS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8")));

	public static final Set<String> SUV_MODELS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8")));

	public static final Set<Integer> GEARS_NUMBER_OPTIONS = Collections
			.unmodifiableSet(new HashSet<Integer>(Arrays.asList(4, 5, 6, 8)));
	
	public static final Set<String> TRANSMISSION_TYPE_OPTIONS = Collections
			.unmodifiableSet(new HashSet<String>(Arrays.asList("AUTO", "MANUAL")));
}

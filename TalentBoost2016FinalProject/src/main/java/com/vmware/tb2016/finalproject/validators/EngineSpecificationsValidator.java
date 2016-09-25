package com.vmware.tb2016.finalproject.validators;

import java.text.ParseException;
import java.util.ArrayList;

import javax.validation.ValidationException;

/**
 * <code>EngineSpecificationsValidator</code> checks all possible combinations
 * for engine specifications required in Talent Boost 2016 final task. If all
 * validations are passed the engine specifications are saved in this class
 * fields and they are ready to be taken and used by other classes.<br>
 * 
 * If a turbocharger is mounted and the input engine power is in:<br>
 * 1)horse powers, it reduces it with 30% and if there is a match in the
 *  specifications it is valid.<br>
 *  example: 233hp = 174kW, 134kW + 30% = 174kW -> engine spec 'engine=p-233hp-T' is
 *  valid<br>
 * 2)cubic centimeters and there is a match in the specifications the kW power
 *  is increased with 30%.<br>
 *  example: 'engine=p-8l-T' is valid -> engine power in kW = 736 + 30% = 957kW = 1282hp<br>
 *  
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class EngineSpecificationsValidator {

	String engineType;
	int enginePowerInKw;
	String emissionStandart;
	int engineDisplacementInCubicCentimeters;
	boolean turbocharger = false;

	/**
	 * It validates the given engine specifications.
	 * 
	 * @throws ParseException
	 * @throws ValidationException
	 */
	public final void validateEngineSpecifications(String engineSpecifications)
			throws ParseException, ValidationException {
		String[] engineSpecificationsArray = engineSpecifications.toUpperCase().split("[ =-]+");
		int specLenth = engineSpecificationsArray.length;
		if (specLenth < 2 || specLenth > 5) {
			throw new ParseException("Cannot parse engine specifications: " + engineSpecifications, 0);
		}

		validateEngineType(engineSpecificationsArray[1]);

		// If specLenth is 2 only only the engine type(P,D or E) has been given.
		// Needed fields are set with default values according to the engine
		// type.
		if (specLenth == 2) {
			if (this.engineType.equals("E")) {
				this.enginePowerInKw = VehicleSpecificationsConstants.DEFAULT_MOTOR_ENGINE_POWER_KW;
				this.emissionStandart = VehicleSpecificationsConstants.DEFAULT_MOTOR_ENGINE_EMISSION_STANDART;
				return;
			} else {
				setDefaultValues();
				return;
			}

		}
		// Checking all other cases for engine specifications.
		if (specLenth >= 3) {
			this.emissionStandart = VehicleSpecificationsConstants.DEFAULT_EMISSION_STANDART;
			if (specLenth > 3) {
				checkForTurbocharger(engineSpecificationsArray[3]);
			}
			if (specLenth == 4) {
				validateEmissionStandart(engineSpecificationsArray[3]);
			}
			if (specLenth == 5) {
				validateEmissionStandart(engineSpecificationsArray[4]);
			}
			validateEnginePower(engineSpecificationsArray[2]);
		}

		// If the engine is diesel it checks the given limitations for it.
		if (engineSpecificationsArray[1].equals("D")) {
			checkDieselLimitation();
		}
	}

	private void validateEngineType(String engineType) throws ValidationException {
		if (VehicleSpecificationsConstants.ENGINE_TYPE_OPTIONS.contains(engineType)) {
			this.engineType = engineType;
		} else {
			throw new ValidationException("Invalid engine type: " + engineType);
		}
	}

	private void setDefaultValues() {
		this.emissionStandart = VehicleSpecificationsConstants.DEFAULT_EMISSION_STANDART;
		if (this.engineType.equals("D")) {
			this.engineDisplacementInCubicCentimeters = VehicleSpecificationsConstants.DEFAULT_DIESEL_ENGINE_DISPLACEMENT;
			this.enginePowerInKw = VehicleSpecificationsConstants.DEFAULT_DIESEL_ENGINE_POWER_KW;
		} else {
			this.engineDisplacementInCubicCentimeters = VehicleSpecificationsConstants.DEFAULT_PETROL_ENGINE_DISPLACEMENT;
			this.enginePowerInKw = VehicleSpecificationsConstants.DEFAULT_PETROL_ENGINE_POWER_KW;
		}

	}

	/**
	 * Checks if power specfification is in liters or horse powers. After that
	 * it iterates through the power specifications list and validates.
	 * 
	 * @throws ValidationException
	 */
	private void validateEnginePower(String enginePowerSpec) throws ValidationException {
		boolean isEnginePowerValid = false;
		int powerSpecWithoutChar = Integer.parseInt(enginePowerSpec.replaceAll("[\\D]", ""));
		ArrayList<Integer> powerListInKw = new ArrayList<Integer>(
				VehicleSpecificationsConstants.ENGINE_POWER_OPTIONS_IN_KW);
		ArrayList<Integer> powerListInCc = new ArrayList<Integer>(
				VehicleSpecificationsConstants.ENGINE_POWER_OPTIONS_IN_CC);

		if (enginePowerSpec.contains("HP") && this.turbocharger) {
			double givenPowerInKw = Math.round(0.7456999872 * powerSpecWithoutChar);
			// givenPowerInKw = powerInKwToLookFor + 30% * powerInKwToLookFor ->
			// powerInKwToLookFor = (givenPowerInKw *10) / 13
			int powerInKwToLookFor = (int) Math.round((givenPowerInKw * 10) / 13);
			if (powerListInKw.contains(powerInKwToLookFor)) {
				int index = powerListInKw.indexOf(powerInKwToLookFor);
				this.enginePowerInKw = (int) givenPowerInKw;
				this.engineDisplacementInCubicCentimeters = powerListInCc.get(index) * 1000;
				isEnginePowerValid = true;
			}
		} else if (enginePowerSpec.contains("HP") && !this.turbocharger) {
			int givenPowerInKw = (int) Math.round(0.7456999872 * powerSpecWithoutChar);
			if (powerListInKw.contains(givenPowerInKw)) {
				int index = powerListInKw.indexOf(givenPowerInKw);
				this.enginePowerInKw = givenPowerInKw;
				this.engineDisplacementInCubicCentimeters = powerListInCc.get(index) * 1000;
				isEnginePowerValid = true;
			}
		} else if (enginePowerSpec.contains("L") && this.turbocharger) {
			if (powerListInCc.contains(powerSpecWithoutChar)) {
				int temp = 0;
				if (powerSpecWithoutChar == 8) {
					temp = powerListInKw.get(powerSpecWithoutChar - 2);
				} else {
					temp = powerListInKw.get(powerSpecWithoutChar - 1);
				}
				this.enginePowerInKw = temp + (temp * 30) / 100; // increasing
																	// with 30%
				this.engineDisplacementInCubicCentimeters = powerSpecWithoutChar * 1000;
				isEnginePowerValid = true;
			}
		} else if (enginePowerSpec.contains("L") && !this.turbocharger) {
			if (powerListInCc.contains(powerSpecWithoutChar)) {
				if (powerSpecWithoutChar == 8) {
					this.enginePowerInKw = powerListInKw.get(powerSpecWithoutChar - 2);
				} else {
					this.enginePowerInKw = powerListInKw.get(powerSpecWithoutChar - 1);
				}
				this.engineDisplacementInCubicCentimeters = powerSpecWithoutChar * 1000;
				isEnginePowerValid = true;
			}
		}

		if (!isEnginePowerValid) {
			throw new ValidationException(String.format("Invalid engine power: [%s]", enginePowerSpec));
		}
	}

	/**
	 * This method is used twice to check if the emission standart is on 4th or
	 * 5th position. On 4th position it could be also the turbocharger character
	 * and if this is the case it doesn't have to throw an exception for
	 * emission standart.
	 * 
	 * @throws ValidationException
	 */
	private void validateEmissionStandart(String emissionStandart) throws ValidationException {
		if (VehicleSpecificationsConstants.EMISSION_TYPE_OPTIONS.contains(emissionStandart)) {
			this.emissionStandart = emissionStandart;
		} else if (emissionStandart.equalsIgnoreCase("T")) {
			return;
		} else {
			throw new ValidationException("Invalid emission standart: " + emissionStandart);
		}
	}

	/**
	 * The turbocharger character 'T' could be on 3rd position of the
	 * specifications array, so this method checks it. If is not a 'T', it is
	 * the emission standart or wrong specification.
	 */
	private void checkForTurbocharger(String valueToCheck) throws ValidationException {
		if (valueToCheck.equalsIgnoreCase("T")) {
			this.turbocharger = true;
		} else if (VehicleSpecificationsConstants.EMISSION_TYPE_OPTIONS.contains(valueToCheck)) {
			return;
		} else {
			throw new ValidationException(
					String.format("Invalid turbocharger character: %s. It must be 'T'.", valueToCheck));
		}
	}

	private void checkDieselLimitation() throws ValidationException {
		if (this.engineType.equals("D") && (this.engineDisplacementInCubicCentimeters < 2000
				|| this.engineDisplacementInCubicCentimeters > 6000)) {
			throw new ValidationException("Invalid engine power. Diesel engines cc limitation: 2000<=cc<=6000");
		}
	}
}

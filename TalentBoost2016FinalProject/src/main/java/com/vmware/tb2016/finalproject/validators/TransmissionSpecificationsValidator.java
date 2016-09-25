package com.vmware.tb2016.finalproject.validators;

import java.text.ParseException;

import javax.validation.ValidationException;

/**
 * <code>TransmissionSpecificationsValidator</code> checks all possible
 * combinations for transmission specifications required in Talent Boost 2016 final
 * task. If all validations are passed the transmission specifications are saved
 * in this class fields and they are ready to be taken and used by other
 * classes.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class TransmissionSpecificationsValidator {

	String transmissionType;
	int gearsNumber;

	/**
	 * Validates transmission specifications.
	 * 
	 * @throws ParseException
	 * @throws ValidationException
	 */
	public final void validateTransmissionSpecifications(String transmissionSpecifications)
			throws ParseException, ValidationException {
		if (transmissionSpecifications.equals("default")) {
			this.transmissionType = VehicleSpecificationsConstants.DEFAULT_TRANSMISSION_TYPE;
			this.gearsNumber = VehicleSpecificationsConstants.DEFAULT_GEARS_NUMBER;
			return;
		}
		String[] transmissionSpecificationsArray = transmissionSpecifications.toUpperCase().split("[ =-]+");
		int specLenth = transmissionSpecificationsArray.length;
		if (specLenth < 2 || specLenth > 3) {
			throw new ParseException("Cannot parse transmission specifications: " + transmissionSpecifications, 0);
		} else {
			validateTransmissionType(transmissionSpecificationsArray[1]);
			if (specLenth == 2) {
				this.gearsNumber = VehicleSpecificationsConstants.DEFAULT_GEARS_NUMBER;
			} else {
				validateGearsNumber(transmissionSpecificationsArray[2]);
			}
			validateManualGears();
		}
	}

	private void validateTransmissionType(String transmissionType) throws ValidationException {
		if (VehicleSpecificationsConstants.TRANSMISSION_TYPE_OPTIONS.contains(transmissionType)) {
			this.transmissionType = transmissionType;
		} else {
			throw new ValidationException("Invalid transmission type: " + transmissionType);
		}
	}

	private void validateGearsNumber(String gearsNumber) throws ValidationException {
		int gearsNumberInt = Integer.parseInt(gearsNumber);
		if (VehicleSpecificationsConstants.GEARS_NUMBER_OPTIONS.contains(gearsNumberInt)) {
			this.gearsNumber = gearsNumberInt;
		} else {
			throw new ValidationException("Invalid gears number: " + gearsNumber);
		}
	}

	private void validateManualGears() throws ValidationException {
		if (this.transmissionType.equals("MANUAL") && this.gearsNumber > 6) {
			throw new ValidationException(
					String.format("Invalid specifications: [%s %d]. Maximum gears for manual transmission is 6.",
							this.transmissionType, this.gearsNumber));
		}
	}
}

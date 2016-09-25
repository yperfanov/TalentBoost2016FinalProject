package com.vmware.tb2016.finalproject.validators;

import java.text.ParseException;

import javax.validation.ValidationException;

/**
 * <code>ModelSpecificationsValidator</code> checks all possible combinations
 * for model specifications required in Talent Boost 2016 final task. If all validations are passed the model
 * specifications are saved in this class fields and they are ready to be taken
 * and used by other classes.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */

public class ModelSpecificationsValidator {

	String vehicleType;
	String modelName;
	String modelType;

	/**
	 * @throws ParseException
	 * @throws ValidationException 
	 */
	public final void validateModelSpecifications(String modelSpecifications) throws ParseException, ValidationException {
		String[] specifications = modelSpecifications.toUpperCase().split("[ =-]+");
		int specLenth = specifications.length;
		if (specLenth < 3 || specLenth > 4) {
			throw new ParseException("Cannot parse model specifications: " + modelSpecifications, 0);
		} else {
			validateVehicleType(specifications[0]);
			validateModelName(specifications[2], modelSpecifications);
			if (specLenth == 3) {
				this.modelType = VehicleSpecificationsConstants.DEFAULT_MODEL_TYPE;
			} else {
				validateModelType(specifications[3]);
			}
		}
	}

	private void validateVehicleType(String vehicleType) throws ValidationException {
		if (vehicleType.equals("CAR")) {
			this.vehicleType = vehicleType;
		} else if (vehicleType.equals("SUV")) {
			this.vehicleType = vehicleType;
		} else {
			throw new ValidationException("Invalid vehicle type: " + vehicleType);
		}
	}

	private void validateModelName(String modelName, String modelSpecifications) throws ValidationException {
		if ((this.vehicleType.equals("CAR") && VehicleSpecificationsConstants.CAR_MODELS.contains(modelName))
				|| (this.vehicleType.equals("SUV") && VehicleSpecificationsConstants.SUV_MODELS.contains(modelName))) {
			this.modelName = modelName;
		} else {
			throw new ValidationException(String.format("Invalid vehicle type and model name [%s %s] %s",
					this.vehicleType, modelName, modelSpecifications));
		}
	}

	private void validateModelType(String modelType) throws ValidationException {
		if (VehicleSpecificationsConstants.MODEL_TYPE_OPTIONS.contains(modelType)) {
			this.modelType = modelType;
		} else {
			throw new ValidationException("Invalid model type: " + modelType);
		}
	}
}

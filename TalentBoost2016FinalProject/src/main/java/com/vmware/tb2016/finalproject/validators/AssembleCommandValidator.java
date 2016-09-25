package com.vmware.tb2016.finalproject.validators;

import java.text.ParseException;

import javax.validation.ValidationException;

/**
 * <code>AssembleCommandValidator</code> validates the "assemble" command
 * required in Talent Boost 2016 final task.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class AssembleCommandValidator {

	private ModelSpecificationsValidator msv;
	private EngineSpecificationsValidator esv;;
	private TransmissionSpecificationsValidator tsv;

	/**
	 * 
	 * @param commandSpecifications
	 * @return formatted assemble command which is "readable" for the
	 *         {@link com.vmware.tb2016.finalproject.assembly_line.VehicleAssemblyLine
	 *         VehicleAssemblyLine}
	 * @throws ParseException
	 * @throws ValidationException
	 * @throws ValidationException 
	 */
	public final String validateAssembleCommand(String commandSpecifications)
			throws ParseException, ValidationException {
		String[] operSpecArray = commandSpecifications.split("[ ]+");
		int specLenth = operSpecArray.length;
		if (specLenth < 3 || specLenth > 5) {
			throw new ParseException("Cannot parse: " + commandSpecifications, 0);
		}
		msv = new ModelSpecificationsValidator();
		esv = new EngineSpecificationsValidator();
		tsv = new TransmissionSpecificationsValidator();
		if (specLenth >= 3) {
			msv.validateModelSpecifications(operSpecArray[0] + " " + operSpecArray[1]);
			esv.validateEngineSpecifications(operSpecArray[2]);
		}
		if (specLenth == 4) {
			tsv.validateTransmissionSpecifications(operSpecArray[3]);
		} else {
			tsv.validateTransmissionSpecifications("default");
		}
		String commandWithValidSpecifications = formatSpecifications();
		return commandWithValidSpecifications;
	}

	private String formatSpecifications() {
		StringBuilder sb = new StringBuilder();
		sb.append(msv.modelName + "-");
		sb.append(msv.modelType + "-");
		sb.append(esv.enginePowerInKw + "-");
		sb.append(esv.emissionStandart + "-");
		sb.append(esv.engineType + "-");

		if (esv.engineDisplacementInCubicCentimeters != 0) {
			sb.append(esv.engineDisplacementInCubicCentimeters + "-");
		} else {
			sb.append("*-");
		}

		if (esv.turbocharger) {
			sb.append("T-");
		} else {
			sb.append("*-");
		}

		if (!esv.engineType.equals("E")) {
			sb.append(tsv.transmissionType + "-");
			sb.append(tsv.gearsNumber);
		}
		return sb.toString();
	}
}

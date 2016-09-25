package com.vmware.tb2016.finalproject.validators;

import java.text.ParseException;

import javax.validation.ValidationException;

import com.vmware.tb2016.finalproject.interfaces.IValidator;

/**
 * <code>CommandValidator</code> validates commands required in Talent Boost 2016 final task.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class CommandValidator implements IValidator {

	@Override
	public String validate(String commandLine) throws ValidationException, ParseException {
		if (commandLine.equalsIgnoreCase("stop")) {
			return commandLine;
		}

		// If command line contains one word and it is not 'stop' is invalid
		if (!commandLine.contains(" ")) {
			throw new ValidationException("Invalid command: " + commandLine);
		}

		int index = commandLine.indexOf(" ");
		String command = commandLine.substring(0, index);
		String commandSpecifications = commandLine.substring(index + 1);
		String result = null;
		
		if (command.equalsIgnoreCase("read")) {
			return commandLine;
		}
		
		if (command.equalsIgnoreCase("assemble")) {
			AssembleCommandValidator acv = new AssembleCommandValidator();

			result = String.format("%s %s", command, acv.validateAssembleCommand(commandSpecifications));
		} else {
			if (command.equalsIgnoreCase("disassemble")) {
				validateDisassembleCommandSpecifications(commandSpecifications);

			} else if (command.equalsIgnoreCase("find")) {
				validateFindCommandSpecifications(commandSpecifications);

			} else if (command.equalsIgnoreCase("update")) {
				validateUpdateCommandSpecifications(commandSpecifications);

			} else if (command.equalsIgnoreCase("display")) {
				validateDisplayCommandSpecifications(commandSpecifications);

			} else {
				throw new ValidationException("Inknown command: " + command);
			}

			result = commandLine;
		}

		return result;
	}

	private void validateDisassembleCommandSpecifications(String commandSpecifications)
			throws ParseException, ValidationException {
		if (commandSpecifications.contains(" ")) {
			throw new ParseException("Cannot parse: " + commandSpecifications, 0);
		}

		// VIN must be with 17 chars.
		if (commandSpecifications.length() != 17) {
			throw new ValidationException(
					String.format("Invalid VIN length: [%s]. It must 17.", commandSpecifications.length()));
		}
	}

	private void validateFindCommandSpecifications(String commandSpecifications) throws ParseException, ValidationException {
		String[] arr = commandSpecifications.split("[ ]");
		if (arr.length != 2) {
			throw new ParseException("Cannot parse: " + commandSpecifications, 0);
		}

		if (!VehicleSpecificationsConstants.EMISSION_TYPE_OPTIONS.contains(arr[1].toUpperCase())) {
			throw new ValidationException(String.format("Invalid emission standart: %s", arr[1]));
		}
	}

	private void validateUpdateCommandSpecifications(String commandSpecifications)
			throws ValidationException, ParseException {
		String[] arr = commandSpecifications.split("[ ]");
		if (arr.length != 2) {
			throw new ParseException("Cannot parse: " + commandSpecifications, 0);
		}

		// VIN must be with 17 chars.
		if (arr[0].length() != 17) {
			throw new ValidationException(String.format("Invalid VIN length: [%s]. It must 17.", arr[0].length()));
		}

		if (!VehicleSpecificationsConstants.EMISSION_TYPE_OPTIONS.contains(arr[1].toUpperCase())) {
			throw new ValidationException(String.format("Invalid emission standart: %s", arr[1]));
		}
	}

	private void validateDisplayCommandSpecifications(String commandSpecifications)
			throws ParseException, ValidationException {
		if (commandSpecifications.contains(" ")) {
			throw new ParseException("Cannot parse: " + commandSpecifications, 0);
		}

		// Two options for display command - "display all" or "display <vin>"
		if (commandSpecifications.equalsIgnoreCase("all")) {
			return;
			// VIN must be with 17 chars.
		} else if (commandSpecifications.length() != 17) {
			throw new ValidationException(String.format("Invalid VIN length: [%s]. It must 17.", commandSpecifications.length()));
		}
	}
}

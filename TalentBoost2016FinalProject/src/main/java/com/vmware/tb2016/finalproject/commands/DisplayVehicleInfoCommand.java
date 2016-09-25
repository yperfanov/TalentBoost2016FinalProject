package com.vmware.tb2016.finalproject.commands;

import java.util.Map;

import com.vmware.tb2016.finalproject.interfaces.ICommand;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;

/**
 * <code>DisplayVehicleInfoCommand</code> displays all vehicles or vehicle with
 * specific VIN.<br>
 * Implements {@link com.vmware.tb2016.finalproject.interfaces.ICommand
 * ICommand}.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class DisplayVehicleInfoCommand implements ICommand {

	private final Map<String, Vehicle> vehicleStorage;

	/**
	 * @param vehicleStorage
	 *            - {@link java.util.Map Map} with key VIN of type
	 *            {@link java.lang.Object.String String} and value of type
	 *            {@link com.vmware.tb2016.finalproject.vehicle.Vehicle Vehicle}
	 *            .
	 */
	public DisplayVehicleInfoCommand(Map<String, Vehicle> vehicleStorage) {
		this.vehicleStorage = vehicleStorage;
	}

	@Override
	public String executeCommand(String commandSpecifications) {
		String result = null;

		if (vehicleStorage.containsKey(commandSpecifications)) {
			result = vehicleStorage.get(commandSpecifications).toString();

		} else if (commandSpecifications.equalsIgnoreCase("all")) {
			result = displayAllVehicles();
		} else {
			result = String.format("Cannot find vehicle with VIN: %s", commandSpecifications);
		}

		return result;
	}

	private String displayAllVehicles() {
		StringBuilder sb = new StringBuilder();
		String newLine = "\n";
		sb.append("------------" + newLine);
		for (Map.Entry<String, Vehicle> vehicle : vehicleStorage.entrySet()) {
			sb.append(vehicle.getValue().toString());
			sb.append(newLine);
			sb.append(newLine);
		}
		sb.append("------------");
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "display";
	}

}

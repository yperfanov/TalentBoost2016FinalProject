package com.vmware.tb2016.finalproject.commands;

import java.util.Map;

import com.vmware.tb2016.finalproject.interfaces.ICommand;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;

/**
 * <code>FindVehicleCommand</code> works only for emission standart (valid command "find engine euro4").<br>
 * Implements {@link com.vmware.tb2016.finalproject.interfaces.ICommand ICommand}.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */

public class FindVehicleCommand implements ICommand {
	
	private final Map<String, Vehicle> vehicleStorage;
	
	/**
	 * 
	 * @param vehicleStorage - {@link java.util.Map Map} with key VIN of type
	 *            {@link java.lang.Object.String String} and value of type
	 *            {@link com.vmware.tb2016.finalproject.vehicle.Vehicle Vehicle}
	 */
	public FindVehicleCommand(Map<String, Vehicle> vehicleStorage) {
		this.vehicleStorage = vehicleStorage;
	}
	
	@Override
	public String executeCommand(String commandSpecifications) {
		String[] arr = commandSpecifications.split("[ ]+");
		StringBuilder sb = new StringBuilder();
		String newLine = "\n";
		sb.append("------------" + newLine);
		int count = 0;
		for (Map.Entry<String, Vehicle> vehicle : vehicleStorage.entrySet()) {
			if (vehicle.getValue().getEngine().getEmissionStandart().equals(arr[1].toUpperCase())) {
				count++;
				sb.append(vehicle.getValue().toString() + newLine);
				sb.append(newLine);
			}
		}
		sb.append("------------");
		return String.format("%d vehicles with %s found:%n%s", count, arr[1].toUpperCase(), sb.toString());
	}

	@Override
	public String getCommandName() {
		return "find";
	}

}

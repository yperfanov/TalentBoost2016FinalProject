package com.vmware.tb2016.finalproject.commands;

import java.util.Map;

import com.vmware.tb2016.finalproject.interfaces.ICommand;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;

/**
 * <code>UpdateVehicleCommand</code> works only for emission standart - valid command "update 'vin' euro5".
 * Implements {@link com.vmware.tb2016.finalproject.interfaces.ICommand ICommand}.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */

public class UpdateVehicleCommand implements ICommand {

private final Map<String, Vehicle> vehicleStorage;
	
	/**
	 * 
	 * @param vehicleStorage - {@link java.util.Map Map} with key VIN of type
	 *            {@link java.lang.Object.String String} and value of type
	 *            {@link com.vmware.tb2016.finalproject.vehicle.Vehicle Vehicle}
	 */
	public UpdateVehicleCommand(Map<String, Vehicle> vehicleStorage) {
		this.vehicleStorage = vehicleStorage;
	}
	
	@Override
	public String executeCommand(String commandSpecifications) {
		String[] specsArray = commandSpecifications.split("[ ]+");
		
		if (!vehicleStorage.containsKey(specsArray[0])) {
			return String.format("Cannot find vehicle with VIN: %s\n", specsArray[0]);
		}
		
		Vehicle vehicle = vehicleStorage.get(specsArray[0]);
		
		if (vehicle.isDisassembled()) {
			return String.format("Cannot update vehicle with VIN: %s\n. It is disassembled.\n", specsArray[0]);
		}
		
		vehicle.getEngine().setEmissionStandart(specsArray[1].toUpperCase());
		vehicleStorage.put(vehicle.getVehicleIdentificationNumber(), vehicle);

		return String.format("Vehicle with VIN \"%s\" setted with %s.\n", specsArray[0], specsArray[1]);
	}
	
	@Override
	public String getCommandName() {
		return "update";
	}
}

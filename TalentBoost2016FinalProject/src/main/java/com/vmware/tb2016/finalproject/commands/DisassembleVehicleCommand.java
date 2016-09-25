package com.vmware.tb2016.finalproject.commands;

import java.util.Map;

import com.vmware.tb2016.finalproject.interfaces.ICommand;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;

/**
 * <code>DisassembleVehicleCommand</code><br>
 * Implements {@link com.vmware.tb2016.finalproject.interfaces.ICommand ICommand}.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class DisassembleVehicleCommand implements ICommand {

	private final Map<String, Vehicle> vehicleStorage;

	/**
	 * @param vehicleStorage
	 *            - {@link java.util.Map Map} with key VIN of type
	 *            {@link java.lang.Object.String String}
	 *            and value of type
	 *            {@link com.vmware.tb2016.finalproject.vehicle.Vehicle Vehicle}
	 *            .
	 */
	public DisassembleVehicleCommand(Map<String, Vehicle> vehicleStorage) {
		this.vehicleStorage = vehicleStorage;
	}

	@Override
	public String executeCommand(String commandSpecifications) {
		if (!vehicleStorage.containsKey(commandSpecifications)) {
			return String.format("Cannot find vehicle with VIN: %s\n", commandSpecifications);
		} else {
			Vehicle v = vehicleStorage.get(commandSpecifications);
			if (v.isDisassembled()) {
				return String.format("Vehicle with VIN: %s is already disassembled.\n", commandSpecifications);
			}
			v.disassembleVehicle(true);
			vehicleStorage.put(v.getVehicleIdentificationNumber(), v);
			return String.format("Vehicle with VIN: %s disassembled.\n", commandSpecifications);
		}
	}

	@Override
	public String getCommandName() {
		return "disassemble";
	}
}

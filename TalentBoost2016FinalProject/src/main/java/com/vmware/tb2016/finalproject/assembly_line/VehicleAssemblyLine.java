package com.vmware.tb2016.finalproject.assembly_line;

import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.vmware.tb2016.finalproject.interfaces.IAssemblyLine;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;
import com.vmware.tb2016.finalproject.vehicle_parts.engine.ElectricMotor;
import com.vmware.tb2016.finalproject.vehicle_parts.engine.Engine;
import com.vmware.tb2016.finalproject.vehicle_parts.engine.InternalCombustionEngine;
import com.vmware.tb2016.finalproject.vehicle_parts.transmission.Transmission;

/**
 * <code>AssemblyLine</code> represents vehicle assembly line which assembles
 * vehicles.<br>
 * Implements {@link com.vmware.tb2016.finalproject.interfaces.IAssemblyLine
 * IAssemblyLine}.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public class VehicleAssemblyLine implements IAssemblyLine {

	private final String factoryCode;
	private final BlockingQueue<String> assembleCommandsQueue;
	private final PrintStream resultOutput;
	private final Map<String, Vehicle> vehicleStorage;

	/**
	 * 
	 * @param vehicleStorage
	 *            - {@link java.util.Map Map} with key VIN of type
	 *            {@link java.lang.Object.String String} and value of type
	 *            {@link com.vmware.tb2016.finalproject.vehicle.Vehicle Vehicle}
	 *            . The assembled vehicles will be put here.
	 * @param assembleCommandsQueue
	 *            - {@link java.util.concurrent.BlockingQueue BlockingQueue}
	 *            from which the assembly line will be taking valid assemble
	 *            commands.
	 * @param output
	 *            - {@link java.io.PrintStream PrintStream} where the result of
	 *            the factory operations will be sent.
	 * @param factoryCode
	 *            - {@link java.lang.Object.String String}
	 */
	public VehicleAssemblyLine(Map<String, Vehicle> vehicleStorage, BlockingQueue<String> assembleCommandsQueue,
			PrintStream output, String factoryCode) {
		this.vehicleStorage = vehicleStorage;
		this.assembleCommandsQueue = assembleCommandsQueue;
		this.resultOutput = output;
		this.factoryCode = factoryCode;
	}

	/**
	 * Assemble command specifications are accepted in the following format:<br>
	 * modelName-modelType-powerInKw-emissionStandart-engineType-
	 * engineDisplaycementInCc-turbo-transmissionType-gearsNumber<br>
	 * Example: 'car model=a6 engine=d-233hp-t' -> 'A6-SEDAN-174-EURO3-D-2000-T-MANUAL-4'.<br>
	 * 'car model=a6 engine=e' -> 'A6-SEDAN-535-EURO6-D-*-*-'
	 * 
	 * @throws InterruptedException
	 */
	private void assembleVehicle() throws InterruptedException {
		String createCommand = assembleCommandsQueue.take();
		String[] commandSpecsArray = createCommand.split("[ -]+");

		int enginePowerInKw = Integer.parseInt(commandSpecsArray[2]);

		// taking at least 1 second to create vehicle as required
		Thread.sleep(1000);

		// assemble vehicle according to the type
		Engine engine = null;
		Vehicle vehicle = null;
		if (commandSpecsArray[4].equals("E")) {
			engine = new ElectricMotor(enginePowerInKw, commandSpecsArray[3]);
			vehicle = new Vehicle.VehicleBuilder(createVin(), commandSpecsArray[0], commandSpecsArray[1]).engine(engine)
					.build();
		} else {
			int engineDisplacementInCubicCentimeters = Integer.parseInt(commandSpecsArray[5]);
			int gearsNumber = Integer.parseInt(commandSpecsArray[8]);
			boolean turbocharger = false;
			if (commandSpecsArray[6].equals("T")) {
				turbocharger = true;
			}

			engine = new InternalCombustionEngine(enginePowerInKw, commandSpecsArray[3], commandSpecsArray[4],
					engineDisplacementInCubicCentimeters, turbocharger);
			vehicle = new Vehicle.VehicleBuilder(createVin(), commandSpecsArray[0], commandSpecsArray[1]).engine(engine)
					.transmission(new Transmission(commandSpecsArray[7], gearsNumber)).build();
		}

		// saves the vehicle
		this.vehicleStorage.put(vehicle.getVehicleIdentificationNumber(), vehicle);
		// send result to the PrintStream
		resultOutput.printf("%s\nVehicle [%s] created with VIN:\"%s\".\n\n", Thread.currentThread().getName(),
				createCommand, vehicle.getVehicleIdentificationNumber());
	}

	private String createVin() {
		String vin = VehicleIdentificationNumberGenerator.generateVin(factoryCode);
		return vin;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				assembleVehicle();
			}
		} catch (InterruptedException e) {
			resultOutput.printf("Assembly line \"%s\" has stopped working.\n%s\n", Thread.currentThread().getName(),
					e.getMessage());
		}
	}

	@Override
	public PrintStream getResultOutput() {
		return this.resultOutput;
	}

	@Override
	public String getFactoryCode() {
		return this.factoryCode;
	}
}

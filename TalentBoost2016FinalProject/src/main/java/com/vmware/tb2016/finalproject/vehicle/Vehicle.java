package com.vmware.tb2016.finalproject.vehicle;

import java.io.Serializable;

import com.vmware.tb2016.finalproject.interfaces.IVehicle;
import com.vmware.tb2016.finalproject.vehicle_parts.engine.Engine;
import com.vmware.tb2016.finalproject.vehicle_parts.transmission.Transmission;

/**
 * <code>Vehicle</code> uses Builder pattern to build vehicles. The mandatory
 * fields are VIN, model name and model type. Other fields are optional.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class Vehicle implements IVehicle, Serializable {

	private static final long serialVersionUID = 1L;
	private final String vehicleIdentificationNumber;
	private final String modelName;
	private final String modelType;
	private boolean disassembled = false;

	private Engine engine;
	private Transmission transmission;

	private Vehicle(VehicleBuilder vehicleBuilder) {
		vehicleIdentificationNumber = vehicleBuilder.vehicleIdentificationNumber;
		modelName = vehicleBuilder.modelName;
		modelType = vehicleBuilder.modelType;
		engine = vehicleBuilder.engine;
		transmission = vehicleBuilder.transmission;
	}

	public boolean isDisassembled() {
		return disassembled;
	}

	public void disassembleVehicle(boolean disassembled) {
		this.disassembled = disassembled;
	}

	public String getVehicleIdentificationNumber() {
		return vehicleIdentificationNumber;
	}

	public String getModelName() {
		return modelName;
	}

	public String getModelType() {
		return modelType;
	}

	public Engine getEngine() {
		return engine;
	}

	public Transmission getTransmission() {
		return transmission;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void setTransmission(Transmission transmission) {
		this.transmission = transmission;
	}

	public static class VehicleBuilder {
		private final String vehicleIdentificationNumber;
		private final String modelName;
		private final String modelType;

		private Engine engine = null;
		private Transmission transmission = null;

		public VehicleBuilder(String vehicleIdentificationNumber, String modelName, String modelType) {
			this.vehicleIdentificationNumber = vehicleIdentificationNumber;
			this.modelName = modelName;
			this.modelType = modelType;
		}

		public VehicleBuilder engine(Engine engine) {
			this.engine = engine;
			return this;
		}

		public VehicleBuilder transmission(Transmission transmission) {
			this.transmission = transmission;
			return this;
		}

		public Vehicle build() {
			return new Vehicle(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vehicleIdentificationNumber == null) ? 0 : vehicleIdentificationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (vehicleIdentificationNumber == null) {
			if (other.vehicleIdentificationNumber != null)
				return false;
		} else if (!vehicleIdentificationNumber.equals(other.vehicleIdentificationNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String engineString = "N/A";
		String transmissionString = "N/A";
		if (this.engine != null) {
			engineString = this.getEngine().toString();
		}
		if (this.transmission != null) {
			transmissionString = this.getTransmission().toString();
		}
		return String.format(
				"vin               |model |type      |engine          |transmission |disassembled%n"
						+ "%1$-17s |%2$-5s |%3$-9s |%4$-15s |%5$-12s |%6$-1b",
				this.vehicleIdentificationNumber, this.modelName, this.modelType, engineString, transmissionString,
				this.disassembled);
	}
}

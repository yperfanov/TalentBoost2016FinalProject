package com.vmware.tb2016.finalproject.interfaces;

/**
 * <code>IVehicle</code> is basic interface for vehicles. Every vehicle(car, truck, bicycle, etc)
 * should have at least VIN, model name and model type.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public interface IVehicle {

	/**
	 * @return {@link java.lang.Object.String String}
	 */
	public String getVehicleIdentificationNumber();


	/**
	 * @return {@link java.lang.Object.String String}
	 */
	public String getModelName();


	/**
	 * @return {@link java.lang.Object.String String}
	 */
	public String getModelType();
}

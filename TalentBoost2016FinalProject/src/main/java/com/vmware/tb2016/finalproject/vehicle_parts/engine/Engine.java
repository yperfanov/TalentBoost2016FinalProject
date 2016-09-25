package com.vmware.tb2016.finalproject.vehicle_parts.engine;

import java.io.Serializable;
/**
 * <code>Engine</code> basic abstract class for engine.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 * @author Admin
 */
public abstract class Engine implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int enginePowerInKw;
	protected String emissionStandart;

	public Engine(int enginePowerInKw, String emissionStandart) {
		this.enginePowerInKw = enginePowerInKw;
		this.emissionStandart = emissionStandart.toUpperCase();
	}

	public final int getEnginePowerInKw() {
		return this.enginePowerInKw;
	}

	public final int getEnginePowerInHp() {
		int powerInHp = (int) Math.round(this.enginePowerInKw * 1.34102209);
		return powerInHp;
	}

	public final String getEmissionStandart() {
		return this.emissionStandart;
	}

	public final void setEnginePowerInKw(int enginePowerInKw) {
		this.enginePowerInKw = enginePowerInKw;
	}

	public final void setEmissionStandart(String emissionStandart) {
		this.emissionStandart = emissionStandart;
	}
}

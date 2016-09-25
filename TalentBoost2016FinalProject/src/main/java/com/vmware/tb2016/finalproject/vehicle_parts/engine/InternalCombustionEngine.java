package com.vmware.tb2016.finalproject.vehicle_parts.engine;
/**
 * <code>InternalCombustionEngine</code> basic class for internal combustion engine.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class InternalCombustionEngine extends Engine {

	private static final long serialVersionUID = 1L;
	private String fuelType;
	private int engineDisplacementInCubicCentimeters;
	private boolean turbocharger;
	
	public InternalCombustionEngine(int enginePowerInKw, String emissionStandart, String fuelType,
			int engineDisplacementInCubicCentimeters, boolean turbocharger) {
		super(enginePowerInKw, emissionStandart);
		this.fuelType = fuelType;
		this.engineDisplacementInCubicCentimeters = engineDisplacementInCubicCentimeters;
		this.turbocharger = turbocharger;
	}

	public final String getFuelType() {
		return fuelType;
	}

	public final int getEngineDisplacementInCubicCentimeters() {
		return this.engineDisplacementInCubicCentimeters;
	}

	public final boolean hasTurbocharger() {
		return this.turbocharger;
	}
	
	public final void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public final void setEngineDisplacementInCubicCentimeters(int engineDisplacementInCubicCentimeters) {
		this.engineDisplacementInCubicCentimeters = engineDisplacementInCubicCentimeters;
	}

	public final void setTurbocharger(boolean turbocharger) {
		this.turbocharger = turbocharger;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.fuelType + "-");
		sb.append(this.getEnginePowerInHp() + "HP");
		if (this.turbocharger) {
			sb.append("-T");
		}
		sb.append("-" + this.emissionStandart);
		return sb.toString();
	}
}

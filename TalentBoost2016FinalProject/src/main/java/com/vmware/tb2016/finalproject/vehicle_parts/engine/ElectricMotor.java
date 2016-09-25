package com.vmware.tb2016.finalproject.vehicle_parts.engine;
/**
 * <code>ElectricMotor</code> basic class for electric motor.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class ElectricMotor extends Engine {

	private static final long serialVersionUID = 1L;

	public ElectricMotor(int enginePowerInKw, String emissionStandart) {
		super(enginePowerInKw, emissionStandart);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("E-" + this.getEnginePowerInHp() + "HP");
		sb.append("-" + this.emissionStandart);
		return sb.toString();
	}
}

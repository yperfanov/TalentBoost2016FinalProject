package com.vmware.tb2016.finalproject.vehicle_parts.transmission;

import java.io.Serializable;
/**
 * <code>Transmission</code> basic class for vehicle transmission.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class Transmission implements Serializable{

	private static final long serialVersionUID = 1L;
	private String transmissionType;
	private int numberOfGears;

	public Transmission(String transmissionType, int numberOfGears) {
		this.transmissionType = transmissionType;
		this.numberOfGears = numberOfGears;
	}

	public final String getType() {
		return transmissionType;
	}

	public final int getGears() {
		return numberOfGears;
	}

	public final void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public final void setNumberOfGears(int numberOfGears) {
		this.numberOfGears = numberOfGears;
	}

	@Override
	public String toString() {
		return String.format("%s-%d", this.transmissionType, this.numberOfGears);
	}
}

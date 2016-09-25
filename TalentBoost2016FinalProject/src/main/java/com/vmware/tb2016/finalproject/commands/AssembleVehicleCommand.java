package com.vmware.tb2016.finalproject.commands;

import java.util.concurrent.BlockingQueue;

import com.vmware.tb2016.finalproject.interfaces.ICommand;

/**
 * <code>AssembleVehicleCommand</code> <br>
 * Implements {@link com.vmware.tb2016.finalproject.interfaces.ICommand ICommand}.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 *
 */
public class AssembleVehicleCommand implements ICommand {

	private final BlockingQueue<String> createCommandsQueue;
	
	/**
	 * 
	 * @param createCommandsQueue - {@link java.util.concurrent.BlockingQueue BlockingQueue}
	 *            where {@link #executeCommand(String) executeCommand} will be sending the command.
	 */
	public AssembleVehicleCommand(BlockingQueue<String> createCommandsQueue) {
		this.createCommandsQueue = createCommandsQueue;
		
	}

	@Override
	public String executeCommand(String commandSpecifications) {
		int index = commandSpecifications.indexOf(" ");
		try {
			createCommandsQueue.put(commandSpecifications.substring(index + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return String.format("Order [%s] sent to assembly lines.", commandSpecifications);
	}


	@Override
	public String getCommandName() {
		return "assemble";
	}
}

package com.vmware.tb2016.finalproject.interfaces;

/**
 * <code>ICommand</code> defines the base behavior which
 * a command should have.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public interface ICommand {

	/**
	 * @param commandSpecifications
	 * @return - {@link java.lang.Object.String String} result of the command
	 */
	String executeCommand(String commandSpecifications);
	
	/**
	 * @return - {@link java.lang.Object.String String} command name
	 */
	String getCommandName();
}

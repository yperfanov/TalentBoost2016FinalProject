package com.vmware.tb2016.finalproject.interfaces;

import java.text.ParseException;

import javax.validation.ValidationException;


/**
 * <code>IValidator</code> interface which describes what behavior validator must have.
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public interface IValidator {

	/**
	 * @param commandLine
	 * @return - {@link java.lang.Object.String String}
	 * @throws ValidationException
	 * @throws ParseException
	 */
	String validate(String commandLine) throws ValidationException, ParseException;
}

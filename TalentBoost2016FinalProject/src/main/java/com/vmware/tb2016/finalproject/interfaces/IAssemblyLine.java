package com.vmware.tb2016.finalproject.interfaces;

import java.io.PrintStream;

/**
 <code>IAssemblyLine</code>
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 *
 */
public interface IAssemblyLine extends Runnable {

	/**
	 * @return {@link java.io.PrintStream PrintStream}
	 */
	PrintStream getResultOutput();
	
	/**
	 * @return {@link java.lang.Object.String String}
	 */
	String getFactoryCode();
}

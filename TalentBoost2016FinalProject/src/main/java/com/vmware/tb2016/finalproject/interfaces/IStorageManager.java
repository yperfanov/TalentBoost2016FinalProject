/**
 * 
 */
package com.vmware.tb2016.finalproject.interfaces;

import java.util.Map;

/**
 * <code>IStorageManager<K, V></code> interface for saving map objects.
 * @param <K> the key type in the map which will be saved
 * @param <V> the value type in the map which will be saved
 *  
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public interface IStorageManager<K, V> {

	/**
	 * 
	 * @return - {@link java.util.Map Map}
	 */
	Map<K, V> getStorage();
	
	boolean load();
	
	boolean save();
}

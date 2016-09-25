package com.vmware.tb2016.finalproject.vehicle_factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.vmware.tb2016.finalproject.interfaces.IStorageManager;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;

/**
 * <code>VehicleStorage</code> keeps and manages the factory storage.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
public class VehicleStorageManager implements IStorageManager<String, Vehicle>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @param vehicleStorage
	 *            - permanent storage for vehicles with VIN for key and
	 *            <code>Vehicle</code> object for value.
	 */
	private final Map<String, Vehicle> vehicleStorage;
	private final File projectStorageFile;

	public VehicleStorageManager() {
		vehicleStorage = new ConcurrentHashMap<String, Vehicle>();
		this.projectStorageFile = setProjectStorageFile();
	}

	private File setProjectStorageFile() {
		File projectDir = new File(System.getProperty("user.home"), ".TalentBoost2016FinalTask");
		String projectDirPath = projectDir.getAbsolutePath();
		File projectStorageFile = new File(projectDirPath, "VehicleStorage.save");

		// if the app starts for first time projectDir and projectStorageFile
		// will be created in the home directory of the host computer.
		if (!projectDir.exists()) {
			projectDir.mkdir();
			try {
				projectStorageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return projectStorageFile;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean load() {
		boolean isLoaded = false;
		ObjectInputStream ois = null;
		try {

			//checks if the file exist and is not empty
			if (projectStorageFile.length() != 0 && projectStorageFile.exists()) {
				ois = new ObjectInputStream(new FileInputStream(projectStorageFile));
				vehicleStorage.clear();
				vehicleStorage.putAll((ConcurrentHashMap<String, Vehicle>) ois.readObject());
				isLoaded = true;
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ois) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isLoaded;
	}

	@Override
	public boolean save() {
		boolean isSaved = false;
		ObjectOutputStream oos = null;
		try {

			if (projectStorageFile.exists()) {
				oos = new ObjectOutputStream(new FileOutputStream(projectStorageFile));
				oos.writeObject(vehicleStorage);

				isSaved = true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != oos) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isSaved;
	}

	@Override
	public Map<String, Vehicle> getStorage() {
		return this.vehicleStorage;
	}
}

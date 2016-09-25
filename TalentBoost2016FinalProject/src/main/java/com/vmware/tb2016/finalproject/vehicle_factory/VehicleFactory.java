package com.vmware.tb2016.finalproject.vehicle_factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.ValidationException;

import com.vmware.tb2016.finalproject.interfaces.IAssemblyLine;
import com.vmware.tb2016.finalproject.interfaces.ICommand;
import com.vmware.tb2016.finalproject.interfaces.IFactory;
import com.vmware.tb2016.finalproject.interfaces.IStorageManager;
import com.vmware.tb2016.finalproject.interfaces.IValidator;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;

/**
 * <code>VehicleFactory</code> representing a vehicle factory with at least one
 * assembly line. More assembly lines could be added afterwards using
 * {@link #addAssemblyLine(IAssemblyLine assemblyLine) addAssemblyLine} method.
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 * 
 */
public class VehicleFactory implements IFactory {

	private final BlockingQueue<String> assembleCommandsQueue;

	private final InputStream instr;
	private final PrintStream resultOutput;
	private ExecutorService executor;
	private IValidator validator;
	private final IStorageManager<String, Vehicle> storageManager;

	private final Map<String, ICommand> commandsMap;
	private final List<IAssemblyLine> vehicleAssemblyLines;

	private boolean stop;

	/**
	 * For valid commands. They can be validated in the factory by setting
	 * {@link com.vmware.tb2016.finalproject.interfaces.IValidator IValidator}
	 * with {@link #setValidator(IValidator) setValidator}.
	 * 
	 * @param commands
	 *            - {@link java.util.Collection Collection} which holds objects
	 *            of type
	 *            {@link com.vmware.tb2016.finalproject.interfaces.ICommand
	 *            ICommand} with all commands which this
	 * 
	 * @param assemblyLine
	 *            -
	 *            {@link com.vmware.tb2016.finalproject.interfaces.IAssemblyLine
	 *            IAssemblyLine}
	 * @param assembleCommandsQueue
	 *            - {@link java.util.concurrent.BlockingQueue BlockingQueue}
	 *            where assemble commands will be put waiting for assembly line
	 *            to take and execute them.
	 * @param resultOutput
	 *            - {@link java.io.PrintStream PrintStream} for the result of
	 *            the factory operations.
	 * @param instr
	 *            - {@link java.io.InputStream InputStream} from which commands
	 *            will be received.
	 * @param storageManager
	 *            -
	 *            {@link com.vmware.tb2016.finalproject.interfaces.IStorageManager
	 *            IStorageManager}
	 */
	public VehicleFactory(Collection<ICommand> commands, IAssemblyLine assemblyLine,
			BlockingQueue<String> assembleCommandsQueue, PrintStream resultOutput, InputStream instr,
			IStorageManager<String, Vehicle> storageManager) {
		this.assembleCommandsQueue = assembleCommandsQueue;
		this.resultOutput = resultOutput;
		this.instr = instr;
		this.vehicleAssemblyLines = new ArrayList<IAssemblyLine>();
		this.addAssemblyLine(assemblyLine);
		this.storageManager = storageManager;
		this.stop = false;

		commandsMap = new HashMap<String, ICommand>();
		for (ICommand operation : commands) {
			commandsMap.put(operation.getCommandName(), operation);
		}
	}

	/**
	 * Add validator.
	 * 
	 * @param validator
	 *            - {@link com.vmware.tb2016.finalproject.interfaces.IValidator
	 *            IValidator}
	 */
	public final void setValidator(IValidator validator) {
		this.validator = validator;
	}

	/**
	 * Add assembly line.
	 * 
	 * @param assemblyLine
	 *            - {@link #IAssemblyLine IAssemblyLine}
	 */
	public final void addAssemblyLine(IAssemblyLine assemblyLine) {
		this.vehicleAssemblyLines.add(assemblyLine);
	}

	@Override
	public void start() {
		// Starting the assembly lines
		executor = Executors.newFixedThreadPool(vehicleAssemblyLines.size());
		for (IAssemblyLine assemlyLine : vehicleAssemblyLines) {
			executor.execute(assemlyLine);
		}
		resultOutput.printf("The Factory has been started with %d assembly lines.\n", vehicleAssemblyLines.size());

		// Loading data from the permanent storage
		if (storageManager.load()) {
			resultOutput.println("Database loaded.");
		} else {
			resultOutput.println("Could not load database.");
		}
		processInput();
	}

	/**
	 * Process the input from the stream.
	 */
	private void processInput() {
		Scanner inscan = new Scanner(instr);
		try {
			while (!stop) {
				try {
					String commandLine = inscan.nextLine().trim();

					// Handling empty lines
					if (!commandLine.equals("")) {
						executeCommand(commandLine);
					}

				} catch (NoSuchElementException | ValidationException | ParseException | FileNotFoundException
						| InterruptedException e) {
					resultOutput.println(e.getMessage());
				}
			}
		} finally {
			inscan.close();
		}
	}

	private void executeCommand(String commandLine)
			throws FileNotFoundException, ValidationException, ParseException, InterruptedException {
		String validCommandLine = null;
		// Checking if command will be validate in the factory or it is
		// already valid.
		if (this.validator != null) {
			validCommandLine = validator.validate(commandLine);
		} else {
			validCommandLine = commandLine;
		}

		if (commandLine.equalsIgnoreCase("stop")) {
			this.stop();
		}

		int index = validCommandLine.indexOf(" ");
		String commandName = validCommandLine.substring(0, index);
		String commandSpecifications = validCommandLine.substring(index + 1);

		if (commandName.equalsIgnoreCase("read")) {
			readFiles(commandSpecifications);
			return;
		}

		ICommand command = commandsMap.get(commandName);
		if (command != null) {
			String result = command.executeCommand(commandSpecifications);
			resultOutput.println(result);
		} else {
			resultOutput.println("Unknown command: " + commandName);
		}
	}

	/**
	 * Iterates over files, reads them and sends every line to {@link #executeCommand(String) executeCommand}.
	 * 
	 * @param path
	 *            - must be a valid directory on the host computer containing
	 *            text files with commands.
	 */
	private void readFiles(String path)
			throws FileNotFoundException, ValidationException, ParseException, InterruptedException {
		File directory = new File(path);
		if (directory.exists()) {
			for (File file : directory.listFiles()) {
				if (file.getName().endsWith((".txt"))) {
					Scanner sc = new Scanner(file);
					while (sc.hasNextLine()) {
						String commandLine = sc.nextLine();
						// checks for empty line
						if (!commandLine.equals("")) {
							executeCommand(commandLine);
						}
					}
					sc.close();
				}
			}
		} else {
			throw new FileNotFoundException("Not valid path: " + path);
		}
	}

	@Override
	public void stop() {
		// Stop taking commands
		this.stop = true;
		try {
			instr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultOutput.println("No more commands are accepted.");

		// Letting assembly lines do their job if there are still commands in
		// the assembleCommandsQueue
		while (!assembleCommandsQueue.isEmpty()) {
		}

		// Stopping the assembly lines.
		executor.shutdownNow();
		while (!executor.isTerminated()) {
		}
		resultOutput.println("All pending operations finished.");

		// Saving to the permanent storage.
		if (storageManager.save()) {
			resultOutput.println("Changes saved.");
		} else {
			resultOutput.println("Could not save changes.");
		}

		// Shutting down the factory.
		resultOutput.print("Factory closed.");
		resultOutput.close();
		System.exit(0);
	}

}

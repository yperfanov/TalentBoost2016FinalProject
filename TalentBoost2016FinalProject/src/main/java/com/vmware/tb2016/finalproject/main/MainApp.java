package com.vmware.tb2016.finalproject.main;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vmware.tb2016.finalproject.assembly_line.VehicleAssemblyLine;
import com.vmware.tb2016.finalproject.commands.AssembleVehicleCommand;
import com.vmware.tb2016.finalproject.commands.DisassembleVehicleCommand;
import com.vmware.tb2016.finalproject.commands.DisplayVehicleInfoCommand;
import com.vmware.tb2016.finalproject.commands.FindVehicleCommand;
import com.vmware.tb2016.finalproject.commands.UpdateVehicleCommand;
import com.vmware.tb2016.finalproject.interfaces.IAssemblyLine;
import com.vmware.tb2016.finalproject.interfaces.ICommand;
import com.vmware.tb2016.finalproject.interfaces.IStorageManager;
import com.vmware.tb2016.finalproject.interfaces.IValidator;
import com.vmware.tb2016.finalproject.validators.CommandValidator;
import com.vmware.tb2016.finalproject.vehicle.Vehicle;
import com.vmware.tb2016.finalproject.vehicle_factory.VehicleFactory;
import com.vmware.tb2016.finalproject.vehicle_factory.VehicleStorageManager;

/**
 * <code>MainApp</code>
 * 
 * @author Yuliyan Perfanov yperfanov@yahoo.com
 */
@SpringBootApplication
public class MainApp {

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class);
	}
	
	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			BlockingQueue<String> assembleCommandsQueue = new LinkedBlockingQueue<String>();
			IStorageManager<String, Vehicle> sm = new VehicleStorageManager();
			IValidator v = new CommandValidator();
			
			IAssemblyLine firstAssemblyLine = new VehicleAssemblyLine(sm.getStorage(), assembleCommandsQueue, System.out,
					"BG0");
			IAssemblyLine secondAssemblyLine = new VehicleAssemblyLine(sm.getStorage(), assembleCommandsQueue, System.out,
					"BG0");

			Collection<ICommand> commands = Arrays.asList(new ICommand[] { new AssembleVehicleCommand(assembleCommandsQueue),
					new DisassembleVehicleCommand(sm.getStorage()),
					new DisplayVehicleInfoCommand(sm.getStorage()),
					new FindVehicleCommand(sm.getStorage()),
					new UpdateVehicleCommand(sm.getStorage()) });
			
			VehicleFactory factory = new VehicleFactory(commands, firstAssemblyLine, assembleCommandsQueue, System.out, System.in, sm);
			factory.setValidator(v);
			factory.addAssemblyLine(secondAssemblyLine);
			factory.start();
		};
	}
}

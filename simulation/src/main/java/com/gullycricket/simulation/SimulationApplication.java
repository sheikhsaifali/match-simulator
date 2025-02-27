package com.gullycricket.simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SimulationApplication{
	public static void main(String[] args) {
		SpringApplication.run(SimulationApplication.class, args);
	}
}

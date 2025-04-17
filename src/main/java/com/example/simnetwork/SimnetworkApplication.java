package com.example.simnetwork;

//import com.example.simnetwork.service.SimDataEnrichmentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SimnetworkApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SimnetworkApplication.class, args);

		// Step 2: Call the enrichment service once on startup
		//SimDataEnrichmentService enrichmentService = context.getBean(SimDataEnrichmentService.class);
		//enrichmentService.enrichSimData(); // Only needed ONCE to update null values
	}
}

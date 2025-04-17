// package com.example.simnetwork.service;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.simnetwork.model.SimPredictionResponse;
// import com.example.simnetwork.model.SimRecord;
// import com.example.simnetwork.repository.SimRepository;

// @Service
// public class SimDataEnrichmentService {

//     private final SimRepository simRepository;
//     private final MLModelService mlModelService;

//     public SimDataEnrichmentService(SimRepository simRepository, MLModelService mlModelService) {
//         this.simRepository = simRepository;
//         this.mlModelService = mlModelService;
//     }

//     @Transactional  // 🔥 This ensures changes get committed to the DB
//     public void enrichSimData() {
//         List<SimRecord> recordsToUpdate = simRepository.findAll().stream()
//                 .filter(record -> record.getScore() == null || record.getBestSimProvider() == null)
//                 .collect(Collectors.toList());

//         for (SimRecord record : recordsToUpdate) {
//             SimPredictionResponse response = mlModelService.getPredictionFromModel(
//                     record.getLatitude(),
//                     record.getLongitude()
//             );

//             record.setScore(response.getScore());
//             record.setBestSimProvider(response.getProvider());
//             simRepository.save(record); // Will now persist properly
//         }

//         System.out.println("Sim data enrichment complete!");
//     }
// }

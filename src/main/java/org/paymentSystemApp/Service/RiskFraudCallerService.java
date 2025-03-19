package org.paymentSystemApp.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.paymentSystemApp.Model.FraudCheckRequestDTO;
import org.paymentSystemApp.Model.FraudCheckResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service

public class RiskFraudCallerService {

    private final WebClient webClient;

    public  RiskFraudCallerService(WebClient webClient){
        this.webClient = webClient;
    }


    public FraudCheckResponseDTO sendDataToRiskFraudService(FraudCheckRequestDTO fraudCheckRequestDTO) throws JsonProcessingException {
        // Convert object to JSON and log
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(fraudCheckRequestDTO);
        System.out.println("Sending JSON request: " + requestBody);
        FraudCheckResponseDTO response = webClient.post() // app config wala webclient use ho rha, naya nahi banega
                .uri("/predict")
                .header("Content-Type", "application/json")
                .bodyValue(fraudCheckRequestDTO)
                .retrieve()
                .bodyToMono(FraudCheckResponseDTO.class)
                .block();  // Blocking for simplicity (adjust for async if needed)
        return response;

    }

}

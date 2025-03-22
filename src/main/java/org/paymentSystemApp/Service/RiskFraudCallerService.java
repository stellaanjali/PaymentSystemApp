package org.paymentSystemApp.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.paymentSystemApp.Config.ShortenSOPln;
import org.paymentSystemApp.Model.FraudCheckRequestDTO;
import org.paymentSystemApp.Model.FraudCheckResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static org.paymentSystemApp.Config.ShortenSOPln.print;

@Service

public class RiskFraudCallerService {

    private final WebClient webClient;

    public  RiskFraudCallerService(WebClient webClient){
        this.webClient = webClient;
    }


    public FraudCheckResponseDTO sendDataToRiskFraudService(FraudCheckRequestDTO fraudCheckRequestDTO) {
        // Convert object to JSON and log
        try {
            ObjectMapper objectMapper = new ObjectMapper();// json ko string banaya tha
            String requestBody = objectMapper.writeValueAsString(fraudCheckRequestDTO);
            print("Sending JSON request: " + requestBody);
        }
        catch(JsonProcessingException ex){
            print("Error converting request to JSON: " + ex.getMessage());
            throw new RuntimeException("JSON processing error", ex);
        }

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

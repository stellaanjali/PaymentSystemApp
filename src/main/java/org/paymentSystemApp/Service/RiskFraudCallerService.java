package org.paymentSystemApp.Service;

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

    public FraudCheckResponseDTO sendDataToRiskFraudService(FraudCheckRequestDTO fraudCheckRequestDTO){
        FraudCheckResponseDTO response = webClient.post()
                .uri("http://127.0.0.1:8000/predict")
                .bodyValue(fraudCheckRequestDTO)
                .retrieve()
                .bodyToMono(FraudCheckResponseDTO.class)
                .block();  // Blocking for simplicity (adjust for async if needed)
        return response;

    }

}

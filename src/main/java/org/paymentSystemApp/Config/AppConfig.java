package org.paymentSystemApp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration


public class AppConfig {
    @Bean
    public WebClient webClient(){ // iss se webclient singleton instance bann gya
        // jaha jaha v webclient ko CI se inject krenge wo isi instance ko use krega har file mei..no new instance will be created
        return WebClient
                .builder()
                .baseUrl("http://127.0.0.1:8000")
                .build();
    }


}

package com.ifif.config;


import com.ifif.service.JSoupExample;
import com.siot.IamportRestClient.IamportClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    String apiKey = "1721518235770524";
    String secretKey = "Xf95NEo1wnIjBYd4i14EveVMzduXlBMKoVoNpNLmKco69slP7XutJmaqyyfg8zKGyqTckWU4Age1c6bw";

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apiKey,secretKey);
    }
    @Bean
    public CommandLineRunner run(JSoupExample jSoupExample){
        return args -> {
            jSoupExample.crawlAndSaveItems();
        };
    }
}

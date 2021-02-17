package com.mycompany.safetyAlert.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.mycompany.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonIt {
    @Autowired
    TestRestTemplate clientRest; // envoie de requetes à Spring
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void initDb() throws Exception {
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void getCommunityEmail() throws Exception {
        // On envoie une requête GET avec en paramètre la ville Culver
        // + on vérifie que le statut de la réponse est 200
        ResponseEntity<String> response = clientRest.getForEntity("/communityEmail?city=Culver", String.class);
        // on vérifie le code retour 200
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // renvoie un jsonnode qu'on attend
        JsonNode expectedJson = objectMapper.readTree(ClassLoader.getSystemResourceAsStream("culverCommunityEmail.json"));
        // on vérifie le contenu
        assertEquals(expectedJson, objectMapper.readTree(response.getBody()));

    }

}




package com.mycompany.safetyAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mycompany.safetyAlert.dao.FirestationDaoImpl;
import com.mycompany.safetyAlert.dao.PersonDaoImpl;
import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
    @Autowired
    // MockMvc est un module de SpringTest permettant de simplifier la création de tests Rest
    MockMvc mockMvc;

    @MockBean
    FirestationDaoImpl firestationDao;

    String addressTest = " 1 rue Verte";
    int stationNumberTest = 9;

    // Création des casernes

    @Test
    void createFirestationValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode(); // on prépare le Json vide
        jsonFirestation.set("address", TextNode.valueOf(addressTest));
        jsonFirestation.set("station", TextNode.valueOf(String.valueOf(stationNumberTest)));

        // WHEN

        // THEN

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void createFirestationInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode(); // on prépare le Json vide
        jsonFirestation.set("address", TextNode.valueOf(""));
        jsonFirestation.set("station", TextNode.valueOf(""));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createFirestationWhenFirestationAlreadyExist() throws Exception {
        // GIVEN
        // provoque l'exception (le fichier Json utilisé en mémoire est vide)
        Mockito.doThrow(DataAlreadyExistException.class).when(firestationDao).createFirestation(Mockito.any());

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode(); // on prépare le Json vide
        jsonFirestation.set("address", TextNode.valueOf(addressTest));
        jsonFirestation.set("station", TextNode.valueOf(String.valueOf(stationNumberTest)));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isConflict());

    }

    // Modification des casernes
    @Test
    void updateFirestationValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode(); // on prépare le Json vide
        jsonFirestation.set("address", TextNode.valueOf(addressTest));
        jsonFirestation.set("station", TextNode.valueOf(String.valueOf(stationNumberTest)));

        // WHEN

        // THEN

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void updateFirestationInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode(); // on prépare le Json vide
        jsonFirestation.set("address", TextNode.valueOf(addressTest));
        jsonFirestation.set("station", TextNode.valueOf(""));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateFirestationWhenFirestationNotExist() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonFirestation = obm.createObjectNode(); // on prépare le Json vide
        jsonFirestation.set("address", TextNode.valueOf(addressTest));
        jsonFirestation.set("station", TextNode.valueOf(String.valueOf(stationNumberTest)));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/firestation").contentType(MediaType.APPLICATION_JSON).content(jsonFirestation.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}

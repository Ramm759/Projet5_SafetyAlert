package com.mycompany.safetyAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mycompany.safetyAlert.dao.MedicalrecordDaoImpl;
import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalrecordControllerTest {
    @Autowired
    // MockMvc est un module de SpringTest permettant de simplifier la création de tests Rest
    MockMvc mockMvc;

    @MockBean
    MedicalrecordDaoImpl medicalrecordDao;

    String firstnameTest = "Marc";
    String lastnameTest = "Dupont";
    String birthdate = "12/06/1975";
    List<String> medications = new ArrayList<String>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
    List<String> allergies = new ArrayList<String>(Arrays.asList("nillacilan", "hydrapermazol:100mg"));

    // Création des Medicalrecord
    @Test
    void createMedicalrecordValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));
        //jsonMedicalrecord.set("medications", TextNode.valueOf(medications));
        //jsonMedicalrecord.set("allergies", TextNode.valueOf(String.valueOf(allergies)));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void createMedicalrecordInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(""));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createMedicalrecordWhenMedicalrecordAlreadyExist() throws Exception {
        // GIVEN
        // provoque l'exception (le fichier Json utilisé en mémoire est vide)
        Mockito.doThrow(DataAlreadyExistException.class).when(medicalrecordDao).createMedicalrecord(Mockito.any());

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isConflict());

    }

    // Modification des Medicalrecord
    @Test
    void updateMedicalrecordValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void updateMedicalrecordInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(""));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateMedicalrecordWhenMedicalrecordNotExist() throws Exception {
        // GIVEN
        // On courtcircuite l'appel de l'exeption (le fichier Json utilisé en mémoire est vide)
        Mockito.doThrow(DataNotFoundException.class).when(medicalrecordDao).updateMedicalrecord(Mockito.any());

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    // Suppression des Medicalrecord
    @Test
    void deleteMedicalrecordValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isResetContent());

    }

    @Test
    void deleteMedicalrecordInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(""));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteMedicalrecordWhenvNotExist() throws Exception {
        // GIVEN
        // On courrtcircuite l'appel de l'exeption
        Mockito.doThrow(DataNotFoundException.class).when(medicalrecordDao).deleteMedicalrecord((Mockito.any()));

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonMedicalrecord = obm.createObjectNode(); // on prépare le Json vide
        jsonMedicalrecord.set("firstName", TextNode.valueOf(firstnameTest));
        jsonMedicalrecord.set("lastName", TextNode.valueOf(lastnameTest));
        jsonMedicalrecord.set("birthdate", TextNode.valueOf(birthdate));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalrecord.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}


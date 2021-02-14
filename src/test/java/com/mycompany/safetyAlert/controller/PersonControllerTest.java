package com.mycompany.safetyAlert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mycompany.safetyAlert.service.PersonService;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    // MockMvc est un module de SpringTest permettant de simplifier la création de tests Rest
    MockMvc mockMvc;

    @MockBean
    PersonService personDao;

    String firstnameTest = "Marc";
    String lastnameTest = "Dupont";

    // Création des personnes
    @Test
    void createPersonValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastnameTest));

        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void createPersonInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(""));
        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createPersonWhenPersonAlreadyExist() throws Exception {
        // GIVEN
        // provoque l'exception (le fichier Json utilisé en mémoire est vide)
        Mockito.doThrow(DataAlreadyExistException.class).when(personDao).createPerson(Mockito.any());

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastnameTest));
        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isConflict());

    }

    // Modification des personnes
    @Test
    void updatePersonValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastnameTest));

        // WHEN

        // THEN

        mockMvc.perform(MockMvcRequestBuilders.put("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void updatePersonInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(""));
        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updatePersonWhenPersonNotExist() throws Exception {
        // GIVEN
        // On courtcircuite l'appel de l'exeption (le fichier Json utilisé en mémoire est vide)
        Mockito.doThrow(DataNotFoundException.class).when(personDao).updatePerson(Mockito.any());

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastnameTest));
        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    // Suppression des personnes
    @Test
    void deletePersonValid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastnameTest));

        // WHEN

        // THEN

        mockMvc.perform(MockMvcRequestBuilders.delete("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isResetContent());

    }

    @Test
    void deletePersonInvalid() throws Exception {
        // GIVEN
        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(""));
        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deletePersonWhenPersonNotExist() throws Exception {
        // GIVEN
        // On courrtcircuite l'appel de l'exeption
        Mockito.doThrow(DataNotFoundException.class).when(personDao).deletePerson((Mockito.any()));

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonPerson = obm.createObjectNode(); // on prépare le Json vide
        jsonPerson.set("firstName", TextNode.valueOf(firstnameTest));
        jsonPerson.set("lastName", TextNode.valueOf(lastnameTest));
        // WHEN

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson.toString())).andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}


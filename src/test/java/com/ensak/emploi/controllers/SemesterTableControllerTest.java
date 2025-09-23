package com.ensak.emploi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ensak.emploi.services.SemesterTableService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SemesterTableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SemesterTableService semesterTableService;

    @Test
    public void testCreatesemestertable() throws Exception {

        mockMvc.perform(post("/semestertable"))
                .andExpect(status().isOk());
    }

}

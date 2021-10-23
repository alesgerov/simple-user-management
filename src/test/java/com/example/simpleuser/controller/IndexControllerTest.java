package com.example.simpleuser.controller;

import com.example.simpleuser.form.RegistrationForm;
import com.example.simpleuser.service.UserService;
import com.example.simpleuser.utils.Utils;
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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testUrlsWithoutAuthorizationInGetMethod() throws Exception {
        String uri = "/api/smt";
        mockMvc.perform(get(uri))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.title", equalTo("Girish qadagandir")))
                .andExpect(jsonPath("$.detail", equalTo("Bu sehifeye girish ucun token elde etmelisiniz")));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testUrlsWithoutAuthorizationInPostMethod() throws Exception {
        String uri = "/api/smt";
        mockMvc.perform(post(uri))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.title", equalTo("Girish qadagandir")))
                .andExpect(jsonPath("$.detail", equalTo("Bu sehifeye girish ucun token elde etmelisiniz")));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testUrlsWithoutAuthorizationInDeleteMethod() throws Exception {
        String uri = "/api/smt";
        mockMvc.perform(delete(uri))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.title", equalTo("Girish qadagandir")))
                .andExpect(jsonPath("$.detail", equalTo("Bu sehifeye girish ucun token elde etmelisiniz")));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    public void testUrlsWithoutAuthorizationInPutMethod() throws Exception {
        String uri = "/api/smt";
        mockMvc.perform(put(uri))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.title", equalTo("Girish qadagandir")))
                .andExpect(jsonPath("$.detail", equalTo("Bu sehifeye girish ucun token elde etmelisiniz")));
        Mockito.verifyNoMoreInteractions(userService);
    }


    @Test
    public void testRegisterUserSuccessful() throws Exception {
        String uri = "/api/account/signup";
        RegistrationForm form = new RegistrationForm("user112", "passworddd");
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.objectToJson(form)))
                .andExpect(status().is(201));
    }

    @Test
    public void testRegisterUserUnSuccessfulValidation() throws Exception {
        String uri = "/api/account/signup";
        RegistrationForm form = new RegistrationForm("user112", "padd");
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.objectToJson(form)))
                .andExpect(status().is(422));
    }
}



package com.alexbt.appdirect.sampleapp.ui.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alexbt.appdirect.sampleapp.ui.model.AuthenticatedUser;
import com.alexbt.appdirect.sampleapp.util.WebConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticatedUserController.class)
@MockBeans(@MockBean(UserDetailsService.class))
public class AuthenticatedUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenIDAuthenticationToken openIDAuthenticationToken;

    @Autowired
    private AuthenticatedUserController userController;

    @Test
    @WithMockOpenIdUser(attributeExchange = { "fullName:TestFullName", "email:Test@Test.com", //
            "uuid:TestUserUuid", "companyUuid:TestCompanyUuid", "companyName:TestCompanyName" })
    public void test() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(WebConstants.WEB_URI_CURRENT_USER));
        MvcResult andReturn = resultActions.andExpect(status().isOk()).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticatedUser user = objectMapper.readValue(andReturn.getResponse().getContentAsString(), AuthenticatedUser.class);

        AuthenticatedUser expectedUser = new AuthenticatedUser();
        expectedUser.setCompanyName("TestCompanyName");
        expectedUser.setCompanyUuid("TestCompanyUuid");
        expectedUser.setEmail("Test@Test.com");
        expectedUser.setName("TestFullName");
        expectedUser.setUuid("TestUserUuid");

        assertEquals(expectedUser, user);
        assertEquals(expectedUser.hashCode(), user.hashCode());
    }

    // This test raised a 302
    // BUT, with Openid Spring Configuration, 401 is rather thrown.
    @Test
    public void testNoMockUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(WebConstants.WEB_URI_CURRENT_USER));
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockOpenIdUser
    public void testNullOpenIDAuthenticationToken() {
        ResponseEntity<AuthenticatedUser> currentUser = userController.getAuthenticatedUser(null);
        assertEquals(HttpStatus.NOT_FOUND, currentUser.getStatusCode());
        assertEquals(null, currentUser.getBody());
    }
}
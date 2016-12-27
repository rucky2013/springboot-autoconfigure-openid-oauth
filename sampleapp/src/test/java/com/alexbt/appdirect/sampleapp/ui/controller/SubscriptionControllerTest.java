package com.alexbt.appdirect.sampleapp.ui.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;
import com.alexbt.appdirect.sampleapp.util.WebConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @Test
    @WithMockOpenIdUser
    public void test() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setId("1");

        List<Subscription> list = Collections.singletonList(subscription);
        given(subscriptionRepository.findAll()).willReturn(list);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(WebConstants.WEB_URI_SUBSCRIPTIONS));

        MvcResult andReturn = resultActions.andExpect(status().isOk()).andReturn();
        String content = andReturn.getResponse().getContentAsString();
        content = StringUtils.removeStart(content, "[");
        content = StringUtils.removeEnd(content, "]");
        assertEquals(subscription, objectMapper.readValue(content, Subscription.class));
    }

    // This test raised a 302
    // BUT, with Openid Spring Configuration, 401 is rather thrown.
    @Test
    public void testNoMockUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(WebConstants.WEB_URI_SUBSCRIPTIONS));
        resultActions.andExpect(status().is3xxRedirection());
    }

}

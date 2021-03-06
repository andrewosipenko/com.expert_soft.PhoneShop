package com.expert_soft.controller;

import com.expert_soft.config.ApplicationConfiguration;
import com.expert_soft.model.Phone;
import com.expert_soft.util.TestDataConfig;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestDataConfig.class} )
@WebAppConfiguration
public class ProductControllerIntegrationTest {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductController controller;

    private MockMvc mockMvc;
    private Phone phone_id_1 ;


    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        phone_id_1 = (Phone) context.getBean("phone_id_1");
    }

    @Test
    public void product_Valid() throws Exception {
        mockMvc.perform(get("/products/1"))
               .andExpect(model().attribute("phone", phone_id_1));
    }

    @Test
    public void product_Invalid() throws Exception {
        mockMvc.perform(get("/products/100"))
               .andExpect(status().isNotFound());

        mockMvc.perform(get("/products/-1"))
               .andExpect(status().isBadRequest());

    }




}

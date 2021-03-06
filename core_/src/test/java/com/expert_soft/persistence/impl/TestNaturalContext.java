package com.expert_soft.persistence.impl;


import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:dataSource-context.xml",
        "classpath:common_context.xml"
})
@TestPropertySource(locations = "classpath:config/application.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})

public class TestNaturalContext {

    private static final Logger logger = Logger.getLogger(TestNaturalContext.class);

    @Autowired private ApplicationContext applicationContext;

    @BeforeClass
    public static void setUp() throws Exception {
        logger.info("Test natural connection");
    }

    @Test
    public void isSchemaExist()  {
        DataSource schema = (DataSource) applicationContext.getBean("schema");
        try(Connection connection = schema.getConnection();) {
            String url =  connection.getMetaData().getURL();
            logger.info("DataSource in use with URL " + url);
             ResultSet tables = connection.getMetaData().getTables("PUBLIC", null, null, null);
            List<String> tablesNames = new ArrayList<>();
            while (tables.next()){
                tablesNames.add(tables.getString(3));
           }
            assertThat(tablesNames, hasItems("ORDERS", "ORDER_ITEMS", "PHONES"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

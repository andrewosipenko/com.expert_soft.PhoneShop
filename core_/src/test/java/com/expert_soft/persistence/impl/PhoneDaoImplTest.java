package com.expert_soft.persistence.impl;

import com.expert_soft.model.Phone;
import com.expert_soft.persistence.PhoneDao;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:persistence-context.xml",
        "classpath:test-dataSource-context.xml",
        "classpath:test-data.xml"
})
@TestPropertySource(locations = "classpath:dbTest.properties")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@Transactional
public class PhoneDaoImplTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PhoneDao dao;

    @Test
    public void setDataSource() throws Exception {}

    @Test
    public void getPhone() throws Exception {
        Phone actual = dao.getPhone(1L);
        Phone expected = (Phone) applicationContext.getBean("phone_id_1");
        assertEquals(expected, actual);
    }


    @Test(expected = org.springframework.dao.EmptyResultDataAccessException.class)
    public void getNotExistingPhone(){
        dao.getPhone(Long.MAX_VALUE);
    }

    @Test
    public void findAll() throws Exception {
        int rowCountExpected = 5;
        List<Phone> all = dao.findAll();
        assertEquals(rowCountExpected, all.size());
    }

    @Test
    @Ignore
    public void savePhone() throws Exception {
        Long idExpected = 6L;
        Phone phoneToBeSaved = (Phone) applicationContext.getBean("phone_to_save_1");
        dao.savePhone(phoneToBeSaved);

        Phone phone = dao.getPhone(idExpected);
        assertEquals(idExpected, phone.getKey());
        phoneToBeSaved.setKey(6L);
        assertEquals(phone, phoneToBeSaved);
    }

    @Test(expected = org.springframework.dao.DuplicateKeyException.class)
    public void saveNotUniquePhone(){
        Phone expected = (Phone) applicationContext.getBean("phone_id_1");
        expected.setKey(null);
        dao.savePhone(expected);
    }
}
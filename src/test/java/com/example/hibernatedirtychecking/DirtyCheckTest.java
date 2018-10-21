package com.example.hibernatedirtychecking;

import com.example.hibernatedirtychecking.entity.Customer;
import com.example.hibernatedirtychecking.entity.Issue;
import com.example.hibernatedirtychecking.repository.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

/**
 * Created by mtumilowicz on 2018-10-21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DirtyCheckTest {

    @Autowired
    CustomerRepository repository;
    
    @Before
    public void before() {
        Customer customer = Customer.builder()
                .id(1)
                .name("Tumilowicz")
                .issues(Collections.singletonList(Issue.builder()
                        .id(1)
                        .description("issue")
                        .build()))
                .build();

        repository.save(customer);
    }
    
    @After
    public void after() {
        repository.deleteAll();
    }
    
    @Test
    public void noChange() {
        System.out.println("noChange start");
        Customer customer = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        
        repository.save(customer);
        System.out.println("noChange end");
    }

    @Test
    public void field_change() {
        System.out.println("field_change start");
        Customer customer = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        customer.setName("changedName");
        
        repository.save(customer);
        System.out.println("field_change end");
    }
    
    @Test
    public void change_in_collection() {
        System.out.println("change_in_collection start");
        Customer customer = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        customer.getIssues().add(Issue.builder()
                .id(2)
                .description("issue2")
                .build());

        repository.save(customer);
        System.out.println("change_in_collection end");
    }
    
    @Test
    public void field_change_in_relation() {
        System.out.println("field_change_in_relation start");
        Customer customer = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        customer.getIssues().get(0).setDescription("descriptionChanged");

        repository.save(customer);
        System.out.println("field_change_in_relation end");
    }
}

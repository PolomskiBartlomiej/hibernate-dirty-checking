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

//        noChange start
//        Hibernate: select customer0_.id as id1_0_0_, customer0_.name as name2_0_0_ from customer customer0_ where customer0_.id=?
//        Hibernate: select customer0_.id as id1_0_1_, customer0_.name as name2_0_1_, issues1_.issues_id as issues_i3_1_3_, issues1_.id as id1_1_3_, issues1_.id as id1_1_0_, issues1_.description as descript2_1_0_ from customer customer0_ left outer join issue issues1_ on customer0_.id=issues1_.issues_id where customer0_.id=?
//        noChange end
    }

    @Test
    public void field_change() {
        System.out.println("field_change start");
        Customer customer = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        customer.setName("changedName");
        
        repository.save(customer);
        System.out.println("field_change end");

//        field_change start
//        Hibernate: select customer0_.id as id1_0_0_, customer0_.name as name2_0_0_ from customer customer0_ where customer0_.id=?
//        Hibernate: select customer0_.id as id1_0_1_, customer0_.name as name2_0_1_, issues1_.issues_id as issues_i3_1_3_, issues1_.id as id1_1_3_, issues1_.id as id1_1_0_, issues1_.description as descript2_1_0_ from customer customer0_ left outer join issue issues1_ on customer0_.id=issues1_.issues_id where customer0_.id=?
//        Hibernate: update customer set name=? where id=?
//        field_change end
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

//        change_in_collection start
//        Hibernate: select customer0_.id as id1_0_0_, customer0_.name as name2_0_0_, issues1_.issues_id as issues_i3_1_1_, issues1_.id as id1_1_1_, issues1_.id as id1_1_2_, issues1_.description as descript2_1_2_ from customer customer0_ left outer join issue issues1_ on customer0_.id=issues1_.issues_id where customer0_.id=?
//        Hibernate: select customer0_.id as id1_0_1_, customer0_.name as name2_0_1_, issues1_.issues_id as issues_i3_1_3_, issues1_.id as id1_1_3_, issues1_.id as id1_1_0_, issues1_.description as descript2_1_0_ from customer customer0_ left outer join issue issues1_ on customer0_.id=issues1_.issues_id where customer0_.id=?
//        Hibernate: select issue0_.id as id1_1_0_, issue0_.description as descript2_1_0_ from issue issue0_ where issue0_.id=?
//        Hibernate: insert into issue (description, id) values (?, ?)
//        Hibernate: update issue set issues_id=? where id=?
//        change_in_collection end
    }
    
    @Test
    public void field_change_in_relation() {
        System.out.println("field_change_in_relation start");
        Customer customer = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        customer.getIssues().get(0).setDescription("descriptionChanged");

        repository.save(customer);
        System.out.println("field_change_in_relation end");

//        field_change_in_relation start
//        Hibernate: select customer0_.id as id1_0_0_, customer0_.name as name2_0_0_, issues1_.issues_id as issues_i3_1_1_, issues1_.id as id1_1_1_, issues1_.id as id1_1_2_, issues1_.description as descript2_1_2_ from customer customer0_ left outer join issue issues1_ on customer0_.id=issues1_.issues_id where customer0_.id=?
//        Hibernate: select customer0_.id as id1_0_1_, customer0_.name as name2_0_1_, issues1_.issues_id as issues_i3_1_3_, issues1_.id as id1_1_3_, issues1_.id as id1_1_0_, issues1_.description as descript2_1_0_ from customer customer0_ left outer join issue issues1_ on customer0_.id=issues1_.issues_id where customer0_.id=?
//        Hibernate: update issue set description=? where id=?
//        field_change_in_relation end
    }
}

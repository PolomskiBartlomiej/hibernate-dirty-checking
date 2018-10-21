package com.example.hibernatedirtychecking.repository;

import com.example.hibernatedirtychecking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mtumilowicz on 2018-10-21.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

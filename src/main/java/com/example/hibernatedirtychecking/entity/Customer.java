package com.example.hibernatedirtychecking.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Created by mtumilowicz on 2018-10-21.
 */
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Customer {
    @Id
    Integer id;

    String name;

    @OneToMany
    @JoinColumn
    Collection<Issue> issues;
}

package com.example.hibernatedirtychecking.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    Collection<Issue> issues;
}

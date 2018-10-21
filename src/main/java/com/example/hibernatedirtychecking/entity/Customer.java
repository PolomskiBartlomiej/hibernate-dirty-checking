package com.example.hibernatedirtychecking.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

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
    List<Issue> issues;
}

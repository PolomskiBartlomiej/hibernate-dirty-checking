package com.example.hibernatedirtychecking.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mtumilowicz on 2018-10-21.
 */
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Issue {
    @Id
    Integer id;

    String description;
}

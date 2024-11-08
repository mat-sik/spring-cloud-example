package com.github.matsik.inventory.availability;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "availabilities")
public class Availability {

    @Id
    private String id;

    private long count;

}

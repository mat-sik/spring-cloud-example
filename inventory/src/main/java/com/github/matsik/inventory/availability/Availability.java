package com.github.matsik.inventory.availability;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "availabilities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Availability {

    @Id
    private String id;

    private long count;

}

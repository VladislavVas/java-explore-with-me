package ru.practicum.ewm.event.model;

import lombok.*;
import ru.practicum.ewm.Create;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    Long id;
    @NotNull(groups = {Create.class}, message = "Field: lat. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: lat. Error: must not be blank.")
    @Column(name = "lat")
    Float lat;
    @NotNull(groups = {Create.class}, message = "Field: lon. Error: must not be null")
    @NotBlank(groups = {Create.class}, message = "Field: lon. Error: must not be blank.")
    @Column(name = "lon")
    Float lon;
}

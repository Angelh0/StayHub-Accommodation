package com.Angelh0.stayhub.dto.accommodation;

import com.Angelh0.stayhub.enums.AccommodationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RequestAccommodationDTO {

    private UUID uuid;

    @NotBlank(message = "El campo nombre es obligatorio")
    private String name;

    @NotNull(message = "El campo tipo es obligatorio")
    private AccommodationType type;

    @NotBlank(message = "El campo descripcion es obligatorio")
    private String description;

    @NotBlank(message = "El campo ciudad es obligatorio")
    private String city;

    @NotBlank(message = "El campo pais es obligatorio")
    private String country;
}
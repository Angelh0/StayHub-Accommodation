package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.RoomType;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoomDTO {

    private UUID uuid;
    private String city;

    @NotNull(message = "El numero de habitaciones no puede estar vacio")
    @Min(value = 1, message = "Debe contener al menos 1 habitacion")
    private Integer room;

    @NotNull(message = "La capacidad de personas no puede estar vacio")
    @Min(value = 1, message = "Debe permitir al menos 1 persona")
    private Integer capacity;

    @NotNull(message = "El numero de camas no puede estar vacio")
    @Min(value = 1, message = "Debe tener al menos 1 cama")
    private Integer beds;

    @NotNull(message = "El tipo de habitacion no puede estar vacio")
    private RoomType type;

    @NotNull(message = "El precio por noche no puede estar vacio")
    @Min(value = 1, message = "El precio no puede ser 0")
    private Double price;

    @NotNull(message = "Los metros cuadrados de la habitacion no puede estar vacio")
    @Min(value = 10, message = "El valor no puede ser inferior a 10")
    private Double areaInSquareMeters;

    @NotNull
    private StatusType status;
}
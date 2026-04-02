package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.RoomType;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoomAndAccDTO {
    private UUID uuid;
    private String nameAccommodation;
    private UUID uuidAccommodation;
    private String city;

    private UUID uuidOwner;

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

    @JsonFormat(pattern = "dd-MM-yy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yy HH:mm:ss")
    private LocalDateTime updatedAt;

    @NotNull
    private StatusType status;

    private List<String> photos;
}

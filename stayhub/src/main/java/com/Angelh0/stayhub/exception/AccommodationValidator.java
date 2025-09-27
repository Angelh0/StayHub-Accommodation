package com.Angelh0.stayhub.exception;

import com.Angelh0.stayhub.dto.AccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccommodationValidator {

    public static void validateAccommodationDTO(AccommodationDTO dto) { // Metodo para comprobar valores que faltan o errores (DTO)
        List<ErrorModel> errors = new ArrayList<>();

        if (dto.getName() == null || dto.getName().isEmpty()) {
            errors.add(new ErrorModel("NAME_REQUIRED", "Name is required"));
        }


        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            errors.add(new ErrorModel("DESCRIPTION_REQUIRED", "Description is required"));
        }

        if (dto.getCountry() == null || dto.getCountry().isEmpty()) {
            errors.add(new ErrorModel("COUNTRY_REQUIRED", "Country is required"));
        }

        if (dto.getCity() == null || dto.getCity().isEmpty()) {
            errors.add(new ErrorModel("LOCATION_REQUIRED", "Location is required"));
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }
    }

    public static AccommodationEntity validateAccommodationExists(Optional<AccommodationEntity> optEntity, String uuid) { // metodo para comprobar existencia de ID (sustituye a Optional)
        return optEntity.orElseThrow(() ->
                new BusinessException(
                        List.of(new ErrorModel("ID_NOT_FOUND", "Accommodation with ID " + uuid + " not found"))
                )
        );
    }
}

package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.UpdateAccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationDraftEntity;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.repository.AccommodationDraftRepository;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.service.AccommodationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceImplTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private AccommodationConverter accommodationConverter;

    @InjectMocks
    private AccommodationServiceImpl accommodationServiceImpl ;

    @Test
    void modifiedAccommodation_DebeActualizarEstancia_ConLosValoresCorrectos() {
        UUID accommodationUuid = UUID.randomUUID();
        UUID ownerUuid = UUID.randomUUID();

        UpdateAccommodationDTO dto = new UpdateAccommodationDTO();
        dto.setMinStay(2);
        dto.setMaxStay(5);

        AccommodationEntity accommodationEntity = new AccommodationEntity();
        accommodationEntity.setMinStay(1);
        accommodationEntity.setMaxStay(10);

        ResponseAccommodationDTO dtoFalso = new ResponseAccommodationDTO();

        when(accommodationRepository.findByUuidAndUuidOwner(accommodationUuid, ownerUuid)).thenReturn(Optional.of(accommodationEntity));

        when(accommodationRepository.save(accommodationEntity)).thenReturn(accommodationEntity);

        when(accommodationConverter.responseToDTO(accommodationEntity)).thenReturn(dtoFalso);

        ResponseAccommodationDTO resultado = accommodationServiceImpl.modifiedAccommodation(dto, accommodationUuid, ownerUuid);

        assertNotNull(resultado);

        assertEquals(2, dto.getMinStay());
        assertEquals(5, dto.getMaxStay());

        verify(accommodationRepository, times(2)).save(accommodationEntity);
    }



}

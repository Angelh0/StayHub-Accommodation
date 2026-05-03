package com.Angelh0.stayhub.dto.DraftAccommodation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AccommodationDraftDTO {

    private UUID uuidDraft;
    private boolean basicCreate;
    private boolean StayCustomer;
    private boolean addRooms;
    private boolean availabilityCalendar;
    private boolean addPhotos;
    private boolean publish;
}

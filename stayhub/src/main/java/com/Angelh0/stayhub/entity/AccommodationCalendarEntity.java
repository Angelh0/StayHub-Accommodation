package com.Angelh0.stayhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ACCOMMODATION_CALENDAR")
@Getter
@Setter
public class AccommodationCalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "accommodation_id")
    private AccommodationEntity accommodation;

    private UUID uuidCalendar;

    @ElementCollection
    @CollectionTable(name = "CALENDAR_MONTHS", joinColumns = @JoinColumn(name = "calendar_id"))
    @Column(name = "month_value")
    private List<Integer> calendarMonth;

    @PrePersist
    public void generatedValue() {
        if (uuidCalendar == null) {
            uuidCalendar = UUID.randomUUID();
        }

        if (calendarMonth == null) {
            calendarMonth = new ArrayList<>();
        }
    }
}
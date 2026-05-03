package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.BlockType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateBlockDTO {

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate blockStartDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endStartDate;

    private String reason;
    private BlockType blockType;
}

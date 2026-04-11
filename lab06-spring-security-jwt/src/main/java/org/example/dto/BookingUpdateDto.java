package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter@Getter
public class BookingUpdateDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date arrivaldate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date stayingdate;

}
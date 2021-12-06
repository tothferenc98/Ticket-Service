package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.util.List;

public interface ScreeningService {
    List<ScreeningDto> listAll();

    String createScreening(String movieTitle, String roomName, String dateTime);

    void deleteScreening(String movieTitle, String roomName, String dateTime);


}

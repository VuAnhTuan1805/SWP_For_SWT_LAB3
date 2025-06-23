package com.swpteam.smokingcessation.service.impl.booking;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

@Service
public class GoogleCalendarService {

    public String createGoogleMeetEvent(String accessToken, String startDateTime, String endDateTime) throws GeneralSecurityException, IOException {
        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> request.getHeaders().setAuthorization("Bearer " + accessToken)
        ).setApplicationName("Your App Name").build();

        Event event = new Event()
                .setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDateTime)))
                .setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDateTime)))
                .setConferenceData(new ConferenceData()
                        .setCreateRequest(new CreateConferenceRequest()
                                .setRequestId(UUID.randomUUID().toString())
                                .setConferenceSolutionKey(new ConferenceSolutionKey().setType("hangoutsMeet"))
                        )
                );

        Event createdEvent = service.events().insert("primary", event)
                .setConferenceDataVersion(1)
                .execute();

        if (createdEvent.getConferenceData() != null && createdEvent.getConferenceData().getEntryPoints() != null) {
            return createdEvent.getConferenceData().getEntryPoints().stream()
                    .filter(e -> "video".equals(e.getEntryPointType()))
                    .findFirst()
                    .map(EntryPoint::getUri)
                    .orElse(null);
        }
        return null;
    }
}
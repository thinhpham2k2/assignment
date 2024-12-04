package com.assignment.core_service.service;

import com.assignment.core_service.service.interfaces.InternationalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InternationalizationServiceImpl implements InternationalizationService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String messageCode, Locale locale) {

        return messageSource.getMessage(messageCode, null, locale);
    }
}

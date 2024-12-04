package com.assignment.core_service.service.interfaces;

import java.util.Locale;

public interface InternationalizationService {

    String getMessage(String messageCode, Locale locale);
}

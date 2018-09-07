package com.futengwl.activeMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jms.core.JmsTemplate;

import java.util.Locale;

public abstract class BaseProcess {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JmsTemplate jmsTemplate;

    @Autowired
    private MessageSource messageSource;

    protected String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE);
    }
}

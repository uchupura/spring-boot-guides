package com.guide.log.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomFilter extends Filter<ILoggingEvent> {
    private List<Level> levelList = new ArrayList<>();

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (levelList.contains(event.getLevel())) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }

    }
}

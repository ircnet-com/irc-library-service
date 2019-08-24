package com.ircnet.service.library.events;

import com.ircnet.common.library.event.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ServSetEventListener extends AbstractEventListener<ServSetEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServSetEventListener.class);

    protected void onEvent(ServSetEvent event) {
        LOGGER.trace("Accepted SERVSET: {}", "0x" + Integer.toHexString(event.getAcceptedSettings()));

        if(event.getIRCService().getServiceConfigurationModel().getBurstFlags() != 0) {
            LOGGER.trace("Expecting burst now");
            event.getIRCConnection().setBurst(true);
            event.getIRCConnection().setBurstStart(new Date());
        }
    }
}

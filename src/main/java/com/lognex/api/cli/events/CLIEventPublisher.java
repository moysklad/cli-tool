package com.lognex.api.cli.events;

import com.lognex.api.cli.commands.Layer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CLIEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public CLIEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendLogInEvent(ConnectionDetails connectionDetails) {
        ConnectionEvent event = new ConnectionEvent(this, connectionDetails);
        publisher.publishEvent(event);
    }

    public void sendLayerSelected(Layer layer) {
        SelectLayerEvent event = new SelectLayerEvent(this, layer);
        publisher.publishEvent(event);
    }
}

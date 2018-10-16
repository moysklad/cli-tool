package com.lognex.api.cli.events;

import org.springframework.context.ApplicationEvent;

public class ConnectionEvent extends ApplicationEvent {
    private ConnectionDetails connectionDetails;

    public ConnectionEvent(Object source, ConnectionDetails connectionDetails) {
        super(source);
        this.connectionDetails = connectionDetails;
    }

    public ConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }
}

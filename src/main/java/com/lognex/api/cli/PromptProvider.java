package com.lognex.api.cli;

import com.lognex.api.cli.commands.Layer;
import com.lognex.api.cli.events.ConnectionDetails;
import com.lognex.api.cli.events.ConnectionEvent;
import com.lognex.api.cli.events.SelectLayerEvent;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PromptProvider implements org.springframework.shell.jline.PromptProvider {

    private ConnectionDetails details;
    private Layer currentLayer;

    @Override
    public AttributedString getPrompt() {
        StringBuilder builder = new StringBuilder();
        if(details != null) {
            builder.append(details.prompt());
            if(currentLayer != null) {
                builder.append("#").append(currentLayer.name().toLowerCase());
            }
        }
        builder.append(":> ");
        return new AttributedString(builder.toString(),
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

    @EventListener
    public void handle(ConnectionEvent event) {
        this.details = event.getConnectionDetails();
    }

    @EventListener
    public void handle(SelectLayerEvent event) {
        this.currentLayer = event.getLayer();
    }
}

package com.lognex.api.cli.events;

import com.lognex.api.cli.commands.Layer;
import org.springframework.context.ApplicationEvent;

public class SelectLayerEvent extends ApplicationEvent {
    private Layer layer;

    public SelectLayerEvent(Object source, Layer layer) {
        super(source);
        this.layer = layer;
    }

    public Layer getLayer() {
        return layer;
    }
}

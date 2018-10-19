package com.lognex.api.cli.commands;

import com.lognex.api.LognexApi;
import com.lognex.api.cli.events.CLIEventPublisher;
import com.lognex.api.cli.events.ConnectionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class EntryPoint implements Quit.Command {

    private final CLIEventPublisher publisher;
    private boolean isConnected = false;
    private boolean layerSelected = false;

    @Autowired
    public EntryPoint(CLIEventPublisher publisher) {
        this.publisher = publisher;
    }

    @ShellMethod("Connect to the moysklad")
    public void connect(String host, String username, String password) {
        LognexApi api = new LognexApi(host, false, username, password);
        isConnected = true;
        ConnectionDetails connectionDetails = new ConnectionDetails(api, host);
        publisher.sendLogInEvent(connectionDetails);
    }

    @ShellMethod("Logout")
    public void logout() {
        isConnected = false;
        layerSelected = false;
        publisher.sendLayerSelected(null);
        publisher.sendLogInEvent(null);
    }

    @ShellMethod("Select the layer")
    public void layer(String layer) {
        Layer _layer = Layer.silentValueOf(layer);
        if(_layer != null) {
            layerSelected = true;
            publisher.sendLayerSelected(_layer);
        } else {
            throw new IllegalArgumentException("illegal layer name");
        }
    }

    @ShellMethod("Show available layers")
    public void list() {
        System.out.println("Layers:");
        for(Layer layer : Layer.values()) {
            System.out.println("* " + layer.name().toLowerCase() + " - " + layer.getDescription());
        }
    }

    @ShellMethod("Exit from the current layer")
    public void exit() {
        if(layerSelected) {
            layerSelected = false;
            publisher.sendLayerSelected(null);
        } else {
            throw new ExitRequest();
        }
    }

    @ShellMethodAvailability({"layer", "logout"})
    public Availability needConnectCheck() {
        return isConnected
                ? Availability.available()
                : Availability.unavailable("you need to connect");
    }

}

package com.lognex.api.cli.commands.assortment;

import com.lognex.api.cli.commands.Layer;
import com.lognex.api.cli.events.SelectLayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup("Assortment Layer")
public class AssortmentLayer {

    private boolean layerIsActive = false;
    private final AssortmentService assortmentService;

    @Autowired
    public AssortmentLayer(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }


    @ShellMethod("delete all assortment")
    public void delete() {
        assortmentService.deleteAssortment();
    }

    @ShellMethod("archive all assortment")
    public void archive() {
        assortmentService.archiveAssortment();
    }

    @ShellMethod("unarchive all assortment")
    public void unarchive() {
        assortmentService.unarchiveAssortment();
    }

    @ShellMethodAvailability
    public Availability availabilityCheck() {
        return layerIsActive
                ? Availability.available()
                : Availability.unavailable("you need to select the assortment layer");
    }


    @EventListener
    public void selectLayerHandle(SelectLayerEvent event) {
        layerIsActive = event.getLayer() == Layer.ASSORTMENT;
    }

}

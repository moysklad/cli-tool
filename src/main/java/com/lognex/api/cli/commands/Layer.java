package com.lognex.api.cli.commands;

public enum Layer {
    ASSORTMENT("mass operation over all types products");

    private String description;
    Layer(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    static public Layer silentValueOf(String name) {
       Layer[] layers = Layer.values();
       for (Layer layer : layers)
           if (layer.name().equals(name.toUpperCase()))
               return Layer.valueOf(name.toUpperCase());
       return null;
   }
}

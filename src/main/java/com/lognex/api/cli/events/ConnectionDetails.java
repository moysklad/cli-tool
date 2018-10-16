package com.lognex.api.cli.events;

import com.lognex.api.LognexApi;

public class ConnectionDetails {
    private final LognexApi api;
    private final String host;

    public ConnectionDetails(LognexApi api, String host) {
        this.api = api;
        this.host = host;
    }

    public LognexApi getApi() {
        return api;
    }

    public String prompt() {
        return host + "@" + api.getLogin();
    }
}

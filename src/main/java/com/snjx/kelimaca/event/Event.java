package com.snjx.kelimaca.event;

import lombok.Getter;

@Getter
public enum Event {
    LOGIN_EVENT("loginEvent"),
    SEND_EVENT("sendEvent")
    ;
    private final String event;

    Event(String event) {
        this.event = event;
    }

}

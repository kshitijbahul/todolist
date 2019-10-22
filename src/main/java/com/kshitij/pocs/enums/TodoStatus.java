package com.kshitij.pocs.enums;

public enum TodoStatus {
    DONE("DONE"),NOTDONE("NOTDONE");

    String value;
    TodoStatus(String message) {
        this.value=message;
    }
}

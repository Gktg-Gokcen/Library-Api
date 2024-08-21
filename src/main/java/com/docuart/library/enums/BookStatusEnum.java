package com.docuart.library.enums;

public enum BookStatusEnum {
    AKTIF("ACTIVE"),
    PASIF("PASIVE");

    private String value;

    BookStatusEnum(String value) {
        this.value = value;
    }
}

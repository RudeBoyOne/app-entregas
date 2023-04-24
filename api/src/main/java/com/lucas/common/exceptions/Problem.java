package com.lucas.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class Problem {

    private Integer status;
    private OffsetDateTime dateTime;
    private String title;
    private List<Field> fields;

    @AllArgsConstructor
    @Getter
    public static class Field {

        private String name;
        private String message;

    }
}

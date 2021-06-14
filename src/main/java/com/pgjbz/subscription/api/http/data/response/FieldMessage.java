package com.pgjbz.subscription.api.http.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldMessage {

    private String field;
    private String message;

}

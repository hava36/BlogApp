package com.skillbox.blogapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponse implements Serializable {

    private Boolean result;

    @JsonInclude(Include.NON_EMPTY)
    private Map<String, String> errors;

    public RegistrationResponse(boolean result, Map<String, String> errors) {
        this.result = result;
        this.errors = errors;
    }

}

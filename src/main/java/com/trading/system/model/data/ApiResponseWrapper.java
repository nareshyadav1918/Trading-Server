package com.trading.system.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.HashMap;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseWrapper {
    private static final long serialVersionUID = 1L;

    @JsonProperty("data")
    private Object response = new HashMap<String ,String>();

    @JsonProperty("error")
    private Errors errorCode = new Errors();


}

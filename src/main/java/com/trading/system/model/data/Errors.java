package com.trading.system.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Errors {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     * Type Name For Example Host , HostComponent refer the RequestErrors class
     * for all Message type
     */

    @JsonProperty("has_errors")
    private boolean hasError = false;

    @JsonProperty("error_definitions")
    private ArrayList<UiMessage> errorDefinitions;

    /**
     * Ui Messages.
     */
    private UiMessage UiMessage;
}

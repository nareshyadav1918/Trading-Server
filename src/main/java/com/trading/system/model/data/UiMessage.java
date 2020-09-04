package com.trading.system.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UiMessage {
    /**
     * error code Which will understand by Ui.
     */
    @JsonProperty("code")
    int code = 0;

    /**
     * error code  Which will understand by Ui.
     */
    @JsonProperty("message")
    String message = null;
}

package com.jiinfotech.restomanager.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AjaxResponse {

    @JsonProperty
    public String parameters;

    @JsonProperty
    public String refresh;

    @JsonProperty
    public String msg;

    @JsonProperty
    public String msgType = "info";

}

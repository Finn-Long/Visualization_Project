package com.visualization.logserver.entity;

public class Content {
    private String behavior;
    private String result;

    private String error;

    public Content(String behavior, String result) {
        this.behavior = behavior;
        this.result = result;
    }

    public Content(String behavior, String result, String error) {
        this.behavior = behavior;
        this.result = result;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Content(){}

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

package controllers;

import play.mvc.Result;

import static play.mvc.Results.status;

public class HealthcheckController {

    public Result healthcheck() {
        return status(200);
    }
}
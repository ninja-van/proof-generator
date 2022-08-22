package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Proof;
import models.ProofOrders;
import models.ProofSignature;
import models.ProofSignatureRequest;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;
import utils.PdfGeneratorUtil;

import java.io.IOException;

import static play.mvc.Controller.request;
import static play.mvc.Http.Context.Implicit.response;
import static play.mvc.Results.ok;

public class ProofController {

    public Result getProof() {
        String json = request().body().asJson().toString();
        ObjectMapper objectMapper = new ObjectMapper();

        Proof request;
        try {
            request = objectMapper.readerFor(Proof.class).readValue(json);
        } catch (IOException e) {
            return Results.status(500);
        }

        byte[] pdf = PdfGeneratorUtil.generateDeliveryReportPdf(request);

        response().setHeader("Content-Disposition", "inline; filename=proof-receipt.pdf");
        return ok(pdf).as("application/pdf");
    }
}

package models;

import lombok.Data;

@Data
public class ProofSignature {

    private String trackingId;

    private String sentFrom;

    private String sentFromEmail;

    private String consigneeName;

    private String signatureUrl;

    private String proofDate;

    private String status;

    private String deliveryStart;

    private String deliveryEnd;

    private String routeId;

    private String driverName;

    private String verificationMethod;
}

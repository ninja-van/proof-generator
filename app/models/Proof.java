package models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Proof {

    private String companyName;

    private String country;

    private String deliveredOn;

    private String deliveredBy;

    private String destinationLat;

    private String destinationLng;

    private String numberOfParcels;

    private String amountPaid;

    private String deliveredTo;

    private String deliveredWhere;

    private String placedAt;

    private String driverComments;

    private String imageUrl;

    private String recievedBy;

    private List<ProofOrders> orders = new ArrayList<>();

}

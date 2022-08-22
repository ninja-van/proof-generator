# Proof of Delivery (POD) Generator

## Overview

This service generates a Proof based on request parameters passed in via a REST API call.

## Prerequisites

* Java JDK 8.x

## Getting started

### Step 1.
Clone this repository, and run with the following
```bash
sbt clean update compile run
```

## Usage examples

### Proof Signatures Report
Generate a proof signatures report with the following. This assumes that you are running the service locally.
```bash
curl --location --request POST 'localhost:9000/1.0/generate-proof-sigs' \
--header 'Content-Type: application/json' \
--data-raw '{
    "proofsSignatures": [
        {
            "trackingId": "A0000001",
            "sentFrom": "Shipper A",
            "sentFromEmail": "NA",
            "consigneeName": "Consignee A",
            "signatureUrl": "",
            "proofDate": "2020-01-01 10:00:00+0800",
            "status": "Completed",
            "deliveryStart": "2020-01-01 08:00:00+0800",
            "deliveryEnd": "2020-01-01 22:00:00+0800",
            "routeId": "1",
            "driverName": "Driver A",
            "verificationMethod": "Signature"
        },
        {
            "trackingId": "A0000002",
            "sentFrom": "Shipper A",
            "sentFromEmail": "NA",
            "consigneeName": "Consignee A",
            "signatureUrl": "",
            "proofDate": "2020-01-01 10:00:00+0800",
            "status": "Completed",
            "deliveryStart": "2020-01-01 08:00:00+0800",
            "deliveryEnd": "2020-01-01 22:00:00+0800",
            "routeId": "1",
            "driverName": "Driver A",
            "verificationMethod": "Doorstep"
        }
    ]
}'
```

### Proof Receipt
Generate a proof receipt with the following. This assumes that you are running the service locally.
```bash
curl --location --request POST 'localhost:9000/1.0/generate-proof' \
--header 'Content-Type: application/json' \
--data-raw '{
    "companyName": "Logistics Company A",
    "country": "Singapore",
    "deliveredOn": "2020-01-01",
    "deliveredBy": "Driver A",
    "destinationLat": "51.47816567760063",
    "destinationLng": "-0.0014137569991960902",
    "numberOfParcels": "2",
    "amountPaid": "0.00",
    "deliveredTo": "Consignee A",
    "deliveredWhere": "Consignee A'\''s Address",
    "placedAt": "Doorstep",
    "driverComments": "Left at doorstep",
    "imageUrl": "",
    "recievedBy": "NA",
    "orders": [
        {
            "shipperName": "Shipper A",
            "trackingId": "A0000001"
        },
        {
            "shipperName": "Shipper B",
            "trackingId": "B0000001"
        }
    ]
}'
```
## License
This source codes are published by Ninja Logistics Pte. Ltd. on open source licence terms pursuant to MIT License, and the codes incorporates `com.itextpdf.itextpdf 5.5.6`, `com.itextpdf.tool.xmlworker 5.5.11` GNU AGPL V.3"

## Contributing

Pull requests are welcome. For major changes, please start a conversation first to discuss what you would like to
change.

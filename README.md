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
These source codes (Airway Bill (AWB) Generator) incorporates source codes from  iText (com.itextpdf.itextpdf
5.5.6, com.itextpdf.tool.xmlworker 5.5.11) and are published under GNU Affero General Public License version 3.
Copyright (C) <2022> <Ninja Logistics Pte. Ltd.>
Copyright (C) iText® 5.5.13 ©2000-2018 iText Group NV (AGPL-version) for abovementioned source
codes from iText

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see https://www.gnu.org/licenses/.

## Contributing

Pull requests are welcome. For major changes, please start a conversation first to discuss what you would like to
change.

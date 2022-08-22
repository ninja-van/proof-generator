package models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProofSignatureRequest {

    private List<ProofSignature> proofsSignatures = new ArrayList<>();
}

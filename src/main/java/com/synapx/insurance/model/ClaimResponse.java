package com.synapx.insurance.model;

import java.util.List;

public class ClaimResponse {

    public ClaimData extractedFields;
    public List<String> missingFields;
    public String recommendedRoute;
    public String reasoning;
}

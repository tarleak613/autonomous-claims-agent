package com.synapx.insurance.router;

import com.synapx.insurance.model.ClaimData;
import java.util.*;

public class ClaimRouter {

    public String route(ClaimData data, List<String> missingFields) {

        if (!missingFields.isEmpty()) {
            return "Manual Review";
        }

        if (containsFraudKeywords(data.description)) {
            return "Investigation Flag";
        }

        if ("injury".equalsIgnoreCase(data.claimType)) {
            return "Specialist Queue";
        }

        if (data.estimatedDamage != null && data.estimatedDamage < 25000) {
            return "Fast-track";
        }

        return "Standard Processing";
    }

    private boolean containsFraudKeywords(String text) {
        if (text == null) return false;
        String t = text.toLowerCase();

        if (t.contains("no fraud")) return false;

        return t.contains("fraud")
                || t.contains("staged")
                || t.contains("inconsistent");
    }

}

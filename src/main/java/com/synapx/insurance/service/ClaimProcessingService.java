package com.synapx.insurance.service;

import com.synapx.insurance.extractor.FnolExtractor;
import com.synapx.insurance.model.*;
import com.synapx.insurance.router.ClaimRouter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClaimProcessingService {

    private final FnolExtractor extractor = new FnolExtractor();
    private final ClaimRouter router = new ClaimRouter();

    public ClaimResponse process(String text) {

        ClaimData data = extractor.extract(text);

        // 1. Identify missing mandatory fields
        List<String> missing = new ArrayList<>();
        if (data.policyNumber == null) missing.add("policyNumber");
        if (data.claimType == null) missing.add("claimType");
        if (data.description == null) missing.add("description");
        if (data.estimatedDamage == null) missing.add("estimatedDamage");

        // 2. Calculate extraction confidence
        int extractedCount = 0;
        if (data.policyNumber != null) extractedCount++;
        if (data.claimType != null) extractedCount++;
        if (data.description != null) extractedCount++;
        if (data.estimatedDamage != null) extractedCount++;

        boolean lowConfidence = extractedCount < 2;

        // 3. Route the claim
        String route = router.route(data, missing);

        // 4. Build response
        ClaimResponse response = new ClaimResponse();
        response.extractedFields = data;
        response.missingFields = missing;
        response.recommendedRoute = route;

        String reason = buildReason(route, missing, lowConfidence);
        response.reasoning = reason;

        return response;
    }

    private String buildReason(String route, List<String> missing, boolean lowConfidence) {

        String baseReason = switch (route) {
            case "Manual Review" ->
                    missing.isEmpty()
                            ? "Insufficient structured data extracted"
                            : "Mandatory fields missing: " + missing;

            case "Investigation Flag" ->
                    "Description contains potential fraud indicators";

            case "Specialist Queue" ->
                    "Claim type is injury and requires specialist handling";

            case "Fast-track" ->
                    "Estimated damage below 25,000 and all mandatory fields are present";

            default ->
                    "Claim does not meet fast-track or special criteria";
        };

        if (lowConfidence) {
            baseReason += " (Low extraction confidence)";
        }

        return baseReason;
    }
}

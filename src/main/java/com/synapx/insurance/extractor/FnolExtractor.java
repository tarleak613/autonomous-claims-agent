package com.synapx.insurance.extractor;

import com.synapx.insurance.model.ClaimData;
import java.util.regex.*;

public class FnolExtractor {

    public ClaimData extract(String text) {

        ClaimData data = new ClaimData();

        data.policyNumber = clean(extract(text, "POLICY NUMBER"));
        data.policyHolderName = clean(extract(text, "NAME OF INSURED"));
        data.incidentDate = clean(extract(text, "DATE OF LOSS"));

        String desc =
                extract(text, "DESCRIPTION OF ACCIDENT") != null
                        ? extract(text, "DESCRIPTION OF ACCIDENT")
                        : extract(text, "DESCRIPTION");
        data.description = clean(desc);

        data.claimType = clean(extract(text, "CLAIM TYPE"));

        String damage = extract(text, "ESTIMATE AMOUNT");
        if (damage != null) {
            String digits = damage.replaceAll("[^0-9]", "");
            try {
                if (!digits.isEmpty()) {
                    data.estimatedDamage = Integer.parseInt(digits);
                }
            } catch (NumberFormatException ignored) {}
        }

        return data;
    }

    private String extract(String text, String field) {
        Pattern p = Pattern.compile(
                field + "[:\\s]+([\\s\\S]{3,})",
                Pattern.CASE_INSENSITIVE
        );
        Matcher m = p.matcher(text);

        while (m.find()) {
            String value = m.group(1).trim();

            // stop at next field-like line
            int newlineIndex = value.indexOf("\n");
            if (newlineIndex != -1) {
                value = value.substring(0, newlineIndex).trim();
            }

            if (!looksLikeLabel(value)) {
                return value;
            }
        }
        return null;
    }


    private String clean(String value) {
        if (value == null) return null;
        String v = value.trim();
        if (looksLikeLabel(v)) return null;
        if (looksLikeHelperText(v)) return null;
        if (looksLikeNameInstruction(v)) return null;
        return v;
    }

    private boolean looksLikeLabel(String value) {
        String v = value.trim();
        // ends with colon → label
        if (v.endsWith(":")) return true;

        // contains no digits and is mostly letters/spaces → label
        return v.matches("[A-Z\\s]+");
    }

    private boolean looksLikeHelperText(String value) {
        return value.startsWith("(") && value.endsWith(")");
    }

    private boolean looksLikeNameInstruction(String value) {
        String v = value.toLowerCase();
        return v.contains("first, middle")
                || v.contains("last")
                || v.contains("mailing address");
    }
}
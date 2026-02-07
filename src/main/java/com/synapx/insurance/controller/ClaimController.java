package com.synapx.insurance.controller;

import com.synapx.insurance.extractor.PdfTextExtractor;
import com.synapx.insurance.model.ClaimResponse;
import com.synapx.insurance.service.ClaimProcessingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimProcessingService service;

    public ClaimController(ClaimProcessingService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ClaimResponse uploadFnol(@RequestParam("file") MultipartFile file)
            throws Exception {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String filename = file.getOriginalFilename().toLowerCase();
        if (!filename.endsWith(".txt") && !filename.endsWith(".pdf")) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        String text;
        if (filename.endsWith(".pdf")) {
            text = PdfTextExtractor.extractText(file.getInputStream());
        } else {
            text = new String(file.getBytes());
        }
        System.out.println("========== FNOL INPUT START ==========");
        System.out.println(text);
        System.out.println("=========== FNOL INPUT END ===========");

        return service.process(text);
    }
}

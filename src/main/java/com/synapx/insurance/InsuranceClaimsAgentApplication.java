package com.synapx.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InsuranceClaimsAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceClaimsAgentApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return """
        <html>
            <head>
                <title>Insurance Claims Agent</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6;">
                <h3>Hi Synapx Team 👋</h3>

                <p>Quick guide to test the FNOL processing application:</p>

                <ol>
                    <li>
                        <b>Browser:</b><br/>
                        Open <a href="/index.html">http://localhost:8080/index.html</a><br/>
                        Upload an FNOL file (<code>.txt</code> or text-based <code>.pdf</code>) and submit.
                    </li>
                    <li>
                        <b>API (Postman / Curl):</b><br/>
                        POST <code>/claims/upload</code><br/>
                        Form-data key: <code>file</code> (upload FNOL document)
                    </li>
                </ol>

                <p><i>Clarity &gt; Complexity</i></p>
            </body>
        </html>
        """;
	}

}

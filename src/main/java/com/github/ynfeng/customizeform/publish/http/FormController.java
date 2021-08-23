package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.service.CreateFormRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@RestController
public class FormController {

    @PostMapping("/v1/forms")
    public ResponseEntity<Void> createForm(@RequestBody CreateFormRequest request) {
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(
                FormController.class, "getForm", "1")
            .buildAndExpand();

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping("/v1/forms/{formId}")
    public ResponseEntity<Void> getForm(@PathVariable String formId) {
        return ResponseEntity.ok().build();
    }
}

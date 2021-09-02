package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<ApiRepresent> apis() {
        Link formDefinitionListLink = linkTo(methodOn(FormDefinitionController.class).all())
            .withRel("form-definition-list");

        Link createFormDefinition = linkTo(methodOn(FormDefinitionController.class).createFormDefinition(null))
            .withRel("form-definition-create");

        ApiRepresent apiRepresent = new ApiRepresent();
        apiRepresent.add(formDefinitionListLink);
        apiRepresent.add(createFormDefinition);

        return ResponseEntity.ok(apiRepresent);
    }
}

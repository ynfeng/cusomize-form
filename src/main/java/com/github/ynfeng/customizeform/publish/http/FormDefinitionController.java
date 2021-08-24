package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName;

import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.service.CreateFormDefinitionRequest;
import com.github.ynfeng.customizeform.service.CreteFormDefinitionService;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

@RestController
public class FormDefinitionController {
    private final CreteFormDefinitionService formService;
    private final FormDefinitionRepository fromRepository;

    public FormDefinitionController(CreteFormDefinitionService formService, FormDefinitionRepository fromRepository) {
        this.formService = formService;
        this.fromRepository = fromRepository;
    }

    @PostMapping("/v1/form-definitions")
    public ResponseEntity<Void> createFormDefinition(@RequestBody CreateFormDefinitionRequest request) {
        String formId = formService.create(request);

        UriComponents uriComponents =
            fromMethodName(FormDefinitionController.class, "getFormDefinition", formId)
                .buildAndExpand();

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @GetMapping("/v1/form-definitions/{formId}")
    public ResponseEntity<FormDefinitionRepresent> getFormDefinition(@PathVariable String formId) {
        return fromRepository.find(formId)
            .map(formDefinition -> ResponseEntity.ok(FormDefinitionRepresent.fromDomain(formDefinition)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/v1/form-definitions/{formId}/form-definition-items")
    public ResponseEntity<CollectionModel<FormDefinitionItemRepresent>> getFormDefinitionItems(@PathVariable String formId) {
        return fromRepository.find(formId)
            .map(formDefinition -> {
                List<FormDefinitionItemRepresent> result = FormDefinitionItemRepresent.fromDomain(formDefinition);

                Link selfLink = linkTo(methodOn(FormDefinitionController.class)
                    .getFormDefinitionItems(formDefinition.formId()))
                    .withSelfRel();
                Link formLink = linkTo(methodOn(FormDefinitionController.class)
                    .getFormDefinition(formDefinition.formId()))
                    .withRel("form-definition");

                return ResponseEntity.ok(CollectionModel.of(result, selfLink, formLink));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

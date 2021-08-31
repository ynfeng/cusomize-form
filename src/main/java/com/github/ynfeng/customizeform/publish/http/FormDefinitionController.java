package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.service.CreateFormDefinitionRequest;
import com.github.ynfeng.customizeform.service.CreateFormDefinitionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

@RestController
public class FormDefinitionController {
    private final CreateFormDefinitionService formService;
    private final FormDefinitionRepository fromRepository;

    public FormDefinitionController(CreateFormDefinitionService formService, FormDefinitionRepository fromRepository) {
        this.formService = formService;
        this.fromRepository = fromRepository;
    }

    @GetMapping("/v1/form-definitions")
    public ResponseEntity<CollectionModel<FormDefinitionRepresent>> all() {
        List<FormDefinitionRepresent> result = fromRepository.all()
            .stream()
            .map(FormDefinitionRepresent::fromDomain).collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(FormDefinitionController.class)
            .all()).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(result, selfLink));
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


    @GetMapping("/v1/form-definitions/{formId}/form-definition-items/{itemName}/data")
    public ResponseEntity<FormDefinitionItemDataRepresent> getFormDefinitionItemData(@PathVariable String formId,
                                                                                     @PathVariable String itemName,
                                                                                     @RequestParam(value = "q",defaultValue = "") String q) {
        Optional<FormDefinition> formCandidate = fromRepository.find(formId);
        if (!formCandidate.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        FormDefinition formDefinition = formCandidate.get();
        Optional<Component> formItemCandidate = formDefinition.items().stream().filter(it -> it.name().equals(itemName)).findAny();
        if (!formItemCandidate.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Component formItem = formItemCandidate.get();
        FormDefinitionItemDataRepresent result = FormDefinitionItemDataRepresent.fromDomain(formId, formItem);

        return ResponseEntity.ok(result);
    }
}

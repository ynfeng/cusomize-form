package com.github.ynfeng.customizeform.publish.http;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class TemplateLink {

    public static Link create(LinkBuilder linkBuilder, String rel, String... templateVariables) {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(linkBuilder.toUri()).build();
        UriTemplate template = UriTemplate.of(uriComponents.toUriString());

        for (String templateVariable : templateVariables) {
            template = template.with(templateVariable, TemplateVariable.VariableType.REQUEST_PARAM);
        }

        return Link.of(template, rel);
    }
}

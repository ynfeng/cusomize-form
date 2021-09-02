package com.github.ynfeng.customizeform.publish.http;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ApiRepresent extends RepresentationModel<ApiRepresent> {
    private final String name = "自定义表单API";
}

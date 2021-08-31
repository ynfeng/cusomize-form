package com.github.ynfeng.customizeform.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateFormDefinitionRequest {
    private String name;
    @Builder.Default
    private List<FormItem> items = Lists.newArrayList();

    @Builder
    @Getter
    public static class FormItem {
        private String name;
        private String screenName;
        private String type;

        @Builder.Default
        private Map<String, Object> params = Maps.newHashMap();
    }
}

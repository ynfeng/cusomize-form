package com.github.ynfeng.customizeform.customizeform.service;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateFormRequest {
    private String name;
    @Builder.Default
    private List<FormItem> items = Lists.newArrayList();

    @Builder
    @Getter
    static class FormItem {
        private String name;
        private String screenName;
        private String type;
    }
}

package com.github.ynfeng.customizeform.publish.http.datalinker;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemRepresent;

public interface DataLinker {
    void makeLinks(String formId, Component formItem, FormDefinitionItemRepresent formItemRepresent);
}

package com.github.ynfeng.customizeform.impl.po;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "form_definition")
@Data
public class FormDefinitionPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_id")
    private String formId;

    @Column(name = "form_name")
    private String name;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public static FormDefinitionPo fromDomain(FormDefinition formDefinition) {
        FormDefinitionPo result = new FormDefinitionPo();
        result.setFormId(formDefinition.formId());
        result.setName(formDefinition.name());
        result.setCreateTime(LocalDateTime.now());
        result.setUpdateTime(LocalDateTime.now());
        return result;
    }
}

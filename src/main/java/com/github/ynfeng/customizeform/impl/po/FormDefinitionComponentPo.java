package com.github.ynfeng.customizeform.impl.po;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "form_definition_component")
@Data
public class FormDefinitionComponentPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_id")
    private String formId;

    @Column(name = "name")
    private String name;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "ds_name")
    private String dsName = "";

    @Column(name = "extra_data")
    private String extraData = "";

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "component_type")
    private String type;
}

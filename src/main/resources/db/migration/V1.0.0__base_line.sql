create table form_definition
(
    id          bigint auto_increment
        primary key,
    form_id     varchar(32)                        not null comment '表单id',
    form_name   varchar(100)                       not null comment '表单名称',
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime                           not null on update CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table form_definition_component
(
    id             bigint auto_increment
        primary key,
    name           varchar(32)                        not null comment '名称',
    screen_name    varchar(100)                       not null comment '显示名称',
    component_type varchar(100)                       not null comment '组件类型',
    ds_name        varchar(100)                       not null comment '数据源名称',
    extra_data     text                               not null comment '扩展数据',
    form_id        varchar(64)                        not null comment '表单id',
    create_time    datetime default CURRENT_TIMESTAMP not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

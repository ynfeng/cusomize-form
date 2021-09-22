create table form_definition
(
    id          bigint auto_increment
        primary key,
    form_id     varchar(32)                        not null comment '表单id',
    form_name   varchar(100)                       not null comment '表单名称',
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime                           not null on update CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

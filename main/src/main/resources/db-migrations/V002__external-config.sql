create table if not exists "external_config_parameter"
(
    id           varchar(255) not null,
    config_key   varchar(255) not null,
    config_value varchar(255),
    created_on   varchar(255),
    updated_on   timestamp(6),
    primary key (id)
);

alter table if exists "external_config_parameter"
    drop constraint if exists UK_l8y7p0wiubd4k85ljii4qv56d;
alter table if exists "external_config_parameter"
    add constraint UK_l8y7p0wiubd4k85ljii4qv56d unique (config_key);
create sequence if not exists "external_config_parameter_SEQ" start with 1 increment by 50;

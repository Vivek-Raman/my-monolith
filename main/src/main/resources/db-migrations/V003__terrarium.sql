create table if not exists terrarium_app_user
(
    user_id   uuid not null,
    password  varchar(255),
    score     integer,
    user_name varchar(255),
    primary key (user_id)
);
create table if not exists terrarium_app_user_action
(
    action_id   uuid not null,
    action_type smallint check (action_type between 0 and 3),
    is_queued   boolean,
    plant_id    uuid,
    user_id     uuid,
    primary key (action_id)
);
create table if not exists terrarium_app_user_session
(
    session_id uuid not null,
    timestamp  timestamp(6),
    user_id    uuid,
    primary key (session_id)
);
create table if not exists terrarium_plant
(
    plant_id            uuid not null,
    date_of_planting    timestamp(6),
    growth_state        integer,
    posX                float4,
    posY                float4,
    posZ                float4,
    rotX                float4,
    rotY                float4,
    rotZ                float4,
    species_id          integer,
    unwatered_day_count integer,
    user_id             uuid,
    primary key (plant_id)
);

alter table if exists terrarium_app_user_action
    drop constraint if exists UK_7470ga87kjfhe9rkyhj1caj8y;
alter table if exists terrarium_app_user_action
    add constraint UK_7470ga87kjfhe9rkyhj1caj8y unique (plant_id);

alter table if exists terrarium_app_user_session
    drop constraint if exists UK_98mop173x7fd4iuofi4xckh0v;
alter table if exists terrarium_app_user_session
    add constraint UK_98mop173x7fd4iuofi4xckh0v unique (user_id);

alter table if exists terrarium_app_user_action
    add constraint FKb3fy416wkik5t0ogaq2b09o54 foreign key (plant_id) references terrarium_plant;
alter table if exists terrarium_app_user_action
    add constraint FK23sf4plxvhbkvxw4cy3g60w02 foreign key (user_id) references terrarium_app_user;
alter table if exists terrarium_app_user_session
    add constraint FK8o2ar2csgqxgc11ybop1giaf5 foreign key (user_id) references terrarium_app_user;
alter table if exists terrarium_plant
    add constraint FKer3yea9hkraangwltp7hhwv1i foreign key (user_id) references terrarium_app_user;

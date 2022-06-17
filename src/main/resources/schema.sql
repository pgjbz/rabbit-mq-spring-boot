create table if not exists status (
    id bigint primary key,
    name varchar(25)
);

insert into status(id, name) values (1, 'ACTIVED') on conflict do nothing;
insert into status(id, name) values (2, 'DESACTIVED') on conflict do nothing;
insert into status(id, name) values (3, 'RESTARTED') on conflict do nothing;

create table if not exists subscription(
    id varchar(255) primary key,
    status_id bigint not null,
    created_at timestamp not null default CURRENT_TIMESTAMP,
    updated_at timestamp,
    constraint fk_status_id foreign key (status_id) references status(id)
);

create table if not exists event_history (
    id bigserial primary key,
    type varchar(70),
    subscription_id varchar(255),
    created_at timestamp not null default CURRENT_TIMESTAMP,
    constraint fk_subscription_id foreign key (subscription_id) references subscription(id)
);

create table if not exists status (id int4 primary key, name varchar(20) not null);

insert into status (id, name) values (1, 'active') on conflict do nothing;
insert into status (id, name) values (2, 'inactive') on conflict do nothing;

commit;

create table if not exists subscriptions (
	id varchar(255) not null,
	created_at timestamp null,
	status_id int4 null,
	updated_at timestamp null,
	constraint pk_subscriptions primary key (id),
	constraint fk_subscription_status foreign key (status_id) references status(id)
);

create table if not exists event_history (
	id bigserial not null,
	created_at timestamp null,
	"type" varchar(255) not null,
	subscription_id varchar(255) not null,
	constraint pk_event_history primary key (id),
	constraint fk_event_history_subscriptions foreign key (subscription_id) references subscriptions(id)
);


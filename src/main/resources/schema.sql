--지하철역 스키마
create table if not exists STATION
(
    id bigint auto_increment not null,
    name varchar(255) not null,
    primary key(id)
);

--지하철 구간 스키마
create table if not exists EDGE
(
    id bigint auto_increment not null,
    line_id bigint not null,
    elapsed_time int not null,
    distance decimal(4,2) not null,
    source_station_id int not null,
    target_station_id int not null,
    primary key(id)
);

--지하철 노선 스키마
create table if not exists LINE
(
    id bigint auto_increment not null,
    name varchar(255) not null unique,
    start_time varchar(255) not null,
    end_time varchar(255) not null,
    interval_time int not null,
    extra_fare int,
    primary key(id)
);
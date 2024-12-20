create table courses (

    id bigint not null auto_increment,
    name varchar(100) not null unique,
    category varchar(100) not null,

    primary key(id)
);

create table users (

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    pass varchar(300) not null unique,

    primary key(id)
);

create table profiles (

    id bigint not null auto_increment,
    name varchar(100) not null unique,
    primary key(id)
);

create table user_profile (
    user_id bigint not null,
    profile_id bigint not null,
    primary key(user_id, profile_id),
    constraint fk_profiles_users foreign key(user_id) references users(id),
    constraint fk_users_profiles foreign key(profile_id) references profiles(id)
);

create table topics (

    id bigint not null auto_increment,
    title varchar(100) not null unique,
    message varchar(250) not null unique,
    creation_date date not null,
    status tinyint not null,
    user_id bigint not null,
    course_id bigint not null,


    primary key(id),
    constraint fk_topics_users foreign key(user_id) references users(id),
    constraint fk_topics_courses foreign key(course_id) references courses(id)
);


create table answers (

    id bigint not null auto_increment,
    message varchar(250) not null,
    topic_id bigint not null,
    creationDate date not null,
    user_id bigint not null,
    solution varchar(250) not null,


    primary key(id),
    constraint fk_answers_topics foreign key(topic_id) references topics(id),
    constraint fk_answers_users foreign key(user_id) references users(id)
);




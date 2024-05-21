create table if not exists employee_roles
(
    id   smallint primary key generated always as identity,
    role varchar(50) not null
);

create table if not exists employees_personal_info
(
    id    int primary key generated always as identity,
    email varchar(50) not null,
    phone varchar(20) not null
);

create table if not exists employees
(
    id               int primary key generated always as identity,
    name             varchar(50) not null,
    surname          varchar(50) not null,
    role_id          smallint    not null references employee_roles (id),
    personal_info_id int         not null unique references employees_personal_info (id) on delete cascade,
    check ( role_id > 0 and personal_info_id > 0)
);

create table if not exists projects
(
    id   int primary key generated always as identity,
    name varchar(50) not null
);

create table if not exists projects_employees
(
    project_id  int not null references projects (id) on delete cascade,
    employee_id int not null references employees (id) on delete cascade,
    check ( project_id > 0 and employee_id > 0),
    primary key (project_id, employee_id)
);
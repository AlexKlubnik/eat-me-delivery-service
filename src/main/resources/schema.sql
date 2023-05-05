create table if not exists public.delivery_address
(
    id bigint not null
        primary key,
    apartment_number integer not null
        constraint delivery_address_apartment_number_check
            check (apartment_number >= 0),
    building_number integer not null
        constraint delivery_address_building_number_check
            check (building_number >= 0),
    city varchar(255),
    house_number integer not null
        constraint delivery_address_house_number_check
            check (house_number >= 0),
    street varchar(255)
);

create table if not exists public.delivery_restaurants
(
    id bigserial
        primary key,
    description varchar(255),
    name varchar(255),
    address_id bigint
--         constraint fk3q62exun25tytyjptyimos8e5
--             references public.delivery_address
);

create table if not exists public.delivery_dishes
(
    id bigserial
        primary key,
    description varchar(500),
    name varchar(255),
    price double precision not null,
    restaurant_id bigint
--         constraint fk33hi379r85a46djuaidfq5k6g
--             references public.delivery_restaurants
);

create table if not exists public.delivery_roles
(
    id bigserial
        primary key,
    name varchar(255)
);

create table if not exists public.delivery_users
(
    id bigserial
        primary key,
    age integer not null
        constraint delivery_users_age_check
            check (age >= 18),
    login varchar(255)
--         constraint uk_lb17eqn8cw4toj00ho9xr3b74
            unique,
    name varchar(255),
    password varchar(255),
    surname varchar(255),
    role_id bigint
--         constraint fk8i8clm96qocsnp69p1uk3tkua
--             references public.delivery_roles
);

create table if not exists public.delivery_orders
(
    id bigint not null
        primary key,
    comment varchar(255),
    created_at timestamp(6),
    status varchar(255),
    total_price double precision not null,
    destination_address_id bigint
--         constraint fkrxtex82yxpny3d8bvxnpwtllb
--             references public.delivery_address,
--     user_id bigint
--         constraint fkkojealeijc7ph8or6xg09gpa4
--             references public.delivery_users
);

create table if not exists public.delivery_order_lines
(
    id bigint not null
        primary key,
    price double precision not null,
    quantity integer not null
        constraint delivery_order_lines_quantity_check
            check (quantity >= 1),
    dish_id bigint
--         constraint fkog8nbc9s0y9jcla4w08lep7lw
--             references public.delivery_dishes,
--     order_id bigint
--         constraint fkel2je0sspnp7ckld38yj67lmu
--             references public.delivery_orders
);

create table if not exists public.restaurant_reviews
(
    restaurant_id bigint not null,
--         constraint fkkwsb7f3r3g4gummc4w4v3mvmm
--             references public.delivery_restaurants,
    reviews varchar(255)
);

create table if not exists public.user_phone_numbers
(
    user_id bigint not null,
--         constraint fk1jw1y2lylvuw8jms9yjtvf8sn
--             references public.delivery_users,
    phone_numbers varchar(255)
);


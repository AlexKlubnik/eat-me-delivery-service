
insert into public.delivery_dishes (id, description, name, price, restaurant_id)
values  (1, 'Big one', 'Pizza', 18.99, 1),
        (2, 'Very tasty', 'Sushi', 15, 1),
        (3, 'spicy', 'Karri', 23.6, 2),
        (4, 'your mouth will be burned', 'Ultra hot Karri', 23.6, 2);

insert into public.delivery_restaurants (id, description, name, address_id)
values  (1, 'family restaurant', 'Dodo', 1),
        (2, 'very spicy dishes', 'India food', 2);

insert into public.delivery_roles (id, name)
values  (2, 'ROLE_USER'),
        (1, 'ROLE_MANAGER');

insert into public.delivery_address (id, apartment_number, building_number, city, house_number, street)
values  (1, 0, 0, 'Minsk', 33, 'Selivanova'),
        (2, 0, 0, 'Minsk', 26, 'Korzha');

insert into public.restaurant_reviews (restaurant_id, reviews)
values  (1, 'Best restaurant ever!!'),
        (1, 'Nice and quiet restaurant))');



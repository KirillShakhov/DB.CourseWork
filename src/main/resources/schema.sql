CREATE TABLE "users" (
                         "id_user" bigserial ,
                         "username" varchar(250) not null unique,
                         "biography" text,
                         "first_name" varchar(250) not null,
                         "last_name" varchar(250) ,
                         "email" varchar(250) not null unique,
                         "pass" varchar(250) not null,
                         "registation_date" date not null,
                         PRIMARY KEY ("id_user")
);

CREATE TABLE "creators" (
                            "id_creators" bigserial,
                            "user" bigint references users(id_user),
                            "creation_date" date not null,
                            PRIMARY KEY ("id_creators")
);


CREATE TABLE "colors" (
                          "id_color" bigserial ,
                          "name" varchar(250) not null unique,
                          "hex" varchar(7) not null unique,
                          "r" int not null check(r >= 0 and r <= 255),
                          "g" int not null check(g >= 0 and g <= 255),
                          "b" int not null check(b >= 0 and b <= 255),
                          PRIMARY KEY ("id_color")
);

CREATE TABLE "series" (
                          "id_series" bigserial ,
                          "name" varchar(250) not null,
                          "description" text,
                          "date_of_start" date not null,
                          "date_of_finish" date,
                          PRIMARY KEY ("id_series")
);

CREATE TABLE "wheels" (
                          "id_wheel" bigserial ,
                          "name" varchar(250) not null,
                          "creator" bigint not null references creators(id_creators),
                          "adhesion_coefficient" decimal,
                          "disk_color" bigint not null references colors(id_color),
                          "photo" varchar(250),
                          PRIMARY KEY ("id_wheel")
);

CREATE TABLE "bumpers" (
                           "id_bumper" bigserial ,
                           "name" varchar(250) not null,
                           "color" bigint not null references colors(id_color),
                           "photo" varchar(250),
                           PRIMARY KEY ("id_bumper")
);

CREATE TABLE "cars" (
                        "id_car" bigserial ,
                        "name" varchar(250),
                        "creator" bigint not null references creators(id_creators),
                        "series" bigint references series(id_series),
                        "first_color" bigint references colors(id_color),
                        "second_color" bigint references colors(id_color),
                        "wheels" bigint references wheels(id_wheel),
                        "bumper" bigint references bumpers(id_bumper),
                        PRIMARY KEY ("id_car")
);

CREATE TABLE "photo" (
                         "id" bigserial ,
                         "car" bigint not null references cars(id_car),
                         "url_photo" bigint not null,
                         "alt_description" varchar(250),
                         PRIMARY KEY ("id")
);

CREATE TABLE "items" (
                         "id_item" bigserial,
                         "car" bigint check((wheel is null and bumper is null) or car is null) references cars(id_car),
                         "wheel" bigint check((car is null and bumper is null) or wheel is null) references wheels(id_wheel),
                         "bumper" bigint check((car is null and wheel is null) or bumper is null) references bumpers(id_bumper),
                         "description" text,
                         "real_photo" varchar(250),
                         "owner" bigint not null references users(id_user),
                         PRIMARY KEY ("id_item")
);

CREATE TABLE "purchase_items" (
                                  "id" bigserial,
                                  "price" bigint not null check(price > 0),
                                  "item" bigint not null references items(id_item),
                                  PRIMARY KEY ("id")
);

CREATE TABLE "contracts" (
                             "id_contract" bigserial ,
                             "from_user" bigint not null references users(id_user),
                             "to_user" bigint references users(id_user),
                             "from_money" int not null check(from_money >= 0),
                             "to_money" int not null check(from_money >= 0),
                             "is_closed" boolean default false,
                             "closing_date" date not null,
                             "closing_time" time default '00:00',
                             PRIMARY KEY ("id_contract")
);

CREATE TABLE "auctions" (
                            "contract" bigint not null references contracts(id_contract),
                            "last_bet_size" int check(last_bet_size> 0),
                            "last_customer" bigint
);

CREATE TABLE "contract_items" (
                                  "contract" bigint not null references contracts(id_contract),
                                  "item" bigint unique not null references items(id_item)
);

CREATE TABLE "articles" (
                            "id_articles" bigserial ,
                            "title" varchar(250) not null,
                            "car" bigint not null references cars(id_car),
                            "text" text not null,
                            "author" bigint not null references users(id_user),
                            "create_date" date not null,
                            PRIMARY KEY ("id_articles")
);

CREATE TABLE "comments" (
                            "id_comments" bigserial primary key ,
                            "reply" bigint references comments(id_comments) ON DELETE CASCADE,
                            "article" bigint check(car is null) references articles(id_articles),
                            "car" bigint check(article is null) references cars(id_car),
                            "text" text not null,
                            "author" bigint not null references users(id_user),
                            "create_date" date not null
);

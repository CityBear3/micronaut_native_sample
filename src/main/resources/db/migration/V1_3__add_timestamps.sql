alter table "user"
    add create_at timestamptz default current_timestamp;

alter table "user"
    add update_at timestamptz;

alter table "user"
    add expire_on timestamptz;

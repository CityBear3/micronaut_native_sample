ALTER TABLE "user" ADD COLUMN refresh_token varchar(255);
ALTER TABLE "user" ADD COLUMN revoked boolean default false;

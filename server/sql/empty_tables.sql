use broapp;
SET foreign_key_checks = 0;

delete from users;
delete from tags;
delete from event_tags;
delete from event_participants;

select * from users;

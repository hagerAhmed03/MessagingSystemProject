CREATE DATABASE MessagingDataBase;

use MessagingDataBase;

show tables;



CREATE TABLE topic (
name  varchar(25),
size int,
CONSTRAINT PK_TOPIC_NAME PRIMARY KEY (name)
);


create table message(
messageIndex int ,
topicName varchar(25),
content varchar(100),
CONSTRAINT PK_COMPOSITE_TOPIC_INDEX PRIMARY KEY (messageIndex,topicName),
constraint FK_TOPIC_NAME FOREIGN KEY (topicName) REFERENCES topic (name)
);

CREATE INDEX index_name
ON message(topicName);


ALTER TABLE message 
MODIFY messageIndex int NOT NULL AUTO_INCREMENT;



insert into topic values("hr",2);

INSERT INTO message(topicName,content) VALUES("hr","hello incorta");
INSERT INTO message(topicName,content) VALUES("hr","hello Manga");



select * from message;
select * from topic;


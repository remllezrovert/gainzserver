CREATE SCHEMA IF NOT EXISTS gainzdb;
SET search_path TO gainzdb;
CREATE EXTENSION IF NOT EXISTS hstore;

CREATE TYPE Unit AS ENUM ('BW','LB','KG','MI','KM','FT','M');

CREATE TYPE Weight AS (
    weightValue smallint,
    weightUnit Unit 
);

CREATE TYPE Distance AS (
    distanceValue decimal(6,3),
    distanceUnit Unit 
);

CREATE TYPE dataType AS ENUM (
    'Strength',
    'Isometric',
    'Cardio',
    'JsonObject',
    'Blob'
);

CREATE SEQUENCE exerciseId START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE clientId START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE templateId START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE formId START WITH 1 INCREMENT BY 1;


-- Table: Client
CREATE TABLE IF NOT EXISTS Client (
    title varchar(32)  NOT NULL,
    id serial  NOT NULL,
    email varchar(64) NULL UNIQUE,
    clientEnabled boolean null,
    clientPassword varchar(64) NOT NULL,
    verificationCode varchar(32) NULL,
    verificationExpire timestamp NULL,
    CONSTRAINT Client_pk PRIMARY KEY (id)
);

-- Table: Device
CREATE TABLE IF NOT EXISTS Device (
    id bigint  NOT NULL,
    Client_id int  NOT NULL,
    sync timestamp  NULL,
    CONSTRAINT Device_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS ClientIdIndex on Device (Client_id ASC);

-- Table: Template
CREATE TABLE IF NOT EXISTS Template (
    id serial NOT NULL,
    Client_id int NOT NULL,
    title varchar(32)  NOT NULL UNIQUE,
    form_id int NOT NULL,
    dataType dataType NULL,
    summary varchar(128)  NULL,
    CONSTRAINT Template_pk PRIMARY KEY (id)
);


--Table: Form
CREATE TABLE IF NOT EXISTS Form(
id serial NOT NULL,
content BYTEA NULL,
CONSTRAINT Form_pk PRIMARY KEY (id)
);




CREATE INDEX IF NOT EXISTS TemplateTitleIndex on Template (title ASC);

CREATE INDEX IF NOT EXISTS TemplateTypeIndex on Template (dataType ASC);

-- Table: Exercise
CREATE TABLE IF NOT EXISTS Exercise (
    id bigserial NOT NULL,
    Client_id int NOT NULL,
    Template_id int  NOT NULL,
    dateValue date  NULL,
    CONSTRAINT Exercise_pk PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS Strength(
Exercise_id bigint NOT NULL,
attr hstore,
CONSTRAINT Strength_pk PRIMARY KEY (Exercise_id)
);

CREATE TABLE IF NOT EXISTS Isometric(
Exercise_id bigint NOT NULL,
attr hstore,
CONSTRAINT Isometric_pk PRIMARY KEY (Exercise_id)
);

CREATE TABLE IF NOT EXISTS Cardio(
Exercise_id bigint NOT NULL,
attr hstore,
CONSTRAINT Cardio_pk PRIMARY KEY (Exercise_id)
);

CREATE TABLE IF NOT EXISTS JsonObject(
Exercise_id bigint NOT NULL,
content JSONB NULL,
CONSTRAINT JsonObject_pk PRIMARY KEY (Exercise_id)
);

CREATE TABLE IF NOT EXISTS Blob(
Exercise_id bigint NOT NULL,
content BYTEA NULL,
CONSTRAINT Blob_pk PRIMARY KEY (Exercise_id)
);



CREATE INDEX IF NOT EXISTS ExerciseTemplateIndex on Exercise (Template_id ASC);
CREATE INDEX IF NOT EXISTS ExerciseClientIndex ON Exercise (Client_id ASC);
ALTER TABLE exercise ALTER COLUMN id SET DEFAULT nextval('exerciseId');
ALTER TABLE client ALTER COLUMN id SET DEFAULT nextval('clientId');
ALTER TABLE template ALTER COLUMN id SET DEFAULT nextval('templateId');
ALTER TABLE form ALTER COLUMN id SET DEFAULT nextval('formId');



ALTER TABLE Strength ADD CONSTRAINT Strength_Exercise
    FOREIGN KEY (Exercise_id)
    REFERENCES Exercise (id)
    ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;


ALTER TABLE Isometric ADD CONSTRAINT Isometric_Exercise 
    FOREIGN KEY (Exercise_id)
    REFERENCES Exercise (id)
    ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

ALTER TABLE Cardio ADD CONSTRAINT Cardio_Exercise 
    FOREIGN KEY (Exercise_id)
    REFERENCES Exercise (id)
    ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

ALTER TABLE JsonObject ADD CONSTRAINT JsonObject_Exercise 
    FOREIGN KEY (Exercise_id)
    REFERENCES Exercise (id)
    ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;
ALTER TABLE Blob ADD CONSTRAINT Blob_Exercise 
    FOREIGN KEY (Exercise_id)
    REFERENCES Exercise (id)
    ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

ALTER TABLE Template ADD CONSTRAINT Template_Form
    FOREIGN KEY (Form_id)
    REFERENCES Form (id)
    ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;


ALTER TABLE Device ADD CONSTRAINT Device_Client
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Template_Client (table: Device)


-- Reference: Exercise_Template (table: Exercise)
ALTER TABLE Exercise ADD CONSTRAINT Exercise_Template
    FOREIGN KEY (Template_id)
    REFERENCES Template (id) ON DELETE CASCADE
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

ALTER TABLE Exercise ADD CONSTRAINT Exercise_Client
    FOREIGN KEY (Client_id)
    REFERENCES Client (id) ON DELETE CASCADE
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;
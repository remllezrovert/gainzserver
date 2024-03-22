-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-03-17 03:24:41.32

-- tables
-- Table: Cardio
CREATE TYPE Unit AS ENUM ('LB','KG','MI','KM');

CREATE TABLE IF NOT EXISTS Cardio (
    Workout_id int8  NOT NULL,
    distance decimal(3,3)  NULL,
    durration time  NULL,
    unit Unit NULL,
    jsonObject jsonb  NULL,
    CONSTRAINT Cardio_pk PRIMARY KEY (Workout_id)
);

-- Table: Client
CREATE TABLE IF NOT EXISTS Client (
    id serial  NOT NULL,
    title varchar(30)  NOT NULL,
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

-- Table: Isometric
CREATE TABLE IF NOT EXISTS Isometric (
    Workout_id int8  NOT NULL,
    weight smallint  NULL,
    timeArr time[]  NULL,
    unit Unit NULL,
    jsonObject jsonb  NULL,
    CONSTRAINT Isometric_pk PRIMARY KEY (Workout_id)
);

-- Table: Strength
CREATE TABLE IF NOT EXISTS Strength (
    Workout_id int8  NOT NULL,
    weight smallint  NULL,
    repArr smallint[]  NULL,
    unit Unit NULL,
    jsonObject jsonb  NULL,
    CONSTRAINT Strength_pk PRIMARY KEY (Workout_id)
);

-- Table: Template
CREATE TABLE IF NOT EXISTS Template (
    id serial  NOT NULL,
    Client_id int  NULL,
    title char(30)  NOT NULL,
    description char(120)  NULL,
    workoutType varchar(15)  NULL,
    jsonObject jsonb  NULL,
    CONSTRAINT Template_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS TemplateTitleIndex on Template (title ASC);

CREATE INDEX IF NOT EXISTS TemplateClientIndex on Template (Client_id ASC);

CREATE INDEX IF NOT EXISTS TemplateTypeIndex on Template (workoutType ASC);

-- Table: Workout
CREATE TABLE IF NOT EXISTS Workout (
    id bigserial  NOT NULL,
    Template_id int  NOT NULL,
    workoutDate date  NULL,
    tagArr varchar(20)[]  NULL,
    jsonObject jsonb  NULL,
    CONSTRAINT Workout_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS WorkoutTemplateIndex on Workout (Template_id ASC);

-- foreign keys
-- Reference: Cardio_Workout (table: Cardio)
ALTER TABLE Cardio ADD CONSTRAINT Cardio_Workout
    FOREIGN KEY (Workout_id)
    REFERENCES Workout (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Device_Client (table: Device)
ALTER TABLE Device ADD CONSTRAINT Device_Client
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Isometric_Workout (table: Isometric)
ALTER TABLE Isometric ADD CONSTRAINT Isometric_Workout
    FOREIGN KEY (Workout_id)
    REFERENCES Workout (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Strength_Workout (table: Strength)
ALTER TABLE Strength ADD CONSTRAINT Strength_Workout
    FOREIGN KEY (Workout_id)
    REFERENCES Workout (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Template_Client (table: Template)
ALTER TABLE Template ADD CONSTRAINT Template_Client
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Workout_Template (table: Workout)
ALTER TABLE Workout ADD CONSTRAINT Workout_Template
    FOREIGN KEY (Template_id)
    REFERENCES Template (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.


-- Last modification date: 2024-03-15 03:27:58.671

-- tables
-- Table: Cardio

SET MODE PostGreSQL;
CREATE TYPE IF NOT EXISTS Unit AS ENUM ('LB','KG','MI','KM');


CREATE TABLE IF NOT EXISTS Cardio (
    Workout_id int8  NOT NULL,
    distance decimal(3,3)  NOT NULL,
    unit Unit  NOT NULL,
    time time  NOT NULL,
    CONSTRAINT Cardio_pk PRIMARY KEY (Workout_id)
);

-- Table: Device
CREATE TABLE IF NOT EXISTS Device (
    id bigint  NOT NULL,
    Client_id int  NOT NULL,
    sync timestamp  NOT NULL,
    CONSTRAINT Device_pk PRIMARY KEY (id,Client_id)
);

-- Table: Isometric
CREATE TABLE IF NOT EXISTS Isometric (
    Workout_id int8  NOT NULL,
    weight smallint  NOT NULL,
    unit Unit  NOT NULL,
    wSet jsonb  NOT NULL,
    CONSTRAINT Isometric_pk PRIMARY KEY (Workout_id)
);

-- Table: Strength
CREATE TABLE IF NOT EXISTS Strength (
    Workout_id int8  NOT NULL,
    weight smallint  NOT NULL,
    unit Unit  NOT NULL,
    wSet jsonb  NOT NULL,
    CONSTRAINT Strength_pk PRIMARY KEY (Workout_id)
);

-- Table: Template
CREATE TABLE IF NOT EXISTS Template (
    id serial  NOT NULL,
    Client_id int  NOT NULL,
    name char(30)  NOT NULL,
    description char(120),
    CONSTRAINT Template_pk PRIMARY KEY (id)
);

CREATE INDEX TemplateNameIndex on Template (name ASC);

CREATE INDEX TemplateUserIndex on Template (Client_id ASC);

-- Table: User
CREATE TABLE IF NOT EXISTS Client (
    id serial  NOT NULL,
    name varchar(30)  NOT NULL,
    CONSTRAINT Client_pk PRIMARY KEY (id)
);

-- Table: Workout
CREATE TABLE IF NOT EXISTS Workout (
    id bigserial  NOT NULL,
    Template_id int  NOT NULL,
    date date  NOT NULL,
    tags jsonb  NOT NULL,
    CONSTRAINT Workout_pk PRIMARY KEY (id)
);

CREATE INDEX WorkoutTemplateIndex on Workout (Template_id ASC);

-- foreign keys
-- Reference: Cardio_Workout (table: Cardio)
ALTER TABLE Cardio ADD CONSTRAINT Cardio_Workout
    FOREIGN KEY (Workout_id)
    REFERENCES Workout (id)  
    -- NOT DEFERRABLE 
    -- INITIALLY IMMEDIATE
;

-- Reference: Device_User (table: Device)
ALTER TABLE Device ADD CONSTRAINT Device_User
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)  
    -- NOT DEFERRABLE 
    -- INITIALLY IMMEDIATE
;

-- Reference: Isometric_Workout (table: Isometric)
ALTER TABLE Isometric ADD CONSTRAINT Isometric_Workout
    FOREIGN KEY (Workout_id)
    REFERENCES Workout (id)  
    -- NOT DEFERRABLE 
    -- INITIALLY IMMEDIATE
;

-- Reference: Strength_Workout (table: Strength)
ALTER TABLE Strength ADD CONSTRAINT Strength_Workout
    FOREIGN KEY (Workout_id)
    REFERENCES Workout (id)  
    -- NOT DEFERRABLE 
    -- INITIALLY IMMEDIATE
;

-- Reference: Template_User (table: Template)
ALTER TABLE Template ADD CONSTRAINT Template_User
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)  
    -- NOT DEFERRABLE 
    -- INITIALLY IMMEDIATE
;

-- Reference: Workout_Template (table: Workout)
ALTER TABLE Workout ADD CONSTRAINT Workout_Template
    FOREIGN KEY (Template_id)
    REFERENCES Template (id)  
    -- NOT DEFERRABLE 
    -- INITIALLY IMMEDIATE
;

-- End of file.


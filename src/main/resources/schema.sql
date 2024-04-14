
CREATE TYPE Unit AS ENUM ('LB','KG','MI','KM');
CREATE SEQUENCE workoutId START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE clientId START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE templateId START WITH 1 INCREMENT BY 1;

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

-- Table: Template
CREATE TABLE IF NOT EXISTS Template (
    id serial  NOT NULL,
    Client_id serial NOT NULL,
    title varchar(30)  NOT NULL,
    workoutType varchar(15)  NULL,
    summary varchar(120)  NULL,
    CONSTRAINT Template_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS TemplateTitleIndex on Template (title ASC);

CREATE INDEX IF NOT EXISTS TemplateTypeIndex on Template (workoutType ASC);

-- Table: Workout
CREATE TABLE IF NOT EXISTS Workout (
    Client_id int NOT NULL,
    id bigserial NOT NULL,
    Template_id int  NOT NULL,
    workoutDate date  NULL,
    unit Unit NULL,
    weight smallint  NULL,
    distance decimal(3,3)  NULL,
    durration time  NULL,
    repArr smallint[]  NULL,
    timeArr time[]  NULL,
    tagArr varchar(20)[]  NULL,
    CONSTRAINT Workout_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS WorkoutTemplateIndex on Workout (Template_id ASC);
CREATE INDEX IF NOT EXISTS WorkoutClientIndex ON Workout (Client_id ASC);
ALTER TABLE workout ALTER COLUMN id SET DEFAULT nextval('workoutId');
ALTER TABLE client ALTER COLUMN id SET DEFAULT nextval('clientId');
ALTER TABLE template ALTER COLUMN id SET DEFAULT nextval('templateId');

ALTER TABLE Device ADD CONSTRAINT Device_Client
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Template_Client (table: Device)
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

ALTER TABLE Workout ADD CONSTRAINT Workout_Client
    FOREIGN KEY (Client_id)
    REFERENCES Client (id)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;




-- End of file.
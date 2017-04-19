DROP TABLE IF EXISTS Swim CASCADE;
DROP TABLE IF EXISTS Heat CASCADE;
DROP TABLE IF EXISTS Event CASCADE;
DROP TABLE IF EXISTS StrokeOf CASCADE;
DROP TABLE IF EXISTS Leg CASCADE;
DROP TABLE IF EXISTS Distance CASCADE;
DROP TABLE IF EXISTS Stroke CASCADE;
DROP TABLE IF EXISTS Meet CASCADE;
DROP TABLE IF EXISTS Participant CASCADE;
DROP TABLE IF EXISTS Org CASCADE;

CREATE TABLE Org (
    id VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    is_univ BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE Participant (
    id VARCHAR(50) NOT NULL,
    gender CHAR(1) NOT NULL,
    org_id VARCHAR(50) NOT NULL,
    name VARCHAR(50),
    PRIMARY KEY(id),
    FOREIGN KEY(org_id) REFERENCES Org(id)
);

CREATE TABLE Meet (
    name VARCHAR(50) NOT NULL,
    start_date DATE,
    num_days INT,
    org_id VARCHAR(50) NOT NULL,
    PRIMARY KEY(name),
    FOREIGN KEY(org_id) REFERENCES Org(id)
);

CREATE TABLE Stroke (
    stroke VARCHAR(50) NOT NULL,
    PRIMARY KEY(stroke)
);

CREATE TABLE Distance (
    distance INT NOT NULL,
    PRIMARY KEY(distance)
);

CREATE TABLE Event (
    id VARCHAR(50) NOT NULL,
    gender CHAR(1) NOT NULL,
    distance INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(distance) REFERENCES Distance(distance)
);

CREATE TABLE Leg (
    leg INT NOT NULL,
    PRIMARY KEY(leg)
);

CREATE TABLE StrokeOf (
    event_id VARCHAR(50) NOT NULL,
    leg INT NOT NULL,
    stroke VARCHAR(50),
    PRIMARY KEY(event_id, leg),
    FOREIGN KEY(event_id) REFERENCES Event(id),
    FOREIGN KEY(leg) REFERENCES Leg(leg),
    FOREIGN KEY(stroke) REFERENCES Stroke(stroke)
);

CREATE TABLE Heat (
    id VARCHAR(50) NOT NULL,
    event_id VARCHAR(50) NOT NULL,
    meet_name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id, event_id, meet_name),
    FOREIGN KEY(event_id) REFERENCES Event(id),
    FOREIGN KEY(meet_name) REFERENCES Meet(name)
);

CREATE TABLE Swim (
    heat_id VARCHAR(50) NOT NULL,
    event_id VARCHAR(50) NOT NULL,
    meet_name VARCHAR(50) NOT NULL, 
    participant_id VARCHAR(50) NOT NULL,
    leg INT,
    swimtime FLOAT,
    PRIMARY KEY(heat_id, event_id, meet_name, participant_id),
    FOREIGN KEY(heat_id, event_id, meet_name) REFERENCES Heat(id, event_id, meet_name),
    FOREIGN KEY(participant_id) REFERENCES Participant(id)
);

DROP FUNCTION IF EXISTS insertOrg(insert_id VARCHAR(50), name VARCHAR(50), is_univ BOOLEAN);
CREATE FUNCTION insertOrg ( insert_id VARCHAR(50), name VARCHAR(50), is_univ BOOLEAN) RETURNS INT
AS $$
    DECLARE
        count INT;
    BEGIN
        INSERT INTO Org VALUES (insert_id, name, is_univ) ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name, is_univ = EXCLUDED.is_univ; 
        SELECT COUNT(*) into count
        FROM Org;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertParticipant(insert_id VARCHAR(50), gender CHAR (1), org_id VARCHAR(50), name VARCHAR(50));
CREATE FUNCTION insertParticipant (insert_id VARCHAR(50), gender CHAR (1), org_id VARCHAR(50), name VARCHAR(50)) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Participant VALUES (insert_id, gender, org_id, name) ON CONFLICT (id) DO UPDATE SET gender = EXCLUDED.gender, org_id = EXCLUDED.org_id, name  = EXCLUDED.name; 
        SELECT COUNT(*) into count
        FROM Participant;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertMeet(insert_name VARCHAR(50), start_date DATE, num_days INT, org_id VARCHAR(50));
CREATE FUNCTION insertMeet (insert_name VARCHAR(50), start_date DATE, num_days INT, org_id VARCHAR(50)) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Meet VALUES (insert_name, start_date, num_days, org_id) ON CONFLICT (name) DO UPDATE SET start_date = EXCLUDED.start_date, num_days = EXCLUDED.num_days, org_id  = EXCLUDED.org_id; 
        SELECT COUNT(*) into count
        FROM Meet;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertStroke(insert_stroke VARCHAR(50));
CREATE FUNCTION insertStroke (insert_stroke VARCHAR(50)) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Stroke VALUES (insert_stroke) ON CONFLICT (stroke) DO UPDATE SET stroke = insert_stroke; 
        SELECT COUNT(*) into count
        FROM Stroke;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertDistance(insert_distance INT);
CREATE FUNCTION insertDistance (insert_distance INT) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Distance VALUES (insert_distance) ON CONFLICT (distance) DO UPDATE SET distance = insert_distance; 
        SELECT COUNT(*) into count
        FROM Distance;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertEvent(insert_id VARCHAR(50), gender CHAR(1), distance INT);
CREATE FUNCTION insertEvent (insert_id VARCHAR(50), gender CHAR(1), distance INT) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Event VALUES (insert_id, gender, distance) ON CONFLICT (id) DO UPDATE SET gender = EXCLUDED.gender, distance = EXCLUDED.distance;
        SELECT COUNT(*) into count
        FROM Event;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertLeg(insert_leg INT);
CREATE FUNCTION insertLeg (insert_leg INT) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Leg VALUES (insert_leg) ON CONFLICT (leg) DO UPDATE SET leg = insert_leg;
        SELECT COUNT(*) into count
        FROM Leg;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertStrokeOf(insert_event_id VARCHAR(50), insert_leg INT, stroke VARCHAR(50));
CREATE FUNCTION insertStrokeOf (insert_event_id VARCHAR(50), insert_leg INT, stroke VARCHAR(50)) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO StrokeOf VALUES (insert_event_id, insert_leg, stroke) ON CONFLICT ON CONSTRAINT StrokeOf_pkey DO UPDATE SET stroke = EXCLUDED.stroke; 
        SELECT COUNT(*) into count
        FROM StrokeOf;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertHeat(insert_id VARCHAR(50), insert_event_id VARCHAR(50), insert_meet_name VARCHAR(50));
CREATE FUNCTION insertHeat (insert_id VARCHAR(50), insert_event_id VARCHAR(50), insert_meet_name VARCHAR(50)) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        INSERT INTO Heat VALUES (insert_id, insert_event_id, insert_meet_name) ON CONFLICT DO NOTHING;
        SELECT COUNT(*) into count
        FROM Heat;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS insertSwim(insert_heat_id VARCHAR(50), insert_event_id VARCHAR(50), insert_meet_name VARCHAR(50), insert_participant_id VARCHAR(50), leg INT, swimtime FLOAT);
CREATE FUNCTION insertSwim (insert_heat_id VARCHAR(50), insert_event_id VARCHAR(50), insert_meet_name VARCHAR(50), insert_participant_id VARCHAR(50), leg INT, swimtime FLOAT) RETURNS INT
AS $$
    DECLARE 
        count INT;
    BEGIN
        IF leg = 0 THEN
            INSERT INTO Swim(heat_id, event_id, meet_name, participant_id, swimtime) VALUES (insert_heat_id, insert_event_id, insert_meet_name, insert_participant_id, swimtime) ON CONFLICT ON CONSTRAINT Swim_pkey DO UPDATE SET swimtime = EXCLUDED.swimtime;
        ELSE
            INSERT INTO Swim VALUES (insert_heat_id, insert_event_id, insert_meet_name, insert_participant_id, leg, swimtime) ON CONFLICT ON CONSTRAINT Swim_pkey DO UPDATE SET swimtime = EXCLUDED.swimtime;
        END IF;
        SELECT COUNT(*) into count
        FROM Swim;
        RETURN count;
    END $$ 
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS dispMeet(disp_meet_name VARCHAR(50));
CREATE OR REPLACE FUNCTION dispMeet(m_meet_name VARCHAR(50))
returns table(
    Event_id VARCHAR(50),
    Heat_id VARCHAR(50),
    Participant_id VARCHAR(50),
    Org_Name VARCHAR(50),
    Individual_Time FLOAT,
    Relay_Time FLOAT,
    Rank BIGINT
)
AS $$
    BEGIN
        DROP TABLE IF EXISTS isRelay CASCADE;
        CREATE TABLE isRelay AS (
            SELECT DISTINCT s.heat_id, s.event_id 
            FROM Swim s
            WHERE s.leg > 1 
                  AND 
                  s.meet_name = m_meet_name
        );

        DROP TABLE IF EXISTS RelayHelper CASCADE;
        CREATE TABLE RelayHelper AS (
            SELECT p.org_id, s.event_id, s.heat_id, sum(s.swimtime) AS Relay_Time, rank() Over (partition by s.event_id ORDER BY sum(s.swimtime)) AS Relay_Rank
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN isRelay i ON s.heat_id = i.heat_id AND s.event_id = i.event_id
            GROUP BY p.org_id, s.event_id, s.heat_id);

        DROP TABLE IF EXISTS new_Table CASCADE;
        CREATE TABLE new_Table AS (
            (SELECT s.event_id, s.heat_id, s.participant_id, o.name, s.swimtime, 0.00 AS Relay_Time, rank() Over (partition by s.event_id ORDER BY s.swimtime)
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN Org o ON p.org_id = o.id
            WHERE s.meet_name = m_meet_name 
                  AND s.leg = 1 
                  AND NOT exists(
                                SELECT * 
                                FROM RelayHelper r 
                                WHERE s.event_id = r.event_id 
                                      AND 
                                      s.heat_id = r.heat_id
            ))
        UNION
            (SELECT r.event_id, r.heat_id, s.participant_id, o.name, s.swimtime, r.Relay_Time AS Relay_Time, r.Relay_Rank
            FROM RelayHelper r
            INNER JOIN Swim s ON r.event_id = s.event_id AND r.heat_id = s.heat_id
            INNER JOIN Participant p ON r.org_id = p.org_id AND s.participant_id = p.id
            INNER JOIN Org o ON r.org_id = o.id)
        );
        return query SELECT * 
                     FROM new_Table T 
                     ORDER BY T.Relay_Time;
    END $$
LANGUAGE plpgsql
VOLATILE;


DROP FUNCTION IF EXISTS dispMeetParticipant(disp_meet_name VARCHAR(50), disp_participant_id VARCHAR(50));
CREATE OR REPLACE FUNCTION dispMeetParticipant(m_meet_name VARCHAR(50), m_participant_id VARCHAR(50))
returns table(
    Event_id VARCHAR(50),
    Heat_id VARCHAR(50),
    Org_Name varchar(50),
    Individual_Time FLOAT,
    Relay_Time FLOAT,
    Rank BIGINT
)
AS $$
    BEGIN
        DROP TABLE IF EXISTS isRelay CASCADE;
        CREATE TABLE isRelay AS (
            SELECT DISTINCT s.heat_id, s.event_id 
            FROM Swim s
            WHERE s.leg > 1 
                  AND 
                  s.meet_name = m_meet_name 
                  AND 
                  s.participant_id = m_participant_id
        );

        DROP TABLE IF EXISTS RelayHelper CASCADE;
        CREATE TABLE RelayHelper AS (
            SELECT p.org_id, s.event_id, s.heat_id, sum(s.swimtime) AS Relay_Time, rank() Over (partition by s.event_id ORDER BY sum(s.swimtime)) AS Relay_Rank
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN isRelay i ON s.heat_id = i.heat_id AND s.event_id = i.event_id
            GROUP BY p.org_id, s.event_id, s.heat_id);

        DROP TABLE IF EXISTS new_Table CASCADE;
        CREATE TABLE new_Table AS (
            (SELECT s.event_id, s.heat_id, o.name, s.swimtime, 0.00 AS Relay_Time, rank() Over (partition by s.event_id ORDER BY s.swimtime)
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN Org o ON p.org_id = o.id
            WHERE s.meet_name = m_meet_name AND s.leg = 1 AND s.participant_id = m_participant_id AND NOT exists(
                SELECT * 
                FROM RelayHelper r 
                WHERE s.event_id = r.event_id 
                      AND 
                      s.heat_id = r.heat_id
            ))
        UNION
            (SELECT r.event_id, r.heat_id, o.name, s.swimtime, r.Relay_Time AS Relay_Time, r.Relay_Rank
            FROM RelayHelper r
            INNER JOIN Swim s ON r.event_id = s.event_id AND r.heat_id = s.heat_id
            INNER JOIN Participant p ON r.org_id = p.org_id AND s.participant_id = p.id
            INNER JOIN Org o ON r.org_id = o.id)
        );
        return query SELECT * 
                     FROM new_Table T 
                     ORDER BY T.Relay_Time;
    END $$
LANGUAGE plpgsql
VOLATILE;

DROP FUNCTION IF EXISTS dispSchoolOwn(disp_meet_name VARCHAR(50), disp_school_id VARCHAR(50));
CREATE OR REPLACE FUNCTION dispSchoolOwn(m_meet_name VARCHAR(50), m_org_id VARCHAR(50))
returns table(
    Event_id VARCHAR(50),
    Heat_id VARCHAR(50),
    Participant_id VARCHAR(50),
    Individual_Time FLOAT,
    Relay_Time FLOAT,
    Rank BIGINT
)
AS $$
    BEGIN
        DROP TABLE IF EXISTS isRelay CASCADE;
        CREATE TABLE isRelay AS (
            SELECT DISTINCT s.heat_id, s.event_id FROM Swim s
            WHERE s.leg > 1 
                  AND 
                  s.meet_name = m_meet_name
        );

        DROP TABLE IF EXISTS RelayHelper CASCADE;
        CREATE TABLE RelayHelper AS (
            SELECT p.org_id, s.event_id, s.heat_id, sum(s.swimtime) AS Relay_Time, rank() Over (partition by s.event_id 
            ORDER BY sum(s.swimtime)) AS Relay_Rank
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id AND p.org_id = m_org_id
            INNER JOIN isRelay i ON s.heat_id = i.heat_id AND s.event_id = i.event_id
            GROUP BY p.org_id, s.event_id, s.heat_id);

        DROP TABLE IF EXISTS new_Table CASCADE;
        CREATE TABLE new_Table AS (
            (SELECT s.event_id, s.heat_id, s.participant_id, s.swimtime, 0.00 AS Relay_Time, rank() Over (partition by s.event_id 
            ORDER BY s.swimtime)
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id AND p.org_id = m_org_id
            INNER JOIN Org o ON p.org_id = o.id
            WHERE s.meet_name = m_meet_name 
                  AND s.leg = 1 
                  AND NOT exists(
                                SELECT * FROM RelayHelper r 
                                WHERE s.event_id = r.event_id 
                                      AND 
                                      s.heat_id = r.heat_id
            ))
        UNION
            (SELECT r.event_id, r.heat_id, s.participant_id, s.swimtime, r.Relay_Time AS Relay_Time, r.Relay_Rank
            FROM RelayHelper r
            INNER JOIN Swim s ON r.event_id = s.event_id AND r.heat_id = s.heat_id
            INNER JOIN Participant p ON r.org_id = p.org_id AND s.participant_id = p.id
            INNER JOIN Org o ON r.org_id = o.id)
        );
        return query SELECT * 
                     FROM new_Table T 
                     ORDER BY T.Relay_Time;
    END $$
LANGUAGE plpgsql
VOLATILE;

DROP FUNCTION IF EXISTS dispSchoolOpp(disp_meet_name VARCHAR(50), disp_school_id VARCHAR(50));
CREATE OR REPLACE FUNCTION dispSchoolOpp(disp_meet_name VARCHAR(50), disp_school_id VARCHAR(50)) 
RETURNS TABLE (heat_id VARCHAR(50), event_id VARCHAR(50), participant_id VARCHAR(50), participant_name VARCHAR(50),leg INT, org_id VARCHAR(50))
AS $$
    BEGIN
        RETURN QUERY SELECT Swim.heat_id, Swim.event_id, Swim.participant_id, Participant.name, Swim.leg, Participant.org_id
                     FROM Swim
                     INNER JOIN Participant ON Swim.participant_id = Participant.id
                     INNER JOIN Org ON Participant.org_id = Org.id
                     WHERE Swim.meet_name = disp_meet_name
                           AND
                           Org.id <> disp_school_id;
    END $$
LANGUAGE plpgsql;


DROP FUNCTION IF EXISTS dispMeetEvent(disp_meet_name VARCHAR(50), disp_event_id VARCHAR(50));
CREATE OR REPLACE FUNCTION dispMeetEvent(m_meet_name varchar(50), m_event_id VARCHAR(50))
returns table(
    Heat_id VARCHAR(50),
    Participant_id VARCHAR(50),
    Participant_Name VARCHAR(50),
    Individual_Time FLOAT,
    Relay_Time FLOAT,
    Rank BIGINT
)
AS $$
    BEGIN
        DROP TABLE IF EXISTS isRelay CASCADE;
        CREATE TABLE isRelay AS (
            SELECT DISTINCT s.heat_id, s.event_id FROM Swim s
            WHERE s.leg > 1 
                  AND 
                  s.meet_name = m_meet_name 
                  AND 
                  s.event_id = m_event_id
        );

        DROP TABLE IF EXISTS RelayHelper CASCADE;
        CREATE TABLE RelayHelper AS (
            SELECT p.org_id, s.event_id, s.heat_id, sum(s.swimtime) AS Relay_Time, rank() Over (partition by s.event_id ORDER BY sum(s.swimtime)) AS Relay_Rank
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN isRelay i ON s.heat_id = i.heat_id AND s.event_id = i.event_id
            GROUP BY p.org_id, s.event_id, s.heat_id);

        DROP TABLE IF EXISTS new_Table CASCADE;
        CREATE TABLE new_Table AS (
            (SELECT s.heat_id, s.participant_id, p.name, s.swimtime, 0.00 AS Relay_Time, rank()
            Over (partition by s.event_id ORDER BY s.swimtime)
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN Org o ON p.org_id = o.id
            WHERE s.meet_name = m_meet_name 
                  AND 
                  s.event_id = m_event_id 
                  AND 
                  s.leg = 1 
                  AND 
                  NOT exists(
                                    SELECT * 
                                    FROM RelayHelper r 
                                    WHERE s.event_id = r.event_id 
                                          AND 
                                          s.heat_id = r.heat_id
            ))
        UNION
            (SELECT r.heat_id, s.participant_id, p.name, s.swimtime, r.Relay_Time AS Relay_Time, r.Relay_Rank
            FROM RelayHelper r
            INNER JOIN Swim s ON r.event_id = s.event_id AND r.heat_id = s.heat_id
            INNER JOIN Participant p ON r.org_id = p.org_id AND s.participant_id = p.id
            INNER JOIN Org o ON r.org_id = o.id)
        );
        return query SELECT * 
                     FROM new_Table T 
                     ORDER BY T.Relay_Time, T.swimtime;
    end $$
LANGUAGE plpgsql
VOLATILE;

DROP FUNCTION IF EXISTS dispAllSchool(disp_meet_name VARCHAR(50));
CREATE OR REPLACE FUNCTION dispAllSchool(disp_meet_name VARCHAR(50)) 
returns table(
    Org_id VARCHAR(50),
    Org_Name VARCHAR(50),
    score numeric
)
AS $$
    BEGIN
        DROP TABLE IF EXISTS isRelay CASCADE;
        CREATE TABLE isRelay AS (
            SELECT DISTINCT s.heat_id, s.event_id FROM Swim s
            WHERE s.leg > 1 AND s.meet_name = disp_meet_name
        );

        DROP TABLE IF EXISTS RelayHelper CASCADE;
        CREATE TABLE RelayHelper AS (
            SELECT p.org_id, s.event_id, s.heat_id, sum(s.swimtime) AS Relay_Time, rank()
                Over (partition by s.event_id ORDER BY sum(s.swimtime)) AS Relay_Rank
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            INNER JOIN isRelay i ON s.heat_id = i.heat_id AND s.event_id = i.event_id
            GROUP BY p.org_id, s.event_id, s.heat_id);

        DROP TABLE IF EXISTS ScoreHelperRelay CASCADE;
        CREATE TABLE ScoreHelperRelay AS (
            SELECT r.org_id, count(r.org_id) * 8 AS score FROM RelayHelper r
            WHERE r.Relay_Rank = 1
            GROUP BY r.org_id
        UNION ALL
            SELECT r.org_id, count(r.org_id) * 4 AS score FROM RelayHelper r
            WHERE r.Relay_Rank = 2
            GROUP BY r.org_id
        UNION ALL
            SELECT r.org_id, count(r.org_id) * 2 AS score FROM RelayHelper r
            WHERE r.Relay_Rank = 3
            GROUP BY r.org_id
        );

        DROP TABLE IF EXISTS TotalRelay CASCADE;
        CREATE TABLE TotalRelay AS (
            SELECT s.org_id, sum(s.score) AS score FROM ScoreHelperRelay s
            GROUP BY s.org_id
        );

        DROP TABLE IF EXISTS Indiv CASCADE;
        CREATE TABLE Indiv AS (
            SELECT p.org_id, s.event_id, s.participant_id, s.swimtime, rank()
            over (partition by s.event_id ORDER BY s.swimtime) AS Indiv_rank
            FROM Swim s
            INNER JOIN Participant p ON s.participant_id = p.id
            WHERE s.meet_name = disp_meet_name AND s.leg = 1 AND not EXISTS(
                SELECT * FROM RelayHelper r WHERE s.event_id = r.event_id AND s.heat_id = r.heat_id
            )
        );

        DROP TABLE IF EXISTS ScoreIndiv CASCADE;
        CREATE TABLE ScoreIndiv AS (
            SELECT i.org_id, count(i.org_id) * 6 AS score FROM Indiv i
            WHERE i.Indiv_rank = 1
            GROUP BY i.org_id
        UNION ALL
            SELECT i.org_id, count(i.org_id) * 4 AS score FROM Indiv i
            WHERE i.Indiv_rank = 2
            GROUP BY i.org_id
        UNION ALL
            SELECT i.org_id, count(i.org_id) * 3 AS score FROM Indiv i
            WHERE i.Indiv_rank = 3
            GROUP BY i.org_id
        UNION ALL
            SELECT i.org_id, count(i.org_id) * 2 AS score FROM Indiv i
            WHERE i.Indiv_rank = 4
            GROUP BY i.org_id
        UNION ALL
            SELECT i.org_id, count(i.org_id) * 1 AS score FROM Indiv i
            WHERE i.Indiv_rank = 5
            GROUP BY i.org_id
        );

        DROP TABLE IF EXISTS TotalIndiv CASCADE;
        CREATE TABLE TotalIndiv AS (
            SELECT s.org_id, sum(s.score) AS score FROM ScoreIndiv s
            GROUP BY s.org_id
        );

        DROP TABLE IF EXISTS CombineRelayIndiv CASCADE;
        CREATE TABLE CombineRelayIndiv AS (
            SELECT * FROM TotalRelay
            UNION ALL
            SELECT * FROM TotalIndiv
        );
        
        DROP TABLE IF EXISTS FinalScore CASCADE;
        CREATE TABLE FinalScore AS (
            SELECT c.org_id, o.name, sum(c.score) AS score FROM CombineRelayIndiv c
            INNER JOIN Org o ON c.org_id = o.id
            GROUP BY c.org_id, o.name
        );
        return query SELECT f.org_id, f.name, f.score FROM FinalScore f ORDER BY f.score desc;
        END $$
LANGUAGE plpgsql;
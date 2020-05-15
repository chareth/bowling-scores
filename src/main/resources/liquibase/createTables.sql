CREATE TABLE IF NOT EXISTS playerscores (
    playerid integer PRIMARY KEY,
    lastupdatedframe integer DEFAULT 0
);

CREATE TABLE IF NOT EXISTS frames (
    playerid integer references playerscores(playerid),
    framenumber integer NOT NULL,
    rollone integer DEFAULT 0,
    rolltwo integer DEFAULT 0,
    rollthree integer DEFAULT 0,
    frameScore integer DEFAULT 0,
    frametype varchar(20) DEFAULT 'OPEN',
    subsequentrolladdsavail int DEFAULT 0,
    PRIMARY KEY(playerid, framenumber)
);

-- indexes
CREATE INDEX IF NOT EXISTS idx_frame_playerscoreid on frames(playerid);
create table market_metadata
(
    ticker   varchar(10)    NOT NULL,
    metadata varchar(50000) not null,
    date     int            NOT NULL,
    PRIMARY KEY (ticker)
);

-- test insert post create
-- insert into market_metadata values('vfv.to', 'test-proto', 20251221);
create table market_data
(
    date   int         NOT NULL,
    ticker varchar(10) NOT NULL,
    price  numeric     not null,
    PRIMARY KEY (date, ticker)
);

-- test insert post create
-- insert into market_data values(20241001, 'vfv.to', 131.23);
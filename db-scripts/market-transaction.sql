create table market_transaction
(
    order_id         varchar(25)  NOT NULL,
    direction        varchar(10)  NOT NULL,
    ticker           varchar(15)  NOT NULL,
    qty              numeric      not null,
    price            numeric      not null,
    price_per_share  numeric      not null,
    date_trade       int          NOT NULL,
    date_settlement  int          NOT NULL,
    name             varchar(101) NOT NULL,
    sector           varchar(20)  NOT NULL,
    account_type     varchar(10)  NOT NULL,
    account_number   varchar(20)  NOT NULL,
    cusip            varchar(20)  NOT NULL,
    transaction_type varchar(20)  NOT NULL,
    instrument_type  varchar(10)  NOT NULL,
    country_code varchar(10) NOT NULL,

    PRIMARY KEY (order_id)
);
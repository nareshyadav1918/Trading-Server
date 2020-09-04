DROP TABLE IF EXISTS executed_orders;
commit;
DROP TABLE IF EXISTS account;
commit;
DROP TABLE IF EXISTS stock;
commit;

CREATE TABLE account(
    account_id VARCHAR(250)  NOT NULL,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    phone VARCHAR(250) NOT NULL,
    user_name VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    main_balance float NOT NULL,
    amount_invested float NOT NULL
);

CREATE TABLE stock(
  symbol_code VARCHAR(250) NOT NULL ,
  stock_price float NOT NULL,
  last_price float NOT NULL,
  symbol_name VARCHAR(250) NOT NULL
);

CREATE TABLE executed_orders(
  executed_orders_id INT AUTO_INCREMENT  PRIMARY KEY,
  account_id VARCHAR(250) NOT NULL,
  symbol_id VARCHAR(250) NOT NULL,
  quantity  int NOT NULL,
  price  float NOT NULL,
  total_price float NOT NULL,
  current_dt DATE default CURRENT_DATE
);











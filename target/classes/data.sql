Delete  from stock;
commit;
Delete  from account;
commit;

INSERT INTO stock (symbol_code, stock_price, last_price, symbol_name) VALUES
  ('005158', 50.51, 50.52, 'Boeing');
commit;
INSERT INTO stock (symbol_code, stock_price, last_price, symbol_name) VALUES
  ('005159', 51.51,  51.52,'Apple');
  commit;
INSERT INTO stock (symbol_code, stock_price,last_price , symbol_name) VALUES
  ('005153', 22.50, 20.52, 'Microsoft');
  commit;
INSERT INTO stock (symbol_code, stock_price, last_price,symbol_name) VALUES
  ('005154', 40.5, 47.5, 'Facebook');
commit;
INSERT INTO account (account_id, first_name, last_name,email,phone,user_name,password,main_balance,amount_invested) VALUES
  ('UMD0034', 'Matt', 'Blanc','Matt_Blanc@gmail.com','0123456789','Matt','12345',1000.0,0.0);
commit;
INSERT INTO account (account_id, first_name, last_name,email,phone,user_name,password,main_balance,amount_invested) VALUES
  ('UMD0035', 'Nicholas', 'Tesla','Nicholas_Teslaa@gmail.com','0123456789','Nicholas','12345',1000.0,0.0);

COMMIT;
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (1000,'zapatos','z000',10,50)
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (2000,'gorra','g000',20,10)
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (3000,'remera','r000',30,25)
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (4000,'pantalones','p000',30,30)

insert into Country (COUNTRYID,COUNTRYNAME,ISOCODE) values (5000, 'Italia','ITL' )
insert into Country (COUNTRYID,COUNTRYNAME,ISOCODE) values (6000, 'Chile','CHL' )

insert into City (city_id,CITYNAME,COUNTRY) values (7000,'venecia',5000)
insert into City (city_id,CITYNAME,COUNTRY) values (8000,'Resistencia',5000)
insert into City (city_id,CITYNAME,COUNTRY) values (9000,'Santiago',6000)
insert into City (city_id,CITYNAME,COUNTRY) values (1000,'Valparaiso',6000)

insert into SHIPPINGADDRESS (SHIPPINGADDRESSID,city_id,POSTALADDRESS) values (1300,7000,3030)

insert into CHECKOUT (CHECKOUTID,SHOPPINGCART,SHIPPINGADDRESS,PAYMENTSTRATEGY) values (1100,null,null,0)

insert into USERS (userId,USERNAME,email,checkout,KEYCLOAKID) values (12,'juan','miMail@hotmail',1100,null)

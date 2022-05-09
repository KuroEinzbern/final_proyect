insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (1,'zapatos','z000',10,50)
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (2,'gorra','g000',20,10)
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (3,'remera','r000',30,25)
insert into PRODUCTS (id,PRODUCTNAME, PRODUCTCODE,stock,UNITPRICE) values (4,'pantalones','p000',30,30)

insert into Country (COUNTRYID,COUNTRYNAME,ISOCODE) values (5, 'Argentina','ARG' )
insert into Country (COUNTRYID,COUNTRYNAME,ISOCODE) values (6, 'Chile','CHL' )

insert into City (city_id,CITYNAME,COUNTRY) values (7,'Buenos Aires',5)
insert into City (city_id,CITYNAME,COUNTRY) values (8,'Resistencia',5)
insert into City (city_id,CITYNAME,COUNTRY) values (9,'Santiago',6)
insert into City (city_id,CITYNAME,COUNTRY) values (10,'Valparaiso',6)

insert into CHECKOUT (CHECKOUTID,SHOPPINGCART,SHIPPINGADDRESS,PAYMENTSTRATEGY) values (11,null,null,0)

insert into USERS (userId,USERNAME,email,checkout,KEYCLOAKID) values (12,'juan','miMail@hotmail',11,null)

use estore_db;
select * from product;


INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (1, "Yaourtière", "Electroménager", 59.99, 1);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (2, "Lave Vaisselle", "Electroménager", 219.98, 8);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (3, "Micro-onde", "Electroménager", 89.90, 5);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (4, "Four", "Electroménager", 379, 3);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (5, "PC Portable", "Electroménager", 299, 3);


INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (6, "6 pieds de tomates", "Jardin", 3.45, 2);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (7, "1 Sac de terreau", "Jardin", 7.90, 1);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (8, "Jardinière", "Jardin", 19.90, 2);


INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (9, "Yaourt", "Alimentation", 2.45, 2);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (10, "Gateaux", "Alimentation", 4.90, 3);
INSERT INTO PRODUCT(productId, label, category, priceInEuro, stock) values (11, "Salade", "Alimentation", 0.99, 4);

select * from COMMENT;

INSERT INTO COMMENT(commentId, productId, author, note, text) values (1, 3, "Alice", 4, "Super produit !");
INSERT INTO COMMENT(commentId, productId, author, note, text) values (2, 3, "Bob", 5, "Excellent !");
INSERT INTO COMMENT(commentId, productId, author, note, text) values (3, 3, "Charlie", 1, "Nul ! Ne sait pas faire les gateaux ...");
INSERT INTO COMMENT(commentId, productId, author, note, text) values (4, 3, "David", 3, "Bien mais pas top.");
DELETE FROM library.reservation;
DELETE FROM library.penalty;
DELETE FROM library.loan_historical;
DELETE FROM library.book_exemplary;
DELETE FROM library.book;
DELETE FROM library.librarian;
DELETE FROM library.`user`;

INSERT INTO library.`user` (`type`,flat,`number`,postal_code,street,first_name,last_name,mail,name,nickname,password,state,tutor,department) VALUES('STUDENT','SN','SN','68410','20 de Noviembre','hernandez','martinez','mail1@example.com','gabriel','gabrielmar','12345678','ACTIVE','jesus uscanga hernandez',NULL);
INSERT INTO library.`user` (`type`,flat,`number`,postal_code,street,first_name,last_name,mail,name,nickname,password,state,tutor,department) VALUES('STUDENT','SN','SN','68415','Brena Torres','uscanga','hernandez','mail2@example.com','jesus','jesusher','12345678','ACTIVE','adela acosta mota',NULL);
INSERT INTO library.`user` (`type`,flat,`number`,postal_code,street,first_name,last_name,mail,name,nickname,password,state,tutor,department) VALUES('STUDENT','SN','SN','68415','Loma Bonita','ramirez','huerta','mail3@example.com','eduardo','eduardohue','12345678','PENALIZED','manuel vela vazquez',NULL);
INSERT INTO library.`user` (`type`,flat,`number`,postal_code,street,first_name,last_name,mail,name,nickname,password,state,tutor,department) VALUES('TEACHER','SN','SN','68415','Miguel Hidalgo','acosta','mota','mail4@example.com','adela','adelamot','12345678','ACTIVE',NULL,'Computer Science');
INSERT INTO library.`user` (`type`,flat,`number`,postal_code,street,first_name,last_name,mail,name,nickname,password,state,tutor,department) VALUES('TEACHER','SN','SN','68415','Juan Aldama','muñoz','del valle','mail5@example.com','diego','diegoval','12345678','ACTIVE',NULL,'Computer Science');
INSERT INTO library.`user` (`type`,flat,`number`,postal_code,street,first_name,last_name,mail,name,nickname,password,state,tutor,department) VALUES('TEACHER','SN','SN','68415','1 de Mayo','vela','vazquez','mail6@example.com','manuel','manuelvaz','12345678','ACTIVE',NULL,'Computer Science');

INSERT INTO library.librarian (nickname,nif,password) VALUES('jalexisrdv','nif','12345678');

INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Robert C. Martin','9780132350884',464,'2022-08-23',2,'Clean Code');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Robert C. Martin','0134494164',432,'2022-08-23',1,'Clean Architecture');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Christine Bresnahan, Christopher Negus','111821854X',864,'2022-08-23',1,'Linux Bible');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Gayle Laakmann McDowell','0984782869',687,'2022-08-23',1,'Cracking the Coding Interview: 189 Programming Questions and Solutions 6th Edition');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Thomas H Cormen, Charles E Leiserson, Ronald L Rivest, Clifford Stein','9780262033848',1292,'2022-08-23',1,'Introduction to Algorithms');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Robert C. Martin','0137081073',256,'2022-08-23',1,'The Clean Coder: A Code of Conduct for Professional Programmers');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Erich Gamma, Richard Helm, Ralph Johnson Dr, John Vlissides','0201633612',416,'2022-08-23',1,'Design Patterns: Elements of Reusable Object-Oriented Software');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('David Thomas, Andrew Hunt','0135957052',352,'2022-08-23',1,'The Pragmatic Programmer: Your Journey to Mastery, 20th Anniversary Edition');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Martin Fowler','0134757599',448,'2022-08-23',1,'Refactoring: Improving the Design of Existing Code');
INSERT INTO library.book (author,isbn,page_number,register_date,stock,title) VALUES('Lasse Koskela','9781932394856',470,'2022-08-23',1,'Test Driven: TDD and Acceptance TDD for Java Developers');

INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',1,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',2,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',3,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',4,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',5,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',6,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',7,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',8,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',9,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','A',NULL,'SITTING_ROOM','New Book',NULL,'AVAILABLE',10,NULL);
INSERT INTO library.book_exemplary (acquisition_date,exemplary_id,lend_date,location,observation,return_date,state,book_id,user_id) VALUES('2022-08-23','B',NULL,'SITTING_ROOM','New Book',NULL,'LENT',1,1);

INSERT INTO library.loan_historical (lend_date,real_return_date,return_date,book_exemplary_id,user_id) VALUES('2022-09-10','2022-10-17','2022-10-17',1,1);

INSERT INTO library.penalty (end_date,start_date,state,user_id) VALUES('2022-09-21','2022-10-19','ACTIVE',1);

INSERT INTO library.reservation (end_date,finalization_state,historical_state,start_date,book_id,user_id) VALUES('2022-10-17','DONE','ACTIVE','2022-09-10',1,1);

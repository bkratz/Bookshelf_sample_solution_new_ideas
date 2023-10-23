INSERT INTO author(firstname, lastname) VALUES ('Erich', 'Gamma');
INSERT INTO book (isbn, title, description) VALUES ('978-0201633610', 'Design Patterns', 'Mit Design Patterns lassen sich wiederkehrende Aufgaben in der objektorientierten Softwareentwicklung effektiv lösen.');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT INTO author(firstname, lastname) VALUES ('Richard', 'Helm');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT INTO author(firstname, lastname) VALUES ('Ralph', 'Johnson');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT INTO author(firstname, lastname) VALUES ('John', 'Vlissides');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT INTO author(firstname, middlename, lastname) VALUES ('Robert', 'C.', 'Martin');
INSERT INTO book (isbn, title, description) VALUES ('978-3826655487', 'Clean Code', 'Das einzige praxisnahe Buch, mit dem Sie lernen, guten Code zu schreiben!');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT INTO book (isbn, title, description) VALUES ('978-3958457249', 'Clean Architecture', 'Das Praxis-Handbuch für professionelles Softwaredesign.Regeln und Paradigmen für effiziente Softwarestrukturierung.');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT INTO author(firstname, lastname) VALUES ('Gottfried', 'Wolmeringer');
INSERT INTO book (isbn, title, description) VALUES ('978-3836211161', 'Coding for Fun', 'Dieses unterhaltsam geschriebene Buch führt Sie spielerisch durch die spektakuläre Geschichte unserer Blechkollegen.');
INSERT INTO book_authors(authors_id, book_id) VALUES ((SELECT CURRVAL('author_id_seq')),(SELECT CURRVAL('book_id_seq')));

INSERT IGNORE INTO users(id, username, password, name, surname)
VALUES (1, 'admin', '$2a$10$0eSnmwX08Hro2CQrEJpDIOp1H1GVmz1kHzf96SHLGeEHKg45kJj92','admin','admin');

INSERT IGNORE INTO admins(id)
VALUES (1);
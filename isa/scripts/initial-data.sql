INSERT INTO isa_role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO isa_role (id, name) VALUES (2, 'ROLE_DRIVER');
INSERT INTO isa_role (id, name) VALUES (3, 'ROLE_ADMIN');

INSERT INTO isa_user(id, email, enabled, firstname, is_blocked, last_password_reset_date, lastname, password, username)
VALUES (1, 'admin@isa-uber.com', true, 'Admin', false, CURRENT_TIMESTAMP, 'Admin', '$2a$10$/UxPf65CGIrC2n/Z5L5CHu/n1NC0DgD0dNKN07NSwpytDL5Add4uS', 'admin');

INSERT INTO public.isa_user_role(user_id, role_id) VALUES (1, 3);
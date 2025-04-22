    use librovuelo;



--         antes de correr este query creear un usuario con Id 1
--     INSERT INTO user(id, nombre, apellido,username ,password,  enabled)
--     VALUES(1,'admin','admin','admin01','$2a$10$NGnnd/xIPR85G.4QAgCWeuIEXu8UEoZ7Bkpv6Js/PjO6EvjhkuF.C',0);


    INSERT INTO user_roles(users_id, roles_id)
    VALUES(1,1),(1,3),(1,4),(1,5);


    INSERT INTO `librovuelo`.`habilitar_vacaciones`  (`id`, `estado_registro`, `habilitar`, `habilitar_pedido_de_vacaciones_desde`, `habilitar_pedido_de_vacaciones_hasta`)
    VALUES (1, 0, 1, '2023-10-17', '2023-10-17');

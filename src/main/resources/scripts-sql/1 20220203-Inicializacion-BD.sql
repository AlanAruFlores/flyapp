    use librovuelo;

    INSERT INTO aeropuerto(codigo, direccion, nombre, provincia)
    VALUES
        ('AEP','','Aeroparque ','CABA'),
        ('EPA','','El Palomar','Buenos Aires'),
        ('EZE','','Ezeiza','Buenos Aires'),
    ('BHI','','Bahía Blanca','Bahía Blanca'),
    ('BRC','','Bariloche','Río Negro'),
    ('CNQ','','Corrientes','Corrientes'),
    ('COR','','Córdoba','Córdoba'),
    ('IGR','','Puerto Iguazú','Misiones'),
    ('JUJ','','Perico','Jujuy'),
    ('MDZ','','Mendoza','Mendoza'),
    ('NQN','','Neuquén','Neuquén'),
    ('PSS','','Posadas','Misiones'),
    ('REL','','Trelew','Chubut'),
    ('RES','','Resistencia','Chaco'),
    ('ROS','','Rosario','Rosario'),
    ('SDE','','Santiago del Estero','Santiago del Estero'),
    ('SLA','','Salta','Salta'),
    ('TUC','','Tucumán','Tucumán'),
    ('ASU','','Asuncion','Paraguay'),
    ('PDP','','Punta Del este','Uruguay'),

    ('FLN','','Florianopolis','Brasil'),
    ('GIG','','Rio de Janeiro','Brasil'),

    ('AFA','','San Rafael Mendoza','Mendoza'),
    ('AOL','','Paso de los Libres - Corrientes ','Corrientes '),
    ('APZ','','Zapala','Neuquén'),
    ('ARR','','Alto Río Senguer','Chubut'),
    ('CCT','','Catriel','Río Negro'),
    ('CLX','','Clorinda','Formosa'),
    ('COC','','Concordia','Entre Ríos'),
    ('CPC','','San Martín de los Andes','Neuquén'),
    ('CRD','','Comodoro Rivadavia','Chubut'),
    ('CRR','','Ceres','Santa Fe'),
    ('CSZ','','Coronel Suárez','Buenos Aires'),
    ('CTC','','San Fernando del Valle de Catamarca','Catamarca'),
    ('CUT','','Cutral-Co','Neuquén'),
    ('CVH','','Caviahue','Neuquén'),
    ('CVI','','Caleta Olivia','Santa Cruz'),
    ('EHL','','El Bolsón','Río Negro'),
    ('ELO','','Eldorado','Misiones'),
    ('EMX','','El Maitén','Chubut'),
    ('EQS','','Esquel','Chubut'),
    ('FDO','','San Fernando','Buenos Aires'),
    ('FMA','','Formosa','Formosa'),
    ('FTE','','El Calafate','Santa Cruz'),
    ('GGS','','Gobernador Gregores','Santa Cruz'),
    ('GHU','','Gualeguaychú','Entre Ríos'),
    ('GNR','','General Roca','Río Negro'),
    ('GPO','','General Pico','La Pampa'),
    ('HOS','','Chos Malal','Neuquén'),
    ('IGB','','Ingeniero Jacobacci','Río Negro'),
    ('IRJ','','La Rioja','La Rioja'),
    ('JNI','','Junín','Buenos Aires'),
    ('JSM','','José de San Martín','Chubut'),
    ('LCM','','La Cumbre','Córdoba'),
    ('LCP','','Loncopué','Neuquén'),
    ('LGS','','Malargüe','Mendoza'),
    ('LHS','','Las Heras','Santa Cruz'),
    ('LLS','','Las Lomitas','Formosa'),
    ('LPG','','La Plata','Buenos Aires'),
    ('LUQ','','San Luis','San Luis'),
    ('MCS','','Monte Caseros','Corrientes'),
    ('MDQ','','Mar del Plata','Buenos Aires'),
    ('MDX','','Mercedes','Corrientes'),
    ('MJR','','Miramar','Buenos Aires'),
    ('NCJ','','Sunchales','Santa Fe'),
    ('NEC','','Necochea','Buenos Aires'),
    ('OES','','San Antonio Oeste','Río Negro'),
    ('OLN','','Sarmiento','Chubut'),
    ('ORA','','San Ramón de la Nueva Orán','Salta'),
    ('OVR','','Olavarría','Buenos Aires'),
    ('OYA','','Goya','Corrientes'),
    ('OYO','','Tres Arroyos','Buenos Aires'),
    ('PEH','','Pehuajó','Buenos Aires'),
    ('PMQ','','Perito Moreno','Santa Cruz'),
    ('PMY','','Puerto Madryn','Chubut'),
    ('PRA','','Paraná','Entre Ríos'),
    ('PRQ','','Presidencia Roque Saenz Peña','Chaco'),
    ('PUD','','Puerto Deseado','Santa Cruz'),
    ('RAF','','Rafaela','Santa Fe'),
    ('RCQ','','Reconquista','Santa Fe'),
    ('RCU','','Río Cuarto','Córdoba'),
    ('RDS','','Rincón de los Sauces','Neuquén'),
    ('RGA','','Río Grande','Tierra del Fuego'),
    ('RGL','','Río Gallegos','Río Gallegos'),
    ('RHD','','Termas de Río Hondo','Santiago del Estero'),
    ('RLO','','Merlo','San Luis'),
    ('ROY','','Río Mayo','Chubut'),
    ('RSA','','Santa Rosa','La Pampa'),
    ('RYO','','Río Turbio/Veintiocho de Noviembre','Santa Cruz'),
    ('RZA','','Puerto Santa Cruz','Santa Cruz'),
    ('SA11','','Santa María','Catamarca'),
    ('SFN','','Sauce Viejo','Santa Fe'),
    ('SGV','','Sierra Grande','Río Negro'),
    ('SST','','Santa Teresita','Buenos Aires'),
    ('TDL','','Tandil','Buenos Aires'),
    ('TTG','','Tartagal','Salta'),
    ('UAQ','','San Juan','San Juan'),
    ('ULA','','Puerto San Julián','Santa Cruz'),
    ('USH','','Ushuaia','Tierra del Fuego'),
    ('UZU','','Curuzú Cuatiá','Corrientes'),
    ('VCF','','Valcheta','Río Negro'),
    ('VDM','','Viedma','Río Negro'),
    ('VDR','','Dolores','Buenos Aires'),
    ('VDC','','Villa Dolores','Córdoba'),
    ('VLG','','Villa Gesell','Buenos Aires'),
    ('VME','','Villa Reynolds','San Luis'),
    ('VMR','','Villa María','Córdoba'),
    ('VRG','','Villa Regina','Río Negro');

    INSERT INTO avion (marca, matricula, nombre) VALUES
    ('Boeing 737-800','LV-HKS','Nelson'),
    ('Boeing 737-800','LV-HFR','Arturo'),
    ('Boeing 737-800','LV-HKR','Hary'),
    ('Boeing 737-800','LV-HQY','Valkiria'),
    ('Boeing 737-800','LV-HFQ','Max'),
    ('Boeing 737-800','LV-HKN','Sonic');


    INSERT INTO horario_por_estacion (comienzo_horario_diurno, comienzo_horario_nocturno, estacion) VALUES
    ('09:00:00','23:01:00','VERANO'),
    ('10:00:00','21:01:00','OTOÑO'),
    ('10:00:00','21:31:00','INVIERNO'),
    ('08:00:00','22:30:00','PRIMAVERA');

    INSERT INTO `librovuelo`.`role` (`name`) VALUES ('ROLE_ADMINISTRADOR');
    INSERT INTO `librovuelo`.`role` (`name`) VALUES ('ROLE_TRIPULANTE');
    INSERT INTO `librovuelo`.`role` (`name`) VALUES ('ROLE_LIDER');
    INSERT INTO `librovuelo`.`role` (`name`) VALUES ('ROLE_OPERACIONES');
    INSERT INTO `librovuelo`.`role` (`name`) VALUES ('ROLE_INSTRUCTOR');


    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (1, 'Generacion 1', '2017-09-18', 1);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (2, 'Generacion 2', '2018-01-27', 2);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (3, 'Generacion 3', '2018-03-08', 3);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (4, 'Generacion 4', '2018-04-01', 4);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (5, 'Generacion 5', '2018-05-31', 5);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (6, 'Generacion 6', '2018-06-04', 6);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (7, 'Generacion 7', '2018-08-21', 7);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (8, 'Generacion 8', '2022-01-15', 8);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (9, 'Generacion 9', '2022-02-15', 9);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (10, 'Generacion 10', '2022-05-19', 10);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (11, 'Generacion 11', '2022-06-16', 11);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (12, 'Generacion 12', '2022-06-21', 12);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (13, 'Generacion 0', '2017-09-18', 0);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (14, 'Generacion 14', '2022-07-01', 14);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (15, 'Generacion 15', '2022-11-22', 15);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (16, 'Generacion 16', '2022-12-01', 16);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (17, 'Generacion 17', '2023-01-02', 17);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (18, 'Generacion 18', '2023-03-01', 18);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (19, 'Generacion 19', '2023-05-15', 19);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (20, 'Generacion 20', '2023-05-15', 20);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (21, 'Generacion 21', '2023-07-01', 21);
    INSERT INTO `librovuelo`.`generacion` (`id`, `descripcion`, `fecha_ingreso`, `numero`) VALUES (22, 'Generacion 22', '2023-07-01', 22);


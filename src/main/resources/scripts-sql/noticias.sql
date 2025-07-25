-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-11-2022 a las 16:21:00
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `librovuelo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `noticia`
--

CREATE TABLE `noticia` (
  `id` bigint(20) NOT NULL,
  `descripcion` longtext DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `autor_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `noticia`
--

INSERT INTO `noticia` (`id`, `descripcion`, `fecha`, `titulo`, `autor_id`) VALUES
(15, '<p>A d&iacute;as de la llegada del noveno avi&oacute;n, Flybondi le da la bienvenida a su d&eacute;cimo Boeing 737-800 NG con capacidad para 189 personas. La llegada de la nueva aeronave demand&oacute; m&aacute;s de 30 horas de operaci&oacute;n, ya que su recorrido se inici&oacute; en la ciudad de Sacheon (Corea del Sur) hacia Chitose (Jap&oacute;n), sigui&oacute; con una escala en Alaska; luego en San Antonio (USA) y una tercera en Guayaquil (Ecuador) para luego aterrizar en el Aeropuerto Internacional de Ezeiza. El proceso de traslado de la aeronave estuvo a cargo de personal de la compa&ntilde;&iacute;a: particip&oacute; una tripulaci&oacute;n de 7 pilotos y 2 mec&aacute;nicos.</p>\r\n<p>De esta manera, Flybondi culmina&nbsp; su plan inicial&nbsp; de crecimiento e inversi&oacute;n en el que se propuso duplicar su flota, su oferta y la cantidad de pasajeros transportados. Luego de un a&ntilde;o del anuncio de su Plan 2X la compa&ntilde;&iacute;a ya anunci&oacute; que sumar&aacute; dos unidades m&aacute;s antes de fin de a&ntilde;o, superando de esta manera el plan original para terminar&nbsp; el 2022 con una flota total de 12 unidades. Este incremento de capacidad permitir&aacute; aumentar y reforzar las frecuencias de las rutas nacionales e internacionales, y tambi&eacute;n prepararse para la temporada de verano. Con respecto al pr&oacute;ximo a&ntilde;o, Flybondi anunci&oacute; que lanzar&aacute; al mercado de leasing una b&uacute;squeda de por lo menos 5 aviones Boeing 737-800 para sumar durante 2023.</p>\r\n<p>Durante este &uacute;ltimo a&ntilde;o Flybondi incorpor&oacute; m&aacute;s de 500 empleados y gener&oacute; m&aacute;s de 13.500 empleos indirectos en todo el territorio argentino. Por otro lado, aument&oacute; sus frecuencias a toda su rutas nacionales e internacionales. Adem&aacute;s de incorporar 4 destinos de cabotaje&nbsp; Ushuaia, Puerto Madryn, El Calafate y Comodoro Rivadav&iacute;a, de esta manera ampli&oacute; la conectividad con el sur del pa&iacute;s. En la actualidad ya vuela a 5 provincias, a 17 destinos nacionales y 2 destinos internacionales.</p>\r\n<p><em>&ldquo;El crecimiento de este a&ntilde;o es el resultado del compromiso de los que hacemos Flybondi hacia nuestros clientes. Fueron meses de mucho esfuerzo, donde nos encontramos con un escenario complejo en la ejecuci&oacute;n de un plan de inversi&oacute;n de esta magnitud. Empezamos 2022 con 4 aviones y hoy nuestra flota tiene 10, con un aporte de m&aacute;s de 200 millones de d&oacute;lares en la industria argentina y una apuesta a futuro de largo plazo y sostenible. Apuntamos a seguir consolid&aacute;ndonos como la aerol&iacute;nea con mayor crecimiento de los &uacute;ltimos a&ntilde;os en Argentina y la regi&oacute;n&rdquo;,</em>&nbsp;coment&oacute; Mauricio Sana, CEO de Flybondi.&nbsp;</p>\r\n<p>La compa&ntilde;&iacute;a low cost ya transport&oacute; m&aacute;s de 1.850.000 pasajeros en el 2022, tanto a nivel dom&eacute;stico como internacional. De esta manera, la aerol&iacute;nea reafirma su prop&oacute;sito fundacional: que m&aacute;s personas puedan viajar en avi&oacute;n en Argentina y la regi&oacute;n, accediendo a un servicio seguro con tarifas bajas y est&aacute;ndares de calidad internacionales.</p>\r\n<p><strong>Flybondi en n&uacute;meros:</strong></p>\r\n<ul>\r\n<li>\r\n<p>Comenz&oacute; a operar en Argentina en 2018 y ya transport&oacute; a m&aacute;s de 5.500.000 personas</p>\r\n</li>\r\n<li>\r\n<p>El 20% de sus pasajeros son personas que vuelan en avi&oacute;n por primera vez en su vida.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una flota de 10 aviones Boeing 737-800 NG con 189 asientos de &uacute;nica clase.</p>\r\n</li>\r\n<li>\r\n<p>Vuela a 17 destinos dom&eacute;sticos: Buenos Aires, Bariloche, Corrientes, C&oacute;rdoba, Jujuy, Mendoza, Neuqu&eacute;n, Posadas, Iguaz&uacute;, Salta, Santiago del Estero, Trelew, Tucum&aacute;n, Puerto Madryn, Ushuaia, El Calafate y Comodoro Rivadavia.&nbsp;</p>\r\n</li>\r\n<li>\r\n<p>Actualmente vuela a dos destinos internacionales en Brasil: R&iacute;o de Janeiro y San Pablo. Y en diciembre, retoma sus vuelos a Florian&oacute;polis.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una cuota de mercado dom&eacute;stica del 17% y 5% de cuota de mercado internacional (octubre 2022).</p>\r\n</li>\r\n</ul>', '2022-11-28', 'Flybondi finalizó su Plan 2X: recibió su décimo avión y comienza una nueva etapa de crecimiento', 2),
(16, '<p>Flybondi, la primera aerol&iacute;nea low cost de Argentina, recibi&oacute; hoy su noveno avi&oacute;n que arrib&oacute; a las 20hs al Aeropuerto Internacional de Ezeiza proveniente del estado de Arkansas, Estados Unidos. La nueva aeronave es un Boeing 737-800 NG con capacidad para 189 personas, que recibir&aacute; la matr&iacute;cula LV - KEG luego de realizar los procesos administrativos de matriculaci&oacute;n y certificaci&oacute;n.&nbsp;&nbsp;</p>\r\n<p>A 11 meses del anuncio de su Plan 2X de inversi&oacute;n y crecimiento la compa&ntilde;&iacute;a ya incorpor&oacute; 5 aeronaves y recientemente anunci&oacute; que sumar&aacute; dos m&aacute;s para completar una flota de 12 aviones. El incremento de capacidad permitir&aacute; aumentar y reforzar las frecuencias de las rutas tanto nacionales como internacionales, y tambi&eacute;n prepararse para la temporada de verano con una oferta muy fuerte en el mercado dom&eacute;stico y en las rutas a Brasil. Con respecto al pr&oacute;ximo a&ntilde;o, Flybondi anunci&oacute; que lanzar&aacute; al mercado de leasing una b&uacute;squeda de por lo menos 5 aviones Boeing 737-800 para sumar durante 2023.&nbsp;&nbsp;&nbsp;</p>\r\n<p><em>&ldquo;Estamos muy cerca de cumplir con los dos grandes objetivos que nos propusimos en diciembre cuando anunciamos el plan de crecimiento: duplicar&nbsp; la flota y la cantidad de pasajeros; y eso nos motiva a ir por m&aacute;s. Ya lanzamos la b&uacute;squeda de 5 nuevos aviones para 2023 y estamos convencidos que si el mercado nos acompa&ntilde;a tendremos posibilidades de llegar a una flota de 20 aviones en los pr&oacute;ximos 2 a&ntilde;os&rdquo;,&nbsp;</em>afirm&oacute; Mauricio Sana, CEO de la aerol&iacute;nea. Luego agreg&oacute;<em>: &ldquo;Este a&ntilde;o vimos una gran recuperaci&oacute;n en los vuelos nacionales y cifras r&eacute;cord de viajes y de turismo, y nuestra operaci&oacute;n ya creci&oacute; respecto de la pre pandemia. La proyecci&oacute;n del pr&oacute;ximo a&ntilde;o es sumar cada vez m&aacute;s personas a la libertad de volar&rdquo;.</em></p>\r\n<p><u>Temporada de verano</u><u><br></u>La aerol&iacute;nea sumar&aacute; frecuencias en diciembre en algunos de sus destinos nacionales como Puerto Iguaz&uacute; que pasa de 20 a 24 vuelos semanales, Mendoza de 19 a 23, Salta de 14 a 17, C&oacute;rdoba de 10 a 13, Neuqu&eacute;n de 9 a 11 y Santiago del Estero de 4 a 6.&nbsp;</p>\r\n<p>&nbsp;Para sus vuelos a Brasil, Flybondi retoma su ruta de Florian&oacute;polis a partir del 1 de diciembre y hasta abril, con una frecuencia diaria y en enero aumentar&aacute; a 2 diarios; la ruta desde y hacia R&iacute;o de Janeiro desde enero tendr&aacute; 3 frecuencias diarias; y la ruta de San Pablo, a partir de enero tendr&aacute; un vuelo diario.&nbsp;&nbsp;</p>\r\n<p>&nbsp;<strong>Flybondi en n&uacute;meros:</strong></p>\r\n<p>&nbsp;</p>\r\n<ul>\r\n<li>\r\n<p>Comenz&oacute; a operar en Argentina en 2018 y ya transport&oacute; a m&aacute;s de 5.500.000 personas</p>\r\n</li>\r\n<li>\r\n<p>El 20% de sus pasajeros son personas que vuelan en avi&oacute;n por primera vez en su vida.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una flota de 8 aviones Boeing 737-800 NG con 189 asientos de &uacute;nica clase.</p>\r\n</li>\r\n<li>\r\n<p>Vuela a 17 destinos dom&eacute;sticos: Buenos Aires, Bariloche, Corrientes, C&oacute;rdoba, Jujuy, Mendoza, Neuqu&eacute;n, Posadas, Iguaz&uacute;, Salta, Santiago del Estero, Trelew, Tucum&aacute;n, Puerto Madryn, Ushuaia, El Calafate y Comodoro Rivadavia.&nbsp;</p>\r\n</li>\r\n<li>\r\n<p>Actualmente vuela a dos destinos internacionales en Brasil: R&iacute;o de Janeiro y San Pablo. Y en diciembre, retoma sus vuelos a Florian&oacute;polis.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una cuota de mercado dom&eacute;stica del 17% y 5% de cuota de mercado internacional (octubre 2022)..</p>\r\n</li>\r\n</ul>', '2022-11-24', 'Flybondi recibió su novena aeronave y en las próximas semanas completará una flota de 10 aviones', 2),
(17, '<p>Flybondi, la primera aerol&iacute;nea low cost del pa&iacute;s, obtuvo la certificaci&oacute;n internacional de Great Place to Work como uno de los mejores lugares para trabajar en Argentina. El programa mide la calidad de la cultura organizacional basada &iacute;ntegramente en las respuestas de los empleados. Flybondi se convierte as&iacute; en la &uacute;nica aerol&iacute;nea en el pa&iacute;s certificada actualmente.</p>\r\n<p>&nbsp;</p>\r\n<p>La compa&ntilde;&iacute;a se encuentra en pleno crecimiento y en pocos meses duplic&oacute; su equipo de trabajo en el pa&iacute;s. Hoy ya cuenta con m&aacute;s de 1.050 empleados en todas las provincias en las que tiene operaci&oacute;n. Este incremento se enmarca dentro del Plan 2X, presentado en diciembre de 2021 que apunta a duplicar la cantidad de pasajeros, a trav&eacute;s del incremento de la oferta y de la flota. Incluso hoy la compa&ntilde;&iacute;a contin&uacute;a contratando personas para las diferentes &aacute;reas de la empresa que contin&uacute;an expandi&eacute;ndose al incorporar m&aacute;s aviones y destinos.</p>\r\n<p>&nbsp;</p>\r\n<p><em>\"El crecimiento de este a&ntilde;o no s&oacute;lo se traduce en aviones, destinos, frecuencias y m&aacute;s flybondiers. Tambi&eacute;n en dar el paso de validar nuestra cultura y clima de trabajo con todas las personas que d&iacute;a a d&iacute;a son los verdaderos hacedores de la libertad de volar. Y ac&aacute; estamos, celebrando felices que logramos la certificaci&oacute;n de Great Place To Work que confirma que Flybondi es uno de los mejores lugares para trabajar en Argentina\",&nbsp;</em>coment&oacute; Mauricio Sana, CEO de Flybondi<em>.</em></p>\r\n<p>&nbsp;</p>\r\n<p><strong>Flybondi en n&uacute;meros:</strong></p>\r\n<ul>\r\n<li>\r\n<p>Comenz&oacute; a operar en Argentina en 2018 y ya transport&oacute; a m&aacute;s de 5.000.000 personas</p>\r\n</li>\r\n<li>\r\n<p>El 20% de sus pasajeros son personas que vuelan en avi&oacute;n por primera vez en su vida.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una flota de 8 aviones Boeing 737-800 NG con 189 asientos de &uacute;nica clase.</p>\r\n</li>\r\n<li>\r\n<p>Vuela a 17 destinos dom&eacute;sticos: Buenos Aires, Bariloche, Corrientes, C&oacute;rdoba, Jujuy, Mendoza, Neuqu&eacute;n, Posadas, Iguaz&uacute;, Salta, Santiago del Estero, Trelew, Tucum&aacute;n, Puerto Madryn, Ushuaia, El Calafate y Comodoro Rivadavia.&nbsp;</p>\r\n</li>\r\n<li>\r\n<p>Actualmente vuela a dos destinos internacionales en Brasil: R&iacute;o de Janeiro y San Pablo. Y en diciembre, retoma sus vuelos a Florian&oacute;polis.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una cuota de mercado dom&eacute;stica del 17% y 5% de cuota de mercado internacional (octubre 2022).</p>\r\n</li>\r\n</ul>', '2022-11-20', 'Flybondi obtuvo la certificación de Great Place to Work como uno de los mejores lugares para trabajar en Argentina', 2),
(18, '<p>En el marco de la 18va edici&oacute;n de ALTA AGM &amp; Airline Leaders Forum, la principal conferencia de l&iacute;deres de la industria de la aviaci&oacute;n comercial en Am&eacute;rica Latina y el Caribe, organizada por la Asociaci&oacute;n Latinoamericana y del Caribe de Transporte A&eacute;reo (ALTA), la primera low cost del pa&iacute;s anunci&oacute; la llegada de m&aacute;s aviones para este a&ntilde;o, adelant&oacute; la posible flota del 2023 e hizo un repaso sobre el Plan 2X de crecimiento e inversi&oacute;n establecido para el 2022.</p>\r\n<p>La compa&ntilde;&iacute;a recibi&oacute; este mes el 8vo avi&oacute;n de su flota y pr&oacute;ximamente, arribar&aacute;n dos nuevas aeronaves Boeing 737-800 NG. Adem&aacute;s, buscar&aacute; finalizar el a&ntilde;o con la llegada de dos unidades m&aacute;s para alcanzar una flota de 12 aviones. El incremento de capacidad permitir&aacute; aumentar y reforzar las frecuencias de las rutas tanto nacionales como internacionales, prepar&aacute;ndose tambi&eacute;n para una fuerte temporada alta de verano.<br><br>Asimismo, en su proyecci&oacute;n de continuar con el crecimiento de capacidad y oferta hacia nuevos destinos nacionales y regionales, la l&iacute;nea &aacute;rea anunci&oacute; que lanzar&aacute; al mercado de leasing una b&uacute;squeda de por lo menos 5 aviones Boeing 737-800 para sumar durante 2023.&nbsp;</p>\r\n<p>Flybondi, seg&uacute;n datos del Informe Mensual que realiza la Administraci&oacute;n Nacional de Aviaci&oacute;n Civil Argentina (ANAC), es la segunda aerol&iacute;nea m&aacute;s grande de Argentina en capacidad y con un 18% de cuota de mercado dom&eacute;stico, duplicando el market share de 2019.&nbsp;</p>\r\n<p>Adem&aacute;s, es la compa&ntilde;&iacute;a con mayor ocupaci&oacute;n en sus vuelos (94%). Cabe destacar que la primera low cost ya transport&oacute; un 87% m&aacute;s de pasajeros respecto a septiembre de 2019, y se acerca a uno de los objetivos propuesto al anunciar el plan de crecimiento.&nbsp;</p>\r\n<p><em>\"Nos encanta poder dar estas noticias en un escenario de recuperaci&oacute;n y crecimiento. Son motivo de orgullo de los m&aacute;s de 1050 flybondiers que d&iacute;a a d&iacute;a trabajamos para democratizar los vuelos en Argentina y la regi&oacute;n, convencidos de que hay un gran mercado por conquistar. Hoy pudimos confirmar que buscaremos tener 12 aviones operativos en los pr&oacute;ximos meses, y que nos lanzamos al mercado de leasing para buscar m&aacute;s flota para 2023, que traer&aacute;n nuevas rutas dom&eacute;sticas y m&aacute;s pa&iacute;ses de la regi&oacute;n conectados con Argentina.</em>&rdquo; asegur&oacute; Mauricio Sana, CEO de Flybondi.</p>\r\n<p>&nbsp;</p>\r\n<p><strong>Flybondi en n&uacute;meros:</strong></p>\r\n<ul>\r\n<li>\r\n<p>Comenz&oacute; a operar en Argentina en 2018 y ya transport&oacute; a m&aacute;s de 5.000.000 personas</p>\r\n</li>\r\n<li>\r\n<p>El 20% de sus pasajeros son personas que vuelan en avi&oacute;n por primera vez en su vida.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una flota de 8 aviones Boeing 737-800 NG con 189 asientos de &uacute;nica clase.</p>\r\n</li>\r\n<li>\r\n<p>Vuela a 17 destinos dom&eacute;sticos: Buenos Aires, Bariloche, Corrientes, C&oacute;rdoba, Jujuy, Mendoza, Neuqu&eacute;n, Posadas, Iguaz&uacute;, Salta, Santiago del Estero, Trelew, Tucum&aacute;n, Puerto Madryn, Ushuaia, El Calafate y Comodoro Rivadavia.&nbsp;</p>\r\n</li>\r\n<li>\r\n<p>Actualmente vuela a dos destinos internacionales en Brasil: R&iacute;o de Janeiro y San Pablo. Y en diciembre, retoma sus vuelos a Florian&oacute;polis.</p>\r\n</li>\r\n<li>\r\n<p>Tiene una cuota de mercado dom&eacute;stica del 18% y 5% de cuota de mercado internacional (septiembre 2022).</p>\r\n</li>\r\n</ul>', '2022-10-27', 'Más aviones: Flybondi apunta a una flota de 12 aeronaves para 2022', 2),
(19, '<p>Flybondi, la primera aerol&iacute;nea low cost del pa&iacute;s, presenta su nueva campa&ntilde;a &ldquo;Historias Reales&rdquo;, una serie de relatos narrados por primeros voladores, que forman parte de la campa&ntilde;a presentada a principios de a&ntilde;o, &ldquo;Vale m&aacute;s de lo que cuesta&rdquo;. Los cortos, grabados en diferentes destinos nacionales, tienen como objetivo visualizar el prop&oacute;sito fundacional de Flybondi: que m&aacute;s personas puedan tener la libertad de volar.&nbsp;</p>\r\n<p>Tomando esto como eje conceptual, se trabaj&oacute; junto con la agencia HOY Buenos Aires capturando esos momentos &uacute;nicos de las personas que volaron por primera vez en su vida en avi&oacute;n. El rodaje tuvo como principal objetivo llevar adelante la honestidad detr&aacute;s de cada historia buscando que sean 100% reales. Flybondi realiz&oacute; la b&uacute;squeda desde sus redes sociales y seleccionaron tres historias reales, cada una de ellas con un insight particular.</p>\r\n<p>&ldquo;<em>Pensamos una campa&ntilde;a que refleja el esp&iacute;ritu de Flybondi. Cada una de estos relatos nos muestran, de la mano de sus protagonistas, lo que significa volar por primera vez. Flybondi tiene una comunidad enorme que nos sigue porque tiene una historia con nosotros, con nuestra marca y eso es lo que buscamos transmitir. Esperamos seguir contando las miles de historias que recorren cada uno de nuestros vuelos</em>&rdquo;, mencion&oacute; Federico Pastori, Chief Commercial Officer.</p>\r\n<p>&nbsp;</p>\r\n<p><strong>Ficha t&eacute;cnica</strong></p>\r\n<p>COO: Elina M&eacute;ndez</p>\r\n<p>DGC: Malu Donaire</p>\r\n<p>Head of craft &amp; innovation: Hern&aacute;n Damilano</p>\r\n<p>Direcci&oacute;n Creativa: Santiago Ledesma - Dami&aacute;n Palopoli</p>\r\n<p>Directora de arte: Ver&oacute;nica Basmagi</p>\r\n<p>Redactora: Melina &Aacute;lvarez</p>\r\n<p>Directora de Cuentas: Sof&iacute;a Pereyra</p>\r\n<p>Supervisora de Cuentas: Agustina Castro</p>\r\n<p>Producci&oacute;n: Marcelo Vega</p>\r\n<p>Coordinadora Creativa: Dolores Manzur</p>\r\n<p>Productora de video: WaldenStudio.co</p>\r\n<p>Realizaci&oacute;n: Guido Cassini - Mat&iacute;as Pisani</p>\r\n<p>M&uacute;sica Original, Dise&ntilde;o de Sonido, Mezcla y Master: Twins Music House</p>\r\n<p>Producci&oacute;n General: Tom Huergo</p>\r\n<p>Direcci&oacute;n Musical: Max Scott</p>\r\n<p>Director Operativo: Jos&eacute; Violante</p>\r\n<p>Director Comercial: Jer&oacute;nimo Gonz&aacute;lez Montalvo</p>\r\n<p>Cliente: Mar&iacute;a Ang&eacute;lica Benetti, Bel&eacute;n De Bartolo, Federico Pastori, Gabriel Caro, Mat&iacute;as Gordon</p>', '2022-11-12', 'Flybondi presenta “Historias Reales”, su nueva campaña digital de primeros voladores', 3),
(20, '<p>Flybondi y TravelX anuncian su alianza estrat&eacute;gica que busca inaugurar una revolucionaria etapa en la industria de viajes: la tokenizaci&oacute;n de los pasajes a&eacute;reos y su evoluci&oacute;n a NFTickets.<br><br>Flybondi ser&aacute; la primera aerol&iacute;nea en sumarse a la plataforma Travel.xyz, permitiendo a sus usuarios comprar pasajes tokenizados y que podr&aacute;n ser adquiridos mediante Binance Pay. Junto con el lanzamiento, se ofrecer&aacute;n promociones y descuentos &uacute;nicos.<br>&nbsp;<br>Gracias a la tecnolog&iacute;a blockchain,&nbsp;<strong>TravelX</strong>&nbsp;presenta al mercado una plataforma que habilitar&aacute; la compra y venta de pasajes a&eacute;reos como NFTickets. En ella, se podr&aacute;n adquirir los&nbsp; NFTickets, as&iacute; como tambi&eacute;n transferir, revender a precio fijo o subastar los NFTickets previamente adquiridos.<br><br><strong><em>&iquest;Qu&eacute; es un NFTicket?</em></strong>&nbsp;En t&eacute;rminos t&eacute;cnicos, es la &ldquo;tokenizaci&oacute;n&rdquo; de un pasaje a&eacute;reo v&iacute;a tecnolog&iacute;a blockchain. La tokenizaci&oacute;n de los pasajes a&eacute;reos, evolucion&aacute;ndolos a NFTickets, inaugura as&iacute; una nueva etapa en donde la industria de viajes y el mundo de la nueva Web3 confluyen para brindar una experiencia mucho m&aacute;s flexible para los viajeros, generando al mismo tiempo nuevas fuentes de ingresos y una fuerte reducci&oacute;n de costos transaccionales a las aerol&iacute;neas.<br><br><strong><em>&iquest;Qu&eacute; quiere decir esto para los pasajeros?</em></strong>&nbsp;Que ahora los pasajes a&eacute;reos ofrecidos como NFTickets permitir&aacute;n reasignar el nombre del pasajero una vez comprado, y posibilita que el pasaje pueda ser transferido o revendido a otra persona f&aacute;cilmente hasta 3 d&iacute;as antes de la fecha de salida del vuelo.&nbsp;<br>&nbsp;<br>Esta innovaci&oacute;n en la industria permitir&aacute; una mayor flexibilidad para los viajeros quienes podr&aacute;n anticipar sus planes de viaje accediendo a mejores tarifas sin los riesgos asociados a las compras de pasajes con mucha antelaci&oacute;n. En caso de cambiar de planes o no poder realizar el viaje el pasajero ahora podr&aacute; transferirlo o revenderlo, recuperando el precio pagado o incluso ganando dinero si la tarifa a&eacute;rea ha subido.<br>&nbsp;<br>Con respecto a esta alianza, el&nbsp;<strong>CEO de Flybondi, Mauricio Sana</strong>&nbsp;coment&oacute;,&nbsp;<em>\"El ADN de Flybondi es brindar a las personas la libertad de volar y, para lograrlo, siempre hemos estado adoptando el cambio, la innovaci&oacute;n y la tecnolog&iacute;a. A trav&eacute;s de esta asociaci&oacute;n con TravelX, una vez m&aacute;s somos pioneros en innovaci&oacute;n y estamos muy contentos de utilizar la tecnolog&iacute;a blockchain para</em>&nbsp;<em>otorgar la mayor libertad a nuestros clientes\".</em><br>&nbsp;<br>Por su parte,&nbsp;<strong>Juan Pablo Lafosse, CEO y co-fundador de TravelX</strong>&nbsp;agreg&oacute;&nbsp;<em>\"Estamos muy contentos de anunciar esta alianza que da inicio a una nueva etapa de flexibilidad y libertad&nbsp; a los viajeros.&nbsp; Flybondi abre camino a una nueva era en la distribuci&oacute;n y comercializaci&oacute;n de pasajes a&eacute;reos como NFTickets, en la que muy pronto veremos a muchas otras aerol&iacute;neas seguir su camino. Encontramos en Flybondi un equipo fant&aacute;stico, innovador, y entusiasta y estamos muy felices de poder trabajar junto a ellos en esta misi&oacute;n\"</em>.&nbsp;<br><br>En esta primera etapa de lanzamiento se podr&aacute;n adquirir NFTickets de Flybondi a trav&eacute;s de la plataforma Travel.xyz y utilizando la billetera virtual Binance Pay, sin embargo en los pr&oacute;ximos meses se ampliar&aacute; a otros medios de pago y billeteras virtuales.<br><br>Para conocer la plataforma&nbsp;<strong>Travel.xyz</strong>&nbsp;ingres&aacute; a&nbsp;<a href=\"https://mktagencia.evlink1.net/servlet/link/248208/1193875/332076158/6233086\" target=\"_blank\" rel=\"noopener\">este link.</a><br>&nbsp;</p>\r\n<p><strong>Acerca de TravelX</strong><br><br>Dirigido por un experimentado equipo que ha fundado importantes compa&ntilde;&iacute;as de travel y tecnolog&iacute;a tales como Almundo, Avantrip, VRtify, Reality Code y ASATEJ, TravelX utiliza&nbsp; tecnolog&iacute;a blockchain para tokenizar el inventario de las l&iacute;neas a&eacute;reas, generando una industria con mayor flexibilidad para los viajeros as&iacute; como tambi&eacute;n una&nbsp; mejor rentabilidad para las aerol&iacute;neas.<br>TravelX es una compa&ntilde;&iacute;a de tecnolog&iacute;a de envergadura global. Est&aacute; basada en la ciudad estadounidense de Miami y posee oficinas en Berl&iacute;n y Buenos Aires.<br>En noviembre de 2021, TravelX ha cerrado una de las rondas iniciales de inversi&oacute;n m&aacute;s grandes en la industria de viajes, superando los $10MM, recibiendo fondos principalmente de inversores de la industria de viajes y el mundo blockchain. Entre sus principales inversores se encuentran: Algorand, Borderless capital, Golden Tree, Draper Cygnus y Barney Harford, ex COO de Uber y CEO de Orbitz.<br><br>Obtenga m&aacute;s informaci&oacute;n en&nbsp;<a href=\"https://mktagencia.evlink1.net/servlet/link/248208/1193875/332076158/6233087\" target=\"_blank\" rel=\"noopener\">www.travelx.io</a></p>', '2022-10-19', 'Flybondi se une con Travel X para transformarse en la primera aerolínea del mundo en ofrecer NFTickets', 3),
(21, '<p>Flybondi, la primera aerol&iacute;nea low cost del pa&iacute;s, incorpor&oacute; 500 personas a su equipo en el primer semestre del a&ntilde;o y contin&uacute;a con la apertura de nuevas posiciones para ampliar su capacidad. Actualmente tiene m&aacute;s de 60 b&uacute;squedas abiertas, dentro de todas las &aacute;reas.</p>\r\n<p>La compa&ntilde;&iacute;a, que se encuentra en pleno crecimiento, ya cuenta con m&aacute;s de 1.000 empleados, adem&aacute;s de generar m&aacute;s de 13.500 empleos indirectos en todo el territorio argentino. Este incremento se enmarca dentro del Plan 2X, presentado en diciembre de 2021, en el cual anunci&oacute; que llegar&iacute;a a casi 1050 empleados para 2022. Entre las b&uacute;squedas m&aacute;s destacadas de la industria ya se contrataron 60 pilotos, m&aacute;s de 115 tripulantes de cabina, 30 mec&aacute;nicos, m&aacute;s de 135 agentes de tr&aacute;fico y 70 agentes de rampa.</p>\r\n<p>El impacto de los puestos de trabajo se diversific&oacute; entre todos los destinos a los que vuela, asimismo las provincias en las que m&aacute;s oportunidades laborales se generaron fueron: Buenos Aires, R&iacute;o Negro, Neuqu&eacute;n, Chubut, Mendoza, Tucum&aacute;n, Salta, Jujuy y Misiones. De esta manera, se refuerza el compromiso no solo de crecimiento de la compa&ntilde;&iacute;a sino tambi&eacute;n con las provincias en las que opera.&nbsp;</p>\r\n<p><em>&ldquo;Tener nuestro Plan 2X como objetivo, nos permiti&oacute; focalizar en nuestras propias metas y la generaci&oacute;n de empleo era uno de los principales desaf&iacute;os. Nos propusimos crecer y duplicar nuestro equipo de trabajo, y eso sin dudas significa un enorme compromiso y responsabilidad con Argentina. Queremos seguir conectando al pa&iacute;s, su gente y vamos por el camino indicado&rdquo;,&nbsp;</em>expres&oacute; Mauricio Sana, CEO de Flybondi.</p>\r\n<p>Por su parte, Esteban Tossutti, Chief People Officer de la aerol&iacute;nea agreg&oacute;:&nbsp;<em>&ldquo;Incrementar las oportunidades laborales significa no s&oacute;lo un crecimiento a nivel compa&ntilde;&iacute;a sino tambi&eacute;n apostar a nuestros desaf&iacute;os profesionales. Somos una empresa joven y queremos crecer de manera conjunta con los talentos locales sumando nuevas miradas y brindando oportunidades diversas con un enfoque federal y regional&rdquo;.</em></p>\r\n<p>Flybondi es una empresa nativa digital y como tal tiene en su ADN beneficios y flexibilidades: pol&iacute;ticas h&iacute;bridas de trabajo como \"Trabaj&aacute; desde d&oacute;nde quieras\", hasta una pol&iacute;tica de viajes ilimitados para empleados y acompa&ntilde;antes (donde s&oacute;lo se abonan tasas e impuestos) y realizan adem&aacute;s, acuerdos estrat&eacute;gicos con sus principales partners para poder ofrecer pol&iacute;ticas de descuentos en distintos servicios (hoteles, alquiler de autos y otros) en aquellas provincias en donde tienen presencia.&nbsp;</p>', '2022-11-04', 'Durante el primer semestre del año, Flybondi contrató a 500 personas', 3),
(22, '<p>Flybondi es la primera aerol&iacute;nea low cost en Argentina en obtener la Certificaci&oacute;n en Operaciones con Visibilidad Reducida (CAT II) otorgada por la Administraci&oacute;n Nacional de Aviaci&oacute;n Civil (ANAC).&nbsp; Dicha certificaci&oacute;n le permite a la compa&ntilde;&iacute;a operar en condiciones de visibilidad reducida de 300 m y meteorol&oacute;gicas adversas en los aeropuertos habilitados como el&nbsp; Aeroparque Internacional Jorge Newbey, Aeropuerto Internacional de Ezeiza, Aeropuerto Internacional de Gale&atilde;o en R&iacute;o de Janeiro y Aeropuerto Internacional de Sao Paulo.&nbsp;</p>\r\n<p><em>&ldquo;Ser la primera aerol&iacute;nea low cost del pa&iacute;s en lograr esta certificaci&oacute;n es un orgullo y demuestra un verdadero trabajo en equipo de todos los que hacemos Flybondi. Tanto nuestro pilotos, el personal de mantenimiento y nuestros aviones ya est&aacute;n preparados para esta nueva etapa, donde vamos a poder operar en d&iacute;as de baja visibilidad (mucha niebla), en aquellos aeropuertos que cuenten con la capacidad de asistir a estas operaciones&rdquo;</em>&nbsp;coment&oacute; Eduardo Gaspari, Chief Operation Officer de Flybondi. Luego agreg&oacute;<em>: &ldquo;Nuestro principal objetivo es que cada vez m&aacute;s personas alcancen la libertad de volar y que reciban un servicio con los m&aacute;s altos est&aacute;ndares de seguridad de la industria en orden internacional.&rdquo;</em></p>\r\n<p>Para lograr la certificaci&oacute;n, las &Aacute;rea de Operaciones y el &Aacute;rea de Mantenimiento de Flybondi trabajaron en conjunto para dise&ntilde;ar los procesos y manuales que luego fueron certificados por la autoridad aeron&aacute;utica. Adem&aacute;s, realizaron la&nbsp; instrucci&oacute;n y el entrenamiento a los pilotos en simulador de vuelo, adecuaci&oacute;n de los equipos a las aeronaves y la capacitaci&oacute;n en el equipo de mantenimiento. Al finalizar esta etapa, se realiz&oacute; un periodo de demostraci&oacute;n en la que se llevaron a cabo m&aacute;s de 30 vuelos para validar los procedimientos antes presentados y obtener as&iacute; la certificaci&oacute;n.&nbsp;</p>', '2022-11-01', 'Flybondi obtuvo la Certificación en Operaciones con Visibilidad Reducida (CAT II)', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `calle` varchar(255) DEFAULT NULL,
  `cantidad_aterrizaje_inical` int(11) DEFAULT NULL,
  `codigo_postal` int(11) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `legajo` int(11) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `numero_de_calle` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `tipo_cargo` int(11) DEFAULT NULL,
  `tv_diurno_inicial` double DEFAULT NULL,
  `tv_nocturno_inicial` double DEFAULT NULL,
  `tv_total_inicial` double DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `generacion_id` bigint(20) DEFAULT NULL,
  `lider_id` bigint(20) DEFAULT NULL,
  `localidad_id` bigint(20) DEFAULT NULL,
  `provincia_id` bigint(20) DEFAULT NULL,
  `vestimenta_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `apellido`, `calle`, `cantidad_aterrizaje_inical`, `codigo_postal`, `direccion`, `fecha_ingreso`, `fecha_nacimiento`, `legajo`, `mail`, `nombre`, `numero_de_calle`, `password`, `sexo`, `telefono`, `tipo_cargo`, `tv_diurno_inicial`, `tv_nocturno_inicial`, `tv_total_inicial`, `username`, `generacion_id`, `lider_id`, `localidad_id`, `provincia_id`, `vestimenta_id`) VALUES
(1, 'buen dia', 'calle', 23, 1234, NULL, '2022-12-01', '2022-11-10', 43223, 'email@email.com', 'rogelio', 123, '$2a$10$PU8yUs9zmeuEzkT/AJuiv.RdT6fU1EUvNdwZhJ1kq.Zv/7f2Orcki', NULL, 12345678, 0, 23, 23, 46, 'agustin', NULL, NULL, NULL, NULL, NULL),
(2, 'nava', 'calle', 2, 1234, NULL, '2022-11-21', '2022-11-01', 1, 'email@email.com', 'Agustin ', 1234, '$2a$10$ITyIO2bgi8Dk5fXENVkxZOiuQ/iIzJ0js6/4jg3lp3tz1vwe7Jv8a', NULL, 12345678, 2, 3, 3, 6, 'agustinAdmin', NULL, NULL, NULL, NULL, NULL),
(3, 'ezequiel', 'calle', 4, 1234, NULL, '2022-11-22', '2022-11-02', 2, 'email222@email.com', 'agustin ', 1234, '$2a$10$rwGQ.76RPVyBljWPqLDxT.Wn3ju75Ux9770kOMQGyhgljOOO48snu', NULL, 12345678, 2, 3, 3, 6, 'agustinLider', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_roles`
--

CREATE TABLE `user_roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `user_roles`
--

INSERT INTO `user_roles` (`users_id`, `roles_id`) VALUES
(2, 1),
(2, 2),
(3, 2),
(3, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `noticia`
--
ALTER TABLE `noticia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKiehf5dcmrrd5oi3oaouwl3xyp` (`autor_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgnd9tq370c88hnb3ipikgj11c` (`generacion_id`),
  ADD KEY `FKf37agwstmv0i31xxbrld7fui9` (`lider_id`),
  ADD KEY `FKl0w7gxc68b0iuw0qm5eeix35p` (`localidad_id`),
  ADD KEY `FK1bgliwotnmlua5wrjl1m5h0i` (`provincia_id`),
  ADD KEY `FK4bm920gqohatigrouek3nd5iu` (`vestimenta_id`);

--
-- Indices de la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`users_id`,`roles_id`),
  ADD KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `noticia`
--
ALTER TABLE `noticia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `noticia`
--
ALTER TABLE `noticia`
  ADD CONSTRAINT `FKiehf5dcmrrd5oi3oaouwl3xyp` FOREIGN KEY (`autor_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKq4o50dd5xw0641hmqor0roqqu` FOREIGN KEY (`imagen_id`) REFERENCES `imagen` (`id`);

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK1bgliwotnmlua5wrjl1m5h0i` FOREIGN KEY (`provincia_id`) REFERENCES `provincia` (`id`),
  ADD CONSTRAINT `FK4bm920gqohatigrouek3nd5iu` FOREIGN KEY (`vestimenta_id`) REFERENCES `vestimenta` (`id`),
  ADD CONSTRAINT `FKf37agwstmv0i31xxbrld7fui9` FOREIGN KEY (`lider_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKgnd9tq370c88hnb3ipikgj11c` FOREIGN KEY (`generacion_id`) REFERENCES `generacion` (`id`),
  ADD CONSTRAINT `FKl0w7gxc68b0iuw0qm5eeix35p` FOREIGN KEY (`localidad_id`) REFERENCES `localidad` (`id`);

--
-- Filtros para la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK7ecyobaa59vxkxckg6t355l86` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`);
COMMIT;

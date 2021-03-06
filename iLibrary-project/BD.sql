-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 03, 2020 at 10:53 PM
-- Server version: 5.7.26
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bibliotheque`
--

-- --------------------------------------------------------

--
-- Table structure for table `emprunts`
--

CREATE TABLE `emprunts` (
  `date_emprunt` datetime NOT NULL,
  `date_retour` datetime NOT NULL,
  `exemplaireId` int(13) NOT NULL,
  `adherentId` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `emprunts`
--

INSERT INTO `emprunts` (`date_emprunt`, `date_retour`, `exemplaireId`, `adherentId`) VALUES
('2020-06-01 03:45:05', '2020-06-11 03:45:05', 30, 'G123456789'),
('2020-06-03 00:00:00', '2020-06-11 00:00:00', 40, 'J137297129');

--
-- Triggers `emprunts`
--
DELIMITER $$
CREATE TRIGGER `deletedEmprunt_trigg` AFTER DELETE ON `emprunts` FOR EACH ROW begin
update exemplaire set etat = "dispo" where id = OLD.exemplaireId;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `reservedExemp_trigg` AFTER INSERT ON `emprunts` FOR EACH ROW begin update exemplaire set etat = "reserve" where id = NEW.exemplaireId; end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `exemplaire`
--

CREATE TABLE `exemplaire` (
  `id` int(11) NOT NULL,
  `etat` enum('dispo','reserve') DEFAULT NULL,
  `idLivre` bigint(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `exemplaire`
--

INSERT INTO `exemplaire` (`id`, `etat`, `idLivre`) VALUES
(1, 'reserve', 9781781100219),
(2, 'dispo', 9782070392537),
(3, 'dispo', 9782070392537),
(4, 'dispo', 9782070392537),
(5, 'dispo', 9782070392537),
(6, 'dispo', 9782070392537),
(7, 'dispo', 9782070392537),
(8, 'dispo', 9782081238060),
(9, 'dispo', 9782081238060),
(10, 'dispo', 9782081238060),
(11, 'dispo', 9782081238060),
(12, 'dispo', 9782264023827),
(13, 'dispo', 9782264023827),
(14, 'dispo', 9782070784370),
(15, 'dispo', 9782070784370),
(16, 'dispo', 9782070784370),
(17, 'dispo', 9782070784370),
(18, 'reserve', 9782070754922),
(19, 'dispo', 9782070612758),
(20, 'dispo', 9782070612758),
(21, 'dispo', 9782070612758),
(22, 'dispo', 9782070612758),
(23, 'dispo', 9782070458028),
(24, 'dispo', 9782070458028),
(25, 'reserve', 9782070342266),
(26, 'dispo', 9782070342266),
(27, 'dispo', 9782070342266),
(28, 'dispo', 9782070342266),
(29, 'dispo', 9782070342266),
(30, 'reserve', 9782070342266),
(31, 'dispo', 9782070342266),
(32, 'dispo', 9782070342266),
(33, 'dispo', 9782070342266),
(34, 'reserve', 9782070111053),
(35, 'dispo', 9782070111053),
(36, 'dispo', 9782070111053),
(37, 'dispo', 9782070111053),
(38, 'dispo', 9782070111053),
(39, 'dispo', 9782070111053),
(40, 'dispo', 9782013225021),
(41, 'dispo', 9782013225021),
(42, 'dispo', 9782013225021),
(43, 'dispo', 9782013225021),
(44, 'dispo', 9782013225021),
(45, 'dispo', 9782013225021),
(46, 'dispo', 9782013225021),
(47, 'dispo', 9782013225021),
(48, 'dispo', 9782013225021),
(49, 'dispo', 9782013225021),
(50, 'dispo', 9782013225021),
(51, 'dispo', 9782013225021),
(52, 'dispo', 9781781100219),
(54, 'reserve', 9782070612758);

-- --------------------------------------------------------

--
-- Table structure for table `livre`
--

CREATE TABLE `livre` (
  `isbn` bigint(13) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `cover_image` varchar(100) NOT NULL,
  `language` varchar(40) NOT NULL,
  `auteur` varchar(50) NOT NULL,
  `genre` varchar(40) NOT NULL,
  `date_edition` int(4) NOT NULL,
  `pages` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `livre`
--

INSERT INTO `livre` (`isbn`, `titre`, `description`, `cover_image`, `language`, `auteur`, `genre`, `date_edition`, `pages`) VALUES
(9781781100219, 'Harry Potter ?? l\'??cole des sorciers', '\"Turning the envelope over, his hand trembling, Harry saw a purple wax seal bearing a coat of arms; a lion, an eagle, a badger and a snake surrounding a large letter \'H\'.\"Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle.\r\nThen, on Harry\'s eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!Pottermore has now launched the Wizarding World Book Club. Visit Pottermore to sign up and join weekly Twitter discussions at WW Book Club.', '/LivresImages/harry-potter.png', 'Anglais', 'J. K. Rowling', 'Roman', 1997, 353),
(9782013225021, 'Le Comte de Monte-Cristo', 'Marseille, 1815. Jamais Edmond Dant??s n\'a ??t?? aussi pr??s du bonheur. Bient??t capitaine du trois-m??ts le Pharaon, fianc?? ?? la belle Merc??d??s, il voit ses esp??rances combl??es. Soudain, le r??ve se brise. Une d??nonciation anonyme... et le voil?? emprisonn?? au ch??teau d\'If. Qui veut lui nuire ? Pourquoi ? Dant??s l\'ignore, mais il se le jure : sa vengeance sera terrible.', '/LivresImages/La_Comte.png', 'Fran??ais', 'Alexandre Dumas', 'Roman', 1844, 471),
(9782020238113, 'Cent ans de solitude', 'Une ??pop??e vaste et multiple, un mythe haut en couleur plein de r??ve et de r??el. Histoire ?? la fois minutieuse et d??lirante d\'une dynastie : la fondation , par l\'anc??tre, d\'un village sud-am??ricain isol?? du reste du monde ; les grandes heures marqu??es parla magie et l\'alchimie, ; la d??cadence ; le d??luge et la mort des animaux. Ce roman prolif??rant, merveilleux et dor?? comme une enluminure, est ?? sa fa??on un Quichotte sud-am??ricain : m??me sens de la parodie, m??me rage d\'??crire, m??me f??te cyclique des soleils et des mots. Cents ans de solitude, compte parmi les chefs-d\'??uvre de la litt??rature mondiale du XXe si??cle. L\'auteur a obtenu le prix Nobel de litt??rature en 1982.', '/LivresImages/Cent.png', 'Espagnol', 'Gabriel Garc??a M??rquez', 'Roman', 1967, 430),
(9782035834256, 'Les Mis??rables', '??Il y a dans notre civilisation des heures redoutables ; ce sont les moments o?? la p??nalit?? prononce un naufrage??, ??crit Victor Hugo au sujet de Jean Valjean. Accul?? par la pauvret??, l\'homme vole un pain pour nourrir les siens et passe dix-neuf ans au bagne! ?? sa sortie, rejet?? de tous, haineux envers la soci??t??, il n\'a qu\'une issue : retomber dans le crime. Mais une rencontre providentielle l\'en d??tourne.\r\nJean Valjean trouvera-t-il le salut esp??r??? Traqu?? sans rel??che par le policier Javert, parviendra-t-il ?? ??chapper ?? son pass??? Roman ?? suspens, r??cit r??aliste, critique sociale et fresque ??pique, le chef-d\'??uvre de Victor Hugo dessine le chemin de croix d\'une ??humanit?? souffrante?? qui, de ??mis??rable??, devient ??sublime??.', '/LivresImages/Les_Miserables.png', 'Fran??ais', 'Victor Hugo', 'Roman', 1862, 287),
(9782070111053, 'Belle du Seigneur', 'Les autres mettent des semaines et des mois pour arriver ?? aimer, et ?? aimer peu, et il leur faut des entretiens et des go??ts communs et des cristallisations. Moi, ce fut le temps d???un battement de paupi??res. Dites moi fou, mais croyez-moi. Un battement de ses paupi??res, et elle me regarda sans me voir, et ce fut la gloire et le printemps et le soleil et la mer ti??de et sa transparence pr??s du rivage et ma jeunesse revenue, et le monde ??tait n??, et je sus que personne avant elle, ni Adrienne, ni Aude, ni Isolde, ni les autres de ma splendeur et jeunesse, toutes d???elle annonciatrices et servantes.', '/LivresImages/Belle.png', 'fran??ais', 'Albert Cohen', 'Roman', 1968, 1034),
(9782070342266, 'La Horde du Contrevent', '??Imaginez une Terre ponc??e, avec en son centre une bande de cinq mille kilom??tres de large et sur ses franges un miroir de glace ?? peine rayable, inhabit??. Imaginez qu\'un vent f??roce en rince la surface. Que les villages qui s\'y sont accroch??s, avec leurs maisons en goutte d\'eau, les chars ?? voile qui la strient, les airpailleurs debout en plein flot, tous r??sistent. Imaginez qu\'en Extr??me-Aval ait ??t?? form?? un bloc d\'??lite d\'une vingtaine d\'enfants aptes ?? remonter au cran, rafale en gueule, leur vie durant, le vent jusqu\'?? sa source, ?? ce jour jamais atteinte : l\'Extr??me-Amont.\r\nMon nom est Sov Strochnis, scribe. Mon nom est Caracole le troubadour et Oroshi Melicerte, a??roma??tre. Je m\'appelle aussi Golgoth, traceur de la Horde, Arval l\'??claireur et parfois m??me Larco lorsque je braconne l\'azur ?? la cage volante. Ensemble, nous formons la Horde du Contrevent. Il en a exist?? trente-trois en huit si??cles, toutes infructueuses. Je vous parle au nom de la trente-quatri??me : sans doute l\'ultime.??', '/LivresImages/La_Horde.png', 'Fran??ais', 'Alain Damasio', 'Science-fiction', 2004, 592),
(9782070369218, 'M??moires d\'Hadrien', 'Cette oeuvre, qui est ?? la fois roman, histoire, po??sie, a ??t?? salu??e par la critique fran??aise et mondiale comme un ??v??nement litt??raire. En imaginant les M??moires d\'un grand empereur romain, l\'auteur a voulu \"refaire du dedans ce que les arch??ologues du XIX ? si??cle ont fait du dehors\" . Jugeant sans complaisance sa vie d\'homme et son oeuvre politique, Hadrien n\'ignore pas que Rome, malgr?? sa grandeur, finira un jour par p??rir, mais son r??alisme romain et son humanisme h??rit?? des Grecs lui font sentir l\'importance de penser et de servir jusqu\'au bout.\r\n\"... Je me sentais responsable de la beaut?? du monde\" , dit ce h??ros dont les probl??mes sont ceux de l\'homme de tous les temps : les dangers mortels qui du dedans et du dehors confrontent les civilisations, la qu??te d\'un accord harmonieux entre le bonheur et la \"discipline auguste\" , entre l\'intelligence et la volont??.', '/LivresImages/Memoires.png', 'Fra??ais', 'Marguerite Yourcenar', 'Roman', 1951, 364),
(9782070373628, 'La Vie devant soi ', 'Sign?? Ajar, ce roman re??ut le prix Goncourt en 1975. Histoire d\'amour d\'un petit gar??on arabe pour une tr??s vieille femme juive : Momo se d??bat contre les six ??tages que Madame Rosa ne veut plus monter et contre la vie parce que \" ??a ne pardonne pas \" et parce qu\'il n\'est \" pas n??cessaire d\'avoir des raisons pour avoir peur \". Le petit gar??on l\'aidera ?? se cacher dans son \" trou juif \", elle n\'ira pas mourir ?? l\'h??pital et pourra ainsi b??n??ficier du droit sacr?? \" des peuples ?? disposer d\'eux-m??mes \" qui n\'est pas respect?? par l\'Ordre des m??decins.\r\nIl lui tiendra compagnie jusqu\'?? ce qu\'elle meure et m??me au-del?? de la mort.', '/LivresImages/La_Vie.png', 'Fran??ais', 'Romain Gary (??mile Ajar)', 'Roman', 1975, 274),
(9782070392537, 'Crime et Ch??timent ', 'A Saint-P??tersbourg, en 1865, Raskolnikov, un jeune noble sombre et altier, renferm?? mais aussi g??n??reux, a interrompu ses ??tudes faute d\'argent. Endett?? aupr??s de sa logeuse qui lui loue une ??troite mansarde, il se sent ??cras?? par sa pauvret??. Mais il se croit aussi appel?? ?? un grand avenir et, d??daigneux de la loi morale, se pense fond?? ?? commettre un crime : ce qu\'il va faire bient??t - de mani??re crapuleuse.\r\nPubli?? en huit livraisons par Le Messager russe au cours de l\'ann??e 1866, le roman de Dosto??evski montre en Raskolnikov un t??moin de la mis??re, de l\'alcoolisme et de la prostitution que l\'auteur d??crit sans voiles, un criminel aussi qui ne sait trop pourquoi il l\'est devenu, tant les raisons qu\'il s\'invente pour agir sont contradictoires. Mais la trag??die n\'exclut pas la vision d\'une vie lumineuse, et le ch??timent de son crime va lui permettre un long cheminement vers la v??rit??, et la renonciation ?? sa m??lancolie brutale.\r\nApr??s quoi sera possible ce que l\'??pilogue annonce : l\'initiation de Raskolnikov ?? une r??alit?? nouvelle, le passage d\'un monde ?? un autre monde.', '/LivresImages/Crime.png', 'Russe', 'Fiodor Dosto??evski', 'Roman', 1867, 700),
(9782070458028, 'La Promesse de l\'aube', 'Ce r??cit co??ncide sur bien des points avec ce que l\'on sait de Romain Gary : \"Ce livre est d\'inspiration autobiographique, mais ce n\'est pas une autobiographie\". Romain Gary raconte son enfance, sa jeunesse ?? Vilnius puis ?? Nice. Elev?? seul par sa m??re, qui r??ve qu\'il devienne c??l??bre. Cette \"promesse de l\'aube\" est une promesse dans les deux sens du mot : promesse que fait la vie au narrateur ?? travers une m??re passionn??e ; promesse qu\'il fait tacitement ?? cette m??re d\'accomplir tout ce qu\'elle attend de lui dans l\'ordre de l\'h??ro??sme et de la r??alisation de soi-m??me.\r\nLe caract??re de cette Russe chim??rique, id??aliste, ??prise de la France, m??lange pittoresque de courage et d\'??tourderie, d\'??nergie indomptable et de l??g??ret??, de sens des affaires et de cr??dulit??, prend un relief extraordinaire. Mais les enfants ??lev??s par ces m??res trop ferventes restent toujours, dit l\'auteur, \"frileux\" de coeur et d\'??me, et charg??s d\'une dette ??crasante qu\'ils se sentent incapables d\'acquitter.\r\nRarement la pi??t?? filiale s\'est exprim??e avec plus de tendresse, de sensibilit??, et cependant avec plus de clairvoyance et d\'humour.', '/LivresImages/La_Promesse.png', 'Fran??ais', 'Romain Gary (??mile Ajar) ', 'Biographie', 1960, 455),
(9782070612758, 'Le Petit Prince', 'Le premier soir je me suis donc endormi sur le sable ?? mille milles de toute terre habit??e. J\'??tais bien plus isol?? qu\'un naufrag?? sur un radeau au milieu de l\'oc??an. Alors vous imaginez ma surprise, au lever du jour, quand une dr??le de petite voix m\'a r??veill??. Elle disait : S\'il vous pla??t... dessine-moi un mouton ! Hein ! Dessine-moi un mouton... J\'ai saut?? sur mes pieds comme si j\'avais ??t?? frapp?? par la foudre.', '/LivresImages/Le_Petit.png', 'Fran??ais', 'Antoine de Saint-Exup??ry ', 'Roman', 1943, 113),
(9782070754922, '?? la recherche du temps perdu', 'Que celui qui pourrait ??crire un tel livre serait heureux, pensais-je, quel labeur devant lui ! Pour en donner une id??e, c\'est aux arts les plus ??lev??s et les plus diff??rents qu\'il faudrait emprunter des comparaisons ; car cet ??crivain, qui d\'ailleurs pour chaque caract??re en ferait appara??tre les faces oppos??es, pour montrer son volume, devrait pr??parer son livre minutieusement, avec de perp??tuels regroupements de forces, comme une offensive, le supporter comme une fatigue, l\'accepter comme une r??gle, le construire comme une ??glise, le suivre comme un r??gime, le vaincre comme un obstacle, le conqu??rir comme une amiti??, le suralimenter comme un enfant, le cr??er comme un monde sans laisser de c??t?? ces myst??res qui n\'ont probablement leur explication que dans d\'autres mondes et dont le pressentiment est ce qui nous ??meut le plus dans la vie et dans l\'art.\r\nEt dans ces grands livres-l??, il y a des parties qui n\'ont eu le temps que d\'??tre esquiss??es et qui ne seront sans doute jamais finies, ?? cause de l\'ampleur m??me du plan de l\'architecte. Combien de grandes cath??drales restent inachev??es !', '/LivresImages/Recherche.png', 'Fran??ais', 'Marcel Proust', 'Roman', 1927, 2400),
(9782070784370, 'L\'Insoutenable L??g??ret?? de l\'??tre', '??Qu\'est-il rest?? des agonisants du Cambodge ? Une grande photo de la star am??ricaine tenant dans ses bras un enfant jaune. Qu\'est-il rest?? de Tomas ? Une inscription : Il voulait le Royaume de Dieu sur la terre. Qu\'est-il rest?? de Beethoven ? Un homme morose ?? l\'invraisemblable crini??re, qui prononce d\'une voix sombre : \"Es muss sein !\" Qu\'est-il rest?? de Franz ? Une inscription : Apr??s un long ??garement, le retour.\r\nEt ainsi de suite, et ainsi de suite. Avant d\'??tre oubli??s, nous serons chang??s en kitsch. Le kitsch, c\'est la station de correspondance entre l\'??tre et l\'oubli.??', '/LivresImages/Insoutenable.png', 'Tch??que', 'Milan Kundera', 'Philosophie', 1984, 393),
(9782081238060, 'Le Dernier Jour d\'un condamn??', 'Un homme sans nom dont on ne sait rien, pas m??me le crime, vient d?????tre condamn?? ?? la guillotine: il ne lui reste plus que quelques jours ?? vivre. Dans l???attente de son ex??cution, il consigne ses derni??res pens??es et sensations : son journal suit le flot chaotique de sa conscience, avec des moments de panique, des sursauts d???espoir ou de r??volte, et une hantise ??? celle de la mort qui vient. Texte d???une inalt??rable actualit?? et premier acte d???un combat dont Hugo demeurera le symbole, Le Dernier jour d???un condamn?? (1829) se pr??sente comme ??la plaidoirie g??n??rale et permanente pour tous les accus??s pr??sents et ?? venir??. Et reste sans doute le plus grand r??quisitoire jamais ??crit contre la peine de mort.\r\n', '/LivresImages/Le_Dernier.png', 'Fran??ais', 'Victor Hugo', 'Roman', 1829, 185),
(9782264023827, 'Orgueil et Pr??jug??s', 'Les p??rip??ties de l\'histoire d\'amour contrari??e entre l\'orgueilleuse Elizabeth Bennett et son nouveau voisin Mr Darcy, sur fond de campagne anglaise au d??but du XIXe si??cle, offrent un plaisir de lecture qui ne s\'att??nue pas quel que soit l\'??ge du lecteur. Cette peinture pleine d\'humour de la vie quotidienne des jeunes filles de la gentry, avec ses bals, ses militaires en garnison, ses demandes en mariage, est aussi le premier grand roman psychologique de la litt??rature anglo-saxonne.', '/LivresImages/Orgueil.png', 'Anglais', 'Jane Austen', 'Roman', 1813, 510);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `cne` varchar(255) NOT NULL,
  `nomComplet` varchar(100) DEFAULT NULL,
  `date_naiss` date DEFAULT NULL,
  `pseudo` varchar(255) NOT NULL,
  `mot_de_pass` varchar(255) NOT NULL,
  `adminOption` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`cne`, `nomComplet`, `date_naiss`, `pseudo`, `mot_de_pass`, `adminOption`) VALUES
('F598709775', 'Elhannani asmae', '1988-06-06', 'elhannani_1', 'f71dbe52628a3f83a77ab494817525c6', 0),
('G123456789', 'aymen matrouh', '1999-03-15', 'matrouh_7', 'c83663d1c69a2024012428f0cdb2c45b', 0),
('J137297129', 'Aghnaj Mostafa', '1999-03-31', 'Nesta.ag', '3ed7dceaf266cafef032b9d5db224717', 0),
('X000000000', 'elamine mehdi', NULL, 'elamine_00', '3ed7dceaf266cafef032b9d5db224717', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `emprunts`
--
ALTER TABLE `emprunts`
  ADD PRIMARY KEY (`exemplaireId`,`adherentId`),
  ADD KEY `adherentId` (`adherentId`);

--
-- Indexes for table `exemplaire`
--
ALTER TABLE `exemplaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idLivre` (`idLivre`);

--
-- Indexes for table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`isbn`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`cne`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `exemplaire`
--
ALTER TABLE `exemplaire`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `emprunts`
--
ALTER TABLE `emprunts`
  ADD CONSTRAINT `emprunts_ibfk_1` FOREIGN KEY (`exemplaireId`) REFERENCES `exemplaire` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `emprunts_ibfk_2` FOREIGN KEY (`adherentId`) REFERENCES `utilisateur` (`cne`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `exemplaire`
--
ALTER TABLE `exemplaire`
  ADD CONSTRAINT `exemplaire_ibfk_1` FOREIGN KEY (`idLivre`) REFERENCES `livre` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

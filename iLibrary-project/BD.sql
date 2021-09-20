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
(9781781100219, 'Harry Potter à l\'école des sorciers', '\"Turning the envelope over, his hand trembling, Harry saw a purple wax seal bearing a coat of arms; a lion, an eagle, a badger and a snake surrounding a large letter \'H\'.\"Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle.\r\nThen, on Harry\'s eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!Pottermore has now launched the Wizarding World Book Club. Visit Pottermore to sign up and join weekly Twitter discussions at WW Book Club.', '/LivresImages/harry-potter.png', 'Anglais', 'J. K. Rowling', 'Roman', 1997, 353),
(9782013225021, 'Le Comte de Monte-Cristo', 'Marseille, 1815. Jamais Edmond Dantès n\'a été aussi près du bonheur. Bientôt capitaine du trois-mâts le Pharaon, fiancé à la belle Mercédès, il voit ses espérances comblées. Soudain, le rêve se brise. Une dénonciation anonyme... et le voilà emprisonné au château d\'If. Qui veut lui nuire ? Pourquoi ? Dantès l\'ignore, mais il se le jure : sa vengeance sera terrible.', '/LivresImages/La_Comte.png', 'Français', 'Alexandre Dumas', 'Roman', 1844, 471),
(9782020238113, 'Cent ans de solitude', 'Une épopée vaste et multiple, un mythe haut en couleur plein de rêve et de réel. Histoire à la fois minutieuse et délirante d\'une dynastie : la fondation , par l\'ancêtre, d\'un village sud-américain isolé du reste du monde ; les grandes heures marquées parla magie et l\'alchimie, ; la décadence ; le déluge et la mort des animaux. Ce roman proliférant, merveilleux et doré comme une enluminure, est à sa façon un Quichotte sud-américain : même sens de la parodie, même rage d\'écrire, même fête cyclique des soleils et des mots. Cents ans de solitude, compte parmi les chefs-d\'œuvre de la littérature mondiale du XXe siècle. L\'auteur a obtenu le prix Nobel de littérature en 1982.', '/LivresImages/Cent.png', 'Espagnol', 'Gabriel García Márquez', 'Roman', 1967, 430),
(9782035834256, 'Les Misérables', '«Il y a dans notre civilisation des heures redoutables ; ce sont les moments où la pénalité prononce un naufrage», écrit Victor Hugo au sujet de Jean Valjean. Acculé par la pauvreté, l\'homme vole un pain pour nourrir les siens et passe dix-neuf ans au bagne! À sa sortie, rejeté de tous, haineux envers la société, il n\'a qu\'une issue : retomber dans le crime. Mais une rencontre providentielle l\'en détourne.\r\nJean Valjean trouvera-t-il le salut espéré? Traqué sans relâche par le policier Javert, parviendra-t-il à échapper à son passé? Roman à suspens, récit réaliste, critique sociale et fresque épique, le chef-d\'œuvre de Victor Hugo dessine le chemin de croix d\'une «humanité souffrante» qui, de «misérable», devient «sublime».', '/LivresImages/Les_Miserables.png', 'Français', 'Victor Hugo', 'Roman', 1862, 287),
(9782070111053, 'Belle du Seigneur', 'Les autres mettent des semaines et des mois pour arriver à aimer, et à aimer peu, et il leur faut des entretiens et des goûts communs et des cristallisations. Moi, ce fut le temps d’un battement de paupières. Dites moi fou, mais croyez-moi. Un battement de ses paupières, et elle me regarda sans me voir, et ce fut la gloire et le printemps et le soleil et la mer tiède et sa transparence près du rivage et ma jeunesse revenue, et le monde était né, et je sus que personne avant elle, ni Adrienne, ni Aude, ni Isolde, ni les autres de ma splendeur et jeunesse, toutes d’elle annonciatrices et servantes.', '/LivresImages/Belle.png', 'français', 'Albert Cohen', 'Roman', 1968, 1034),
(9782070342266, 'La Horde du Contrevent', '«Imaginez une Terre poncée, avec en son centre une bande de cinq mille kilomètres de large et sur ses franges un miroir de glace à peine rayable, inhabité. Imaginez qu\'un vent féroce en rince la surface. Que les villages qui s\'y sont accrochés, avec leurs maisons en goutte d\'eau, les chars à voile qui la strient, les airpailleurs debout en plein flot, tous résistent. Imaginez qu\'en Extrême-Aval ait été formé un bloc d\'élite d\'une vingtaine d\'enfants aptes à remonter au cran, rafale en gueule, leur vie durant, le vent jusqu\'à sa source, à ce jour jamais atteinte : l\'Extrême-Amont.\r\nMon nom est Sov Strochnis, scribe. Mon nom est Caracole le troubadour et Oroshi Melicerte, aéromaître. Je m\'appelle aussi Golgoth, traceur de la Horde, Arval l\'éclaireur et parfois même Larco lorsque je braconne l\'azur à la cage volante. Ensemble, nous formons la Horde du Contrevent. Il en a existé trente-trois en huit siècles, toutes infructueuses. Je vous parle au nom de la trente-quatrième : sans doute l\'ultime.»', '/LivresImages/La_Horde.png', 'Français', 'Alain Damasio', 'Science-fiction', 2004, 592),
(9782070369218, 'Mémoires d\'Hadrien', 'Cette oeuvre, qui est à la fois roman, histoire, poésie, a été saluée par la critique française et mondiale comme un événement littéraire. En imaginant les Mémoires d\'un grand empereur romain, l\'auteur a voulu \"refaire du dedans ce que les archéologues du XIX ? siècle ont fait du dehors\" . Jugeant sans complaisance sa vie d\'homme et son oeuvre politique, Hadrien n\'ignore pas que Rome, malgré sa grandeur, finira un jour par périr, mais son réalisme romain et son humanisme hérité des Grecs lui font sentir l\'importance de penser et de servir jusqu\'au bout.\r\n\"... Je me sentais responsable de la beauté du monde\" , dit ce héros dont les problèmes sont ceux de l\'homme de tous les temps : les dangers mortels qui du dedans et du dehors confrontent les civilisations, la quête d\'un accord harmonieux entre le bonheur et la \"discipline auguste\" , entre l\'intelligence et la volonté.', '/LivresImages/Memoires.png', 'Fraçais', 'Marguerite Yourcenar', 'Roman', 1951, 364),
(9782070373628, 'La Vie devant soi ', 'Signé Ajar, ce roman reçut le prix Goncourt en 1975. Histoire d\'amour d\'un petit garçon arabe pour une très vieille femme juive : Momo se débat contre les six étages que Madame Rosa ne veut plus monter et contre la vie parce que \" ça ne pardonne pas \" et parce qu\'il n\'est \" pas nécessaire d\'avoir des raisons pour avoir peur \". Le petit garçon l\'aidera à se cacher dans son \" trou juif \", elle n\'ira pas mourir à l\'hôpital et pourra ainsi bénéficier du droit sacré \" des peuples à disposer d\'eux-mêmes \" qui n\'est pas respecté par l\'Ordre des médecins.\r\nIl lui tiendra compagnie jusqu\'à ce qu\'elle meure et même au-delà de la mort.', '/LivresImages/La_Vie.png', 'Français', 'Romain Gary (Émile Ajar)', 'Roman', 1975, 274),
(9782070392537, 'Crime et Châtiment ', 'A Saint-Pétersbourg, en 1865, Raskolnikov, un jeune noble sombre et altier, renfermé mais aussi généreux, a interrompu ses études faute d\'argent. Endetté auprès de sa logeuse qui lui loue une étroite mansarde, il se sent écrasé par sa pauvreté. Mais il se croit aussi appelé à un grand avenir et, dédaigneux de la loi morale, se pense fondé à commettre un crime : ce qu\'il va faire bientôt - de manière crapuleuse.\r\nPublié en huit livraisons par Le Messager russe au cours de l\'année 1866, le roman de Dostoïevski montre en Raskolnikov un témoin de la misère, de l\'alcoolisme et de la prostitution que l\'auteur décrit sans voiles, un criminel aussi qui ne sait trop pourquoi il l\'est devenu, tant les raisons qu\'il s\'invente pour agir sont contradictoires. Mais la tragédie n\'exclut pas la vision d\'une vie lumineuse, et le châtiment de son crime va lui permettre un long cheminement vers la vérité, et la renonciation à sa mélancolie brutale.\r\nAprès quoi sera possible ce que l\'épilogue annonce : l\'initiation de Raskolnikov à une réalité nouvelle, le passage d\'un monde à un autre monde.', '/LivresImages/Crime.png', 'Russe', 'Fiodor Dostoïevski', 'Roman', 1867, 700),
(9782070458028, 'La Promesse de l\'aube', 'Ce récit coïncide sur bien des points avec ce que l\'on sait de Romain Gary : \"Ce livre est d\'inspiration autobiographique, mais ce n\'est pas une autobiographie\". Romain Gary raconte son enfance, sa jeunesse à Vilnius puis à Nice. Elevé seul par sa mère, qui rêve qu\'il devienne célèbre. Cette \"promesse de l\'aube\" est une promesse dans les deux sens du mot : promesse que fait la vie au narrateur à travers une mère passionnée ; promesse qu\'il fait tacitement à cette mère d\'accomplir tout ce qu\'elle attend de lui dans l\'ordre de l\'héroïsme et de la réalisation de soi-même.\r\nLe caractère de cette Russe chimérique, idéaliste, éprise de la France, mélange pittoresque de courage et d\'étourderie, d\'énergie indomptable et de légèreté, de sens des affaires et de crédulité, prend un relief extraordinaire. Mais les enfants élevés par ces mères trop ferventes restent toujours, dit l\'auteur, \"frileux\" de coeur et d\'âme, et chargés d\'une dette écrasante qu\'ils se sentent incapables d\'acquitter.\r\nRarement la piété filiale s\'est exprimée avec plus de tendresse, de sensibilité, et cependant avec plus de clairvoyance et d\'humour.', '/LivresImages/La_Promesse.png', 'Français', 'Romain Gary (Émile Ajar) ', 'Biographie', 1960, 455),
(9782070612758, 'Le Petit Prince', 'Le premier soir je me suis donc endormi sur le sable à mille milles de toute terre habitée. J\'étais bien plus isolé qu\'un naufragé sur un radeau au milieu de l\'océan. Alors vous imaginez ma surprise, au lever du jour, quand une drôle de petite voix m\'a réveillé. Elle disait : S\'il vous plaît... dessine-moi un mouton ! Hein ! Dessine-moi un mouton... J\'ai sauté sur mes pieds comme si j\'avais été frappé par la foudre.', '/LivresImages/Le_Petit.png', 'Français', 'Antoine de Saint-Exupéry ', 'Roman', 1943, 113),
(9782070754922, 'À la recherche du temps perdu', 'Que celui qui pourrait écrire un tel livre serait heureux, pensais-je, quel labeur devant lui ! Pour en donner une idée, c\'est aux arts les plus élevés et les plus différents qu\'il faudrait emprunter des comparaisons ; car cet écrivain, qui d\'ailleurs pour chaque caractère en ferait apparaître les faces opposées, pour montrer son volume, devrait préparer son livre minutieusement, avec de perpétuels regroupements de forces, comme une offensive, le supporter comme une fatigue, l\'accepter comme une règle, le construire comme une église, le suivre comme un régime, le vaincre comme un obstacle, le conquérir comme une amitié, le suralimenter comme un enfant, le créer comme un monde sans laisser de côté ces mystères qui n\'ont probablement leur explication que dans d\'autres mondes et dont le pressentiment est ce qui nous émeut le plus dans la vie et dans l\'art.\r\nEt dans ces grands livres-là, il y a des parties qui n\'ont eu le temps que d\'être esquissées et qui ne seront sans doute jamais finies, à cause de l\'ampleur même du plan de l\'architecte. Combien de grandes cathédrales restent inachevées !', '/LivresImages/Recherche.png', 'Français', 'Marcel Proust', 'Roman', 1927, 2400),
(9782070784370, 'L\'Insoutenable Légèreté de l\'être', '«Qu\'est-il resté des agonisants du Cambodge ? Une grande photo de la star américaine tenant dans ses bras un enfant jaune. Qu\'est-il resté de Tomas ? Une inscription : Il voulait le Royaume de Dieu sur la terre. Qu\'est-il resté de Beethoven ? Un homme morose à l\'invraisemblable crinière, qui prononce d\'une voix sombre : \"Es muss sein !\" Qu\'est-il resté de Franz ? Une inscription : Après un long égarement, le retour.\r\nEt ainsi de suite, et ainsi de suite. Avant d\'être oubliés, nous serons changés en kitsch. Le kitsch, c\'est la station de correspondance entre l\'être et l\'oubli.»', '/LivresImages/Insoutenable.png', 'Tchèque', 'Milan Kundera', 'Philosophie', 1984, 393),
(9782081238060, 'Le Dernier Jour d\'un condamné', 'Un homme sans nom dont on ne sait rien, pas même le crime, vient d’être condamné à la guillotine: il ne lui reste plus que quelques jours à vivre. Dans l’attente de son exécution, il consigne ses dernières pensées et sensations : son journal suit le flot chaotique de sa conscience, avec des moments de panique, des sursauts d’espoir ou de révolte, et une hantise – celle de la mort qui vient. Texte d’une inaltérable actualité et premier acte d’un combat dont Hugo demeurera le symbole, Le Dernier jour d’un condamné (1829) se présente comme «la plaidoirie générale et permanente pour tous les accusés présents et à venir». Et reste sans doute le plus grand réquisitoire jamais écrit contre la peine de mort.\r\n', '/LivresImages/Le_Dernier.png', 'Français', 'Victor Hugo', 'Roman', 1829, 185),
(9782264023827, 'Orgueil et Préjugés', 'Les péripéties de l\'histoire d\'amour contrariée entre l\'orgueilleuse Elizabeth Bennett et son nouveau voisin Mr Darcy, sur fond de campagne anglaise au début du XIXe siècle, offrent un plaisir de lecture qui ne s\'atténue pas quel que soit l\'âge du lecteur. Cette peinture pleine d\'humour de la vie quotidienne des jeunes filles de la gentry, avec ses bals, ses militaires en garnison, ses demandes en mariage, est aussi le premier grand roman psychologique de la littérature anglo-saxonne.', '/LivresImages/Orgueil.png', 'Anglais', 'Jane Austen', 'Roman', 1813, 510);

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

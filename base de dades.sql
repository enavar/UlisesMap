create table users (name varchar(20),password text,PRIMARY KEY(name));
create table routes (name varchar(50),description text,PRIMARY KEY(name));
create table points (name varchar(50),lat numeric(12,9),lon numeric(12,9),street text, description text, image text, url text,PRIMARY KEY(name));
create table valoration (def int,fk_route varchar(15) references routes(name),fk_user varchar(20) references users(name),PRIMARY KEY (fk_user,fk_route));
create table comments (def text,fk_route varchar(15) references routes(name),fk_user varchar(20) references users(name),PRIMARY KEY (fk_user,fk_route));

insert into users values ('admin', md5('admin'));
insert into users values ('castorp', md5('buscargrial'));
insert into users values ('settembrini', md5('burgues'));
insert into users values ('leoNaphta', md5('jesuita'));
insert into users values ('clawdia_Chaychat', md5('femmefatal'));
insert into users values ('mynheerPeeperkorn', md5('gerhart'));
insert into users values ('Joachim.Ziemssen', md5('omirp'));
insert into users values ('Heraclit', md5('fire'));
insert into users values ('Parmenides', md5('aparences'));
insert into users values ('Ulises', md5('odisea'));
insert into users values ('penelope', md5('araña'));
insert into users values ('atenea', md5('guerra'));
insert into users values ('poseidon', md5('marea'));
insert into users values ('afrodita', md5('amor'));
insert into users values ('hera', md5('familia'));
insert into users values ('CharlesDarwin', md5('origen'));
insert into users values ('Jean-BaptisteLamarck', md5('girafa'));
insert into users values ('VladimirNabokob', md5('ninfulas'));
insert into users values ('MaryShelley', md5('frankenstein'));
insert into users values ('HayaoMiyazaki', md5('Totoro'));
insert into users values ('Arrietty', md5('diminuta'));
insert into users values ('jiroHirikoshi', md5('aviones'));
insert into users values ('ponyo', md5('sirena'));
insert into users values ('janeGoodall', md5('monos'));
insert into users values ('margaretAtwood', md5('oryzandcrake'));

insert into points values (
	'Museu d’Art Contemporani de Barcelona', 
	41.383191300, 
	2.166866800,
	'Plaça dels Àngels, 1',
	'The Barcelona Museum of Contemporary Art is situated in the Plaça dels Àngels, in El Raval, Ciutat Vella, Barcelona, Spain. The museum opened to the public on November 28, 1995. Its current director is Bartomeu Marí (since 2008). Previous directors were Daniel Giralt-Miracle (1988–1994), Miguel Molins (1995–1998), Manuel J. Borja-Villel (1998–2007).', 
	'MACBA.jpg', 
	'http://www.macba.cat/');
insert into points values (
	'Museu Picasso',
	41.385216000,
	2.180892700,
	'Carrer Montcada, 15-23',
	'The Museu Picasso, located in Barcelona, Spain, houses one of the most extensive collections of artworks by the 20th-century Spanish artist Pablo Picasso. With more than 3,500 works by the painter, the museum has the most complete collection of works. The museum is housed in five adjoining medieval palaces in Barcelona’s La Ribera and is located on Montcada Street in the (Bank District) of Barcelona. It opened to the public on March 9 in 1963, becoming the first museum dedicated to Picasso’s work and the only one created during the artist’s life. It has since been declared a (museum of national interest) by the Government of Catalonia.',
	'Museu_Picasso.jpg',
	'http://www.museupicasso.bcn.cat/');
insert into points values (
	'Museu Nacional d’Art de Catalunya',
	41.368439900,
	2.153570000,
	'Parc de Montjuïc, s/n',
	'The Museu Nacional d’Art de Catalunya, abbreviated as MNAC, is the national museum of Catalan visual art located in Barcelona, Catalonia, Spain. Situated on Montjuïc hill at the end of Avinguda de la Reina Maria Cristina, near Pl Espanya. The museum is especially notable for its outstanding collection of romanesque church paintings, and for Catalan art and design from the late 19th and early 20th centuries, including modernisme and noucentisme. The Museum is housed in the Palau Nacional, a huge, Italian-style building dating to 1929. The Palau Nacional, which has housed the Museu d’Art de Catalunya since 1934, was declared a national museum in 1990 under the Museums Law passed by the Catalan Government. That same year, a thorough renovation process was launched to refurbish the site, based on plans drawn up by the architects Gae Aulenti and Enric Steegmann, who were later joined in the undertaking by Josep Benedito. The Oval Hall was reopened in 1992 on the occasion of the Olympic Games, and the various collections were installed and opened over the period from 1995 (when the Romanesque Art section was reopened) to 2004. The Museu Nacional d’Art de Catalunya (Museu Nacional) was officially inaugurated on 16 December 2004.',
	'MNAC.jpg',
	'http://museunacional.cat');
insert into points values (
	'Museu de la Xocolata',
	41.387677000,
	2.181664900,
	'Museu de la Xocolata is a private museum in Barcelona, Catalonia, Spain, owned by the Gremi de Pastisseria de Barcelona (the city pastry-makers’ guild).',
	'Museu_de_la_Xocolata_de_Barcelona.JPG',
	'http://www.museuxocolata.cat/');
insert into points values (
	'Museu del FC Barcelona',
	41.380842400,
	2.122879900,
	'Carrer del Comerç, 36',
	'The FC Barcelona museum was inaugurated on 24 September 1984 under the presidency of Josep Lluís Nuñez. In 2000 the museum was renamed President Nuñez museum under the presidency of his successor, Joan Gaspart. On 15 June 2010 the museum was reopened after a long restructuring. The restructuring saw the museum split in three separate sections with a 3D cinema, audiovisual touch-screen, and information on the history of FC Barcelona. The first section includes a collection of photos, documents and trophies detailing the club’s history on an interactive glass wall, allowing visitors to touch the screens and see information wall. The glass wall, equipped with laser technology, allows the exhibition of video, images and music through user-feedback. The second section is a private art collection on permanent display at the museum which exhibits works by local artists as Dalí, Miró and Tàpies. In the third section is the Futbolart Collection displays various football memorabilia from throughout the history of the club including a trophy-room with every trophy, or a replica thereof, that the club has ever won.',
	'Trophies_FCBarcelona_museum.jpg',
	'http://www.fcbarcelona.es/camp-nou/camp-nou-experience');
insert into points values (
	'Fundació Antoni Tàpies',
	41.391547000,
	2.163795000,
	'Carrer d’Aragó, 255',
	'The Fundació Antoni Tàpies is a cultural center and museum, located in Carrer d’Aragó, in Barcelona, Catalonia (Spain), dedicated mainly to the life and works of the painter Antoni Tàpies. The Fundació was created in 1984 by the artist Antoni Tàpies to promote the study and knowledge of modern and contemporary art. It combines the organisation of temporary exhibitions, symposia, lectures and film seasons with a range of publications to go with the activities and periodic shows of Antoni Tàpies’ work. The Fundació owns one of the most complete collections of Tàpies’ work, mostly made up of donations by Antoni and Teresa Tàpies.',
	'fundacioTapies.JPG',
	'http://www.fundaciotapies.org/');
insert into points values (
	'Centre d’Art Santa Mònica',
	41.377793200,
	2.175954500,
	'La Rambla, 7',
	'Centre d’Art Santa Mònica, sometimes shortened as CASM, is a public museum of contemporary art in Barcelona (Catalonia) opened in 1988. It’s located in the Raval side of Rambla de Santa Mònica (Ciutat Vella district). It has no permanent collection but it hosts a number of travelling expositions of contemporary Spanish and international artists every year. Entrance is free.The building that hosts CASM is a 1626 Renaissance convent that became a monument of national interest in 1984.',
	'Centre_Art_Santa_Monica.JPG',
	'http://www.artssantamonica.cat');
insert into points values (
	'Centre de Cultura Contemporània de Barcelona',
	41.383764000,
	2.166495300,
	'Carrer Montalegre, 5',
	'The Centre de Cultura Contemporània de Barcelona is one of the most visited exhibition and arts centres in the city of Barcelona, Spain. Situated in the Raval district, the Centre’s core theme is the city and urban culture. Its success is based on quality, its rather eclectic approach, attention to a broad cross section of publics and the unique way it addresses issues with the aim of linking the academic world with creative processes and citizens in general. The CCCB organizes and produces exhibitions, debates, festivals and concerts; programmes film cycles, courses and lectures; encourages creation using new technologies and languages, explores and promotes the ongoing fusion of languages and different genres, and takes in-house productions to other national and international arts centres, museums and institutions. The underlying aim of these activities is to generate debate, thinking and reflection on the theme of the city and public space, and other issues that define current affairs. The CCCB is also an open space for creators, associations and freelance programmers with whom it has forged links over the years.',
	'CCCB_20070408.jpg'
	'http://www.cccb.org');
insert into points values (
	'Museu Europeu d’Art Modern',
	41.384946200,
	2.180383300,
	'Carrer de la Barra de Ferro, 5',
	'The European Museum of Modern Art , MEAM, exhibits the finest Contemporary Figurative Art. It is owned by TheFoundation of Arts and Artists, and the purpose is to promote and diffuse Figurative Art of the 20th and 21st Centuries. The Foundation’s claim is to find a new contemporary language without denying tradition by progressing into the new Century. Moreover, the Foundation of Arts and Artists, organizes each year the Figurativas Painting and Sculpture Awards, and with the wesbiteFigurativas en Red opens a window with the artworks of the Contemporary Figurative artists.',
	'museu_de_art_modern.jpeg',
	'http://www.meam.es/');
insert into points values (
	'Museu de l’Eròtica de Barcelona',
	41.382289800,
	2.172588600,
	'La Rambla, 96',
	'Right in the heart of one of the most beautiful and visited cities in the world, the Erotic Museum of Barcelona is, since its opening in 1997, one of the most emblematic spots of the count city. The museum is located at La Rambla, opposite La Boqueria, one of the most attractive and known markets in the world. The sexy world of the Hindu Kama Sutra, erotic art banned in Japan, the origins of Spanish pornographic cinema, the history of erotic art through the eyes of the great masters of painting and the most astonishing sexual records...'
	'museo-erotico-de-barcelona.jpg',
	'http://www.erotica-museum.com/');
insert into points values (
	'Museu de Cera de Barcelona',
	41.377284500,
	2.177398900, 
	'Passatge de la Banca, 7',
	'In the Museu de Cera affect the more than 360 figures of kings, murderers, popes and other famous personalities almost lifelike as the great model, the waxworks Madame Tussauds in London. The focus of the wax figures Cabinet in Barcelona is on personalities of the Spanish-Catalan history and the region, but also many internationally known figures are here.',
	'museo_cera_barcelona_small.jpg',
	'http://www.museocerabcn.com/');
insert into points values (
	'Museu d’idees i invents de Barcelona',
	41.382210400,
	2.178085500,
	'Carrer de la Ciutat, 7',
	'The miba houses a wide variety of content, encompassing the fascinating spectrum of invention and creativity. Each space fulfils a specific role, with the aim of creating an interesting experience for all those involved in the creative and business processes as well as the general public. The miba has three sections: Limitless Society, Reflectionarium, Corner of the Absurd.'
	'miba.jpg',
	'http://www.mibamuseum.com/');
insert into points values (
	'Museu Blau',
	41.410789000,
	2.220654000,
	'Carrer de Leonardo Da Vinci, 4',
	'The Natural History Museum of Barcelona consists of four centres located in three strategic parts of the city: Ciutadella Park, Montjuïc Mountain and the Forum. Ciutadella Park houses two museums, the Martorell Museum (the former geology building) and the Laboratory of Nature (the former zoology building, called El Castell dels tres Dragons, or the Castle of the Three Dragons). Montjuïc Mountain is home to the Botanical Garden, the Botanical Institute, a research centre now under the combined auspices of the CSIC (the Scientific Research Council) and Barcelona City Council) and the Historic Botanical Garden, recently re-opened to the public. The Forum houses the Museu Blau (the Blue Museum), a centre with excellent modern facilities specifically designed for public use (exhibitions, workshops, conferences, media libraries, etc.).',
	'Museu_Blau.jpg',
	'http://www.museuciencies.cat/');


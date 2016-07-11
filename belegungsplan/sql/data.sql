INSERT INTO `belegungsplan`.`vermieter` (
`pid` ,
`name` ,
`vorname` ,
`adresse` ,
`plz` ,
`ort` ,
`telefon` ,
`natel` ,
`email` ,
`benutzername` ,
`passwort`
)
VALUES (
'', 'Florian', 'Huber', 'Wiesenstrasse 121', '4040', 'Basel', '061 345 97 37', '079 345 97 37', 'FlorianHuber@einrot.com', 'Ourief', 'quoo0fieSh'
);

INSERT INTO `belegungsplan`.`objekte` (`oid`, `bezeichnung`, `zusatzinfo`, `groesse`, `pid`) 
VALUES ('', 'Chalet Erika', 'Zusatz Terasse', '4 Zimmer Wohung, 4 Betten', '1');
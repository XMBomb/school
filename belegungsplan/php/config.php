<?php
/*
 *  @autor Daniel Mosimann
 *  @version 2.0
 *
 *  Dieses Modul definert dall Konfigurationsparameter und stellt die DB-Verbindung her.
 */

// Default-CSS-Klasse zur Formatierung der Eingabefelder
setValue('cfg_css_class_normal',"txt");
// Klasse zur Formatierung der Eingabefelder, falls die Eingabeprüfung negativ ausfällt
setValue('cfg_css_class_error',"err");
// Akzeptierte Funktionen
setValue('cfg_func_list', array("overview","calender"));
// Inhalt des Menus
// setValue( 'cfg_menu_list', array("overview"=>"Kontaktformular","liste"=>"Erfasste Kontakte") );

// Datenbankverbundung herstellen
mysql_connect("localhost", "root", "");		// Zu Datenbankserver verbinden		
mysql_select_db("belegungsplan");			// Datenbank selektieren
	   
?>
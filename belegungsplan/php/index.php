<?php
/*
 *  @autor Daniel Mosimann
 *  @version 2.0
 * 
 *  Ausschliessliche dieses Modul wird �ber die URL aufgerufen. Je nach �bergebenem
 *  Parameter "id" wird die entsprechende Funktion ausgef�hrt. Am Schluss wird das
 *  Haupttem�late eingef�gt.
 * 
 *  Beispielaufruf:         http://localhost/index.php?id=show
 * 
 *  Im Beispiel wird die Funktion "show" ausgef�hrt.
 */
session_start();
include("basic_functions.php");
include("config.php");
include("db_functions.php");
include("appl_functions.php");

error_reporting(E_ERROR | E_WARNING | E_DEPRECATED | E_STRICT);


// Anmeldung oder andere Sicherheitschecks, falls erw�nscht!
// anmeldung(), check_security(), etc.

// Dispatching, die �ber den Parameter "id" definierte Funktion ausf�hren
$func = $_REQUEST['id'];
// Falls  die verlangte Funktion nicht in der Liste der akzeptierten Funktionen ist, Default-Wert setzen!
$flist = getValue('cfg_func_list');
if ( !in_array($func, $flist) ) $func = $flist[0];
// Aktiver Link global speichern, da dieser sp�ter noch verwendet wird
setValue('func', $func);
// Funktion aufrufen und R�ckgabewert in "inhalt" speichern
setValue( 'inhalt', $func() );

// Haupttemplate aufrufen, Ausgabe an Client (Browser) senden
echo runTemplate("../templates/index.htm.php");
?>

<?php
/**
 *  @autor Daniel Mosimann
 *  @version 2.0
 *
 *  Dieses Modul stellt grundlegende Funktionen zur Verf�gung und ist damit
 *  Bestandteil des MVC-GIBS.
 *
 */

/*
 * Assoziativer, globaler Array f�r den Transport von Werten zwischen Anwendung und Templates
 */
$params = array();

/**
 * F�hrt ein HTML-Template aus und gibt das Produkt zur�ck.
 *
 * @param     $template     Filename des Templates
 * @param     $params       Assoziativer Array mit Werten, welche im Template eingef�gt werden.
 *                          key: Name der Variable, value: Wert
 */
function runTemplate( $template ) {
	ob_start();
	include($template);
	$inhalt=ob_get_contents();
	ob_end_clean();
	return $inhalt;
}

/*
 * Einen Wert im globalen Array $params speichern.
 *
 * @param       $key        Schl�ssel des Wertes (Index im globalen Array
 * @param       $value      Wert des Wertes
 *
 */
function setValue( $key, $value ) {
	global $params;
	$params[$key] = $value;
}

/*
 * Mehrere Werte im globalen Array $params speichern.
 *
 * @param       $list      Assoziativer Array mit den zu speichernden Werten
 *
 */
function setValues( $list ) {
	global $params;
	if ( count($list )) {
		foreach ($list as $k => $v ) {
			$params[$k] = $v;
		}
	}
}

/*
 * Wert aus dem globalen Array lesen
 *
 * @param       $field      Index des gew�nschten Wetes
 *
 */
function getValue( $key ) {
	global $params;
	return $params[$key];
}

/*
 * Wert aus dem globalen Array lesen und in HTML-Syntax umwandeln
 *
 * @param       $field      Index des gew�nschten Wetes
 *
 */
function getHtmlValue( $key ) {
	global $params;
	return htmlentities($params[$key]);
}

/**
 * Erstellt das Menu und gibt dieses aus. Wird im Haupttemplate aufgerufen.
 *
 * @param   $mlist      Array mit den Menueintr�gen. key: ID (Funktion), value: Menuoption
 * @param   $title      Menutitel
 * @param   $horizonal  true/false (horizontale/vertikale Ausrichtung des Menus)
 */
function getMenu( $mlist, $title="", $horizontal=false ) {
			if ( count($mlist )) {
				//$active_link = $_REQUEST['id'];
				$active_link = getValue('func');
				if (empty($active_link)) $active_link=key($mlist);
				$printmenu = "<table class=\"table_menu\">";
				if ( !empty($title) && !$horizontal ) $printmenu .= "<tr><th align=\"left\">$title</th></tr>";
				if ($horizontal) $printmenu .= "<tr>";
				foreach ( $mlist as $element => $option ) {
					if (!$horizontal) $printmenu .= "<tr>";
					$active = "";
					if ( $element == $active_link) $active = "id=active";
					$printmenu .= "<td nowrap><a class=\"link_menu\" $active href=\"".$_SERVER['PHP_SELF']."?id=".$element."\">".
					$option."</a></td>";
					if (!$horizontal) $printmenu .= "</tr>";
				}
				if ($horizontal) $printmenu .= "</tr>";
				$printmenu .= "</table>";
			}
			echo $printmenu;
}

/**
 * �bergebene SQL-Anweisung auf der DB ausf�hren und Resultat zur�ckgeben.
 *
 * @param   $sql       Select-Befehl, welcher ausgef�hrt werden soll
 */
function sqlSelect($sql) {
	$result = mysql_query($sql);
	if ( !$result ) die("Fehler: ".mysql_error());
	while ( $row=mysql_fetch_assoc($result) ) $data[]=$row;
	return $data;
}

/**
 * F�hrt einen SQL-Befehl aus.
 *
 * @param   $sql    SQL-Befehl, welcher ausgef�hrt werden soll
 */
function sqlQuery($sql) {
	$result = mysql_query($sql);
	if ( !$result ) die(mysql_error()."<pre>".$sql."</pre>");
}

/**
 * Aktives php-Modul noch einmal aufrufen.
 *
 * @param   $id     ID der Funktion, welche aufgerufen werden soll
 */
function redirect( $id="" ) {
	if (!empty($id)) $id="?id=$id";
	header("Location: ".$_SERVER['PHP_SELF'].$id);
	exit();
}

/**
 * Pr�ft ob ein Eingabewert leer ist oder nicht.
 *
 * @param   $value      Eingabewert
 * @param   $maxlength  Minimale L�nge der Eingabe
 */
function CheckEmpty( $value, $minlength=Null ) {
	if (empty($value)) return false;
	if ( $minlength != Null && strlen($value) < $minlength ) return false;
	else return true;
}

/**
 * Pr�ft ob eine Emailadresse korrekt ist oder nicht.
 *
 * @param   $value      Eingabewert
 * @param   $empty      Die Email-Adresse kann leer sein ('Y') oder nicht ('N')
 */
function CheckEmail( $value, $empty='N' ) {
	$pattern_email = '/^[^@\s<&>]+@([-a-z0-9]+\.)+[a-z]{2,}$/i';
	if ($empty=='Y' && empty($value)) return true;
	if ( preg_match($pattern_email, $value) ) return true;
	else return false;
}

/**
 * Pr�ft ob eine Name (Nachname, Vorname) korrekt ist oder nicht.
 * Erlaubt sind die Zeichen in den eckigen Klammern, mit einer L�nge
 * von mindestens 2 Zeichen.
 *
 * @param   $value      Eingabewert
 * @param   $empty      Der Name kann leer sein ('Y') oder nicht ('N')
 */
function CheckName( $value, $empty='N' ) {
	$pattern_name = '/^[a-zA-Z������ \-]{2,}$/';
	if ($empty=='Y' && empty($value)) return true;
	if ( preg_match($pattern_name, $value) ) return true;
	else return false;
}

/**
 * Pr�ft ob eine Ort korrekt ist oder nicht.
 *
 * @param   $value      Eingabewert
 * @param   $empty      Der Ort kann leer sein ('Y') oder nicht ('N')
 */
function CheckOrt( $value, $empty='N' ) {
	$pattern_ort = '/^[a-zA-Z������ \-]{2,}$/';
	if ($empty=='Y' && empty($value)) return true;
	if (empty($value)) return false;
	if ( preg_match($pattern_ort, $value) ) return true;
	else return false;
}

/**
 * Pr�ft ob es sich beim �bergebenen Wert um eine Zahl handelt.
 *
 * @param   $value      �bergebender Wert
 */
function isNumber( $value ) {
	if ( !is_numeric($value) ) return false;
	return true;
}

/**
 * Pr�ft ob ein Eingabewert eine Zahl ist.
 *
 * @param   $value         Eingabewert
 * @param   $minlength     Minimale L�nge der Zahl
 */
function CheckNumber( $value ) {
	if ( !isNumber($value) ) return false;
	else return true;
}

/**
 * Pr�ft ob es sich beim �bergebenen Wert um eine positive Ganzzahl handelt (ohne e,+,-).
 *
 * @param   $value      �bergebender Wert
 */
function isCleanNumber( $value ) {
	if ( !is_numeric($value) ) return false;
	$pattern_number = '/^[0-9]*$/';
	if ( preg_match($pattern_number, $value) ) return true;
	else return false;
	return true;
}

/**
 * Pr�ft ob ein Eingabewert eine Zahl ist. Eine Leereingabe ist erlaubt.
 *
 * @param   $value         Eingabewert
 * @param   $minlength     Minimale L�nge der Zahl
 */
function CheckCleanNumberEmpty( $value, $minlength=0) {
	if ( empty($value) ) return true;
	if ( !isCleanNumber($value) || strlen($value) < $minlength ) return false;
	else return true;
}

function getMonthName($monthNo){
	$months = array(
		'Januar',
		'Februar',
		'M&auml;rz',
		'April',
		'Mai',
		'Juni',
		'Juli',
		'August',
		'September',
		'Oktober',
		'November',
		'Dezember'
	);
	return $months[$monthNo - 1];
}

?>
<h1 class="title">Belegungsplan Kalender</h1>
<div class="calender">
<?php

$out = '';

$year = '2014';

$out .= '
	<table class="calenderTable">';
for ($monthNo=1; $monthNo <= 12; $monthNo++) { 
	$out .= '
		<tr>
			<td class="monthName">'.getMonthName($monthNo).'</td>'."\n";
	$daysPerMonth = date('t', strtotime('1-'.$monthNo.'-'.date('Y')));
	for ($dayNo=1; $dayNo <= $daysPerMonth; $dayNo++) {
		$isWeekend = (date('w' , strtotime($year.'-'.$monthNo.'-'.$dayNo)) == 0 || date('w' , strtotime($year.'-'.$monthNo.'-'.$dayNo)) == 6);
		if($isWeekend){
			$out .= '
			<td class="weekEnd" onclick="changeOcc(\''.$year.'-'.$monthNo.'-'.$dayNo.'\')">'.$dayNo.'</td>'."\n";
		}else{
			$out .= '
			<td class="weekDay" onclick="changeOcc(\''.$year.'-'.$monthNo.'-'.$dayNo.'\')">'.$dayNo.'</td>'."\n";
		}
	}
	$out .= '
		 </tr>';
}
echo $out;
?>
</div>
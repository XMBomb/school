<h1 class="title">Belegungsplan &Uuml;bersicht</h1>
<table class="overview" width="100%" cellspacing="1" >
		<tr>
				<th></th>
				<th></th>
				<th>Bezeichnung</th>
				<th>Grösse</th>
				<th>Name</th>
				<th>Vorname</th>
				<th>Ort</th>
				<th>Email</th>
				<th>Telefon</th>
		</tr>
		<?php
		$output = '';
		foreach (getValue('data') as $row) {
			$output = '
				<tr>
					<td><a href="?id=calender"><img center width="20" src="../images/search.png" alt="search"></a></td>
					<td><a href="id=?edit"><img width="20" src="../images/edit.png" alt="edit"></a></td>
					<td>'.$row['bezeichnung'].'</td>
					<td>'.$row['groesse'].'</td>
					<td>'.$row['name'].'</td>
					<td>'.$row['vorname'].'</td>
					<td>'.$row['ort'].'</td>
					<td><a class="email" href="mailto:'.$row['email'].'">'.$row['email'].'</a></td>
					<td>'.$row['telefon'].'</td>
				</tr>';
		}
		echo $output;
		?>

</table>
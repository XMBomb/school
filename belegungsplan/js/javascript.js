function confdel() {
    answer=confirm("Möchten Sie den Eintrag wirklich löschen?");
    return answer;
}

function changeOcc(date){
	console.log('here : '+date);
	window.location.replace('http://localhost/belegungsplan/php/?id=calender&addDate="' + date +'"') 
}
function confdel() {
    answer=confirm("M�chten Sie den Eintrag wirklich l�schen?");
    return answer;
}

function changeOcc(date){
	console.log('here : '+date);
	window.location.replace('http://localhost/belegungsplan/php/?id=calender&addDate="' + date +'"') 
}
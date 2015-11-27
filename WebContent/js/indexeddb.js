

function checkAvailable() {

	if (KageDB.isAvailable()) {

		document.getElementById("friendList").innerHTML = "利用可能です。";



	} else {
		document.getElementById("friendList").innerHTML = "利用できません。";
	}


}




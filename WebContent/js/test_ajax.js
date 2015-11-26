

function getFriendList() {
	var xmlResult = new XMLHttpRequest();
	var jsonParam = "user_id=2";
	var xmlURL = "http://localhost:8080/clipdb/ajax_test";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {

		if (xmlResult.readyState == 4) {
			var friendList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult["friendList"].length ; i++) {

				friend = jsonResult["friendList"][i];
				friendList += "ニックネーム" + friend["nickname"] + "<br>" + "シェアID"
						+ friend["share_id"] + "<br><br>";
			}

			document.getElementById('friendList').innerHTML = friendList;
			xmlResult = null;
			jsonResult = null;
		}

	}
	xmlResult.send(jsonParam);
	return true;

}

$('button').click(function() {
	getFriendList();
});

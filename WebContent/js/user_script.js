// ログインロック
function lock() {

	var result = null;
	console.log("ロックスクリプト使われてるよ");
	var cookieName = 'lock=';
	var allcookies = document.cookie;

	var position = allcookies.indexOf(cookieName);
	if (position != -1) {
		var startIndex = position + cookieName.length;

		var endIndex = allcookies.indexOf(';', startIndex);
		if (endIndex == -1) {
			endIndex = allcookies.length;
		}
		result = decodeURIComponent(allcookies.substring(startIndex, endIndex));
		console.log(result);
	}

	console.log("あとは、判定だわ");
	if (result != null) {
		if (result == "true") {
			console.log("ロック判定：ロック");
			$('#lock').attr('disabled', 'disabled');
		} else {
			console.log("ロック判定：ロック解除");
			$('#lock').removeAttr('disabled');
		}
	} else {
		console.log("ロック判定：ロック解除");
		$('#lock').removeAttr('disabled');
	}

	var cookieName = 'visited=';
	var allcookies = document.cookie;

	var position = allcookies.indexOf(cookieName);
	if (position != -1) {
		var startIndex = position + cookieName.length;

		var endIndex = allcookies.indexOf(';', startIndex);
		if (endIndex == -1) {
			endIndex = allcookies.length;
		}
		result2 = decodeURIComponent(allcookies.substring(startIndex, endIndex));
		console.log(result2);
	}

	var cnt = Number(result2);
	console.log(cnt);

	if (cnt == null) {
		$userList = "";
		console.log("失敗してない");
	} else if (cnt < 5) {
		console.log("1回以上失敗してる");
		$userList = $("<h4>" + cnt
				+ "回ログイン失敗しました<br>5回失敗すると、5分間ロックされます</h4>");
	} else {
		$userList = $("<h4>5分間ロックされます</h4>");
		console.log("ロックされちゃった");
	}
	$("#login").append($userList).trigger("create");
}

// ログアウト
function logout() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/logout";
	var userList = "";
	var setappend = function(json) {
		userList = "";

		document.getElementById('logout').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// 新規登録後
function addUser() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/adduser";
	var userList = "";
	var setappend = function(json) {
		userList = json.ErrorMessage + "<br>";
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// ユーザ削除
function deleteUser() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deleteuser";
	var userList = "";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" + "メールアドレス:"
				+ json.mailaddress + "<br>";
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// ユーザ情報の表示
function getUserList() {

	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList = "";
	var setappend = function(json) {
		userList = "<h2>" + "ニックネーム<br>" + json.nickname + "<br><br>"
				+ "メールアドレス:<br>" + json.mailaddress + "<br><br></h2>";
		document.getElementById('info').innerHTML = userList;
		var kyoka = "";
		var kyohi = "";
		var flag = "";
		if (json.friend_flag == 0) {
			kyoka = "checked";
			kyohi = "";
			flag = "checked";
		} else {
			flag = "";
			kyoka = "";
			kyohi = "checked";
		}
		var $onoffkun = $("<input type='checkbox' name='onoffswitch' class='onoffswitch-checkbox' id='myonoffswitch' "
				+ flag
				+ ">"
				+ "<label class='onoffswitch-label' for='myonoffswitch' onclick='test()'>"
				+ "<span  class='onoffswitch-inner'"
				+ kyoka
				+ "></span>"
				+ "<span  class='onoffswitch-switch'"
				+ kyohi
				+ "></span>"
				+ "</label>");
		$("#onoffswitch").append($onoffkun).trigger("create");

	};
	getJSON(URL, null, setappend);
}

// ニックネーム変更画面
function NickName() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList = "";
	var setappend = function(json) {
		userList = "ニックネーム:" + "<br>" + json.nickname + "<br>";
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// メールアドレス変更画面
function MailAddress() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList = "";
	var setappend = function(json) {
		userList = "メールアドレス:" + "<br>" + json.mailaddress + "<br>";
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

function MailAddress() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList = "";
	var setappend = function(json) {
		userList = "メールアドレス:" + "<br>" + json.mailaddress + "<br>";
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンド申請 承認設定
function AcceptFriend() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/acceptfriend";
	var userList = "";
	var setappend = function(json) {
		userList = json.Message;
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンド申請 拒否設定
function DenyFriend() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/denyfriend";
	var userList = "";
	var setappend = function(json) {
		userList = json.Message;
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

function test() {
	var data = document.getElementById('myonoffswitch').checked;

	if (data == false) {
		AcceptFriend();
	} else if (data == true) {
		DenyFriend();
	}
}

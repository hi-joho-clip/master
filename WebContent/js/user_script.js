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

	// console.log("あとは、判定だわ");
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
	var result2 = null;

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

	if (result2 == null) {
		$userList = "";
		console.log("失敗してない");
	} else if (cnt < 5) {
		console.log("1回以上失敗してる");
		$userList = $("<h4>" + cnt + "回ログイン失敗しました<br>5回失敗すると、5分間ロックされます</h4>");
	} else {
		$userList = $("<h4>5分間ロックされます</h4>");
		console.log("ロックされちゃった");
	}
	$("#login").append($userList).trigger("create");
}

// パスワード再発行ロック
function ForgotPass_lock() {

	var result = null;
	console.log("ロックスクリプト使われてるよ");
	var cookieName = 'lock_pass=';
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

	// console.log("あとは、判定だわ");
	if (result != null) {
		if (result == "true") {
			console.log("パスワード再発行_ロック判定：ロック");
			$('#lock').attr('disabled', 'disabled');
		} else {
			console.log("パスワード再発行_ロック判定：ロック解除");
			$('#lock').removeAttr('disabled');
		}
	} else {
		console.log("パスワード再発行_ロック判定：ロック解除");
		$('#lock').removeAttr('disabled');
	}

	var cookieName = 'ForgotPASS=';
	var allcookies = document.cookie;
	var result2 = null;

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

	if (result2 == null) {
		$userList = "";
		console.log("失敗してない");
	} else if (cnt < 5) {
		console.log("1回以上失敗してる");
		$userList = $("<h4>" + cnt
				+ "回パスワード再発行失敗しました<br>5回失敗すると、5分間ロックされます</h4>");
	} else {
		$userList = $("<h4>5分間ロックしました</h4>");
		console.log("ロックされちゃった");
	}
	$("#forgotpass").append($userList).trigger("create");
}

// ユーザID確認ロック
function userID_lock() {

	var result = null;
	console.log("ロックスクリプト使われてるよ");
	var cookieName = 'lock_user=';
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

	// console.log("あとは、判定だわ");
	if (result != null) {
		if (result == "true") {
			console.log("ユーザID_ロック判定：ロック");
			$('#lock').attr('disabled', 'disabled');
		} else {
			console.log("ユーザID_ロック判定：ロック解除");
			$('#lock').removeAttr('disabled');
		}
	} else {
		console.log("ユーザID_ロック判定：ロック解除");
		$('#lock').removeAttr('disabled');
	}

	var cookieName = 'LOCK_USERID=';
	var allcookies = document.cookie;
	var result2 = null;

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

	if (result2 == null) {
		$userList = "";
		console.log("失敗してない");
	} else if (cnt < 5) {
		console.log("1回以上失敗してる");
		$userList = $("<h4>" + cnt
				+ "回ユーザID確認失敗しました<br>5回失敗すると、5分間ロックされます</h4>");
	} else {
		$userList = $("<h4>5分間ロックしました</h4>");
		console.log("ロックされちゃった");
	}
	$("#IDLock").append($userList).trigger("create");
}

// ログアウト
function logout() {
	var jsonParam = "nonce=" + $('#nonce').val();;// 送りたいデータ
	var URL = hostURL + "/logout";
	var userList = "";
	var setappend = function(json) {
		userList = "";

		// ログアウト時はデータベースを削除する
		deleteDatabase("article");
		document.getElementById('logout').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// 新規登録後
function addUser() {
	if ($('#re_email').val() === $('#email').val()
			&& $('#passwd').val() === $('#re_passwd').val()
			&& $('#email').val().indexOf('@') != -1
			&& $('div.formErrorContent').val() != ""
			&& $('#username').val() != "" && $('#nickname').val() != ""
			&& $('#year').val() != "0"
			&& $('#month').val() != "0"
			&& $('#day').val() != "0") {
		var jsonParam = "username=" + encodeURIComponent(username.value)
				+ '&nickname=' + encodeURIComponent(nickname.value) + '&birth='
				+ year.value + '-' + month.value + '-' + day.value + '&email='
				+ encodeURIComponent(email.value) + '&password='
				+ encodeURIComponent(passwd.value) + '&nonce=' + nonce.value;// 送りたいデータ
		var URL = hostURL + "/adduser";
		var userList = "";
		var setappend = function(json) {
			if (json.flag == 0) {
				userList = "<c>" + json.ErrorMessage + "</c><br>";
			} else if (json.flag == 1) {
				location.href = hostURL + "/login/AccountCreateOK.html";
			}
			document.getElementById('info').innerHTML = userList;
		};
		getJSON(URL, jsonParam, setappend);
	} else {
		var List ="<c>入力フォームが正しくありません</c>";
		document.getElementById('info').innerHTML = List;
		/*toastr.error("入力フォームが正しくありません");*/
	}
}

// ユーザ削除（リダイレクトLoginページ）
function deleteuser() {

	var jsonParam ="nonce=" + $('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/deleteuser";
	var userList = "";
	var setappend = function(json) {
		userList = "";
		document.getElementById('deleteUser').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// ユーザ情報の表示
function getUserList() {

	var URL = hostURL + "/viewuser";
	var userList = "";
	var setappend = function(json) {
		userList = "<h5>" + "ニックネーム<br>" + json.nickname + "<br>"
				+ "メールアドレス<br>" + json.mailaddress + "</h5>";
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

		var online_flag = "";
		var on_kyohi = "";
		var on_kyoka = "";
		if (docCookies.getItem('online') === 'false') {
			online_flag = "";
			on_kyohi = "checked";
			on_kyoka = "";
			console.log("off");
		} else if (docCookies.getItem('online') === 'true') {
			online_flag = "checked";
			on_kyohi = "";
			on_kyoka = "checked";
			console.log("on");
		} else {
			console.log("unknown");
		}

		var $onoffkun = $("<input type='checkbox' name='onoffswitch' class='onoffswitch-checkbox' id='myonoffswitch' "
				+ flag
				+ ">"
				+ "<label class='onoffswitch-label' for='myonoffswitch' onclick='onoff()'>"
				+ "<span  class='onoffswitch-inner'"
				+ kyoka
				+ "></span>"
				+ "<span  class='onoffswitch-switch'"
				+ kyohi
				+ "></span>"
				+ "</label>");
		$("#onoffswitch").append($onoffkun).trigger("create");

		var $online = $("<input type='checkbox' name='onlineswitch' class='onlineswitch-checkbox' id='myonlineswitch' "
				+ online_flag
				+ ">"
				+ "<label class='onlineswitch-label' for='myonlineswitch' onclick='onlineMode()'>"
				+ "<span  class='onlineswitch-inner'"
				+ on_kyoka
				+ "></span>"
				+ "<span  class='onlineswitch-switch'"
				+ on_kyohi
				+ "></span>"
				+ "</label>");
		$("#onlineswitch").append($online).trigger("create");

	};
	getJSON(URL, null, setappend);
}

/**
 * オンラインとオフラインモード切替
 */
function onlineMode() {
	var data = document.getElementById('myonlineswitch').checked;
	console.log(data);

	if (data == false) {
		docCookies.setItem('online', true);
		console.log(docCookies.getItem('online'));
	} else if (data == true) {
		docCookies.setItem('online', false);
	}
}

// ニックネーム変更画面
function NickName() {

	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/viewuser";
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
	var URL = hostURL + "/viewuser";
	var userList = "";
	var setappend = function(json) {
		userList = "メールアドレス:" + "<br>" + json.mailaddress + "<br>";
		document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// ニックネーム変更時
function nick() {
	if ($('div.formErrorContent').val() != "" && $('#nickname').val() != "") {
		var jsonParam = "newnickname=" + encodeURIComponent(nickname.value)
				+ '&password=' + encodeURIComponent(passwd.value) + "&nonce="
				+ nonce.value;// 送りたいデータ
		console.log(jsonParam);
		var URL = hostURL + "/updatenickname";
		var setappend = function(json) {
			/*
			 * if(json.flag==0){ toastr.error(json.state); }else
			 * if(json.flag==1){ toastr.success(json.state); }else
			 * if(json.flag==2){ userList += json.state; }
			 * document.getElementById('nick').innerHTML = userList;
			 */
			if (json.flag == 0) {
				toastr.error(json.state);
			} else {
				location.href = hostURL + "/login/info.html";
				toastr.success(json.state);
			}
		};
		getJSON(URL, jsonParam, setappend);
	} else {
		toastr.error("入力フォームが正しくありません");
	}
}

// メールアドレス変更時
function mail() {

	if ($('#re_email').val() === $('#email').val()
			&& $('#email').val().indexOf('@') != -1
			&& $('div.formErrorContent').val() != "") {
		var jsonParam = "newemail=" + encodeURIComponent(email.value)
				+ '&password=' + encodeURIComponent(passwd.value) + "&nonce="
				+ nonce.value;// 送りたいデータ
		console.log(jsonParam);
		var URL = hostURL + "/updatemailaddress";
		var setappend = function(json) {
			/*
			 * if(json.flag==0){ toastr.error(json.state); }else
			 * if(json.flag==1){ toastr.success(json.state); }else
			 * if(json.flag==2){ userList += json.ErrorMessage; }
			 *
			 * document.getElementById('mail').innerHTML = userList;
			 */
			if (json.flag == 0) {
				toastr.error(json.state);
			} else {
				location.href = hostURL + "/login/info.html";
				toastr.success(json.state);
			}
		};
		getJSON(URL, jsonParam, setappend);
	} else {
		toastr.error("入力フォームが正しくありません");
	}
}

// パスワード変更時
function pass() {
	if ($('#passwd').val() === $('#re_passwd').val()
			&& $('div.formErrorContent').val() != ""
			&& $('#passwd').val() != "") {
		var jsonParam = "newpassword=" + encodeURIComponent(passwd.value)
				+ '&password=' + encodeURIComponent(now_pass.value) + "&nonce="
				+ nonce.value;// 送りたいデータ
		console.log(jsonParam);
		var URL = hostURL + "/updatepassword";
		var setappend = function(json) {
			/*
			 * if(json.flag==0){ toastr.error(json.state); }else
			 * if(json.flag==1){ toastr.success(json.state); }else
			 * if(json.flag==2){ userList += json.ErrorMessage; }
			 *
			 * document.getElementById('pass').innerHTML = userList;
			 */
			if (json.flag == 0) {
				toastr.error(json.state);
			} else {
				location.href = hostURL + "/login/info.html";
				toastr.success(json.state);
			}
		};
		getJSON(URL, jsonParam, setappend);
	} else {
		toastr.error("入力フォームが正しくありません");
	}
}

// フレンド申請 承認設定
function AcceptFriend() {

	var jsonParam ='nonce='+$('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/acceptfriend";
	var setappend = function(json) {
		if (json.flag == 0) {
			toastr.error(json.state);
		} else {
			toastr.success(json.state);
		}
		// userList = json.Message;
		// document.getElementById('info').innerHTML = userList;
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンド申請 拒否設定
function DenyFriend() {

	var jsonParam ='nonce='+$('#nonce').val();
	var URL = hostURL + "/denyfriend";
	var setappend = function(json) {
		if (json.flag == 0) {
			toastr.error(json.state);
		} else {
			toastr.success(json.state);
		}
	};
	getJSON(URL, jsonParam, setappend);
}

function onoff() {
	var data = document.getElementById('myonoffswitch').checked;
	// console.log(data);

	if (data == false) {
		AcceptFriend();
	} else if (data == true) {
		DenyFriend();
	}
}

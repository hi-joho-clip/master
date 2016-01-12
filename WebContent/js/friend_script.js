//フレンド登録者一覧画面
function getFriendList() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlist";
	var friendList = " <div class='remodal-bg'> <ul class='friendlist'>";
	var setappend = function(json) {

		for ( var i = 0; i < json.length; i++) {
			// リクエスト申請したユーザ一覧
			if (json[i].status == 2) {
				friendList += "<li>"
						+ json[i].nickname
						+ "&emsp;"
						+ "<a href='#' data-remodal-target='delete_request' onclick='document.getElementById(\"user_id\").innerHTML=\""
						+ json[i].friend_user_id + "\";'>取消</a></li>";
			}
		}
		for ( var i = 0; i < json.length; i++) {
			// フレンド一覧
			if (json[i].status == 3) {
				friendList += "<li>"
						+ json[i].nickname
						+ "&emsp;"
						+ "<a href='#' data-remodal-target='delete_friend' onclick='document.getElementById(\"user_id\").innerHTML=\""
						+ json[i].friend_user_id + "\";'>削除</a></li>";
			}
		}
		friendList += "</ul>";
		document.getElementById('info').innerHTML = friendList + "</div>";
		$('input#id_search').quicksearch('section div div ul li');
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンドボックス画面
function getFriendRequest() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendrequest";
	var friendList = " <div class='remodal-bg'> ";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			friendList += json[i].nickname
					+ "&emsp;"
					+ "<a href='#' data-remodal-target='kyoka' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id
					+ "\";'>承認</a>"
					+ "&emsp;"
					+ "<a href='#' data-remodal-target='kyohi' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>拒否</a>" + "<br>";
		}

		document.getElementById('info').innerHTML = friendList + "</div>";
	};
	getJSON(URL, jsonParam, setappend);

}

// フレンド検索画面
function getFriendSearch(nickname) {
	var jsonParam = "nickname=" + nickname.item(0).value;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/searchfriend";
	var friendList = "<div class='remodal-bg'>";
	var setappend = function(json) {
		if (json.length != 0) {
			for ( var i = 0; i < json.length; i++) {
				friendList += json[i].nickname
						+ "&emsp;"
						+ "<a href='#' data-remodal-target='add' onclick='document.getElementById(\"user_id\").innerHTML=\""
						+ json[i].user_id + "\";'>追加</a>" + "<br>";
			}
		} else {
			friendList += "空入力 または、検索結果が1件もない場合は、表示しません";
		}
		document.getElementById('info').innerHTML = friendList + "</div>";
	};
	getJSON(URL, jsonParam, setappend);
}

// リクエストを申請後の画面
function addRequest(user_id) {
	var jsonParam = "friend_user_id=" + user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/addrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

// リクエストを承認後の画面
function acceptRequest(user_id) {

	var jsonParam = "friend_user_id=" + user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/acceptrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;
	};
	getJSON(URL, jsonParam, setappend);
	location.reload();
}

// リクエストを否認後の画面
function denyRequest(user_id) {

	var jsonParam = "friend_user_id=" + user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/denyrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;
	};
	getJSON(URL, jsonParam, setappend);
	location.reload();
}

// リクエストを削除後の画面
function deleteRequest(user_id) {
	var jsonParam = "friend_user_id=" + user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deleterequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンドを削除後の画面
function deleteFriend(user_id) {

	var jsonParam = "friend_user_id=" + user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deletefriend";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

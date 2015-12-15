
//フレンド登録者一覧画面
function getFriendList(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlist";
	var friendList = "";
	var setappend = function(json) {

		friendList = "<form  method='post' action='../'>";

		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//フレンド承認・否認表示画面
function getFriendRequest(){
	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//フレンド検索画面
function getFriendSearch(){
	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/searchfriend";
	var friendList = "";
	var setappend = function(json) {

		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id +
			"<a onclick='http://localhost:8080/clipMaster/addrequest'>追加</a>" + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//リクエストを申請後の画面
function addRequest(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/addrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//リクエストを承認後の画面
function acceptRequest(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/acceptrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//リクエストを否認後の画面
function denyRequest(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/denyrequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//リクエストを削除後の画面
function deleteRequest(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deleterequest";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

//フレンドを削除後の画面
function deleteFriend(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deletefriend";
	var friendList = "";
	var setappend = function(json) {
		for ( var i = 0; i < jsonResult.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

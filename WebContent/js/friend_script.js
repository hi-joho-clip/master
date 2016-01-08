
//フレンド登録者一覧画面
function getFriendList(){

	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlist";
	var friendList = " <div class='remodal-bg'> <ul class='friendlist'>";
	var setappend = function(json) {

		for ( var i = 0; i < json.length ; i++) {
			console.log(json[i].status);
			//リクエスト申請したユーザ一覧
			if(json[i].status== 2){
				friendList += "<li>" + json[i].nickname + "&emsp;" +
				"<a href='#' data-remodal-target='delete_request'>取消</a></li>";
			}
		}
		for ( var i = 0; i < json.length ; i++) {
			//フレンド一覧
			if(json[i].status == 3){
				friendList += "<li>" + json[i].nickname + "&emsp;" +
				"<a href='#' data-remodal-target='delete_friend'>削除</a></li>" ;
			}
		}
		friendList += "</ul>";
		/*console.log(friendList);*/
		document.getElementById('info').innerHTML=friendList;
		$('input#id_search').quicksearch('section div div ul li');
	};
	getJSON(URL, jsonParam, setappend);
}

//フレンドボックス画面
function getFriendRequest(){
	var jsonParam = null;//送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendrequest";
	var friendList = " <div class='remodal-bg'> ";
	var setappend = function(json) {
		for ( var i = 0; i < json.length ; i++) {
			friendList += json[i].nickname + "&emsp;" +
			"<a href='#' data-remodal-target='kyoka'>承認</a>"  + "&emsp;" +
			"<a href='#' data-remodal-target='kyohi'>拒否</a>" + "<br>";
		}

		document.getElementById('info').innerHTML=friendList + "</div>";
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
			friendList += jsonResult[i].nickname + "&emsp;" +
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
		for ( var i = 0; i < json.length ; i++) {
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
		for ( var i = 0; i < json.length ; i++) {
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
		for ( var i = 0; i < json.length ; i++) {
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
		for ( var i = 0; i < json.length ; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML=friendList;
	};
	getJSON(URL, jsonParam, setappend);
}

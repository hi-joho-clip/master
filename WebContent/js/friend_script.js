/*// フレンドボックス画面
function getFriendRequest() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendrequest";
	var friendList = " <div class='remodal-bg'> ";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			friendList += "<div id='"+json[i].nickname+"'>" + "<h8>" + json[i].nickname + "</h8>"
					+ "<a class='btn btn-primary' href='#' data-remodal-target='kyoka' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>承認</a>"
					+"&emsp;"
					+ "<a class='btn btn-danger' href='#' data-remodal-target='kyohi' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>拒否</a></div>" ;
		}
		document.getElementById('info').innerHTML = friendList + "</div>";
	};
	getJSON(URL, jsonParam, setappend);

}*/


// フレンドボックス画面
function getFriendRequest() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendrequest";
	var setappend = function(json) {
		for ( var i = 0; i < json.length; i++) {
			$('#table-body').append('<tr id="'+json[i].nickname+'" class="first"><td><h8 style="padding-right:1em; font-size:4em">' + json[i].nickname + "</h8></td><td><h8 style='padding-right:0.2em'>" + "<a style='font-size:0.7em;' href='#' class='btn btn-primary' data-remodal-target='kyoka' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>承認</a></h8></td>" + "&emsp;"
					+ "<td><h8><a style='font-size:0.7em;' href='#' class='btn btn-danger' data-remodal-target='kyohi' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>拒否</a></h8></td>");
		}
		/*document.getElementById('info').innerHTML = friendList + "</div>";*/
	};
	getJSON(URL, jsonParam, setappend);

}

// フレンド検索画面
function getFriendSearch(nickname) {
	var jsonParam = "nickname=" + nickname.item(0).value;// 送りたいデータ
	var URL = hostURL + "/searchfriend";
	var friendList = "<div class='remodal-bg'><table><tbody>";
	var setappend = function(json) {
		if (json.length != 0) {
			for ( var i = 0; i < json.length; i++) {
				friendList +="<tr><td id='"+json[i].nickname+"'><h8 style='padding-right: 1em;'>" + json[i].nickname
						+ "</h8><a class='btn btn-success' href='#' data-remodal-target='add' onclick='document.getElementById(\"user_id\").innerHTML=\""
						+ json[i].user_id + "\";'>追加</a></td><tr>";

			}
		} else {
			friendList += "空入力 または、検索結果が1件もありません。";
		}
		document.getElementById('info').innerHTML = friendList + "<tbody></table></div>";
	};
	getJSON(URL, jsonParam, setappend);
}

// リクエストを申請後の画面
function addRequest(user_id) {
	console.log($('#nonce').val());
	var jsonParam = "friend_user_id=" + user_id+"&nonce="+$('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/addrequest";
	var friendList = "";
	var setappend = function(json) {
		/*for ( var i = 0; i < json.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;*/
		if(json.flag==0){
			toastr.error(json.state);
		}else{
			toastr.success(json.state);
			$('#' + json.nickname).remove();
		}
	};
	getJSON(URL, jsonParam, setappend);
}

// リクエストを承認後の画面
function acceptRequest(user_id) {

	var jsonParam = "friend_user_id=" + user_id+"&nonce="+$('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/acceptrequest";
	var friendList = "";
	var setappend = function(json) {
	/*	for ( var i = 0; i < json.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}

		document.getElementById('info').innerHTML = friendList;*/
		if(json.flag==0){
			toastr.error(json.state);
		}else{
			toastr.success(json.state);
			$('#' + json.nickname).remove();
		}
	};
	getJSON(URL, jsonParam, setappend);
	//location.reload();
}

// リクエストを否認後の画面
function denyRequest(user_id) {

	var jsonParam = "friend_user_id=" + user_id+"&nonce="+$('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/denyrequest";
	var friendList = "";
	var setappend = function(json) {
		/*for ( var i = 0; i < jsonResult.length; i++) {
			friendList += "ID:" + jsonResult[i].friend_user_id + "<br>";
		}
		document.getElementById('info').innerHTML = friendList;*/
		if(json.flag==0){
			toastr.error(json.state);
		}else{
			toastr.success(json.state);
			$('#' + json.nickname).remove();
		}
	};
	getJSON(URL, jsonParam, setappend);
	//location.reload();
}

// フレンド検索のフレンド、申請に上限
function limit() {

	var jsonParam = "";// 送りたいデータ
	var URL = hostURL + "/friendlistaf";
	var $remodal = "";
	var setappend = function(json) {

		if (json.flag == 2) {
			$remodal += "<h3>フレンド人数オーバー</h3>"
					+ "<a class='remodal-confirm' href='#'>OK</a>";
		} else if (json.flag == 1) {
			$remodal += "<h3>フレンド申請オーバー</h3>"
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#'>OK</a>";
		} else if (json.flag == 0) {
			$remodal += "<h3>リクエスト送信</h3>"
					//+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#' onclick='addRequest(document.getElementById(\"user_id\").innerHTML);location.reload()\'>追加</a>"
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#' onclick='addRequest(document.getElementById(\"user_id\").innerHTML);\'>追加</a>"
					+ "&emsp;<a data-remodal-action='cancel' class='remodal-cancel' href='#'onclick='javascript:return false;'>キャンセル</a>";
		}
		$("#limit").append($remodal).trigger("create");
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンド数が上限だと、承認ボタンが消える処理
function limitBox() {

	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendlistaf";
	var $remodal = "";
	var setappend = function(json) {
		if (json.flag == 2) {
			$remodal += "<h3>フレンド数が上限により承認できません</h3>"
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#'>OK</a>";
		} else {
			$remodal += "<h3>フレンド承認</h3>"
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#' onclick='acceptRequest(document.getElementById(\"user_id\").innerHTML);location.reload()\'>OK</a>"
					+ "<a data-remodal-action='cancel' class='remodal-cancel' href='#' onclick='javascript:return false;'>キャンセル</a>";
		}
		$("#limitBox").append($remodal).trigger("create");
	};
	getJSON(URL, jsonParam, setappend);
}

//フレンド登録者のリストをもらう処理
function getFriends() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendlistff";
	getJSON(URL, jsonParam, get_friends);
}

//フレンド申請があると、申請通知が来る
function notice() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendrequest";
	var setappend = function(json) {
		if (json.length > 0) {
			console.log("申請あるよ");
			$remodal = "<h4>フレンドボックス<img src='img/friendBOX.png'></h4>";
		} else {
			console.log("申請ないよ");
			$remodal = "<h4>フレンドボックス</h4>";
		}
		$("#notice").append($remodal).trigger("create");
	};
	getJSON(URL, jsonParam, setappend);
};


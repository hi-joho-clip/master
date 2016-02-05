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
			$('#table-body').append('<tr id="'+json[i].nickname+'" class="first"><td><h8>' + json[i].nickname + "</h8></td><td><h8>" + "<a href='#' class='btn btn-primary' data-remodal-target='kyoka' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>承認</a></h8></td>" + "&emsp;"
					+ "<td><h8><a href='#' class='btn btn-danger' data-remodal-target='kyohi' onclick='document.getElementById(\"user_id\").innerHTML=\""
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
	var friendList = "<div class='remodal-bg'>";
	var setappend = function(json) {
		if (json.length != 0) {
			for ( var i = 0; i < json.length; i++) {
				friendList +="<div id='"+json[i].nickname+"'><h8>" + json[i].nickname
						+ "</h8><a class='btn btn-primary' href='#' data-remodal-target='add' onclick='document.getElementById(\"user_id\").innerHTML=\""
						+ json[i].user_id + "\";'>追加</a></div>";

			}
		} else {
			friendList += "空入力 または、検索結果が1件もありません。";
		}
		document.getElementById('info').innerHTML = friendList + "</div>";
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
	var URL = hostURL + "/friendlist";
	var $remodal = "";
	var setappend = function(json) {
		var CON = 0; // 申請人数
		var friend = 0; // フレンドの人数
		for ( var i = 0; i < json.length; i++) {
			if (json[i].status == 2) {
				CON += 1;
			} else {
				friend += 1;
			}
		}
		console.log(CON);

		if (json.length > 49) {
			$remodal += "<h2>フレンド人数オーバー</h2>"
					+ "<a class='remodal-confirm' href='#'>OK</a>";
		} else if (CON > 9) {
			$remodal += "<h2>フレンド申請オーバー</h2>"
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#'>OK</a>";
		} else if (json.length < 50 && CON < 10) {
			$remodal += "<h2>リクエスト送信</h2>"
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
	var URL = hostURL + "/friendlist";
	var $remodal = "";
	var setappend = function(json) {
		console.log("来てる");
		var friend = 0; // フレンドの人数
		for ( var i = 0; i < json.length; i++) {
			if (json[i].status == 3) {
				friend += 1;
			}
		}
		if (friend > 49) {
			$remodal += "<h2>フレンド数が上限により承認できません</h2>"
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#'>OK</a>";
		} else {
			$remodal += "<h2>フレンド承認</h2>"
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


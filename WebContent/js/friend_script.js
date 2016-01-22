var tableData = new Array();
var sortKey = ["Name","Time"]; // ソート項目
var asc = false; // 昇順(true)/降順(false)
var nowSortKey = "Name"; // 現在ソートキー
//フレンド登録者一覧画面
function getFriendList() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlist";
	var setappend = function(json) {

		for ( var i = 0; i < json.length; i++) {
			// リクエスト申請したユーザ一覧
			if (json[i].status == 2) {
				var now = new Date();
				now.setTime(json[i].created);

				tableData[i] = new Array();
				tableData[i] = {Name:json[i].nickname, Time:now.getFullYear() + "年" + (now.getMonth() + 1) + "月" + now.getDate() + "日", Submit: "<a href='#' data-remodal-target='delete_request' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>取消</a>"};
			}
		}
		for ( var i = 0; i < json.length; i++) {
			if(json[i].status == 3){
				//フレンド一覧
				var now = new Date();
				now.setTime(json[i].created);

				tableData[i] = new Array();
				tableData[i] = {Name:"<a href='index.html'onclick='javascript:$.cookie(\"viewMode\",\"3\");$.cookie(\"shareLists\",\""+json[i].friend_user_id+"\");'>"+json[i].nickname, Time:now.getFullYear() + "年" + (now.getMonth() + 1) + "月" + now.getDate() + "日", Submit: "<a href='#' data-remodal-target='delete_friend' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>削除</a>"};
			}
		}
		$('input#id_search').quicksearch('section div div ul li');
	};
	getJSON(URL, jsonParam, setappend);
	return tableData;
}
//ソート機能

function createTable(obj) {
	$(document).ready(function() {
		getFriendList();
	});

	var sort = "Name"; // デフォルトソート
	if (obj != undefined && obj.id != undefined) {
		sort = obj.id;
		var sortFlg = false;
		for ( var i = 0; i < sortKey.length; i++) {
			if (sortKey[i] == sort) {
				sortFlg = true;
				break;
			} else {
				continue;
			}
		}
		if (!sortFlg)
			return false; // ソート項目でなければ処理スキップ
	}
	asc = !asc; // 昇順/降順切り替え
	if (nowSortKey != sort) {
		asc = true; // ソート項目変更時は昇順
		nowSortKey = sort;
	}
	// ヘッダー編集
	editHeader();

	var tbodyElm = document.getElementById("tbody_detail");
	// tbody配下クリア
	deleteTable(tbodyElm);
	// データをソート
	tableData.sort(function(a, b) {
		var aName = a[sort];
		var bName = b[sort];
		if (asc)
			return (aName > bName) ? 1 : -1;
		else
			return (aName < bName) ? 1 : -1;
	});
	// tbody配下再作成
	for ( var i = 0; i < tableData.length; i++) {
		tbodyElm.appendChild(createTrElement(tableData[i]));
	}
}
/**
 * テーブルデータ削除
 */
function deleteTable(tbodyElm) {
	// 全ての子ノードを削除
	for ( var i = tbodyElm.childNodes.length - 1; i >= 0; i--) {
		tbodyElm.removeChild(tbodyElm.childNodes[i]);
	}
}
/**
 * <tr>タグ生成
 */
function createTrElement(data) {
	var trElm = document.createElement("tr");
	trElm.appendChild(createTdSubmitElement(data.Name));
	trElm.appendChild(createTdElement(data.Time));
	trElm.appendChild(createTdSubmitElement(data.Submit));
	return trElm;
}
/**
 * <td>タグ生成
 */
function createTdElement(txt) {
	var tdElm = document.createElement("td");
	var txtObj = document.createTextNode(txt);
	tdElm.appendChild(txtObj);
	return tdElm;
}

//タグを反応させるため
function createTdSubmitElement(txt) {
	var tdElm = document.createElement("td");
	tdElm.innerHTML=txt;
	return tdElm;
}

/**
 * テーブルヘッダー編集
 */
function editHeader() {
	// ヘッダー初期化
	for ( var i = 0; i < sortKey.length; i++) {
		var obj = document.getElementById(sortKey[i]);
		obj.style.color = "#000000";
		obj.removeChild(obj.childNodes[0]);
		obj.appendChild(document.createTextNode(sortKey[i]));
	}
	// ソート項目の編集
	if (nowSortKey != undefined && nowSortKey != "") {
		var sortObj = document.getElementById(nowSortKey);
		sortObj.style.color = "#0000FF";
		sortObj.removeChild(sortObj.childNodes[0]);
		var txt = (asc) ? "▼" : "▲";
		sortObj.appendChild(document.createTextNode(nowSortKey + txt));
	}
}
/**
 * onloadイベント付与
 */
if (window.addEventListener) { // for W3C DOM
	window.addEventListener("load", createTable, false);
} else if (window.attachEvent) { // for IE
	window.attachEvent("onload", createTable);
} else {
	window.onload = createTable;
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
					+ json[i].friend_user_id + "\";'>承認</a>"
					+"&emsp;"
					+ "<a href='#' data-remodal-target='kyohi' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>拒否</a><br>" ;
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
	location.reload();
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
	location.reload();
}

// フレンド検索のフレンド、申請に上限
function limit() {

	var jsonParam = "";// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlist";
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
					+ "<a data-remodal-action='confirm' class='remodal-confirm' href='#' onclick='addRequest(document.getElementById(\"user_id\").innerHTML);location.reload()\'>追加</a>"
					+ "&emsp;<a class='remodal-cancel' href='#' onclick='javascript:return false;'>キャンセル</a>";
		}
		$("#limit").append($remodal).trigger("create");
	};
	getJSON(URL, jsonParam, setappend);
}

// フレンド数が上限だと、承認ボタンが消える処理
function limitBox() {

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlist";
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

// フレンド登録者のリストをもらう処理
function getFriends() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendlistff";
	getJSON(URL, jsonParam, get_friends);
}

// フレンド申請があると、申請通知が来る
function notice() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/friendrequest";
	var setappend = function(json) {
		if (json.length > 0) {
			console.log("申請あるよ");
			$remodal = "<img src='img/friendBOX.png'>";
		} else {
			console.log("申請ないよ");
			$remodal = "";
		}
		$("#notice").append($remodal).trigger("create");
	};
	getJSON(URL, jsonParam, setappend);
};


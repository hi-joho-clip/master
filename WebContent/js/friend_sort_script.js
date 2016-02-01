//var tableData = new Array();
//var sortKey = [ "Name", "Time" ]; // ソート項目
//var asc = false; // 昇順(true)/降順(false)
//var nowSortKey = "Name"; // 現在ソートキー

// フレンド登録者一覧画面
function getFriendList() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendlist";
	var setappend = function(json) {
		var cnt = 0;

		for ( var i = 0; i < json.length; i++) {
			// リクエスト申請したユーザ一覧
//			if (json[i].status == 2) {
//				var now = new Date();
//				now.setTime(json[i].created);
//				cnt += 1;
//
//
//				tableData[i] = {
//					Name : json[i].nickname,
//					Time : now.getFullYear() + "年" + (now.getMonth() + 1) + "月"
//							+ now.getDate() + "日",
//					Submit : "<a href='#' data-remodal-target='delete_request' onclick='document.getElementById(\"user_id\").innerHTML=\""
//							+ json[i].friend_user_id + "\";'>取消</a>"
//				};
//			}
		}
		for ( var i = 0; i < json.length; i++) {
			//フレンドを一覧
//			if (json[i].status == 3) {
//				// フレンド一覧
//				var now = new Date();
//				now.setTime(json[i].created);
//
//				tableData[i] = {
//					Name : "<a href='index.html'onclick='javascript:$.cookie(\"viewMode\",\"3\");$.cookie(\"shareLists\",\""
//							+ json[i].friend_user_id
//							+ "\");'>"
//							+ json[i].nickname,
//					Time : now.getFullYear() + "年" + (now.getMonth() + 1) + "月"
//							+ now.getDate() + "日",
//					Submit : "<a href='#' data-remodal-target='delete_friend' onclick='document.getElementById(\"user_id\").innerHTML=\""
//							+ json[i].friend_user_id + "\";'>削除</a>"
//				};
//			}
		}
		$('input#id_search').quicksearch('table tbody tr');
		console.log("owatta");

	};
	getJSON(URL, jsonParam, setappend);
	return tableData;

}
// ソート機能
//
//function createTable(obj) {
//
//	var sort = "Name"; // デフォルトソート
//	if (obj != undefined && obj.id != undefined) {
//		sort = obj.id;
//		var sortFlg = false;
//		for ( var i = 0; i < sortKey.length; i++) {
//			if (sortKey[i] == sort) {
//				sortFlg = true;
//				break;
//			} else {
//				continue;
//			}
//		}
//		if (!sortFlg)
//			return false; // ソート項目でなければ処理スキップ
//	}
//	asc = !asc; // 昇順/降順切り替え
//	if (nowSortKey != sort) {
//		asc = true; // ソート項目変更時は昇順
//		nowSortKey = sort;
//	}
//	// ヘッダー編集
//	editHeader();
//
//	var tbodyElm = document.getElementById("tbody_detail");
//	// tbody配下クリア
//	deleteTable(tbodyElm);
//	// データをソート
//	tableData.sort(function(a, b) {
//
//		var aName = a[sort];
//		var bName = b[sort];
//
//		aName = deleteTag(aName);
//		bName = deleteTag(bName);
//
//		if (asc)
//			return (aName > bName) ? 1 : -1;
//		else
//			return (aName < bName) ? 1 : -1;
//	});
//	// tbody配下再作成
//	for ( var i = 0; i < tableData.length; i++) {
//		tbodyElm.appendChild(createTrElement(tableData[i]));
//	}
//}
///**
// * テーブルデータ削除
// */
//function deleteTable(tbodyElm) {
//	// 全ての子ノードを削除
//	for ( var i = tbodyElm.childNodes.length - 1; i >= 0; i--) {
//		tbodyElm.removeChild(tbodyElm.childNodes[i]);
//	}
//}
///**
// * <tr>タグ生成
// */
//function createTrElement(data) {
//	var trElm = document.createElement("tr");
//	trElm.appendChild(createTdSubmitElement(data.Name));
//	trElm.appendChild(createTdElement(data.Time));
//	trElm.appendChild(createTdSubmitElement(data.Submit));
//	return trElm;
//}
///**
// * <td>タグ生成
// */
//function createTdElement(txt) {
//	var tdElm = document.createElement("td");
//	var txtObj = document.createTextNode(txt);
//	tdElm.appendChild(txtObj);
//	return tdElm;
//}
//
//// タグを反応させるため
//function createTdSubmitElement(txt) {
//	var tdElm = document.createElement("td");
//	tdElm.innerHTML = txt;
//	return tdElm;
//}
//
//// ソートうまく動かすために必要、不要なタグを削除
//function deleteTag(wasteA) {
//	console.log(wasteA[0]);
//	if (wasteA[0] == "<") {
//		for ( var i = 0; wasteA[i] != ">"; 0) {
//			wasteA = wasteA.slice(1);
//		}
//		wasteA = wasteA.slice(1);
//	}
//	return wasteA;
//}
//
///**
// * テーブルヘッダー編集
// */
//function editHeader() {
//	// ヘッダー初期化
//	for ( var i = 0; i < sortKey.length; i++) {
//		var obj = document.getElementById(sortKey[i]);
//		obj.style.color = "#999";
//		obj.removeChild(obj.childNodes[0]);
//		obj.appendChild(document.createTextNode(sortKey[i]));
//	}
//	// ソート項目の編集
//	if (nowSortKey != undefined && nowSortKey != "") {
//		var sortObj = document.getElementById(nowSortKey);
//		sortObj.style.color = "#428bca";
//		sortObj.removeChild(sortObj.childNodes[0]);
//		var txt = (asc) ? "▼" : "▲";
//		sortObj.appendChild(document.createTextNode(nowSortKey + txt));
//	}
//}
///**
// * onloadイベント付与
// */
//
//if (window.addEventListener) { // for W3C DOM
//	window.addEventListener("load", getFriendList, false);
//	getFriendList().createTable(obj);
//	console.log("addevent");
//	window.addEventListener("load", createTable, false);
//
//	console.log("addeventlistener createtable");
//} else if (window.attachEvent) { // for IE
//	window.attachEvent("onload", createTable);
//	console.log("attachevent createtable");
//} else {
//	window.onload = createTable;
//	console.log("onload createtable");
//}
// リクエストを削除後の画面
function deleteRequest(user_id) {
	var jsonParam = "friend_user_id=" + user_id;// 送りたいデータ
	var URL = hostURL + "/deleterequest";
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
	var URL = hostURL + "/deletefriend";
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

// フレンド登録者のリストをもらう処理
function getFriends() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendlistff";
	getJSON(URL, jsonParam, get_friends);
}

// フレンド申請があると、申請通知が来る
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
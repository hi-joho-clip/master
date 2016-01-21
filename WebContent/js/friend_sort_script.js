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
				tableData[i] = {Name:json[i].nickname, Time:now.getFullYear() + "年" + (now.getMonth() + 1) + "月" + now.getDate() + "日", Submit: "<a href=http://cly7796.net/wp/'>取消</a>"};

				/*"<a href='#' data-remodal-target='delete_request' onclick='document.getElementById(\"user_id\").innerHTML=\""
					+ json[i].friend_user_id + "\";'>取消</a>"};*/

				/*tableData += "{\"Name\":" + json[i].nickname + ",\"Time\":"
						+ now.getFullYear() + "年" + (now.getMonth() + 1) + "月, \"Submit\":" + "<a href='#' data-remodal-target='delete_request' onclick='document.getElementById(\"user_id\").innerHTML=\""
							+ json[i].friend_user_id + "\";'>取消</a>},";*/
			}else if(json[i].status == 3){
				//フレンド一覧
				var now = new Date();
				now.setTime(json[i].created);

				tableData[i] = new Array();
				tableData[i] = {Name:json[i].nickname, Time:now.getFullYear() + "年" + (now.getMonth() + 1) + "月" + now.getDate() + "日", Submit: "<a href=http://cly7796.net/wp/'>取消</a>"};

			}
		}
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
	trElm.appendChild(createTdElement(data.Name));
	trElm.appendChild(createTdElement(data.Time));
	trElm.appendChild(createTdElement(data.Submit));
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

//フレンド申請があると、申請通知が来る
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
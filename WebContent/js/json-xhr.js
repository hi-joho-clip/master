// このメソッドはすべてプロミスで書かれるべき
/*
 * ここでやる処理
 * ・JSONをPOSTして更新処理
 * ・更新の必要があるリストを更新する
 */

/**
 * プロミスの初期化処理（これはメインスレッドで呼ぶ必要がある）
 */
function initPromise() {

	if (!window.Promise) {
		operative.Promise = ES6Promise.Promise;
	}
}
var hostURL = "http://localhost:8080/clipMaster"; // http://clip-sc.com

/**
 * URLからJSONオブジェクトを取得する
 *
 * @param URL
 * @param param
 *            必要なパラメータ article_idとかね
 * @returns {Promise}
 */
function getURL(URL, param) {

	return new Promise(function(resolve, reject) {

		// サーバからアーティクルリストを取得する

		// console.log('parameter:' + param);
		if (URL === "") {
			reject(new Error("url is null"));
		}
		var req = new XMLHttpRequest();

		req.open("POST", URL, true);
		req.responseText = "json";
		// 送る方もJSONにしちゃった
		req.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// イベントリスナー
		req.onreadystatechange = function() {
			// 正常に取得できていた場合
			if (req.readyState == 4) {
				if (req.status == 200) {

					// console.log(req.responseText);
					resolve(req.responseText);
				} else {
					// 正常に取得できない
					reject(req.statusText);
				}
			}
		};
		req.onerror = function() {
			reject(new Error(req.statusText));
		};
		// タイムアウトは7000ms
		req.timeout = 20000;
		req.ontimeout = function() {
			reject(new Error("time out"));
		};
		req.send(param);
	});

}

/**
 * URLからJSONオブジェクトを取得する
 *
 * @param URL
 * @param param
 *            必要なパラメータ article_idとかね
 * @returns {Promise}
 */
function getURLForRedirect(URL, param) {

	return new Promise(function(resolve, reject) {

		// サーバからアーティクルリストを取得する

		// console.log('parameter:' + param);
		if (URL === "") {
			reject(new Error("url is null"));
		}
		var req = new XMLHttpRequest();

		req.open("POST", URL, true);
		req.responseText = "json";
		// 送る方もJSONにしちゃった
		req.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// イベントリスナー
		req.onreadystatechange = function() {
			// 正常に取得できていた場合
			if (req.readyState == 4) {
				if (req.status == 200) {

					// console.log(req.responseText);
					resolve(req.response);
				} else {
					// 正常に取得できない
					reject(req.statusText);
				}
			}
		};
		req.onerror = function() {
			reject(new Error(req.statusText));
		};
		// タイムアウトは7000ms
		req.timeout = 20000;
		req.ontimeout = function() {
			reject(new Error("time out"));
		};
		req.send(param);
	});

}

/**
 * リクエストの準備のため
 *
 * @returns {___anonymous1233_1573}
 */
function getRequest() {
	/**
	 * 使用するリクエストはここに書いておくと便利なはず
	 */
	var request = {
		articlelist : function getArticleLists() {

			return getURL(hostURL + "/mylist", null).then(JSON.parse);
		},
		article : function getArticle(param) {

			// JSONをテキストからオブジェクトへパースする必要がある。
			return getURL(hostURL + "/viewarticle", param).then(JSON.parse);
		},
		updatelist : function getArticleLists(param) {
			return getURL(hostURL + "/getupdatearticle", param);
		}

	};
	return request;
}

/**
 * 処理の最小単位として、機能ごとは必須 これらはすべてPromiseで書いて、ワーカーを利用する入口部分だけは
 * operativeを使ってDifferdでプロミスを書く必要がある
 *
 *
 *
 * 使い方：DB読み込み→読み込み完了→解析完了→本体ダウンロード完了→DB書き込み完了 大まかにこれらの処理がプロミスチェーンされておく必要がある
 *
 * @param param:記事IDとか
 */
function getArticleListAsync(param) {

	// URLなどのリクエスト取得
	// 問い合わせはGUIDいらないんじゃね？セッションやし。
	// Null、空の場合は全件取得用の

	var request = getRequest();
	// 文字列で送ります。
	param = 'json=' + JSON.stringify(param);
	// console.log('param' + JSON.stringify(param));

	// Promiseで実行順序を決定したまとめたメソッド(今回は取得のみ）
	return request.updatelist(param);
};

/**
 * getURLをラップして使いやすくしてみました テスト用のスタブとして利用くださいまし コールバックとして関数を渡すと返り値もテキストとしてもらえるなり。
 */
function getJSON(URL, param, callback) {

	// console.log("getJSON" + param);
	return new Promise(function(resolve, reject) {
		getURL(URL, param).then(JSON.parse).then(callback).then(function() {
			resolve();
		})['catch'](function(error) {
			console.log(error);
			reject();
		});
	});
}

/**
 * 記事追加用リンク
 */
function addArticle() {

	// getArticle_id(article_id);//html内の<div
	// UTF-8で取得する
	var jsonParam = 'url=' + encodeURIComponent($("#search-2").val());
	console.log(jsonParam);
	// Nonceを載せる
	jsonParam = jsonParam + "&nonce=" + $('#nonce').val();
	console.log("log:" + encodeURIComponent($("#search-1e").html()));
	var URL = hostURL + "/addarticle";
	var update_article = function(json) {

		if (json.flag == 0) {
			toastr.error(json.state);
		} else {
			toastr.success(json.state);
		}
	};
	getJSON(URL, jsonParam, update_article);
}

function thumView(json, width, height) {
	if (json.thum != null) {
		// console.log("now null");
		return "<img src='data:image/jpeg;base64," + json.thum + "' width='"
				+ width + "' height='" + height + "'alt='" + json.title + "'/>";
	} else {
		// console.log("notttt null");
		return "<img src='" + hostURL + "/img/sm2.png" + "' width='" + width
				+ "' height='" + height + "'alt='" + json.title + "'/>";
	}
}

function colorMode() {
	var color = docCookies.getItem('color');
	if (!color) {
		color = 'white';
		docCookies.setItem('color', color);
	}
	document.getElementById("viewcolor").href ="css/articlemenu_" + color+ ".css";
}

function changeColor() {
	var color = docCookies.getItem('color');
	if (!color) {
		color = 'black';
		docCookies.setItem('color', color);
	} else {
		if (color === 'white') {
			color = 'black';
			docCookies.setItem('color', color);
			document.getElementById("viewcolor").href ="css/articlemenu_" + color+ ".css";
		} else {
			color = 'white';
			docCookies.setItem('color', color);
			document.getElementById("viewcolor").href ="css/articlemenu_" + color+ ".css";
		}
	}

}

//フレンド登録者のリストをもらう処理
function getFriends() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendlistff";
	getJSON(URL, jsonParam, get_friends);
}

function setSessionStorage(name, value) {
	var storage = sessionStorage;
	storage.setItem(name, value);
}

function setLocalStorage(name, value) {
	var storage = localStorage;
	storage.setItem(name, value);
}
function getSessionStorage(name) {
	var storage = sessionStorage;
	return storage.getItem(name);
}

function getLocalStorage(name) {
	var storage = localStorage;
	return storage.getItem(name);
}


function isSettinOnLine() {
	var SetFlag = docCookies.getItem('online');

	if (SetFlag == null) {
		docCookies.setItem('online', true);
	}

	console.log(SetFlag);
	if (SetFlag === 'true') {
		if (navigator.onLine === true) {
			console.log('true');
			return true;
		} else if (navigator.onLine === false) {
			return false;
			console.log('false');
		} else {
			return false;
			console.log('UnknownNetworkState');
		}
	} else if (SetFlag === 'false') {
		return false;
	} else {
		toastr.warning('再ログインしてくだaさい');
	}
}

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

		console.log('parameter:' + param);
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

					//console.log(req.responseText);
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
		req.timeout = 7000;
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

			return getURL("http://localhost:8080/clipMaster/mylist", null)
					.then(JSON.parse);
		},
		article : function getArticle(param) {

			// JSONをテキストからオブジェクトへパースする必要がある。
			return getURL("http://localhost:8080/clipMaster/viewarticle", param)
					.then(JSON.parse);
		},
		updatelist : function getArticleLists(param) {
			return getURL("http://localhost:8080/clipMaster/getupdatearticle",
					param);
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
	//console.log('param' + JSON.stringify(param));

	// Promiseで実行順序を決定したまとめたメソッド(今回は取得のみ）
	return request.updatelist(param);
};

/**
 * getURLをラップして使いやすくしてみました テスト用のスタブとして利用くださいまし コールバックとして関数を渡すと返り値もテキストとしてもらえるなり。
 */
function getJSON(URL, param, callback) {

	console.log("getJSON" + param);
	getURL(URL, param).then(JSON.parse).then(function(json) {
		console.log(json);
		callback(json);
	})['catch'](function(error) {
		console.log(error);
	});

	/*
	 * .then(function(value) { return JSON.stringify(value, null, ' '); })
	 */
}

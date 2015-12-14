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
 * @param param 必要なパラメータ article_idとかね
 * @param guid 必須項目
 * @returns {Promise}
 */
function getURL(URL, guid ,param) {

	return new Promise(function(resolve, reject) {

		// サーバからアーティクルリストを取得する

		console.log(param);
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
					// var jsonResult = JSON.parse(xmlResult.responseText);
					// guid も渡す
					// プロパティで返す
					ret = {guid:guid, json:req.responseText};
					console.log(ret);
					resolve(ret);
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
		// guidは通信するときは必ず送るのですよ
		req.send(param + '&guid=' + guid);
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
		articlelist : function getArticleLists(guid) {
			return getURL("http://localhost:8080/clipMaster/mylist", guid, null)
					.then(JSON.parse);
		},
		article : function getArticle(guid,param) {
			// JSONをテキストからオブジェクトへパースする必要がある。
			return getURL("http://localhost:8080/clipMaster/viewarticle", guid, param)
					.then(JSON.parse);
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
function getArticleListAsync(guid) {

	// URLなどのリクエスト取得
	var request = getRequest();
	console.log(request);
	// Promiseで実行順序を決定したまとめたメソッド(今回は取得のみ）
	return request.articlelist(guid);
};

/**
 * getURLをラップして使いやすくしてみました テスト用のスタブとして利用くださいまし
 * コールバックとして関数を渡すと返り値もテキストとしてもらえるなり。
 */
function getJSON(URL, param, callback) {

	var guid = null;

	getURL(URL,guid, param).then(JSON.parse).then(function(json) {
		callback(json);
	})['catch'](function(error) {

		console.log(error);
	});

	/*.then(function(value) {
	*return JSON.stringify(value, null, '  ');
	*})
	*/
}

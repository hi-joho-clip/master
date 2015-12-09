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
 *            必要なパラメータ なしの場合もOK
 * @returns {Promise}
 */
function getURL(URL, param) {

	return new Promise(function(resolve, reject) {

		// サーバからアーティクルリストを取得する
		var req = new XMLHttpRequest();

		req.open("POST", URL, true);
		req.responseText = "json";
		req.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// イベントリスナー
		req.onreadystatechange = function() {
			// 正常に取得できていた場合
			if (req.readyState == 4) {
				if (req.status == 200) {
					// var jsonResult = JSON.parse(xmlResult.responseText);
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
		// タイムアウトは2000ms
		req.timeout = 2000;
		req.ontimeout = function() {
			reject(new Error("time out"));
		};
		// paramないときはnullなんで大丈夫やろ
		req.send(param);
	});

}


/**
 * リクエストの準備のため
 * @returns {___anonymous1233_1573}
 */
function getRequest() {
	 /**
	 * 使用するリクエストはここに書いておくと便利なはず
	 */
	var request = {
		articlelist : function getArticleLists(param) {
			return getURL("http://localhost:8080/clipMaster/mylist", param)
					.then(JSON.parse);
		},
		article : function getArticle(param) {
			// JSONをテキストからオブジェクトへパースする必要がある。
			return getURL("http://localhost:8080/clipMaster/viewarticle", param)
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
 * 使い方：DB読み込み→読み込み完了→解析完了→本体ダウンロード完了→DB書き込み完了
 * 大まかにこれらの処理がプロミスチェーンされておく必要がある
 *
 * @param param:記事IDとか
 */
function getArticleAsync(param) {

	// URLなどのリクエスト取得
	var request = getRequest();
	console.log(request);
	// Promiseで実行順序を決定したまとめたメソッド(今回は取得のみ）
	return request.articlelist(param).then(JSON.stringify);
};




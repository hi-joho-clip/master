
// このメソッドはメインスレッドで点火される
/*
 * ここでやる処理
 * ・JSONをPOSTして更新処理
 * ・更新の必要があるリストを更新する
 */

//importScripts('indexeddb.js','lib/es6-promise.min.js');

function getArticleAsync() {

	var operation;

	if (!window.Promise) {
		operative.Promise = ES6Promise.Promise;
	}

	// ここに渡す関数が別スレッド処理になる。
	operation = operative(function(value) {
		var deferred = this.deferred();

		// APIとしてアクセスする場合の認証


		console.log(loadArticleList(function() {
			if (value) {
				deferred.reject('reject');
			} else {
				deferred.fulfill('fulfill');
			}
		}));

//		getArticle(function() {
//			if (value) {
//				deferred.reject('reject');
//			} else {
//				deferred.fulfill('fulfill');
//			}
//		}, 0);

		return deferred;
	});

	// then：成功とcatch：失敗での処理
	operation(true).then(function(text) {
		console.log(text);
	})['catch'](function(err) {
		console.error(err);
	});

}


function loadArticleList() {
	// サーバからアーティクルリストを取得する
		var xmlResult = new XMLHttpRequest();
		var jsonParam = "user_id=2";
		var xmlURL = "http://localhost:8080/clipMaster/viewarticle";

		xmlResult.open("POST", xmlURL, true);
		xmlResult.responseText = "json";
		xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		// イベントリスナー
		xmlResult.onreadystatechange = checkStatus;

		function checkStatus() {

			if (xmlResult.readyState == 4) {
				var jsonResult = JSON.parse(xmlResult.responseText);

				return jsonResult;

			}

		}
		xmlResult.send(jsonParam);
		return true;


}
/**
 * マイリスト画面が呼ばれた時の処理 readyでこれ呼べばよろし ここではjQuery使える
 */

function initTopPage() {

	initPromise();
	/*
	 * まずオフラインとオンラインのモードをローカルストレージ クッキーの情報をローカルストレージ
	 */
	var username = docCookies.getItem('username');
	store.set('username', username);
	store.set('auto', true);

	if (true) {// isSupported([ 'chrome', 'opera', 'firefox', 'ie11', 'ie10'
		// ])) {

		// IDBに自身のキャッシュデータがない場合にアラート
		// getIDBAllArticleList(username).then(
		// function(values) {
		//
		// // 返り値はオブジェクトでないとダメ
		// if (values.length === 0) {

		// if (confirm("キャッシュデータがありません。\n"
		// + "すべての記事をダウンロードしますか？\n"
		// + "大量のデータをダウンロードする可能性があります。\n")) {
		// // 「はい」選択時の処理
		//
		// // usernameがある場合
		// if (username) {
		// startUpdate(username);
		// }
		// } else {
		// // 「いいえ」選択時の処理
		// console.log('alert no');
		// }
		// } else {
		// alert(getLocalStorage('auto'));
		// 自動更新の設定ならそのまま
		if (getLocalStorage('auto') === 'true') {
			// alert(username);
			startUpdate(username);
		} else {
			console.log('not autoupdate');
		}
	}
	// });
}
// }
function startUpdate(username) {
	var syncworker = operative({

		update : function(username, callback) {
			// initPromise();
			// var username = data.username;
			// updatecall(username);
			console.log('kousin');
			getIDBAllArticleList(username).then(getArticleListAsync).then(
					updateIDBArticleList).then(function() {
						callback();
					})['catch'](function(error) {
				// self.postMessage('更新失敗');
			});
			// 削除処理（サーバになくて、ローカルにあるものを消す
			var deleteWorkerArticle = function(json) {
				return new Promise(
						function(resolve, reject) {
							// 更新用リストを取ってきて、
							var ret = function(json) {
								updateIDBArticleListDelete(json);
							};
							json = JSON.stringify(json);
							getJSON(hostURL + "/getdeletearticle", "json="
									+ json, ret);

						});
			};

			getIDBAllArticleList(username).then(deleteWorkerArticle).then(
					function(value) {
						console.log('success');
					})['catch'](function(error) {
				console.error(error);
			});
			// 更新処理
			getFriendListCache();
			getTagCache();
			getUserListCache();
		}
	}, [ hostURL + '/js/json-xhr.js', hostURL + '/js/indexeddb.js',
			hostURL + '/js/lib/es6-promise.min.js',
			hostURL + '/js/lib/kagedb.js' ]);

	var suctoastr = function() {
		toastr.info('同期完了');
	};
	syncworker.update(username, suctoastr);

}

function updateManualStatus(key) {

	$('#kore').empty();

	if ((parseInt($('#updatekazu').val()) - 1) != 0) {
		$('#kore')
				.prepend(
						'<h6>残り：' + (parseInt($('#updatekazu').val()) - 1)
								+ '件</h6>');
	} else {
		toastr.success('更新完了');
	}

}

function manualStartUpdate() {

	// Downloadボタンを無効に

	initPromise();
	var username = docCookies.getItem('username');

	getIDBAllArticleList(username).then(getArticleListAsync).then(
			updateManualIDBArticleList)['catch'](function(error) {
		// self.postMessage('更新失敗');
	});

	// 更新処理
	//alert('manualStartupdate');
	// 削除処理（サーバになくて、ローカルにあるものを消す
	var deleteWorkerArticle = function(json) {
		return new Promise(function(resolve, reject) {
			// 更新用リストを取ってきて、
			var ret = function(json) {
				updateIDBArticleListDelete(json);
			};
			json = JSON.stringify(json);
			getJSON(hostURL + "/getdeletearticle", "json=" + json, ret);

		});
	};

	getIDBAllArticleList(username).then(deleteWorkerArticle).then(
			function(value) {
				console.log('success');
			})['catch'](function(error) {
		console.error(error);
	});
	getFriendListCache();
	getTagCache();
	getUserListCache();
}

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

	/*
	 * 対応ブラウザならオフライン処理を開始 （ここでダイアログ
	 */

	if (isSupported([ 'chrome', 'opera', 'firefox', 'ie11', 'ie10' ])) {

		// IDBに自身のキャッシュデータがない場合にアラート
		getIDBAllArticleList(username).then(
				function(values) {

					// 返り値はオブジェクトでないとダメ
					if (values.length === 0) {

						if (confirm("キャッシュデータがありません。\n"
								+ "すべての記事をダウンロードしますか？\n"
								+ "大量のデータをダウンロードする可能性があります。\n")) {
							// 「はい」選択時の処理

							// usernameがある場合
							if (username) {
								startUpdate(username);
							}
						} else {
							// 「いいえ」選択時の処理
							console.log('alert no');
						}
					} else {
						// 自動更新の設定ならそのまま
						if (store.get('auto')) {
							startUpdate(username);
						} else {
							console.log('is null');
						}
					}
				});
	}
}
function startUpdate(username) {

	if (window.Worker) {
		console.log('worker start:' + username);
		// これが疑似的なPromiseオブジェクト→Deferredオブジェクト
		var worker = new Worker('../js/worker.js');
		worker.addEventListener('message', function(e) {
			// ここではconsoleでJSONデータを表示する
			toastr.success('更新が完了しました');

			// testDataなら書き込める
			//console.log('JSON data: ', e.data);
		}, true);

		worker.postMessage({
			'cmd' : 'IDBupdate',
			'username' : username
		});

	}
}

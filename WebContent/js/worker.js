


importScripts('json-xhr.js','indexeddb.js', 'lib/es6-promise.min.js');
/**
 * 使用するリクエストはここに書いておくと便利なはず
 *
 *
 *ワーカーごとに取れるイベントハンドラーは1つなのでパラメータで識別する必要がある
 *このWorkerファイルの単位は、手続き的に処理されるサービスごと
 *EX：バックグラウンドでIDBと同期（比較、通信、ダウンロード、更新）
 *
 *ワーカーが利用できない環境でも（IE9以前）閲覧などできる必要があるので
 *通常の通信ではワーカークラスは利用しない
 *IDBはオフライン対応に必須となるので積極利用をお勧めする。
 *
 *
 */

/**
 * ワーカーの起動を受け取る 引数はe.dataで受け取れるmessageはリスナー名
 */
self.addEventListener('message', function(e) {

	/*
	 * ここの処理は別スレッドとなるためメインスレッドは参照できない
	 */

	var data = e.data;
	switch(data.cmd) {
		case  'article':

		var param = data.param;
		// タスクを開始
		getArticleAsync(param).then(function(value) {
			// 成功したときは値を返す
			self.postMessage(value);
		})['catch'](function(error) {
			// 失敗したときは失敗した旨を返す
			self.postMessage(error);
		});
		break;

		case 'IDEAllArticles':
			var guid = data.guid;

			test(guid).then(function(values) {
				self.postMessage(values);
			})['catch'](function(error) {
				self.postMessage(error);
			});
	}
}, true);


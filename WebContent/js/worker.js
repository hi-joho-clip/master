


importScripts('json-xhr.js','indexeddb.js', 'lib/es6-promise.min.js');
/**
 * 使用するリクエストはここに書いておくと便利なはず
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


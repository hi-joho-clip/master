/*
 * 必要な処理
 * 全記事のリスト取得：更新する記事の決定
 * 1件分の記事取得：更新する記事の選択
 *
 * 閲覧
 * 全件表示での記事
 * タグ検索での記事
 * 記事タイトル検索での記事：
 *
 */

/**
 * JSON形式でArticleIDの記事を取得する 画像も取得して返す(Promiseなのね）
 *
 * @param article_id
 * @param guid
 */
function getArticle(guid, article_id) {

	return new Promise(function(resolve, reject) {
		if (KageDB.isAvailable()) {
			// 成功の場合
			var tutorial = getArticleInstance();

			tutorial.tx([ "article" ], function(tx, todo) {
				todo.fetch({
					filter : article_filter
				}, function(values) {
					console.log("values = " + JSON.stringify(values));
					console.log("done.");
					console.log(values.body.created);
				});
			});
		} else {
			// エラー処理のコールバック
			rejece(new Error());

		}
		function article_filter(record) {
			// GUIDが自身であり、アーティクルIDが同一
			return record.guid == guid && revord.article_id == article_id;
		}
		;
	});
};

/**
 * 記事の件数分ループさせて更新する
 *
 * 記事の更新考察：PromiseALLで配列にして渡すので 取得と更新が1セットになるべき。
 *
 * あと重たいデータは画像の部分なので注意されたし
 *
 * @param article
 *            記事本体のデータ(JSON:1件分)
 * @param guid
 *            一時的なトークンID
 */
function updateArticle(json_article) {

	return new Promise(function(resolve, reject) {

		// GUIDをレスポンス内に含む
		var guid = '123';// article.guid;

		var article = json_article;

		console.log('jsonarticle:' + article['article_id']);
		// スキーマのインスタンス取得
		var tutorial = getArticleInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		// 更新処理
		tutorial.tx([ "article" ], "readwrite", function(tx, todo) {
			todo.put({
				article_id : article['article_id'],
				article : article,
				share_id : article.share_id,
				guid : guid,
				// 登録日時はサーバで管理するべき（ブラウザ依存は排除）
				modified : article.modified
			}, function(key) {
				console.log("done. key = " + key);
				// 成功時はキーを渡す
				resolve(key);
			});
		});
	});

};

/**
 * ログイン後、クッキーにセットされたユーザIDとguidをIDBへ書き込む
 *
 * @param prop
 *            use_id, guid, startedを持つ
 */
function updateIDBUser(prop) {

	return new Promise(function(resolve, reject) {

		console.log(prop);
		// スキーマのインスタンス取得
		var tutorial = getUserInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};
		// 更新処理
		tutorial.tx([ "user" ], "readwrite", function(tx, todo) {
			todo.put({
				user_id : prop.user_id,
				guid : prop.guid,
				started : prop.started
			}, function(key) {
				console.log("done. key = " + key);
				// 成功時はキーを渡す
				resolve(key);
			});
		});
	});

};

/**
 * 結局guidがわからなければならいものが、GUIDになっただけ。よってまったく意味なし。（廃止予定）
 *
 * @param guid
 */
function getIDBUser(user_id, guid) {

	return new Promise(function(resolve, reject) {
		var filter = {
			filter : guid_filter,
		};
		// DBのインスタンス取得
		var article_db = getArticleInstance();
		article_db.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		article_db.tx([ "article" ], function(tx, todo) {
			todo.fetch(filter, function(values) {
				if (values) {
					// ボディやサムネの画像は削除しておく
					// やっぱりループで一つ一つ削除するしかなさそう
					for ( var i in values) {
						delete values[i]["body"];
					}
					console
							.log("values = "
									+ JSON.stringify(values, null, ' '));
					console.log("done.");
					resolve(values);
				} else {
					reject(new Error("not found"));
				}
			});
		});

		function guid_filter(record) {
			return record.guid === guid;
		}
		;
	});

};

/**
 * 取得したArticleリスト
 *
 * @param articles
 */
function updateIDBArticleList(values) {

	// GUIDはセッションから持ってくると値を渡すことがないので良い。
	var jsons = JSON.parse(values);

	var pro_list = [];

	/*
	 * PromiseAll（promiseサーバ通信→promise書き込み）これをリスト分回す（全部完了したら成功にする） param :
	 * "article_id="?
	 */
	for ( var art_json in jsons) {
		param = "article_id=" + jsons[art_json].article_id;
		console.log(param);
		pro_list.push(getRequest().article(param).then(updateArticle));
	}
	if (pro_list) {
		return Promise.all(pro_list).then('success')['catch'](function(error) {
			return (error);
		});
	} else {
		return Promise.reject("no update");
	}
}

/**
 * データベース内の記事一覧のJSONを取得 なお画像は関係ない模様 (Promise)
 *
 * @param guid
 * @param page
 */
function getIDEArticleList(guid, page) {

	return new Promise(function(resolve, reject) {

		console.log(guid + page);

		var offset_filter = {
			filter : guid_filter,
			// 20件ずつ表示
			offset : page * 20 - 20,
			limit : 20
		};

		console.log(offset_filter);
		var tutorial = getArticleInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		tutorial.tx([ "article" ], function(tx, todo) {
			todo.fetch(offset_filter, function(values) {
				if (values) {
					console.log("values = " + JSON.stringify(values));
					console.log("done.");
					resolve(values);
				} else {
					reject(new Error("not found"));
				}

			});
		});

		function guid_filter(record) {
			return record.guid === guid;
		}
	});
};

/**
 * サーバと同期するためのDB内のリスト送信
 *
 * @param guid
 */
function getIDBAllArticleList(guid) {

	return new Promise(function(resolve, reject) {
		var filter = {
			filter : guid_filter,
		};
		// DBのインスタンス取得
		var article_db = getArticleInstance();
		article_db.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			console.log(event);
			reject(event.kage_message);
		};

		article_db.tx([ "article" ], function(tx, todo) {
			todo.fetch(filter, function(values) {
				if (values) {
					// ボディやサムネの画像は削除しておく
					// やっぱりループで一つ一つ削除するしかなさそう
					for ( var i in values) {
						delete values[i]["body"];
					}
					console
							.log("values = "
									+ JSON.stringify(values, null, ' '));
					console.log("done.");
					// 変える値は純粋に持っている記事一覧のJSON
					resolve(values);
				} else {
					reject(new Error("not found"));
				}
			});
		});

		function guid_filter(record) {
			return record.guid === guid;
		}
		;
	});

};

/**
 * 記事のデータベーススキーマ
 *
 * @returns {KageDB}
 */
function getArticleInstance() {
	var tutorial = new KageDB({
		name : "clip",
		version : 3,
		migration : {
			1 : function(ctx, next) {
				var db = ctx.db;
				var todo = db.createObjectStore("article", {
					keyPath : "id",
					autoIncrement : true
				});
				todo.createIndex("article", "article_id", {
					unique : true
				});
				next();
			}
		}
	});

	return tutorial;
};

/**
 * ユーザ情報のデータベーススキーマ
 *
 * @returns {KageDB}
 */
function getUserInstance() {
	/*
	 * id, user_id, guid, started
	 */
	var tutorial = new KageDB({
		name : "clip",
		version : 2,
		migration : {
			1 : function(ctx, next) {
				var db = ctx.db;
				var todo = db.createObjectStore("user", {
					keyPath : "user_id",

				});
				next();
			}
		}
	});
	return tutorial;
};

/**
 * 画像のデータベーススキーマ
 *
 * @returns {KageDB}
 */
function getImageInstance() {

	var image = new KageDB({
		name : "clip",
		migration : {
			1 : function(ctx, next) {
				var db = ctx.db;
				var todo = db.createObjectStore("image", {
					keyPath : "id",
					autoIncrement : true
				});
				next();
			}
		}
	});
	return image;
};

/**
 * データベース名を指定してデータベースを削除[article, image, friend]
 *
 * @param database
 */
function deleteDatabase(database) {
	if (KageDB.isAvailable()) {
		if (database == "article") {
			var tutorial = getArticleInstance();
			tutorial.deleteDatabase(function() {
				console.log("done");
			});
		} else if (database == "image") {
			// 画像テーブルの削除
		} else if (database == "friend") {
			// フレンドテーブルの削除
		} else {
			console.log("not found DB");
		}
	} else {
		console.log("indexedDB not supported");
	}
}

/**
 * GUIDの更新を行う。 GUIDの期限が切れていた場合に、再度ログインした際更新する必要がある
 *
 * メインスレッド利用可能
 *
 * @param guid
 */
function updateGuid(guid) {

	return new Promise(function(resolve, reject) {

		// スキーマのインスタンス取得
		var tutorial = getArticleInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		// 更新処理
		tutorial.tx([ "article" ], "readwrite", function(tx, todo) {
			todo.put({
				// 確かこれで全件更新できるはず
				guid : guid,
			// 登録日時はサーバで管理するべき（ブラウザ依存は排除）
			}, function(key) {
				console.log("done. key = " + key);
				// 成功時はキーを渡す
				resolve(key);
			});
		});
	});
}

/**
 * ユーザ追加のテストメソッド
 */
function UserAdd() {

	var user_data = {
		user_id : "aiueo",
		guid : 12345,
		started : 9999999
	};

	updateIDBUser(user_data).then(function(value) {
		console.log("success:" + values);
	})['catch'](function(error) {
		console.error(error);
	});
}
function test(guid) {

	// GUIDがなかった場合はエラー
	if (!guid) {
		return Promise.reject("GUID error");
	}

	return getIDBAllArticleList(guid);
}

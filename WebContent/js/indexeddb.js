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
function getArticle(article_id, guid) {

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
 * @param article
 *            記事本体のデータ
 * @param guid
 *            一時的なトークンID
 */
function updateArticle(article, guid, page) {

	if (KageDB.isAvailable) {

		// スキーマのインスタンス取得
		var tutorial = getArticleInstance();

		var modified = article.modified;

		var share_id = 2;

		// article = {
		// "acceptdate" : null,
		// "created" : 1448330151000,
		// "friendList" : [],
		// "friend_id" : 11,
		// "friend_user_id" : 2000,
		// "friendlists" : [],
		// "modified" : null,
		// "nickname" : "dummy7485"
		// };

		// 更新処理
		tutorial.tx([ "article" ], "readwrite", function(tx, todo) {
			todo.put({
				article_id : article.article_id,
				body : article,
				share_id : share_id,
				guid : guid,
				// 登録日時はサーバで管理するべき（ブラウザ依存は排除）
				modified : article.modified
			// ローカルストレージのGUIDを参照
			}, function(key) {
				console.log("done. key = " + key);
			});
		});

	} else {
		document.getElementById("friendList").innerHTML = "利用できません。";
	}

};

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
		};
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
		version : 2,
		migration : {
			1 : function(ctx, next) {
				var db = ctx.db;
				var todo = db.createObjectStore("article", {
					keyPath : "article_id",
					autoIncrement : true
				});
				// todo.createIndex("article_id", "article_id", {
				// unique : true
				// });
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
	var tutorial = new KageDB({
		name : "clip",
		migration : {
			1 : function(ctx, next) {
				var db = ctx.db;
				var todo = db.createObjectStore("image", {
					keyPath : "id",
					autoIncrement : true
				});
				todo.createIndex("article_id", "article_id", {
					unique : true
				});
				next();
			}
		}
	});
	return tutorial;
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
 * @param guid
 */
function updateGuid(guid) {

}


function test(guid) {

	// GUIDがなかった場合はエラー
	if (!guid) {
		return Promise.reject("GUID error");
	}

	return getIDBAllArticleList(guid);
}

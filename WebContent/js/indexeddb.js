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
function getArticle(username, article_id) {

	return new Promise(function(resolve, reject) {

			// 成功の場合
			var tutorial = getArticleInstance();

			console.log('offline' + username + article_id);
			tutorial.tx([ "article" ], function(tx, todo) {
				todo.fetch({
					filter : article_filter,
				// filter : user_filter
				}, function(values) {
					// console.log("values = " + JSON.stringify(values));
					console.log("done.");
					resolve(values);
					// console.log(values.body.created);
				});
			});
//		} else {
//			// エラー処理のコールバック
//			rejece(new Error());
//
//		}
		function article_filter(record) {
			// GUIDが自身であり、アーティクルIDが同一
			return record.article_id == article_id;
		}
		;
		function user_filter(record) {
			return record.username == username;
		}
		;
	});
};

/**
 * JSON形式でArticleIDの記事を取得する 画像も取得して返す(Promiseなのね）
 *
 * @param article_id
 * @param guid
 */
function getArticleID(article_id) {
	return new Promise(function(resolve, reject) {
		//if (KageDB.isAvailable()) {
			// 成功の場合
			var tutorial = getArticleInstance();

			tutorial.tx([ "article" ], function(tx, todo) {
				todo.fetch({
					filter : article_filter
				}, function(values) {
					// console.log("values = " + JSON.stringify(values));
					// console.log("done.");
					console.log(values[0]['id']);
					resolve(values[0]['id']);
					// console.log(values.body.created);
				});
			});
		// } else {
		// // エラー処理のコールバック
		// rejece(new Error());
		//
		//		}
		function article_filter(record) {
			// GUIDが自身であり、アーティクルIDが同一
			return record.article_id === article_id;
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

		// ユーザ名でDB識別

		var article = json_article;

		// console.log('jsonarticle:' + article['article_id']);
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
				username : article.username,
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
 * 記事の削除
 *
 * @param article
 *            記事本体のデータ(JSON:1件分)
 * @param guid
 *            一時的なトークンID
 */
function updateArticleDelete(id) {

	return new Promise(function(resolve, reject) {
		// ユーザ名でDB識別
		// console.log("hhhhhhh"+id);

		// console.log('jsonarticle:' + article['article_id']);
		// スキーマのインスタンス取得
		var tutorial = getArticleInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		// 更新処理
		tutorial.tx([ "article" ], "readwrite", function(tx, todo) {
			todo.del(id, function() {
				// console.log("done. key = ");
				// 成功時はキーを渡す
				resolve();
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

		// console.log(prop);
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
 * 取得したArticleリストから取得する記事毎にプロミス作成
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
		// console.log(param);
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
 * 取得したArticleリストから取得する記事毎にプロミス作成
 *
 * @param articles
 */
function updateIDBArticleListDelete(values) {

	// GUIDはセッションから持ってくると値を渡すことがないので良い。
	var jsons = values;

	var pro_list = [];

	// console.log("きているうううううううう");

	/*
	 * PromiseAll（promiseサーバ通信→promise書き込み）これをリスト分回す（全部完了したら成功にする） param :
	 * "article_id="?
	 */
	for ( var art_json in jsons) {
		param = jsons[art_json].article_id;
		// console.log("パラメータ:" + param);
		pro_list.push(getArticleID(param).then(updateArticleDelete));
	}
	if (pro_list) {
		console.log(pro_list);
		return Promise.all(pro_list).then('success')['catch'](function(error) {
			return (error);
		});
	} else {
		return Promise.reject("no update");
	}
}

/**
 * データベース内の記事一覧のJSONを取得 なお画像は関係ない模様 (Promise) 純粋なリストを作ろう
 *
 * @param guid
 * @param page
 */
function getIDEArticleList(username, page, share_id, title, fav_state, direct) {

	return new Promise(function(resolve, reject) {
		// console.log(guid + page);

		var offset_filter;
		console.log(page);
		// 1スタートなので
		page = page -1;

		var direct = "prev";

		if (fav_state) {
			// 絶対お気に入り
			console.log("fav_false");
			offset_filter = {
				filter : fav_true,
				offset : page * 20 - 18,
				limit : 20,
				direction : direct
			};

		} else {
			//
			offset_filter = {
				filter : fav_false,
				offset : page * 20 - 18,
				limit : 20,
				direction : direct
			};
		}

		// console.log(offset_filter);
		var tutorial = getArticleInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		tutorial.tx([ "article" ], function(tx, todo) {
			todo.index("modified").fetch(offset_filter, function(values) {
				if (values) {
					// console.log("values = " +
					// JSON.stringify(values));
					var val = [];
					for ( var i in values) {
						val.push(values[i]["article"]);
						//console.log("art:" + values[i]['modified']);
					}
					//console.log(val.length);
					resolve(val);
				} else {
					reject(new Error("not found"));
				}

			});
		});

		function guid_filter(record) {
			//console.log('guid');
			return record.username === username;
		}
		function share_filter(record) {
			//console.log('share');
			return record.share_id === share_id;
		}
		function title_filter(record) {
			//console.log('title');
			flag = false;
			if (record.article.title.indexOf(title) != -1) {
				flag = true;
			}
			return flag;
		}
		function fav_filter(record) {
			//console.log('fav');
			flag = false;
			if (record.article.favflag === true) {
				flag = true;
			}
			return flag;
		}
		function fav_true(record) {
			//console.log('fabt');
			if (guid_filter(record) && share_filter(record)
					&& title_filter(record) && fav_filter(record)) {
				return true;
			} else {
				return false;
			}
		}
		function fav_false(record) {
			//console.log('fabf');
			if (guid_filter(record) && share_filter(record)
					&& title_filter(record)) {
				return true;
			} else {
				return false;
			}
		}
	});
};

/**
 * タグ専用のリスト検索
 *
 * @param guid
 * @param page
 */
function getIDEArticleTagList(username, page, title, tag, direct) {

	return new Promise(function(resolve, reject) {
		// console.log(guid + page);

		var offset_filter;
		page = page -1;

		var direct = "prev";

		//
		offset_filter = {
			filter : fav_false,
			offset : page * 20 - 19,
			limit : 20,
			direction : direct
		};

		// console.log(offset_filter);
		var tutorial = getArticleInstance();
		tutorial.onerror = function(event) {
			// エラーの詳細をコンソールに出力する
			reject(event.kage_message);
		};

		tutorial.tx([ "article" ], function(tx, todo) {
			todo.index("modified").fetch(offset_filter, function(values) {
				if (values) {
					// console.log("values = " +
					// JSON.stringify(values));
					var val = [];
					for ( var i in values) {
						val.push(values[i]["article"]);
						//console.log("art:" + values[i]['modified']);
					}
					//console.log(val.length);
					resolve(val);
				} else {
					reject(new Error("not found"));
				}

			});
		});

		function guid_filter(record) {
			//console.log('guid');
			return record.username === username;
		}

		function title_filter(record) {
			//console.log('title');
			flag = false;
			if (record.article.title.indexOf(title) != -1) {
				flag = true;
			}
			return flag;
		}

		function tag_filter(record) {
			tags = record.article.tagBeans;

			flag= false;
			for ( var i in tags) {
				//console.log(JSON.stringify(tags[i].tag_body));
				if (JSON.stringify(tags[i].tag_body).indexOf(tag) != -1){
					flag = true;
				}
			}
			return flag;
		}

		function fav_false(record) {
			if (guid_filter(record) && title_filter(record)) {
				//console.log('true');
				if (tag_filter(record)) {
					//console.log('tagtrue');
					return true;
				} else {
					//console.log('tagfalse');
				}
			} else {
				//console.log('false');
				return false;
			}
		}
	});
};

/**
 * サーバと同期するためのDB内のリスト送信
 *
 * @param guid
 */
function getIDBAllArticleList(username) {

	return new Promise(function(resolve, reject) {
		var filter = {
			filter : username_filter,
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
						delete values[i]["article"];
						// console.log("art:" + val);
					}
					// values = JSON.stringify(values);

					resolve(values);
				} else {
					reject(new Error("not found"));
				}
			});
		});

		function username_filter(record) {
			return record.username === username;
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
		version : 4,
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
				todo.createIndex("modified", "modified", {
					keyPath : "modified",
					unique : false
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

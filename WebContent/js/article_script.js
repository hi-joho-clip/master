//現在の状態がタイル表示かリスト表示かの判断
function tileView() {
	if (getLocalStorage("Style") === "tile") {
		return true;
	} else if (getLocalStorage("Style") === "list") {
		return false;
	}
}

/**
 * オフラインでのマイリスト（検索対応）
 *
 * @param page
 * @returns {Promise}
 */
function getOffMyList(page) {

	return new Promise(function(resolve, reject) {

		console.log("offline" + page);
		//var username = docCookies.getItem('username');
		var username = getLocalStorage('username');

		$('h1.title').html('マイリスト');
		var word = '';

		if ($('#searchMode').val() === "true") {
			// マイリストの検索をしているページを出す
			word = getSessionStorage('search');
		}
		getIDEArticleList(username, page, 0, word, false).then(function(json) {
			// 純粋なリストが必要
			if (getLocalStorage('Style') === 'tile') {
				get_mylists(json);
				resolve();
			} else {
				get_mylists_list(json);
				resolve();
			}
			changeViewSwitti(get_mylists, get_mylists_list);
		})['catch'](function(error) {
			console.log(error);
			reject();
		});
	});

}

/**
 * オフラインでのマイリスト（検索対応）
 *
 * @param page
 * @returns {Promise}
 */
function getOffFavList(page) {

	return new Promise(function(resolve, reject) {

		console.log("offline" + page);
		//var username = docCookies.getItem('username');
		var username = getLocalStorage('username');
		$('h1.title').html('お気に入り');
		var word = '';

		if ($('#searchMode').val() === "true") {
			// マイリストの検索をしているページを出す
			word = getSessionStorage('search');
		}
		getIDEArticleList(username, page, 0, word, true).then(function(json) {
			// 純粋なリストが必要
			if (getLocalStorage('Style') === 'tile') {
				get_mylists(json);
				resolve();
			} else {
				get_mylists_list(json);
				resolve();
			}
			changeViewSwitti(get_mylists, get_mylists_list);
		})['catch'](function(error) {
			console.log(error);
			reject();
		});
	});

}

/**
 *
 * @param page
 * @returns {Promise}
 */
function getMyList(page) {
	return new Promise(function(resolve, reject) {
		var func = get_mylists;
		$('h1.title').html('マイリスト');
		func = changeViewSwitti(get_mylists, get_mylists_list);
		var jsonParam = 'page=' + page + '&article_id=' + $('#lastid').val();// 送りたいデータ(ページ番号）
		var URL = hostURL + "/mylist";

		console.log(jsonParam);

		$('#viewmode').val('0');
		console.log(page + 'ゲットおおお');
		getJSON(URL, jsonParam, func).then(function(json) {
			console.log('保存プロセス');
			setStorage(json);
			resolve();
		})['catch'](function(error) {
			reject();
		});
	});
}
// お気に入り
function getFavList(page) {
	return new Promise(
			function(resolve, reject) {

				$('h1.title').html('お気に入り');

				func = changeViewSwitti(get_mylists, get_mylists_list);

				var jsonParam = 'page=' + page + '&article_id='
						+ $('#lastid').val();// 送りたいデータ
				var URL = hostURL + "/favlist";

				$('#viewmode').val("1");
				getJSON(URL, jsonParam, func);
			});
}

// シェア記事一覧
function getShareList(page) {
	$('h1.title').html(getSessionStorage('friend'));
	console.log(getSessionStorage('friend'));
	var func = get_sharelists;

	func = changeViewSwitti(get_sharelists, get_sharelists_list);

	var jsonParam = "friend_user_id=" + getSessionStorage('shareLists')
			+ '&page=' + page + "&article_id=" + $('#lastid').val();// 送りたいデータ
	var URL = hostURL + "/sharelist";

	$('#viewmode').val('3');
	getJSON(URL, jsonParam, func);
}

/**
 * オフラインでのシェアリスト
 *
 * @param page
 */
function getOffShareList(page) {

	return new Promise(
			function(resolve, reject) {
				$('h1.title').html(getSessionStorage('friend'));

				// 表示
				changeViewSwitti();

				$('h1.title').html(getSessionStorage('friend'));

				console.log("offline" + page);
				//var username = docCookies.getItem('username');
				var username = getLocalStorage('username');

				var word = '';

				if ($('#searchMode').val() === "true") {
					// マイリストの検索をしているページを出す
					word = getSessionStorage('search');
				}

				console.log(getSessionStorage('shareListId'));
				getIDEArticleList(username, page, getSessionStorage('shareListId'), word, false).then(
						function(json) {
							// 純粋なリストが必要
							if (getLocalStorage('Style') === 'tile') {
								get_sharelists(json);
								resolve();
							} else {
								get_sharelists_list(json);
								resolve();
							}

							changeViewSwitti(get_sharelists, get_sharelists_list);
						})['catch'](function(error) {
					console.log(error);
					reject();
				});
				$('#viewmode').val('3');
			});
}


function changeViewSwitti(tile, list) {
	var func;
	//console.log(tileView());
	if (tileView()) {
		func = tile;
		$('div.stylebutton')
				.html(
						'<div id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></div>');
		$('div#themebutton')
				.html(
						'<div id="themachange" title="テーマ切り替え" style="visibility: hidden;">'
								+ '<img onclick="javascript:changeColor()" src="img/thema.png"style="visibility: visible; width: 25px;"></div>');
	} else {
		func = list;
		$('div.stylebutton')
				.html(
						'<div id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></div>');
		$('div#themebutton')
				.html(
						'<div id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></div>');
	}
	return func;
}


//記事の表示
function getOneArticle(article_id, callback) {

	var jsonParam = "article_id=" + article_id;// 送りたいデータ
	var URL = hostURL + "/viewarticle";
	var setappend = callback;
	getJSON(URL, jsonParam, setappend);
}

// 記事の表示
function getViewArticle(article_id) {

	var jsonParam = "article_id=" + article_id;// 送りたいデータ
	var URL = hostURL + "/viewarticle";
	var setappend = setViewArticle;
	getJSON(URL, jsonParam, setappend);
}

function getViewArticleOffline(article_id) {

	getArticle(getLocalStorage('username'), article_id).then(function(json) {
		json = json[0].article;
		setViewArticle(json);
	});

}

function setViewArticle(json) {

	// console.log(json);

	$('#image').append(thumView(json, "300px", "auto"));

	// nonceとArticleID設定
	$("div.hiddenarea").append(
			'<input type="hidden" id="article_id" value="' + json.article_id
					+ '">');
	$("div.hiddenarea").append(
			'<input type="hidden" id="nonce" value="'
					+ docCookies.getItem("nonce") + '">');
	$('div.hiddenarea').append('<input type="hidden" id="article-url" value="' + json.url + '">');

	$("div.view-title").append(json.title);
	$('div.view-title').append(
			'<div><a class="url" href=' + json.url + ' target="_blank">'
					+ json.url + '</a></div><br>');
	$("div#editable").append(json.body);

	var con_type = "jpeg";
	var data;

	for ( var image in json.imageListDTO) {
		data = 'data:image/' + con_type + ';base64,'
				+ json.imageListDTO[image].blob_image;
		$("div.images")
				.append(
						'<a href='
								+ data
								+ ' data-lightbox="gazo"><img id="icon_here"   style="max-width:auto; max-height:200px;padding :10px" src = '
								+ data + '></a>');
	}

	// document.getElementById('viewArticle').innerHTML = viewArticle;
};

// 記事の削除
function deleteArticle() {
	console.log("article_idkun:" + $('#article_id').val());
	var jsonParam = "article_id=" + $('#article_id').val() + "&nonce="
			+ $('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/deletearticle";
	var delete_article = function(json) {
		if (json.flag == "0") {
			// 失敗
			toastr.error(json.state);
		} else {
			// 成功したらその要素を削除する
			// console.log(json.article_id);
			$('#' + json.article_id).remove();
			if (tileView()) {
				// タイル表示の場合、削除した後レイアウトを整えるため必要なメソッド
				$grid.isotope('layout');
			}

			toastr.success(json.state);

		}
	};
	getJSON(URL, jsonParam, delete_article);
	// location.reload();
}

// お気に入りの追加削除
function addFavArticle(article_id) {
	// グローバルFLAG true=追加可、false=削除可

	var jsonParam = "article_id=" + article_id + "&nonce=" + $('#nonce').val();// 送りたいデータ
	var grobalflag = "grobalflag" + article_id;// 今のお気に入りの状態を表すグローバルflagの名前付け
	var favtitle = "favtitle" + article_id;// タイトルの★マークの名前
	var title = "title" + article_id;
	var url = "url" + article_id;
	console.log("globalflag:" + $("#" + grobalflag).val());
	console.log("favtitle:" + $("#" + favtitle).html());
	console.log("title:" + $("#" + title).val());
	console.log("url:" + $("#" + url).val());
	if ($("#" + grobalflag).val() === "false") {

		// 追加の処理
		var URL = hostURL + "/addfav";

		// タイトルの横に★マークをつける

		var success = null;

		if (getLocalStorage('Style') === "tile") {
			success = function(json) {
				if (json.flag == "0") {
					toastr.error(json.state);
				} else {
					$('#' + favtitle).attr('style', 'color:#FF9800');
					$('#' + favtitle).html(
							"★" + $("#" + title).val()
									+ "<BR><a class='art-title' href='"
									+ $("#" + url).val() + "'>"
									+ $("#" + url).val() + "</a>");
					// グローバルflagをfalseにする
					$("#" + grobalflag).val(true);
					toastr.success(json.state);
				}
			};
		} else if (getLocalStorage('Style') === 'list') {
			success = function(json) {
				if (json.flag == "0") {
					toastr.error(json.state);
				} else {
					$('#' + favtitle).attr('style', 'color:#FF9800');
					$('#' + favtitle).html("★" + $("#" + title).val());

					// グローバルflagをfalseにする
					$("#" + grobalflag).val(true);
					toastr.success(json.state);
				}
			};
		}

		getJSON(URL, jsonParam, success);

	} else if ($("#" + grobalflag).val() === "true") {
		// 削除の処理
		var URL = hostURL + "/deletefav";

		// タイトルの横の★マークを削除
		var failed = null;

		if (getLocalStorage('Style') === "tile") {
			failed = function(json) {
				if (json.flag == "0") {
					toastr.error(json.state);
				} else {
					$('#' + favtitle).removeAttr('style', 'color:#FF9800');
					$('#' + favtitle).html(
							$("#" + title).val()
									+ "<BR><a class='art-title' href='"
									+ $("#" + url).val() + "'>"
									+ $("#" + url).val() + "</a>");

					// グローバルflagをtrueにする
					$("#" + grobalflag).val(false);
					toastr.success(json.state);
				}
			};

		} else if (getLocalStorage('Style') === "list") {
			failed = function(json) {
				if (json.flag == "0") {
					toastr.error(json.state);
				} else {
					$('#' + favtitle).removeAttr('style', 'color:#FF9800');
					$('#' + favtitle).html($("#" + title).val());

					// グローバルflagをtrueにする
					$("#" + grobalflag).val(false);

					toastr.success(json.state);
				}

			};

		}

		getJSON(URL, jsonParam, failed);

	}
}

/**
 * 記事の更新（本文のみバージョン）
 */
function updateArticleOnline() {
	// getArticle_id(article_id);//html内の<div
	// id='article_id'>にhiddenでarticle_idを持たせる
	var jsonParam = "article_id=" + $('#article_id').val(); // 送りたいデータ
	// Nonceを載せる
	jsonParam = jsonParam + "&nonce=" + $('#nonce').val();
	jsonParam = jsonParam + '&body='
			+ encodeURIComponent($("div#editable").html());
	var URL = hostURL + "/updatearticle";
	var update_article = function(json) {
		if (json.flag == 0) {
			toastr.error(json.state);
		} else {
			toastr.success(json.state);
		}
	};
	getJSON(URL, jsonParam, update_article);
}

// シェア記事の追加
function shareArticle(friend_user_id) {
	console.log("Share:" + $('#article_id').val());
	var param = "article_id=" + $('#article_id').val() + "&friend_id="
			+ friend_user_id + "&nonce=" + $('#nonce').val();

	var URL = hostURL + "/addshare";

	var cal = function(json) {
		if (json.flag == 0) {
			toastr.error(json.state);
		} else {
			toastr.success(json.state);
		}
	};

	getJSON(URL, param, cal);

}

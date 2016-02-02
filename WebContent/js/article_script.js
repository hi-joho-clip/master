//現在の状態がタイル表示かリスト表示かの判断
function tileView() {
	if (getLocalStorage("Style") === "tile") {
		return true;
	} else if (getLocalStorage("Style") === "list") {
		return false;
	}
}

// マイリスト
function getMyList(page) {
	return new Promise(
			function(resolve, reject) {
				var func = get_mylists;
				$('h1.title').html('マイリスト');
				if (tileView()) {
					func = get_mylists;
					$('div.stylebutton')
							.html(
									'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
				} else {
					func = get_mylists_list;
					$('div.stylebutton')
							.html(
									'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
				}
				var jsonParam = 'page=' + page + '&article_id='
						+ $('#lastid').val();// 送りたいデータ(ページ番号）
				var URL = hostURL + "/mylist";

				console.log(jsonParam);

				$('#viewmode').val('0');
				console.log(page + 'ゲットおおお');
				getJSON(URL, jsonParam, func).then(function(json) {
					console.log('保存プロセス');
					setStorage(json);
					resolve();
				}['catch'](function(error) {
					reject();
				}));
			});
}
// お気に入り
function getFavList(page) {
	return new Promise(
			function(resolve, reject) {

				$('h1.title').html('お気に入り');
				if (tileView()) {
					func = get_mylists;
					$('div.stylebutton')
							.html(
									'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
				} else {
					func = get_mylists_list;
					$('div.stylebutton')
							.html(
									'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
				}

				var jsonParam = 'page=' + page + '&article_id='
						+ $('#lastid').val();// 送りたいデータ
				var URL = hostURL + "/favlist";

				$('#viewmode').val("1");
				getJSON(URL, jsonParam, func);
			});
}

// シェア記事一覧
function getShareList(friend_user_id, page) {
	$('h1.title').html('シェア記事');
	var func = get_sharelists;
	if (tileView()) {
		func = get_sharelists;
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
		// document.getElementById('title').innerHTML = '<h1>シェア記事</h1><div
		// style="text-align: right;"><button id="stylechange"
		// title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png"
		// style="visibility:visible"></button></div>';
	} else {
		func = get_sharelists_list;
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
		// document.getElementById('title').innerHTML = '<h1>シェア記事</h1><div
		// style="text-align: right;"><button id="stylechange"
		// title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png"
		// style="visibility:visible"></button></div>';
	}

	var jsonParam = "friend_user_id=" + friend_user_id + '&page=' + page;// 送りたいデータ
	var URL = hostURL + "/sharelist";

	$('#viewmode').val('3');
	getJSON(URL, jsonParam, func);
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

	//console.log(json);

	$('#image').append(thumView(json, "300px", "100%"));

	// nonceとArticleID設定
	$("div.hiddenarea").append(
			'<input type="hidden" id="article_id" value="' + json.article_id
					+ '">');
	$("div.hiddenarea").append(
			'<input type="hidden" id="nonce" value="'
					+ docCookies.getItem("nonce") + '">');

	$("div.view-title").append(json.title);
	$('div.view-title').append('<div><a class="url" href=' + json.url + ' target="_blank">' + json.url + '</a></div><br>' );
	$("div#editable").append(json.body);

	var con_type = "jpeg";
	var data;

	for ( var image in json.imageListDTO) {
		data = 'data:image/' + con_type + ';base64,'
				+ json.imageListDTO[image].blob_image;
		$("div.images")
				.append(
						'<img id="icon_here"  style="max-width:auto; max-height:200px;padding :10px" src = '
								+ data + '>');
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
					$('#' + favtitle).attr('style', 'color:#FDB91B');
					$('#' + favtitle).html(
							"★" + $("#" + title).val() + "<BR><a class='art-title' href='"
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
					$('#' + favtitle).attr('style', 'color:#FDB91B');
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
					$('#' + favtitle).removeAttr('style', 'color:#FDB91B');
					$('#' + favtitle).html(
							$("#" + title).val() + "<BR><a class='art-title' href='"
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
					$('#' + favtitle).removeAttr('style', 'color:#FDB91B');
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

// マイリスト（タイル表示）
function getMyList(page) {
	var jsonParam = 'page=' + page;// 送りたいデータ(ページ番号）
	var URL = hostURL + "/clipMaster/mylist";
	document.getElementById('title').innerHTML = '<h1>マイリスト</h1>'+'<input type="button" id="stylechange"value="AAA">';
	$('#viewmode').val('0');
	getJSON(URL, jsonParam, get_mylists);
}
//マイリスト（リスト表示）
function getMyListList(page) {
	var jsonParam = 'page=' + page;// 送りたいデータ(ページ番号）
	var URL = hostURL + "/clipMaster/mylist";
	document.getElementById('title').innerHTML = '<h1>マイリスト</h1>'+'<input type="button" id="stylechange"value="AAA">';
	$('#viewmode').val('0');
	getJSON(URL, jsonParam, get_mylists_list);
}

// お気に入り（タイル表示）
function getFavList(page) {
	var jsonParam = 'page=' + page;// 送りたいデータ
	var URL = hostURL + "/clipMaster/favlist";
	document.getElementById('title').innerHTML = '<h1>お気に入り</h1>'+'<input type="button" id="stylechange"value="AAA">';
	$('#viewmode').val("1");
	getJSON(URL, jsonParam, get_mylists);
}
//お気に入り（リスト表示）
function getFavListList(page) {
	var jsonParam = 'page=' + page;// 送りたいデータ
	var URL = hostURL + "/clipMaster/favlist";
	document.getElementById('title').innerHTML = '<h1>お気に入り</h1>'+'<input type="button" id="stylechange"value="AAA">';
	$('#viewmode').val("1");
	getJSON(URL, jsonParam, get_mylists_list);
}

// シェア記事一覧（タイル表示）
function getShareList(friend_user_id, page) {
	var jsonParam = "friend_user_id=" + friend_user_id + '&page=' + page;// 送りたいデータ
	var URL = hostURL + "/clipMaster/sharelist";
	document.getElementById('title').innerHTML = '<h1>シェア記事</h1>'+'<input type="button" id="stylechange"value="AAA">';
	$('#viewmode').val('3');
	getJSON(URL, jsonParam, get_sharelists);
}
//シェア記事一覧（リスト表示）
function getShareList(friend_user_id, page) {
	var jsonParam = "friend_user_id=" + friend_user_id + '&page=' + page;// 送りたいデータ
	var URL = hostURL + "/clipMaster/sharelist";
	document.getElementById('title').innerHTML = '<h1>シェア記事</h1>'+'<input type="button" id="stylechange"value="AAA">';
	$('#viewmode').val('3');
	getJSON(URL, jsonParam, get_sharelists_list);
}




// 記事の表示
function getViewArticle(article_id) {

	var jsonParam = "article_id=" + article_id;// 送りたいデータ
	var URL = hostURL + "/clipMaster/viewarticle";
	var setappend = function(json) {


		$('#image').append(thumView(json,"300px","100%"));

		// nonceとArticleID設定
		$("div.hiddenarea").append(
				'<input type="hidden" id="article_id" value="' + article_id
						+ '">');
		$("div.hiddenarea").append(
				'<input type="hidden" id="nonce" value="'
						+ docCookies.getItem("nonce") + '">');

		$("div.title").append(json.title + "<br>");
		$("div#editable").append(json.body);

		var con_type = "jpeg";
		var data;

		for ( var image in json.imageListDTO) {
			data = 'data:image/' + con_type + ';base64,'
					+ json.imageListDTO[image].blob_image;
			$("div.images").append(
					'<img id="icon_here"  style="max-width:auto; max-height:200px;padding :10px" src = ' + data + '>');
		}

		// document.getElementById('viewArticle').innerHTML = viewArticle;
	};
	getJSON(URL, jsonParam, setappend);
}
// 記事の削除
function deleteArticle(article_id) {
	// getArticle_id(article_id);//html内の<div
	// id='article_id'>にhiddenでarticle_idを持たせる
	var jsonParam = "article_id=" + article_id.item(0).value;// 送りたいデータ
	var URL = hostURL + "/clipMaster/deletearticle";
	var delete_article = function() {

	};
	getJSON(URL, jsonParam, delete_article);
	location.reload();
	toastr.success('記事を削除しました');
}
// お気に入りの追加削除
function addFavArticle(article_id) {
	// グローバルFlagを用意する。記事リストを作成時にグローバルFlagはtrue
	// true=追加可、false=削除可

	var jsonParam = "article_id=" + article_id;// 送りたいデータ
	var grobalflag = "grobalflag" + article_id;// グローバルflagの名前付け
	var favtitle = "favtitle" + article_id;// タイトルの★マークの名前
	var title = "title" + article_id;
	var url = "url" + article_id;
	var flag = document.getElementById(grobalflag).value;
	if (flag == "false") {

		// 追加の処理
		var URL = hostURL + "/clipMaster/addfav";

		// タイトルの横に★マークをつける

		var success = null;

		if ($('#mode').val() === "tile") {
			success = function(json) {
				$('#' + favtitle).attr('style', 'color:#FFEB3B');
				document.getElementById(favtitle).innerHTML = "★"
						+ document.getElementById(title).value
						+ "<BR><a href='" + document.getElementById(url).value
						+ "'>" + document.getElementById(url).value + "</a>";
				// グローバルflagをfalseにする
				document.getElementById(grobalflag).value = true;
				toastr.success(json.state);
			};
		} else if ($('#mode').val() === 'list') {
			success = function(json) {
				$('#' + favtitle).attr('style', 'color:#FFEB3B');
				document.getElementById(favtitle).innerHTML = "★"
						+ document.getElementById(title).value;
				// グローバルflagをfalseにする
				document.getElementById(grobalflag).value = true;
				toastr.success(json.state);
			};
		}

		getJSON(URL, jsonParam, success);

	} else if (flag == "true") {
		// 削除の処理
		var URL = hostURL + "/clipMaster/deletefav";

		// タイトルの横の★マークを削除
		var failed = null;

		if ($('#mode').val() === "tile") {
			failed = function(json) {
				$('#' + favtitle).removeAttr('style', 'color:#FFEB3B');

				getJSON(URL, jsonParam, null);
				document.getElementById(favtitle).innerHTML = document
						.getElementById(title).value
						+ "<BR><a href='"
						+ document.getElementById(url).value
						+ "'>" + document.getElementById(url).value + "</a>";
				// グローバルflagをtrueにする
				document.getElementById(grobalflag).value = false;
				toastr.success(json.state);
			};
		} else if ($('#mode').val() === "list") {
			failed = function(json) {
				$('#' + favtitle).removeAttr('style', 'color:#FFEB3B');

				getJSON(URL, jsonParam, null);
				document.getElementById(favtitle).innerHTML = document
						.getElementById(title).value;
				// グローバルflagをtrueにする
				document.getElementById(grobalflag).value = false;
				toastr.success(json.state);
			};

		}

		getJSON(URL, jsonParam, failed);

	}
}

/**
 * 記事の更新（本文のみバージョン）
 */
function updateArticle() {
	// getArticle_id(article_id);//html内の<div
	// id='article_id'>にhiddenでarticle_idを持たせる
	var jsonParam = "article_id=" + $('#article_id').val(); // 送りたいデータ
	// Nonceを載せる
	jsonParam = jsonParam + "&nonce=" + $('#nonce').val();
	jsonParam = jsonParam + '&body='
			+ encodeURIComponent($("div#editable").html());
	var URL = hostURL + "/clipMaster/updatearticle";
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
function shareArticle(friend_user_id, article_id) {
	var param = "article_id=" + article_id.item(0).value + "&friend_id="
			+ friend_user_id + "";

	var URL = hostURL + "/clipMaster/addshare";

	var cal = function(json) {
		if (json.flag == 0) {
			toastr.error(json.state);
		} else {
			toastr.success(json.state);
		}
	};

	getJSON(URL, param, cal);

}

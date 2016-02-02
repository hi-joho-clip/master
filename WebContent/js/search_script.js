//var hostURL = "http://localhost:8080";

// どこの検索なのか判断する
$(document).on('click', '#titlesearch', function() {

	document.getElementById('pure-toggle-right').checked=false;
	console.log("viewmodehidden:" + $('#viewmode').val());

	var word = document.getElementById('searchbox').value;

	switch ($('#viewmode').val()) {
	case "0":
		// マイリストの検索
		if (isSettinOnLine()) {

			$('.grid').empty();
			myListSearch(word);
		} else {



			$('.grid').empty();
			var username = docCookies.getItem('username');

			// テスト用修正必要
			page = 1;
			getIDEArticleList(username, page, 0,word).then(function(json) {
				// 純粋なリストが必要
				get_mylists(json);
			})['catch'](function(error) {
				console.log(error);
			});
		}
		break;
	case "1":
		// お気に入りの検索
		$('.grid').empty();
		favListSearch(word);
		break;
	case "2":
		// タグの検索
		$('.grid').empty();
		tagSearch(word);
		break;
	case "3":
		// シェアの検索
		$('.grid').empty();
		shareListSearch(word);
		break;
	}
});


// マイリスト内のタイトル検索
function myListSearch(word) {
	var func = get_mylists;
	if (tileView()) {
		func = get_mylists;
		document.getElementById('title').innerHTML = '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button></div>';
	} else {
		func = get_mylists_list;
		document.getElementById('title').innerHTML =  '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button></div>';
	}
	var jsonParam = "text=" + word;// 送りたいデータ
	var URL = hostURL + "/mylistsearch";
	getJSON(URL, jsonParam, func);
}
// お気に入りのタイトル検索
function favListSearch(word) {
	var func = get_mylists;
	if (tileView()) {
		func = get_mylists;
		document.getElementById('title').innerHTML = '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button></div>';
	} else {
		func = get_mylists_list;
		document.getElementById('title').innerHTML =  '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button></div>';
	}
	var jsonParam = "text=" + word;// 送りたいデータ
	var URL = hostURL + "/favlistsearch";

	getJSON(URL, jsonParam, func);
}
// タグのタイトル検索
function tagSearch(word) {
	var func = get_mylists;
	if (tileView()) {
		func = get_mylists;
		document.getElementById('title').innerHTML = '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button></div>';
	} else {
		func = get_mylists_list;
		document.getElementById('title').innerHTML =  '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button></div>';
	}
	var jsonParam = "tag=" + $.cookie("tagLists") + "&text=" + word;
	var URL = hostURL + "/tagsearch";
	getJSON(URL, jsonParam, func);
}
// シェアのタイトル検索
function shareListSearch(word) {
	var func = get_sharelists;
	if (tileView()) {
		func = get_sharelists;
		document.getElementById('title').innerHTML = '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button></div>';
	} else {
		func = get_sharelists_list;
		document.getElementById('title').innerHTML =  '<h1 class="title">検索結果</h1><div style="text-align: right;"><button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button></div>';
	}
	var jsonParam = "friend_user_id=" + $.cookie("shareLists") + "&text=" + word;
	var URL = hostURL +  "/sharelistsearch";
	getJSON(URL, jsonParam, func);
};

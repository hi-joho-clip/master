//var hostURL = "http://localhost:8080";

// どこの検索なのか判断する
$(document).on('click', '#titlesearch', function() {
	onclickSearch();
});

function enterSearch() {
	if (window.event.keyCode == 13) {
		onclickSearch();
	}
}

function onclickSearch() {
	document.getElementById('pure-toggle-right').checked = false;
	console.log("viewmodehidden:" + $('#viewmode').val());

	var word = document.getElementById('searchbox').value;

	var sesStorage = sessionStorage;
	sesStorage.setItem('search', word);
	// sesStorage.setItem('searchMode', true);
	$('#searchMode').val("true");

	$('#art-page').val('1');
	$('#art-add').val('true');
	// lastIDは使わない
	$('#lastid').val('0');
	var username = docCookies.getItem('username');

	switch ($('#viewmode').val()) {
	case "0":
		// マイリストの検索
		if (isSettinOnLine() === true) {

			$('.grid').empty();
			// 最初は1ページ目
			myListSearch(1);
		} else if (isSettinOnLine() === false){

			$('.grid').empty();
			page = 1;
			getOffMyList(page);
		}

		break;
	case "1":
		// お気に入りの検索
		if (isSettinOnLine() === true) {
			$('.grid').empty();
			favListSearch(1);
		} else if (isSettinOnLine() === false){
			// テスト用修正必要
			page = 1;
			getOffFavList(page);
		}

		break;
	case "2":
		// タグの検索
		if (isSettinOnLine()) {
			$('.grid').empty();
			tagSearch(word);
		}

		break;
	case "3":
		// シェアの検索
		if (isSettinOnLine()) {
			$('.grid').empty();
			shareListSearch(word);
		}

		break;
	}
}

// マイリスト内のタイトル検索
function myListSearch(page) {
	var func = get_mylists;
	$('h1.title').html('検索結果');
	if (tileView()) {
		func = get_mylists;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
	} else {
		func = get_mylists_list;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
	}
	var jsonParam = "text=" + getSessionStorage('search') + "&page=" + page;// 送りたいデータ
	var URL = hostURL + "/mylistsearch";
	getJSON(URL, jsonParam, func);
}
// お気に入りのタイトル検索
function favListSearch(page) {
	var func = get_mylists;
	if (tileView()) {
		func = get_mylists;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
	} else {
		func = get_mylists_list;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
	}
	var jsonParam = "text=" + getSessionStorage('search') + "&page=" +  page;// 送りたいデータ
	var URL = hostURL + "/favlistsearch";

	getJSON(URL, jsonParam, func);
}
// タグのタイトル検索
function tagSearch(page) {
	var func = get_mylists;
	if (tileView()) {
		func = get_mylists;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
	} else {
		func = get_mylists_list;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
	}
	var jsonParam = "tag=" + getSessionStorage("tagLists") + "&text=" + getSessionStorage('search') + '&page=' + page;
	var URL = hostURL + "/tagsearch";
	getJSON(URL, jsonParam, func);
}
// シェアのタイトル検索
function shareListSearch(page) {
	var func = get_sharelists;
	if (tileView()) {
		func = get_sharelists;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
	} else {
		func = get_sharelists_list;
		$('div#themebutton')
				.html(
						'<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'
								+ '<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
		$('div.stylebutton')
				.html(
						'<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
	}
	var jsonParam = "friend_user_id=" + getSessionStorage("shareLists")
			+ "&text=" + getSessionStorage('search') + '&page=' + page;
	var URL = hostURL + "/sharelistsearch";
	getJSON(URL, jsonParam, func);
};

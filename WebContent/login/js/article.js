/*
 * Index.htmlの最初に行う処理
 * */

$(document).ready(function() {
	$('.isotope').isotope({
		itemSelector : '.item',
		masonry : {
			columnWidth : 100
		}
	});
});

$(document).ready(function() {
	$('#tag-it').tagit({
		fieldName : "tags[]",
		tagLimit : 15,
		autocomplete : {}
	});
});

$(document).ready(
		function() {

			$("div.hiddenarea").append(
					'<input type="hidden" id="nonce" value="'
							+ docCookies.getItem("nonce") + '">');

		});

var $grid;
$(document).ready(function() {

	$grid = $('.grid').isotope({
		itemSelector : '.grid-item',

		percentPosition : true,
		masonry : {
			columnWidth : '.grid-sizer'
		},
	});

	switch ($.cookie("viewMode")) {
	case "0":// マイリスト画面を表示しているとき
		$('.grid').empty();

		// document.write('<script type="text/javascript"
		// src="../../js/import.js"></script>');
		// var isOnline = navigator.onLine;
		if (isSettinOnLine() === true) {
			getMyList();
			initTopPage();
			toastr.warning("オンライン状態なり");

		} else if (isSettinOnLine() === false) {

			$('.head-bar').css({
				'background' : '#31708f'
			});
			toastr.warning("オフラインなんだなーこれ");
			var username = docCookies.getItem('username');
			page = 1;
			getIDEArticleList(username, page).then(function(json) {
				// 純粋なリストが必要
				$('#title').append('<h1>マイリスト</h1>');
				get_mylists(json);
			})['catch'](function(error) {
				console.log(error);
			});

		}

		break;
	case "1":// お気に入り画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// オフライン判断
			getFavList();
		} else {

		}

		break;
	case "2":// 特定のタグ画面を表示しているとき
		$('.grid').empty();
		// オフライン判断
		if (isSettinOnLine() === true) {
			console.log($.cookie("tagLists"));
			getTagArticleList(0, $.cookie("tagLists"));
		}
		break;
	case "3":// 特定のタグ画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// オフライン判断
			getShareList($.cookie("shareLists"));
		}
		break;
	}
});

function isSettinOnLine() {
	var SetFlag = docCookies.getItem('online');

	if (SetFlag == null) {
		docCookies.setItem('online', true);
	}

	console.log(SetFlag);
	if (SetFlag  === 'true') {
		if (navigator.onLine === true) {
			return true;
		} else {
			return false;
		}
	} else if (SetFlag === 'false'){
		return false;
	} else {
		toastr.warning('再ログインしてくだaさい');
	}
}

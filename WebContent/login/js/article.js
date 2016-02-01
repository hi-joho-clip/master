/*
 * Index.htmlの最初に行う処理
 * */

$(window).bind("unload", function() {
});
//$(document).ready(
//		function() {
//
//		});

function TESTinitPagingMylist(callback) {
	// var page = parseInt($('#art-page').val());
	// ページング用番号の初期化

	console.log('aaaaaaaaaaaaaaaaaaaaaaaaa:' + $('#art-page').val());
	// モードがタイルの場合
	if ($('#art-page').val() === '1') {
		// getMyListList(1);
		callback(1);
	} else {
		// 1でないときはその分までループする
		for ( var i = 1; i <= parseInt($('#art-page').val()); i++) {
			console.log("grid:" + i);
			callback(i);
		}
	}
}

/**
 *
 * @param callback
 */
function initPagingMylist(callback) {
	// var page = parseInt($('#art-page').val());
	// ページング用番号の初期化

	var count = 1;

	var proMylist = function() {
		return new Promise(function(resolve, reject) {

			setTimeout(function() {
				callback(count++);
				resolve();
			}, 800);
		});
	};
	// モードがタイルの場合
	if ($('#art-page').val() === '1') {
		$('#lastid').val('0');
		var last_promise = proMylist();
		last_promise.then();
	} else {
		// 1でないときはその分までループする
		// 最後のIDは初期化しておく
		$('#lastid').val('0');
		var last_promise = proMylist();
		for ( var i = 2; i <= parseInt($('#art-page').val()); i++) {
			last_promise = last_promise.then(proMylist);
		}
	}
}

function initPagingSharelist(callback, friend_id) {
	// var page = parseInt($('#art-page').val());
	// ページング用番号の初期化

	// モードがタイルの場合
	if ($('#art-page').val() === '1') {
		callback(friend_id, 1);
		// callback(2);
	} else {
		// 1でないときはその分までループする
		for ( var i = 1; i <= parseInt($('#art-page').val()); i++) {
			console.log("grid:" + i);
			callback(friend_id, i);
		}
	}
}

var $grid;
$(document).ready(function() {

	$grid = $('.grid').isotope({
		itemSelector : '.grid-item',

		percentPosition : true,
		masonry : {
			columnWidth : '.grid-sizer'
		},
	});

	$('.isotope').isotope({
		itemSelector : '.item',
		masonry : {
			columnWidth : 100
		}
	});
	$('#tag-it').tagit({
		fieldName : "tags[]",
		tagLimit : 15,
		autocomplete : {}
	});

	$("div.hiddenarea").append(
			'<input type="hidden" id="nonce" value="'
					+ docCookies.getItem("nonce") + '">');

	$('.pure-pusher').on("scroll", function() {
		console.log($('#wrap').height() + ":" +  $('.pure-pusher').height() + ":" + $('.pure-pusher').scrollTop());


		var scrollHeight = $('#wrap').height();
		var scrollPosition = $('.pure-pusher').height() + $('.pure-pusher').scrollTop();
		if ((scrollHeight - scrollPosition) / scrollHeight < 0) {
			// when scroll to bottom of the page

			if ($('#art-add').val() === 'true') {
				// 今のページ番号を取得
				console.log("ｹﾂ");

				var page = parseInt($('#art-page').val()) + 1;
				$('#art-page').val(page);
				switch ($.cookie("viewMode")) {
				case "0":
					console.log("netstat" + isSettinOnLine());
					if (isSettinOnLine === true) {
						console.log("マイリスト");
						getMyList(page);
					} else {
						getMyList(page);
					}
					break;
				case "2":

					// お気に入り
					if (isSettinOnLine === true) {
						//getFavList(page);
					} else {
						//getFavList(page);
					}
					break;
				case "3":
					getShareList($.cookie("shareLists"), page);
					break;
				case "4":
					getMyListList(page);
					break;
				}
			}
		}
	});

	// 記事追加用のリスナー
	$(document).on('click', '#add-article', function() {
		// 今のページ番号を取得
		addArticle();
	});
	// タイル表示リスト表示用のリスナー
	$(document).on('click', '#stylechange', function() {
		if ($('#mode').val() === "tile") {
			$('#mode').val("list");
			$.cookie('Style', 'list');
			$('#art-page').val("1");
			$('#art-add').val("true");
			$('#lastid').val('0');
			$('div.grid').css({
				'height' : '0px'
			});
			console.log("今の状態：" + $('#mode').val());
			styleListChange();
		} else if ($('#mode').val() === "list") {
			$('#mode').val("tile");
			$.cookie('Style', 'tile');
			$('#art-page').val("1");
			$('#lastid').val('0');
			$('#art-add').val("true");
			console.log("今の状態：" + $('#mode').val());
			styleListChange();
		}
	});

	styleListChange();
	if (isSettinOnLine()) {
		initTopPage();
	}

});

function isSettinOnLine() {
	var SetFlag = docCookies.getItem('online');

	if (SetFlag == null) {
		docCookies.setItem('online', true);
	}

	console.log(SetFlag);
	if (SetFlag === 'true') {
		if (navigator.onLine === true) {
			console.log('true');
			return true;
		} else if (navigator.onLine === false) {
			return false;
			console.log('false');
		} else {
			return false;
			console.log('UnknownNetworkState');
		}
	} else if (SetFlag === 'false') {
		return false;
	} else {
		toastr.warning('再ログインしてくだaさい');
	}
}

// タイルリスト表示を押したときに走るメソッド
function styleListChange() {
	startload();

	// 1ページ目へ戻すよ
	switch ($.cookie("viewMode")) {
	case "0":// マイリスト画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// ページング処理
			// if ($.cookie("Style") === "tile") {
			initPagingMylist(getMyList);
			// } else if ($.cookie("Style") === "list") {
			// console.log('マイリスト');
			// initPagingMylist(getMyListList);
			// }
			// toastr.warning("オンライン状態なり");

		} else if (isSettinOnLine() === false) {

			$('.head-bar').css({
				'background' : '#31708f'
			});
			toastr.warning("オフラインなんだなーこれ");
			var username = docCookies.getItem('username');

			// テスト用修正必要
			//var i_page = 1;
			var offlineMyList = function(page) {
				getIDEArticleList(username, page, '0', '', false).then(
				function(json) {
					// 純粋なリストが必要
					$('#title').append('<h1>マイリスト</h1>');
					get_mylists(json);
				})['catch'](function(error) {
					console.log(error);
				});
			};
			initPagingMylist(offlineMyList);

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
	case "3":// シェア画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// オフライン判断
			initPagingSharelist(getShareList, $.cookie("shareLists"));
			initTopPage();
			toastr.warning("オンライン");

		} else if (isSettinOnLine() === false) {

		} else {

		}
		break;
	case "4":// シェア画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// オフライン判断
			initPagingMylist(getMyListList);
			toastr.warning("オンライン");

		} else if (isSettinOnLine() === false) {

		} else {

		}
		break;
	}
}

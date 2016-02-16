/*
 * Index.htmlの最初に行う処理
 * */

$(window).bind("unload", function() {
});
// $(document).ready(
// function() {
//
// });

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
 * 単なるマイリスト表示用
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

	if ($('#art-page').val() === '1') {
		$('#lastid').val('0');
		callback(1);
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

function initPagingMylistSearch(callback) {
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
$(document).ready(
		function() {

			$('#search-2').on('click', function(e) {
				e.target.setSelectionRange(0, e.target.value.length);
			});

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

			$('#add-button').on("click", function() {
				if ($('#art-add').val() === 'true') {
					// 今のページ番号を取得
					console.log("lastidマン" + $('#lastid').val());

					var page = parseInt($('#art-page').val()) + 1;
					$('#art-page').val(page);
					console.log("pageマン" + page);
					switch (getSessionStorage("viewMode")) {
					case "0":
						console.log("netstat" + isSettinOnLine());
						// マイリスト
						if (isSettinOnLine() == true) {
							console.log("マイリスト");
							if ($('#searchMode').val() === "true") {
								// マイリストの検索をしているページを出す
								myListSearch(page);
							} else {
								// 普通のマイリストを出す
								getMyList(page);
							}
						} else if (isSettinOnLine() == false) {
							console.log('オフラインマイリスト');
							getOffMyList(page);
						}
						break;
					case "1":
						// お気に入り

						if (isSettinOnLine) {
							if ($('#searchMode').val() === "true") {
								// お気に入りの検索をしているページを出す
								favListSearch(page);
							} else {
								// 普通のマイリストを出す
								getFavList(page);
							}
						} else if (isSettinOnLine() == false) {
							getOffFavList(page);
						}
						break;
					case "2":
						// 特定のタグ

						if (isSettinOnLine) {
							if ($('#searchMode').val() === "true") {
								// タグの検索をしているページを出す
								tagSearch(page);
							} else {
								// 普通のマイリストを出す
								getTagArticleList(page);
							}
						} else if (isSettinOnLine() == true) {
							getTagOffArticleList(page);
						}

						break;
					case "3":
						// シェア画面
						if (isSettinOnLine) {
							if ($('#searchMode').val() === "true") {
								// シェアの検索をしているページを出す
								shareListSearch(page);
							} else {
								// 普通のマイリストを出す
								getShareList(page);
							}
						} else if (isSettinOnLine() == false) {
							console.log('offline share');
							getOffShareList(page);
						}
						break;
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
				if (getLocalStorage('Style') === "tile") {

					setLocalStorage('Style', 'list');
					$('#art-page').val("1");
					$('#art-add').val("true");
					$('#lastid').val('0');
					$('div.grid').css({
						'height' : '0px'
					});
					console.log("今の状態：" + getLocalStorage('Style'));

					// こいつがリロードじゃなくなる
					styleListChange();
				} else if (getLocalStorage('Style') === "list") {
					setLocalStorage('Style', 'tile');
					$('#art-page').val("1");
					$('#lastid').val('0');
					$('#art-add').val("true");
					console.log("今の状態：" + getLocalStorage('Style'));
					// こいつがリロード
					styleListChange();
				}
			});

			styleListChange();
			console.log("onnline:" + isSettinOnLine());
			if (isSettinOnLine() === true) {

			} else if (isSettinOnLine() === false) {
				colorOffline();
				// menuを削除

			}

		});

// タイルリスト表示を押したときに走るメソッド
function styleListChange() {
	startload();

	// ボタン表示
	$('#buttonbox').css({
		'display' : 'block'
	});

	// 1ページ目へ戻すよ
	switch (getSessionStorage("viewMode")) {
	case "0":// マイリスト画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			if ($('#searchMode').val() === "true") {
				// マイリストの検索をしているページを出す
				initPagingMylist(myListSearch);

			} else {
				// 普通のマイリストを出す
				initPagingMylist(getMyList);

				var flag = false;
				// 更新処理（お使いの端末で初めてのログイン）
				if (getLocalStorage('auto') == null) {
					getIDBAllArticleList(docCookies.getItem('username')).then(
							getArticleListAsync).then(
							function(json) {
								var itemlength = JSON.parse(json).length;

								if (itemlength > 0) {
									flag = true;
									setLocalStorage('auto', 'false');
									$('#updatetext').append(
											'<h2>キャッシュをダウンロードしますか？</h2>'
													+ 'お使いの端末にキャッシュがありません'
													+ '<BR>ダウンロードしますか？');
									$('[data-remodal-id=updatemodal]')
											.remodal().open();
									$('#updatekazu').val(itemlength);
									$('#updatesize').append(
											'<h6>更新:' + itemlength + '件</h6>');
								}
							});
				}
				if (getLocalStorage('auto') == 'true') {
					$('#autoupdate').empty();
					getIDBAllArticleList(docCookies.getItem('username')).then(
							getArticleListAsync).then(
							function(json) {
								var itemlength = JSON.parse(json).length;
								if (itemlength >= 30) {
									$('#updatekazu').val(itemlength);
									$('#updatetext').append(
											'<h2>自動更新が有効です。</h2>'
													+ '更新ファイルが30件以上あります。'
													+ '<BR>ダウンロードしますか？');
									$('[data-remodal-id=updatemodal]')
											.remodal().open();
									$('#updatesize').append(
											'<h6>更新:' + JSON.parse(json).length
													+ '件</h6>');
								} else {
									initTopPage();
								}
							});
				} else {
					// 無効になってる旨のとーすた
					$('#autoupdate').prepend('<h6>更新:OFF </h6>');
					if (flag) {
						// toastr.options = {
						// "closeButton" : false,
						// "debug" : false,
						// "newestOnTop" : false,
						// "progressBar" : true,
						// "positionClass" : "toast-bottom-right",
						// "preventDuplicates" : false,
						// "showDuration" : "300",
						// "hideDuration" : "1000",
						// "timeOut" : "5000",
						// "extendedTimeOut" : "1000",
						// "showEasing" : "swing",
						// "hideEasing" : "linear",
						// "showMethod" : "fadeIn",
						// "hideMethod" : "fadeOut"
						// };
						// toastr["warning"]("自動更新はオフです");
						// toastr.options = {
						// "timeOut" : "1000",
						// };


					}
				}
			}
		} else if (isSettinOnLine() === false) {

			initPagingMylist(getOffMyList);

		}

		break;
	case "1":// お気に入り画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// オフライン判断
			if ($('#searchMode').val() === "true") {
				// お気に入りの検索をしているページを出す
				initPagingMylist(favListSearch);
			} else {
				// 普通のお気に入りを出す
				initPagingMylist(getFavList);
			}
		} else if (isSettinOnLine() === false) {

			initPagingMylist(getOffFavList);
		}

		break;
	case "2":// 特定のタグ画面を表示しているとき
		$('.grid').empty();
		// オフライン判断
		if (isSettinOnLine() === true) {
			if ($('#searchMode').val() === "true") {
				// 特定のタグの検索をしているページを出す
				initPagingMylist(tagSearch);
				console.log("tokuteino tag");
			} else {
				// 普通の特定のタグを出す
				initPagingMylist(getTagArticleList);

			}
		} else {
			initPagingMylist(getOffTagMyList);
		}
		break;
	case "3":// シェア画面を表示しているとき
		$('.grid').empty();

		if (isSettinOnLine() === true) {
			// オフライン判断

			if ($('#searchMode').val() === "true") {
				// 特定のタグの検索をしているページを出す
				initPagingMylist(shareListSearch);
			} else {
				// 普通の特定のタグを出す
				initPagingMylist(getShareList);
			}
			/*
			 * initPagingSharelist(getShareList,
			 * getSessionStorage("shareLists")); initTopPage();
			 */
			// toastr.warning("オンライン");
		} else if (isSettinOnLine() === false) {
			initPagingMylist(getOffShareList);
		}
		break;
	}

	if (isSettinOnLine() === true) {
		// alert('stop!');
		// initTopPage();
	} else if (isSettinOnLine() === false) {
		colorOffline();
		// menuを削除

	}
}

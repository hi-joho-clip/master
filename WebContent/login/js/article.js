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

$(document).ready(function() {


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
		getMyList();

		var isOnline = navigator.onLine;
		if (navigator.onLine === true) {
			 alert("current network status is online");

		} else if (navigator.onLine === false) {
			 alert("current network status is offline");

		} else {
			alert("current network status is unknown");
		}
		// オフライン判断

		break;
	case "1":// お気に入り画面を表示しているとき
		$('.grid').empty();
		// オフライン判断
		getFavList();

		break;
	case "2":// 特定のタグ画面を表示しているとき
		$('.grid').empty();
		// オフライン判断
		console.log($.cookie("tagLists"));
		getTagArticleList(0, $.cookie("tagLists"));
		break;
	case "3":// 特定のタグ画面を表示しているとき
		$('.grid').empty();
		// オフライン判断
		getShareList($.cookie("shareLists"));
		break;
	}
});

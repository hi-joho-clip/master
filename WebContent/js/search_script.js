var hostURL = "http://localhost:8080";

// どこの検索なのか判断する
$(document).on('click', '#titlesearch', function() {
	console.log("hidden:" + $('#viewmode').val());

	console.log("cookie:" + $.cookie("viewMode"));
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
	console.log("mylist");
	var jsonParam = "text=" + word;// 送りたいデータ
	var URL = "/clipMaster/mylistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
// お気に入りのタイトル検索
function favListSearch(word) {
	console.log("favlist");
	var jsonParam = "text=" + word;// 送りたいデータ
	var URL = "/clipMaster/favlistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
// タグのタイトル検索
function tagSearch(word) {
	console.log("taglist");
	var jsonParam = "tag=" + $.cookie("tagLists") + "&text=" + word;
	var URL = "/clipMaster/tagsearch";
	getJSON(URL, jsonParam, get_mylists);
}
// シェアのタイトル検索
function shareListSearch(word) {
	console.log("sharelist");
	var jsonParam = "friend_user_id=" + $.cookie("shareLists") + "&text=" + word;
	var URL = "/clipMaster/sharelistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
};

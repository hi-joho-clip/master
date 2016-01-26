var hostURL = "http://localhost:8080";

//どこの検索なのか判断する
$(document).on('click', '#titlesearch', function() {
	console.log("hidden:"+$('#viewmode').val());

	console.log("cookie:"+$.cookie("viewMode"));

	switch($('#viewmode').val()){
	case "0":
		//マイリストの検索
		$('.grid').empty();
		myListSearch();
		break;
	case "1":
		//お気に入りの検索
		$('.grid').empty();
		favListSearch();
		break;
	case "2":
		//タグの検索
		$('.grid').empty();
		tagSearch();
		break;
	case "3":
		//シェアの検索
		$('.grid').empty();
		shareListSearch();
		break;
	}
});

//マイリスト内のタイトル検索
function myListSearch() {
	console.log("mylist");
	var jsonParam = "text="+document.getElementById('searchbox').value;// 送りたいデータ
	var URL = "/clipMaster/mylistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
//お気に入りのタイトル検索
function favListSearch() {
	console.log("favlist");
	var jsonParam = "text="+document.getElementById('searchbox').value;// 送りたいデータ
	var URL = "/clipMaster/favlistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
//タグのタイトル検索
function tagSearch() {
	console.log("taglist");
	var jsonParam = "tag="+$.cookie("tagLists")+"&text="+document.getElementById('searchbox').value;// 送りたいデータ
	var URL = "/clipMaster/tagsearch";
	getJSON(URL, jsonParam, get_mylists);
}
//シェアのタイトル検索
function shareListSearch() {
	console.log("sharelist");
	var jsonParam = "friend_user_id="+$.cookie("shareLists")+"&text="+document.getElementById('searchbox').value;// 送りたいデータ
	var URL = "/clipMaster/sharelistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
};

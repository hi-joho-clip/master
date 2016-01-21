var hostURL = "http://localhost:8080";

//どこの検索なのか判断する
function judge(judge_flag,text){
	switch(judge_flag){
	case 0:
		//マイリストの検索
		myListSearch(text);
		break;
	case 1:
		//お気に入りの検索
		favListSearch(text);
		break;
	case 2:
		//タグの検索
		tagSearch(text);
		break;
	case 3:
		//シェアの検索
		shareListSearch(text);
		break;
	}
}
//マイリスト内のタイトル検索
function myListSearch(text) {
	var jsonParam = "text="+text;// 送りたいデータ
	var URL = "/clipMaster/mylistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
//お気に入りのタイトル検索
function favListSearch(text) {
	var jsonParam = "text="+text;// 送りたいデータ
	var URL = "/clipMaster/favlistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
//タグのタイトル検索
function tagSearch(text) {
	var jsonParam = "tag="+$.cookie("tagLists")+"&text="+text;// 送りたいデータ
	var URL = "/clipMaster/tagsearch";
	getJSON(URL, jsonParam, get_mylists);
}
//シェアのタイトル検索
function shareListSearch(text) {
	var jsonParam = "friend_user_id="+$.cookie("shareLists")+"&text="text;// 送りたいデータ
	var URL = "/clipMaster/sharelistsearch";
	document.getElementById('title').innerHTML = '<h1>検索結果</h1>';
	getJSON(URL, jsonParam, get_mylists);
}

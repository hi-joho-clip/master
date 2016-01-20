var hostURL = "http://localhost:8080";

//マイリスト内のタイトル検索
function myListSearch() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/mylist";
	document.getElementById('title').innerHTML = '<h1>マイリスト</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
//お気に入りのタイトル検索
function favListSearch() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/mylist";
	document.getElementById('title').innerHTML = '<h1>お気に入り</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
//タグのタイトル検索
function tagSearch() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/mylist";
	getJSON(URL, jsonParam, get_mylists);
}
//シェアのタイトル検索
function shareListSearch() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/mylist";
	document.getElementById('title').innerHTML = '<h1>シェア記事</h1>';
	getJSON(URL, jsonParam, get_mylists);
}

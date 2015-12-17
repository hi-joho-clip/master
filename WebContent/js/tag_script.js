
//タグを追加
function addTagArticle(tag_list,article_id) {

	var xmlResult = new XMLHttpRequest();
	var arr = [];
	arr[0]=article_id.item(0).value;//0番目にArticle_idを入れる。
	for(var i=0;i<tag_list.length;i++){
		arr[i+1]=tag_list.item(i).value;//1番目からタグが入る
	}
	var tag_list = "tag_list="+JSON.parse(JSON.stringify(arr));

	var xmlURL = "http://localhost:8080/clipMaster/addtagarticle";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type","application/x-www-form-urlencoded");

	// イベントリスナー
	// readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		// 4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {

		}
	}
	xmlResult.send(tag_list);
	return true;
}
//タグを取得
function getTagArticle(article_id) {
	getArticle_id(article_id);
	var jsonParam = "article_id="+article_id;
	var URL = "http://localhost:8080/clipMaster/gettag";
	getJSON(URL, jsonParam, get_tag_article_lists);
}
function deleteTag(){
	$('#tag-it').tagit('removeAll');
}


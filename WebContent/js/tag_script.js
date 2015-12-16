
//タグを追加
function addTagArticle(tag_list) {

	var xmlResult = new XMLHttpRequest();
	/*for文による繰り返し処理で要素の内容を格納する*/
	alert(tag_list.item(0).value);
	var list="";
	for(var i=0;i<tag_list.length;i++){
		list+=tag_list.item(i).value+"/";
	}
	var jsonParam="tag_list="+list;


	console.log(jsonParam);
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
	xmlResult.send(jsonParam);
	return true;
}



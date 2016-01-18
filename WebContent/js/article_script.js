//マイリスト
function getMyList() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/mylist";
	document.getElementById('title').innerHTML = '<h1>マイリスト</h1>';
	getJSON(URL, jsonParam, get_mylists);
}
// お気に入り
function getFavList() {
	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/favlist";
	document.getElementById('title').innerHTML = '<h1>お気に入り</h1>';
	getJSON(URL, jsonParam, get_mylists);
}

// シェア記事一覧
function getShareList() {

}

// タグ内お気に入り一覧
function getTagFavList() {
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;// 送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/tagfavlist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	// イベントリスナー
	// readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		// 4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var tagFavList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length; i++) {
				tagFavList += "ID:" + jsonResult[i].article_id + "<br>"
						+ "タイトル:" + jsonResult[i].title + "<br>" + "URL:"
						+ jsonResult[i].url + "<br>" + "作成日:"
						+ jsonResult[i].created + "<br>" + "サムネイル："
						+ jsonResult[i].thum + "<br><br>";
			}
			document.getElementById('tagFavList').innerHTML = tagFavList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}

// 記事に付与されたタグを取得
function getTag() {
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;// 送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/gettag";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	// イベントリスナー
	// readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		// 4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var getTag = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length; i++) {
				getTag += "タグID" + jsonResult[i].tag_id + "<br>" + "タグ名:"
						+ jsonResult[i].tag_body + "<br>" + "使用日時:"
						+ jsonResult[i].lastest + "<br><br>";
			}
			document.getElementById('getTag').innerHTML = getTag;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
// 記事の表示（内容と画像の表示がよくわからん）
function getViewArticle(article_id) {

	var jsonParam = "article_id=" + article_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewarticle";
	var setappend = function(json) {
		alert(json.length);


		$("div.title").append("タイトル:" + json.title + "<br>");
		$("div.editable").append("内容:" + json.body);

		var con_type = "jpeg";
		var data;

		for ( var image in json.imageListDTO) {
			console.log(image);
			data = 'data:image/' + con_type + ';base64,'
					+ json.imageListDTO[image].blob_image;
			$("div.images").append(
					'<img id="icon_here" width ="400px" src = ' + data + '>');
		}

		//document.getElementById('viewArticle').innerHTML = viewArticle;
	};
	getJSON(URL, jsonParam, setappend);
}
// 記事の削除
function deleteArticle(article_id) {
	console.log(article_id.item(0).value);
	// getArticle_id(article_id);//html内の<div
	// id='article_id'>にhiddenでarticle_idを持たせる
	var jsonParam = "article_id=" + article_id.item(0).value;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deletearticle";
	var delete_article = function() {

	};
	getJSON(URL, jsonParam, delete_article);
	location.reload();
	toastr.success('記事を削除しました');
}
// お気に入りの追加削除
function addFavArticle(article_id) {
	// グローバルFlagを用意する。記事リストを作成時にグローバルFlagはtrue
	// true=追加可、false=削除可

	var jsonParam = "article_id=" + article_id;// 送りたいデータ
	var grobalflag = "grobalflag" + article_id;// グローバルflagの名前付け
	var favtitle = "favtitle" + article_id;// タイトルの★マークの名前
	var title = "title" + article_id;
	var url = "url" + article_id;
	var flag = document.getElementById(grobalflag).value;
	console.log(flag);
	if (flag == "false") {

		// 追加の処理
		var URL = "http://localhost:8080/clipMaster/addfav";

		// タイトルの横に★マークをつける

		getJSON(URL, jsonParam, null);

		document.getElementById(favtitle).innerHTML = "★"
				+ document.getElementById(title).value + "<BR><a href='"
				+ document.getElementById(url).value + "'>"
				+ document.getElementById(url).value + "</a>";
		// グローバルflagをfalseにする
		document.getElementById(grobalflag).value = true;

	} else if (flag == "true") {
		// 削除の処理
		var URL = "http://localhost:8080/clipMaster/deletefav";

		// タイトルの横の★マークを削除

		getJSON(URL, jsonParam, null);
		document.getElementById(favtitle).innerHTML = document
				.getElementById(title).value
				+ "<BR><a href='"
				+ document.getElementById(url).value
				+ "'>"
				+ document.getElementById(url).value + "</a>";
		// グローバルflagをtrueにする
		document.getElementById(grobalflag).value = false;

	}
}
// シェア記事の追加
function shareArticle(friend_user_id, article_id) {
	var arr = [];
	arr[0] = article_id.item(0).value;
	arr[1] = friend_user_id;
	var param = "article_id="+arr[0]+"&friend_id="+arr[1]+"";

	var URL = "http://localhost:8080/clipMaster/addshare";

	getJSON(URL, param, null);
}

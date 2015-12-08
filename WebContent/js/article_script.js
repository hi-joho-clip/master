
//マイリスト
function getMyList(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/mylist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var myList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				myList = "<div class='"+item_class[i]+"'>"+
							"ID:"+jsonResult[i].article_id+"<br>"+
							"タイトル:" + jsonResult[i].title + "<br>" +
							"URL:"+jsonResult[i].url+"<br>"+
							"サムネイル："+jsonResult[i].thum+"<br>"
							+"</div>";
				console.log(myList);
				$grid.prepend(myList).isotope('prepended', myList).trigger('create');
			}
			//document.getElementById('myList').innerHTML = myList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}

var item_class = ["grid-item--width2","grid-item grid-item--height2","grid-item","grid-item grid-item--3",
                  "grid-item grid-item--width2 grid-item--height2","grid-item grid-item--width2","grid-item grid-item--width2",
                  "grid-item grid-item--height2","grid-item","grid-item grid-item--width2","grid-item grid-item--height2",
                  "grid-item grid-item--3","grid-item"];


//お気に入り一覧
function getFavList(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/favlist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var favList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				favList += "ID:"+jsonResult[i].article_id+"<br>"+"タイトル:" + jsonResult[i].title + "<br>" + "URL:"+jsonResult[i].url+"<br>"+"作成日:"+jsonResult[i].created+"<br>"+"サムネイル："+jsonResult[i].thum+"<br><br>";
			}
			document.getElementById('favList').innerHTML = favList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
//シェア記事一覧
function getShareList(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/sharelist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var shareList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				shareList += "ID:"+jsonResult[i].article_id+"<br>"+"タイトル:" + jsonResult[i].title + "<br>" + "URL:"+jsonResult[i].url+"<br>"+"作成日:"+jsonResult[i].created+"<br>"+"サムネイル："+jsonResult[i].thum+"<br><br>";
			}
			document.getElementById('shareList').innerHTML = shareList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
//特定のタグの記事一覧
function getTagArticleList(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/tagarticlelist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var tagArticleList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				tagArticleList += "ID:"+jsonResult[i].article_id+"<br>"+"タイトル:" + jsonResult[i].title + "<br>" + "URL:"+jsonResult[i].url+"<br>"+"作成日:"+jsonResult[i].created+"<br>"+"サムネイル："+jsonResult[i].thum+"<br><br>";
			}
			document.getElementById('tagArticleList').innerHTML = tagArticleList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
//タグ内お気に入り一覧
function getTagFavList(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/tagfavlist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var tagFavList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				tagFavList += "ID:"+jsonResult[i].article_id+"<br>"+"タイトル:" + jsonResult[i].title + "<br>" + "URL:"+jsonResult[i].url+"<br>"+"作成日:"+jsonResult[i].created+"<br>"+"サムネイル："+jsonResult[i].thum+"<br><br>";
			}
			document.getElementById('tagFavList').innerHTML = tagFavList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
//タグ一覧
function getTagList(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/taglist";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var tagList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				tagList += "タグID"+jsonResult[i].tag_id+"<br>"+"タグ名:" + jsonResult[i].tag_body + "<br>" + "使用日時:"+ jsonResult[i].lastest +"<br><br>";
			}
			document.getElementById('tagList').innerHTML = tagList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
//記事に付与されたタグを取得
function getTag(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/gettag";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var getTag = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length ; i++) {
				getTag += "タグID"+jsonResult[i].tag_id+"<br>"+"タグ名:" + jsonResult[i].tag_body + "<br>" + "使用日時:"+ jsonResult[i].lastest +"<br><br>";
			}
			document.getElementById('getTag').innerHTML = getTag;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}
//記事の表示（内容と画像の表示がよくわからん）
function getViewArticle(){
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;//送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/viewarticle";

	xmlResult.open("POST", xmlURL, true);
	xmlResult.responseText = "json";
	xmlResult.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	// イベントリスナー
	//readyState プロパティが変わるごとに自動的に呼ばれる関数(あるいは関数名)を格納する
	xmlResult.onreadystatechange = checkStatus;

	function checkStatus() {
		//4: リクエストは終了してレスポンスの準備が完了
		if (xmlResult.readyState == 4) {
			var viewArticle = "";
			var jsonResult = JSON.parse(xmlResult.responseText);

			for ( var i = 0; i < jsonResult.length ; i++) {
				viewArticle += "ID"+jsonResult[i].article_id+"<br>"+"タイトル:" + jsonResult[i].title + "<br>" + "内容:"+ jsonResult[i].body +"<br>"+"画像："+jsonResult[i].blob_image+"<br><br>";
			}
			document.getElementById('viewArticle').innerHTML = viewArticle;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}

$('button').click(function() {
	switch(button.getAttribute('name')){
	case 'mylist':
		getMyList();
		break;
	case 'favlist':
		getFavList();
		break;
	case 'sharelist':
		getShareList();
		break;
	case 'tagarticlelist':
		getTagArticleList();
		break;
	case 'tagfavlist':
		getTagFavList();
		break;
	case 'taglist':
		getTagList();
		break;
	case 'viewarticle':
		getViewArticle();
		break;
	case 'gettag':
		getTag();
		break;
	}
});
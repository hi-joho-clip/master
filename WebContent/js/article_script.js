var item_class = ["grid-item grid-item--width2",
                  "grid-item grid-item--height2",
                  "grid-item",
                  "grid-item grid-item--3",
                  "grid-item grid-item--width2 grid-item--height2",
                  "grid-item grid-item--width2",
                  "grid-item grid-item--width2",
                  "grid-item grid-item--height2",
                  "grid-item",
                  "grid-item grid-item--width2",
                  "grid-item grid-item--height2",
                  "grid-item grid-item--3",
                  "grid-item"
                  ];

//マイリスト
function getMyList(){
	$('.grid').empty();
	$.cookie("viewMode" , "0");//ブラウザを閉じたらクッキー削除
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
			var $myList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			//i=8, 876543210
			$myList = $("<div class='grid-sizer'></div>");
			$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
			for ( var i =jsonResult.length-1 ; i >= 0 ; i--) {
				$myList = $("<div id='"+jsonResult[i].article_id+"'class='"+item_class[i]+"'>"+
							"タイトル:" + jsonResult[i].title + "<br>" +
							"URL:"+jsonResult[i].url+"<br>"+
							"サムネイル："+jsonResult[i].thum+"<br>"
							+"</div>");
				$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
			}
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}


//お気に入り一覧
function getFavList(){
	$('.grid').empty();
	$.cookie("viewMode" , "1");//ブラウザを閉じたらクッキー削除
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
			var $favList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			$favList = $("<div class='grid-sizer'></div>");
			$grid.prepend($favList).isotope('prepended', $favList).trigger('create');
			for ( var i =jsonResult.length-1 ; i >= 0 ; i--) {
				$favList = $("<div id='"+jsonResult[i].article_id+"'class='"+item_class[i]+"'>"+
						"タイトル:" + jsonResult[i].title + "<br>" +
						"URL:"+jsonResult[i].url+"<br>"+
						"サムネイル："+jsonResult[i].thum+"<br>"
						+"</div>");
				$grid.prepend($favList).isotope('prepended', $favList).trigger('create');
			}
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
	$('.grid').empty();
	$.cookie("viewMode" , "2");//ブラウザを閉じたらクッキー削除
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
			var $tagList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			$tagList = $("<div class='grid-sizer'></div>");
			$grid.prepend($tagList).isotope('prepended', $tagList).trigger('create');
			for ( var i =jsonResult.length-1 ; i >= 0 ; i--) {
				$tagList =  $("<div class='"+item_class[i]+"'>"+
							"タグID"+jsonResult[i].tag_id+"<br>"+
							"タグ名:" + jsonResult[i].tag_body + "<br>" +
							"使用日時:"+ jsonResult[i].lastest +"<br>"+
							"</div>");
				$grid.prepend($tagList).isotope('prepended', $tagList).trigger('create');
			}

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
function getViewArticle(article_id){
	//$('.grid').empty();
	var xmlResult = new XMLHttpRequest();
	var jsonParam = "article_id="+article_id;//送りたいデータ
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
			//document.getElementById('viewArticle').innerHTML = viewArticle;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
}

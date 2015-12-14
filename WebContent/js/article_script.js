/*var item_class = [ "grid-item grid-item--width2",
		"grid-item grid-item--height2", "grid-item", "grid-item grid-item--3",
		"grid-item grid-item--width2 grid-item--height2",
		"grid-item grid-item--width2", "grid-item grid-item--width2",
		"grid-item grid-item--height2", "grid-item",
		"grid-item grid-item--width2", "grid-item grid-item--height2",
		"grid-item grid-item--3", "grid-item" ];*/

var item1 =[ "grid-item grid-item--width2",
			           		"grid-item grid-item--height2",
			                   "grid-item",
			           		"grid-item",
			           		"grid-item",
			           		"grid-item grid-item--3",
			                    "grid-item grid-item--width2",
			           		"grid-item grid-item--height2",
			                    "grid-item",
			           		"grid-item grid-item--width2",
			                    "grid-item grid-item--height2",
			                   "grid-item grid-item--3",
			                   "grid-item",
			                   "grid-item grid-item--height2",
			                    "grid-item",
			                    "grid-item grid-item--height2",

			                   "grid-item",
			           		"grid-item",
			           		"grid-item grid-item--width2",
			                   "grid-item"
		];

var item2 = [ "grid-item",
           		"grid-item",
           		"grid-item grid-item--3",
           		"grid-item grid-item--3",
                    "grid-item",
                   "grid-item",
                   "grid-item 2",
                    "grid-item",
           		"grid-item grid-item--width2",
                    "grid-item",
                   "grid-item",
           		"grid-item",
                   "grid-item",
                   "grid-item",
                   "grid-item grid-item--3",
           		"grid-item",
           		"grid-item",
                    "grid-item",
           		"grid-item",
                    "grid-item"

		];

var item3 =	[ 	 "grid-item grid-item--3",
                    "grid-item grid-item--width2",
           		"grid-item",
           	 "grid-item",
           		"grid-item grid-item--width2",
           		"grid-item grid-item--width2",
                   "grid-item",
                   "grid-item grid-item--3",
                    "grid-item",
                    "grid-item",
                   "grid-item",
           		"grid-item",
                 "grid-item grid-item--width2",
           		"grid-item",
                   "grid-item",
                   "grid-item",
           		"grid-item2",
           		"grid-item ",
                    "grid-item grid-item--width2",
           		"grid-item"
		];

var item = [item1, item2, item3];

//マイリスト
function getMyList() {
	$('.grid').empty();
	$.cookie("viewMode", "0");// ブラウザを閉じたらクッキー削除

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/mylist";
	document.getElementById('title').innerHTML='<h1>マイリスト</h1>';
	var setappend = function(json) {
		var random =Math.floor(Math.random()*3);
		console.log(random);
		// i=8, 876543210

		$myList = $("<div class='grid-sizer'></div>");
		$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
		for ( var i = json.length - 1; i >= 0; i--) {

			$myList = $("<div class='"+item[random][i] + " mosaic-block bar'>" +
							"<div class='mosaic-overlay'>"+
								"<div id='menu'>"+
									"<ol>"+
										"<li><a href='"+json[i].url+"'>"+json[i].url+"</a></li>"+
									"</ol>"+
								"</div>"+
								"<div id='menu2'>"+
									"<a href='a'><img src='img/trash1.png' align='right'width='20'height='20'></img></a>"+
									"<a href='i'><img src='img/share1.png' align='right'width='20'height='20'></img></a>"+
									"<a href='u'><img src='img/tag1.png'align='right' width='20'height='20'></img></a>"+
									"<a href='e'><img src='img/star1.png' align='right'width='20'height='20'></img></a>"+
								"</div>"+
							"</div>"+
							"<a href='../login/article.html?"+json[i].article_id+"'target='_blank'><div class='mosaic-backdrop relative'>" +
								"<img src='http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg'width='100%'height='100%'/>" +
								"<p class='absolute'>"+json[i].title+"</p>" +
							"</div></a>"+
						"</div>");
			$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
		}
		jQuery(function($){
			$('.bar').mosaic({
				animation	:	'slide'		//fade or slide
			});
	    });
	};
	getJSON(URL, jsonParam, setappend);
}
//お気に入り
function getFavList() {
	$('.grid').empty();
	$.cookie("viewMode", "1");// ブラウザを閉じたらクッキー削除

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/favlist";
	document.getElementById('title').innerHTML='<h1>お気に入り</h1>';
	var setappend = function(json) {
		var random =Math.floor(Math.random()*3);
		console.log(random);
		// i=8, 876543210
		$myList = $("<div class='grid-sizer'></div>");
		$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
		for ( var i = json.length - 1; i >= 0; i--) {

			$favList = $("<div class='"+item[random][i] + " mosaic-block bar'>" +
							"<div class='mosaic-overlay'>"+
								"<div id='menu'>"+
									"<ol>"+
										"<li><a href='"+json[i].url+"'>"+json[i].url+"</a></li>"+
									"</ol>"+
								"</div>"+
								"<div id='menu2'>"+
									"<a href='a'><img src='img/trash1.png' align='right'width='20'height='20'></img></a>"+
									"<a href='i'><img src='img/share1.png' align='right'width='20'height='20'></img></a>"+
									"<a href='u'><img src='img/tag1.png'align='right' width='20'height='20'></img></a>"+
									"<a href='e'><img src='img/star1.png' align='right'width='20'height='20'></img></a>"+
								"</div>"+
							"</div>"+
							"<a href='../login/article.html?"+json[i].article_id+"'target='_blank'><div class='mosaic-backdrop relative'>" +
								"<img src='http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg'width='100%'height='100%'/>" +
								"<p class='absolute'>"+json[i].title+"</p>" +
							"</div></a>"+
						"</div>");
			$grid.prepend($favList).isotope('prepended', $favList).trigger('create');
		}
		jQuery(function($){
			$('.bar').mosaic({
				animation	:	'slide'		//fade or slide
			});
	    });
	};
	getJSON(URL, jsonParam, setappend);
}

// シェア記事一覧
function getShareList() {

}
// 特定のタグの記事一覧
function getTagArticleList() {
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;// 送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/tagarticlelist";

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
			var tagArticleList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			for ( var i = 0; i < jsonResult.length; i++) {
				tagArticleList += "ID:" + jsonResult[i].article_id + "<br>"
						+ "タイトル:" + jsonResult[i].title + "<br>" + "URL:"
						+ jsonResult[i].url + "<br>" + "作成日:"
						+ jsonResult[i].created + "<br>" + "サムネイル："
						+ jsonResult[i].thum + "<br><br>";
			}
			document.getElementById('tagArticleList').innerHTML = tagArticleList;
			xmlResult = null;
			jsonResult = null;
		}
	}
	xmlResult.send(jsonParam);
	return true;
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
// タグ一覧
function getTagList() {
	$('.grid').empty();
	$.cookie("viewMode", "2");// ブラウザを閉じたらクッキー削除
	var xmlResult = new XMLHttpRequest();
	var jsonParam = null;// 送りたいデータ
	var xmlURL = "http://localhost:8080/clipMaster/taglist";

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
			var $tagList = "";
			var jsonResult = JSON.parse(xmlResult.responseText);
			$tagList = $("<div class='grid-sizer'></div>");
			$grid.prepend($tagList).isotope('prepended', $tagList).trigger(
					'create');
			for ( var i = jsonResult.length - 1; i >= 0; i--) {
				$tagList = $("<div class='" + item_class[i] + "'>" + "タグID"
						+ jsonResult[i].tag_id + "<br>" + "タグ名:"
						+ jsonResult[i].tag_body + "<br>" + "使用日時:"
						+ jsonResult[i].lastest + "<br>" + "</div>");
				$grid.prepend($tagList).isotope('prepended', $tagList).trigger(
						'create');
			}

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
	var viewArticle = "";
	var setappend = function(json) {
		alert("AA");


			viewArticle = "ID" + json[0].article_id + "<br>"
					+ "タイトル:" + json[0].title + "<br>" + "内容:"
					+ json[0].body + "<br>" + "画像："
					+ json[0].blob_image + "<br><br>";

		document.getElementById('viewArticle').innerHTML = viewArticle;

	};

	getJSON(URL, jsonParam, setappend);
	alert(viewArticle);
}


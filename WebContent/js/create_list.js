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

var item3 =	[ 	 "grid-item grid-item--3 ",
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
           		"grid-item",
           		"grid-item ",
                    "grid-item grid-item--width2",
           		"grid-item"
		];


var item4 =	[ 	 "grid-item",
                    "grid-item",
           		"grid-item",
           	 "grid-item",
           		"grid-item",
           		"grid-item",
                   "grid-item",
                   "grid-item",
                    "grid-item",
                    "grid-item",
                   "grid-item",
           		"grid-item",
                 "grid-item",
           		"grid-item",
                   "grid-item",
                   "grid-item",
           		"grid-item",
           		"grid-item ",
                    "grid-item ",
           		"grid-item"
		];

var item = [item1, item2, item3,item4];

// タグを編集するときにArticle_idも送りたいので実装
function getArticle_id(article_id){
	$('#article_id').val(article_id);
}
// タグを削除するときにtag_idも送りたいので実装
function getTag_id(tag_id){
	$('#tag_id').val(tag_id);
}

function locateArticleView(article_id) {

	// IDをセッションに書き込んでリダイレクト
	setSessionStorage('article-id', article_id);
	console.log(getSessionStorage('article-id'));
	location.href = hostURL + '/login/article.html';
}

// "<a href='../login/article.html?"+json[i].article_id+"'target='_blank'>


// 記事一覧を作成（タイル表示）
var get_mylists = function(json) {

	return new Promise(function(resolve, reject) {

		console.log(json.length);

		stopload();
		setLocalStorage("Style","tile");
		var random =Math.floor(Math.random()*3);
		random = 3;
		$myList = $("<div class='grid-sizer'></div>");
		$grid.append($myList).isotope('appended', $myList).trigger('create');
		console.log("jsonlength:"+json.length);
		/*21ページ以上記事がある場合、lengthが21となるため、余分にリストを作ることをやめさせるためlengthを操作する*/
		var listLength = 0;
		if(json.length==21){
			listLength=json.length-1;//21ページ以上ある場合余分なリストを作成させない
		}else{
			listLength=json.length;//20ページ以下はこのまま
		}
		for ( var i = 0; i < listLength; i++) {
			var flag="";
			if(json[i].favflag==true){
				flag="★"+json[i].title;
			}else if(json[i].favflag==false){
				flag=json[i].title;
			}
			$myList = $("<div id='" + json[i].article_id+ "' class='"+item[random][i] + " mosaic-block bar' title='" + json[i].title +"'>" +
							"<div class='mosaic-overlay'>"+

							// "<div id='link-body'><a href='../login/article.html?"+json[i].article_id+"'></a></div>" +
							 "<div id='link-body'><a href='#' onclick='javascript:locateArticleView("+json[i].article_id+");'></a></div>" +
								"<div id='menu-block'>" +
									"<div class='menu2'>"+
										"<div class='remodal-bg'>"+
										"<input type='hidden' value='"+json[i].favflag+"' id='grobalflag"+json[i].article_id+"'>"+

										"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" +
										"<img src='img/trash1.png' align='right'width='42'height='42'></img>" +
										"</a>"+

										"<a href='#' data-remodal-target='sharemodal'onclick='javascript:getFriends();getArticle_id("+json[i].article_id+");return false;'><img src='img/share1.png' align='right'width='42'height='42'></img></a>"+

										 "<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json[i].article_id+");getUsingTags();return false;'>"+
										  "<img src='img/tag1.png'align='right' width='42'height='42'></img>" +
										  "</a>"+

										"<a href='#'onclick='javascript:addFavArticle("+json[i].article_id+");return false;'><img src='img/star1.png' align='right'width='42'height='42'></img></a>"+

										"</div>"+
									"</div>"+
								"</div>"+
							"</div>"+
							"<div class='mosaic-backdrop relative'>" + thumView(json[i], "100%", "100%")+
								"<div class='absolute'id='favtitle"+json[i].article_id+"'>"+flag+"<BR><a class='art-title' href='"+json[i].url+"'target='_blank'>"+json[i].url+"</a></div>" +
								"<input type='hidden' value='"+json[i].title+"' id='title"+json[i].article_id+"'>"+
								"<input type='hidden' value='"+json[i].url+"' id='url"+json[i].article_id+"'>"+
							"</div>"+
						"</div>");

			// http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg
			$grid.prepend($myList).isotope('insert', $myList).trigger('create');
			if(json[i].favflag==true){
				//console.log('#favtitle'+json[i].article_id);
				$('#favtitle'+json[i].article_id).attr('style', 'color:#FF9800');
			}
		}
		addTileButton();
		// ページング処理
		addViewNext(json);
		jQuery(function($){
			$('.bar').mosaic({
				animation	:	'slide'		// fade or slide
			});
	    });

		// sessionstrageに保存する場合がある
		resolve(json);
	});
};

// リスト表示の時
function addListMoreButton() {
	// 消して入れる
	// モード切替時のために要素自体
	$('#buttonbox').remove();
	$('#buttonbox').empty();
	$button = $('<input type="button" id="add-button"  value="次を読み込む" onclick="javascript:addButton();disabled = true;"></div>');
	$('#buttonbox').append($button).trigger('create');
}

//
function addTileButton() {
	 // 追加ボタン復活
	$('#buttonbox').empty();
	$('#buttonbox').append('<input type="button" id="add-button"  value="次を読み込む" onclick="javascript:addButton();disabled = true;">');
}

// リストへ追加ボタン
function addListButton(i) {
	$('#buttonbox').empty();
	$('.list-buttonbox').remove();
	if (i == 19) {
		$button = $('<div class="list-buttonbox" style="text-align:center"><input type="button" id="add-button"  value="次を読み込む" onclick="javascript:addButton();disabled = true;"></div>');
		$grid.append($button).isotope('insert', $button).trigger('create');
	}
}

function addViewNext(json) {

	// 20件以上の場合はValueを加算し、20件未満の場合はボタンを削除する。
	if (json.length > 20) {
		console.log('number:' + $('#art-page').val());
		//サーチをするときなどに有効にしないと消えたままとなる。
		$('#buttonbox').css({'display' : 'block'});
		$("#add-button").prop("disabled", false);
		$('#art-add').val('true');
		console.log("21page:"+json[json.length-1].article_id);
		// 最後に読み込んだArticleIDを残す
		$('#lastid').val(json[json.length-1].article_id);
	} else if (json.length <= 20){
		console.log("under 19");
		// 無限読み込み停止用
		$('#buttonbox').css({'display' : 'none'});
		$('#art-add').val('false');
	}

	// オフラインモードならメニューを削除
	if (isSettinOnLine() == true) {

	} else if (isSettinOnLine() == false) {
		$('.menu2').empty();
	}
}


// シェア記事一覧を作成（タイル表示）
var get_sharelists = function(json) {
	stopload();
	setLocalStorage("Style","tile");
	var random =Math.floor(Math.random()*3);
	random = 3;
	$myList = $("<div class='grid-sizer'></div>");
	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	var listLength = 0;
	if(json.length==21){
		listLength=json.length-1;//21ページ以上ある場合余分なリストを作成させない
	}else{
		listLength=json.length;//20ページ以下はこのまま
	}
	for ( var i = 0; i < listLength ; i++) {
		$myList = $("<div id='" + json[i].article_id+ "' class='"+item[random][i] + " mosaic-block bar'>" +
						"<div class='mosaic-overlay'>"+
						 "<div id='link-body'><a href='#' onclick='javascript:locateArticleView("+json[i].article_id+");'></a></div>" +
							"<div id='menu-block'>" +
								"<div class='menu2'>"+
									"<div class='remodal-bg'>"+

										"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" +
										"<img src='img/trash1.png' align='right'width='42'height='42'></img>" +
										"</a>"+

									"</div>"+
								"</div>"+
							"</div>"+
						"</div>"+
						"<div class='mosaic-backdrop relative'>" + thumView(json[i], "100%", "100%")+

							"<div class='absolute'>"+json[i].title+"<BR><a class='art-title' href='"+json[i].url+"' target='_blank'>"+json[i].url+"</a></div>" +
						"</div>"+
					"</div>");
		$grid.prepend($myList).isotope('insert', $myList).trigger('create');
	}

	addViewNext(json);
	jQuery(function($){
		$('.bar').mosaic({
			animation	:	'slide'		// fade or slide
		});
    });

};
// 記事一覧を作成（リスト表示）
var get_mylists_list = function(json) {
	return new Promise(function(resolve, reject) {
		setLocalStorage("Style","list");
		$myList = $("<div class='grid-sizer' ></div>");
		$grid.append($myList).isotope('insert', $myList).trigger('create');
		var listLength = 0;
		if(json.length==21){
			listLength=json.length-1;//21ページ以上ある場合余分なリストを作成させない
		}else{
			listLength=json.length;//20ページ以下はこのまま
		}
		for ( var i = 0; i < listLength ; i++) {
			var flag="";
			if(json[i].favflag==true){
				flag="★"+json[i].title;
			}else if(json[i].favflag==false){
				flag=json[i].title;
			}
			$myList = $("<ol id='" + json[i].article_id+ "'>"+
							"<a class='art-title' href='#' onclick='javascript:locateArticleView("+json[i].article_id+");'><li class='first'>"+
								"<div class='dan'>"+
									thumView(json[i], "100px", "100px")+
								"</div>"+
								"<div class='mawari'>"+
									"<div class='dan2'id='favtitle"+json[i].article_id+"'>"+
									""+flag+""+
									"</div>"+
									"<div class='dan3'>"+
									"<a class='art-title' href='"+json[i].url+"' target='_blank'>"+json[i].url+"</a>"+
									"</div>"+
									"<input type='hidden' value='"+json[i].title+"' id='title"+json[i].article_id+"'>"+
									"<input type='hidden' value='"+json[i].url+"' id='url"+json[i].article_id+"'>"+
								"</div>"+
								"<div class='firstbuttons'>"+


										"<div class='menu2'>"+
											"<div class='remodal-bg'>"+
											"<input type='hidden' value='"+json[i].favflag+"' id='grobalflag"+json[i].article_id+"'>"+

											"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" +
											"<img src='img/trash1.png' align='right'width='30'height='30'></img>" +
											"</a>"+

											"<a href='#' data-remodal-target='sharemodal'onclick='javascript:getFriends();getArticle_id("+json[i].article_id+");return false;'><img src='img/share1.png' align='right'width='30'height='30'></img></a>"+

											 "<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json[i].article_id+");getUsingTags();return false;'>"+
											  "<img src='img/tag1.png'align='right' width='30'height='30'></img>" +
											  "</a>"+

											"<a href='#'onclick='javascript:addFavArticle("+json[i].article_id+");return false;'><img src='img/star1.png' align='right'width='30'height='30'></img></a>"+

											"</div>"+
										"</div>"+

								"</div>"+
							"</li></a>"+
						"</ol>");
			$grid.append($myList).isotope('insert', $myList).trigger('create');
			// i = 20
			addListButton(i);

			if(json[i].favflag==true){
				//console.log('#favtitle'+json[i].article_id);
				$('#favtitle'+json[i].article_id).attr('style', 'color:#FF9800');
			}//#FFEB3B
		}

		addViewNext(json);
		stopload();
		resolve();
	});
};
// シェア記事一覧を作成（リスト表示）
var get_sharelists_list = function(json) {
	setLocalStorage("Style","list");
	$myList = $("<div class='grid-sizer' ></div>");
	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	var listLength = 0;
	if(json.length==21){
		listLength=json.length-1;//21ページ以上ある場合余分なリストを作成させない
	}else{
		listLength=json.length;//20ページ以下はこのまま
	}
	for ( var i = 0; i < listLength ; i++) {
		$myList = $("<ol id='" + json[i].article_id+ "'>"+
						"<a class='art-title' href='#' onclick='javascript:locateArticleView("+json[i].article_id+");'><li class='first'>"+
							"<div class='dan'>"+
								thumView(json[i], "100px", "100px")+
							"</div>"+
							"<div class='mawari'>"+
								"<div class='dan2'id='favtitle"+json[i].article_id+"'>"+
								""+json[i].title+""+
								"</div>"+
								"<div class='dan3'>"+
								"<a class='art-title' href='"+json[i].url+"' target='_blank'>"+json[i].url+"</a>"+
								"</div>"+

							"</div>"+
							"<div class='firstbuttons'>"+
									"<div class='menu2'>"+
										"<div class='remodal-bg'>"+
										"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" +
										"<img src='img/trash1.png' align='right'width='30'height='30'></img>" +
										"</a>"+
										"</div>"+
									"</div>"+

							"</div>"+
						"</li></a>"+
					"</ol>");
		$grid.append($myList).isotope('insert', $myList).trigger('create');
	}

	addViewNext(json);
	stopload();
};
// タグ一覧を作成
var get_taglists = function(json) {
	// $.cookie('viewMode','2');$.cookie('tagLists',json[i].tag_body);

	var tagList = "";
	tagList = "<table>";
	for ( var i = 0; i < json.length; i++) {
		tagList +=

				"<tr id='tagtable"+json[i].tag_id+"' align='left' valign='top'><td style='border-bottom:1px solid #d3381c;'>"+
				"<h8><a style='padding-right: 1em;' class='art-title' href='index.html'onclick='javascript:setSessionStorage(\"viewMode\",\"2\");setSessionStorage(\"tagLists\",\""+json[i].tag_body+"\");'>"+json[i].tag_body + "</a></h8><br>" +
				"<input type='hidden' value='"+json[i].lastest+"' name='lastest"+i+"'>"+
				"</td><td style='border-bottom:1px solid #d3381c;'><h8><a style='color : #FFF;' class='btn btn-danger art-title' href='/' data-remodal-target='tagdeletemodal' onclick='javascript:getTag_id("+json[i].tag_id+");return false;'>削除</a></h8></td></tr>";

	}
	tagList += "</table>";
	document.getElementById('taglist').innerHTML = tagList;
	stopload();
};



// 登録しているフレンドが入ったセレクトボックスを作成
var get_friends = function(json){
	$('.selectbox').empty();// 削除しないとフレンドがセレクトボックス内にたまるから削除する
	$('.selectbox').off();// イベントハンドラがたまるから解除する
	var option = "";
	var dummy = "";
	dummy = document.createElement('option');
	dummy.value = "";
	dummy.appendChild(document.createTextNode('フレンドを選択'));
	$(".selectbox").append(dummy).trigger('create');
	for(var i=0; i<json.length; i++){
		option=document.createElement('option');
		option.value = json[i].friend_user_id;
		// 画面に表示されるテキスト部分は createTextNode で作って、optionの子要素として追加
		option.appendChild(document.createTextNode(''+json[i].nickname+''));
		// プルダウンに追加
		$('.selectbox').append(option).trigger('create');
		option="";
	}
	// ここでセレクトボックスを生成
	$('.selectbox').select2({width:"50%"}).trigger('create');
	stopload();

};

// 更新日時が新しいタグが入ったセレクトボックスを作成
var get_using_tags = function(json){
	$('.tagselect').empty();// 削除しないとタグがセレクトボックス内にたまるから削除する
	$('.tagselect').off();// イベントハンドラがたまるから解除する
	var option = "";
	var dummy = "";
	dummy = document.createElement('option');
	dummy.value = "";
	dummy.appendChild(document.createTextNode('タグを選択'));
	$(".tagselect").append(dummy).trigger('create');
	for(var i=0; i<json.length; i++){
		option=document.createElement('option');
		option.value = json[i].tag_body;
		// 画面に表示されるテキスト部分は createTextNode で作って、optionの子要素として追加
		option.appendChild(document.createTextNode(''+json[i].tag_body+''));
		// プルダウンに追加
		$(".tagselect").append(option).trigger('create');

		option="";
	}
	// ここでセレクトボックスを生成
	$('.tagselect').select2({width:"50%",minimumResultsForSearch: Infinity}).trigger('create');
	// セレクトボックスを選択した際に発火するイベント
	$(".tagselect").change(function () {
			$('#tag-it').tagit('createTag',$(".tagselect").val());
	});
	stopload();

};

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

//タグを編集するときにArticle_idも送りたいので実装
function getArticle_id(article_id){
	var articleid = "<input type='hidden' value='"+article_id+"' name='article_id'>";
	document.getElementById('article_id').innerHTML = articleid;
}
//タグを削除するときにtag_idも送りたいので実装
function getTag_id(tag_id){
	var tagid = "<input type='hidden' value='"+tag_id+"' name='tag_id'>";
	document.getElementById('tag_id').innerHTML = tagid;
}


//"<a href='../login/article.html?"+json[i].article_id+"'target='_blank'>


//記事一覧を作成

var get_mylists = function(json) {
	var random =Math.floor(Math.random()*3);
	$myList = $("<div class='grid-sizer'></div>");
	$grid.append($myList).isotope('appended', $myList).trigger('create');
	for ( var i = 0; i < json.length; i++) {
		var flag="";
		if(json[i].favflag==true){
			flag="★"+json[i].title;
		}else if(json[i].favflag==false){
			flag=json[i].title;
		}
		$myList = $("<div class='"+item[random][i] + " mosaic-block bar'>" +
						"<div class='mosaic-overlay'>"+

						 "<div id='link-body'><a href='../login/article.html?"+json[i].article_id+"'></a></div>" +
							"<div id='menu-block'>" +
								"<div id='menu2'>"+
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
						"<div class='mosaic-backdrop relative'>" +
							"<img src='http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg'width='100%'height='100%'alt='"+json[i].title+"'/>" +
							"<p class='absolute'id='favtitle"+json[i].article_id+"'>"+flag+"<BR><a href='"+json[i].url+"'target='_blank'>"+json[i].url+"</a></p>" +
							"<input type='hidden' value='"+json[i].title+"' id='title"+json[i].article_id+"'>"+
							"<input type='hidden' value='"+json[i].url+"' id='url"+json[i].article_id+"'>"+
						"</div>"+
					"</div>");



		$grid.prepend($myList).isotope('insert', $myList).trigger('create');
		if(json[i].favflag==true){
			console.log('#favtitle'+json[i].article_id);
			$('#favtitle'+json[i].article_id).attr('style', 'color:#FFEB3B');
		}
	}

	// ページング処理
	addViewNext(json);
	jQuery(function($){
		$('.bar').mosaic({
			animation	:	'slide'		//fade or slide
		});
    });
};

function addViewNext(json) {

	// 20件以上の場合はValueを加算し、20件未満の場合はボタンを削除する。
	if (json.length >= 19) {
		console.log($('#art-page').val());
		//$('#art-page').val(page + 1);
	} else if (json.length < 19){
		console.log("under 19");
		$('#add-button').remove();
	}
}


//シェア記事一覧を作成
var get_sharelists = function(json) {
	var random =Math.floor(Math.random()*3);
	$myList = $("<div class='grid-sizer'></div>");
	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	for ( var i = 0; i < json.length ; i++) {
		$myList = $("<div class='"+item[random][i] + " mosaic-block bar'>" +
						"<div class='mosaic-overlay'>"+
						 "<div id='link-body'><a href='../login/article.html?"+json[i].article_id+"'></a></div>" +
							"<div id='menu-block'>" +
								"<div id='menu2'>"+
									"<div class='remodal-bg'>"+

										"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" +
										"<img src='img/trash1.png' align='right'width='42'height='42'></img>" +
										"</a>"+

										 "<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json[i].article_id+");getUsingTags();return false;'>"+
										  "<img src='img/tag1.png'align='right' width='42'height='42'></img>" +
										  "</a>"+

									"</div>"+
								"</div>"+
							"</div>"+
						"</div>"+
						"<div class='mosaic-backdrop relative'>" +
							"<img src='http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg'width='100%'height='100%'alt='"+json[i].title+"'/>" +
							"<p class='absolute'>"+json[i].title+"<BR><a href='"+json[i].url+"' target='_blank'>"+json[i].url+"</a></p>" +
						"</div>"+
					"</div>");
		$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	}

	addViewNext(json);
	jQuery(function($){
		$('.bar').mosaic({
			animation	:	'slide'		//fade or slide
		});
    });
};
//タグ一覧を作成
var get_taglists = function(json) {
	//$.cookie('viewMode','2');$.cookie('tagLists',json[i].tag_body);

	var tagList = "";
	tagList = "<table>";
	for ( var i = 0; i < json.length; i++) {
		tagList +=

				"<tr align='left' valign='top'><td>"+
				"<h4><a href='index.html'onclick='javascript:$.cookie(\"viewMode\",\"2\");$.cookie(\"tagLists\",\""+json[i].tag_body+"\");'>"+json[i].tag_body + "</a></h4><br>" +
				"<input type='hidden' value='"+json[i].lastest+"' name='lastest"+i+"'>"+
				"</td><td><h4><a href='/' data-remodal-target='tagdeletemodal' onclick='javascript:getTag_id("+json[i].tag_id+");return false;'>削除</a></h4></td></tr>";

	}
	tagList += "</table>";
	document.getElementById('taglist').innerHTML = tagList;
};

//登録しているフレンドが入ったセレクトボックスを作成
var get_friends = function(json){
	$('.selectbox').empty();//削除しないとフレンドがセレクトボックス内にたまるから削除する
	var option = "";

	for(var i=0; i<json.length; i++){
		option=document.createElement('option');
		option.value = json[i].friend_user_id;
		//画面に表示されるテキスト部分は createTextNode で作って、optionの子要素として追加
		option.appendChild(document.createTextNode(''+json[i].nickname+''));
		//プルダウンに追加
		$('.selectbox').append(option).trigger('create');
		option="";
	}
	//ここでセレクトボックスを生成
	$('.selectbox').select2({width:"50%"}).trigger('create');


};

//更新日時が新しいタグが入ったセレクトボックスを作成
var get_using_tags = function(json){
	$('.tagselect').empty();//削除しないとタグがセレクトボックス内にたまるから削除する
	$('.tagselect').off();//イベントハンドラがたまるから解除する
	var option = "";
	var dummy = "";
	dummy = document.createElement('option');
	dummy.value = "";
	dummy.appendChild(document.createTextNode('タグを選択'));
	$(".tagselect").append(dummy).trigger('create');
	for(var i=0; i<json.length; i++){
		option=document.createElement('option');
		option.value = json[i].tag_body;
		//画面に表示されるテキスト部分は createTextNode で作って、optionの子要素として追加
		option.appendChild(document.createTextNode(''+json[i].tag_body+''));
		//プルダウンに追加
		$(".tagselect").append(option).trigger('create');

		option="";
	}
	//ここでセレクトボックスを生成
	$('.tagselect').select2({width:"50%",minimumResultsForSearch: Infinity}).trigger('create');
	//セレクトボックスを選択した際に発火するイベント
	$(".tagselect").change(function () {
			$('#tag-it').tagit('createTag',$(".tagselect").val());
	});

};
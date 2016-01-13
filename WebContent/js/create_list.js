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
	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	for ( var i = json.length - 1; i >= 0; i--) {
		var flag="";
		if(json[i].favflag==true){
			flag="★"+json[i].title;
		}else if(json[i].favflag==false){
			flag=json[i].title;
		}
		$myList = $("<div class='"+item[random][i] + " mosaic-block bar'>" +
						"<div class='mosaic-overlay'>"+

						 "<div id='link-body'><a href='../login/article.html?"+json[i].article_id+"'target='_blank'></a></div>" +
							"<div id='menu-block'>" +
								"<div id='menu'>"+
									"<ol>"+
										"<li><a href='"+json[i].url+"'>"+json[i].url+"</a></li>"+
									"</ol>"+
								"</div>"+
								"<div id='menu2'>"+
									"<div class='remodal-bg'>"+
									"<input type='hidden' value='"+json[i].favflag+"' id='grobalflag"+json[i].article_id+"'>"+
									"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" +
									"<img src='img/trash1.png' align='right'width='20'height='20'></img>" +
									"</a>"+


									"<a href='i'><img src='img/share1.png' align='right'width='20'height='20'></img></a>"+


									 "<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json[i].article_id+");return false;'>"+
									  "<img src='img/tag1.png'align='right' width='20'height='20'></img>" +
									  "</a>"+



									"<a href='#'onclick='javascript:addFavArticle("+json[i].article_id+");return false;'><img src='img/star1.png' align='right'width='20'height='20'></img></a>"+
									"</div>"+

								"</div>"+
							"</div>"+
						"</div>"+
						"<div class='mosaic-backdrop relative'>" +
							"<img src='http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg'width='100%'height='100%'alt='"+json[i].title+"'/>" +
							"<p class='absolute'id='favtitle"+json[i].article_id+"'>"+flag+"</p>" +
							"<input type='hidden' value='"+json[i].title+"' id='title"+json[i].article_id+"'>"+
						"</div>"+
					"</div>");
		$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	}
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
	tagList = "<table><tr><th><h2>タグリスト</h2></th></tr>";
	for ( var i = 0; i < json.length; i++) {
		tagList +=
				"<tr align='left' valign='top'><td>"+
				"<h2><a href='index.html'onclick='javascript:$.cookie(\"viewMode\",\"2\");$.cookie(\"tagLists\",\""+json[i].tag_body+"\");'>"+json[i].tag_body + "</a></h2><br>" +
				"<input type='hidden' value='"+json[i].lastest+"' name='lastest"+i+"'>"+
				"</td><td><h2><a href='/' data-remodal-target='tagdeletemodal' onclick='javascript:getTag_id("+json[i].tag_id+");return false;'>削除</a></h2></td></tr>";
		console.log(json[i].tag_body);
	}
	tagList += "</table>";
	document.getElementById('taglist').innerHTML = tagList;
};
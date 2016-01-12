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

//"<a href='../login/article.html?"+json[i].article_id+"'target='_blank'>

var get_mylists = function(json) {
	var random =Math.floor(Math.random()*3);
	$myList = $("<div class='grid-sizer'></div>");
	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	for ( var i = json.length - 1; i >= 0; i--) {
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
							"<p class='absolute'id='favtitle"+json[i].article_id+"'>"+json[i].title+"</p>" +
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
//"<a href='#'data-remodal-target='modal'onclick='javascript:getArticle_id("+json[i].article_id+");return false;'>" ;
var get_favlists = function(json) {
	var random =Math.floor(Math.random()*3);

	$favList = $("<div class='grid-sizer'></div>");
	$grid.prepend($favList).isotope('prepended', $favList).trigger('create');
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
								"<div class='remodal-bg'>"+
								"<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json[i].article_id+");return false;'>"+
								  "<img src='img/tag1.png'align='right' width='20'height='20'></img>" +
								  "</a>"+
								"</div>"+
								"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getTagArticle("+json[i].article_id+");return false;'><img src='img/star1.png' align='right'width='20'height='20'></img></a>"+
							"</div>"+
						"</div>"+
						"<a href='../login/article.html?"+json[i].article_id+"'target='_blank'><div class='mosaic-backdrop relative'>" +
							"<img src='http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg'width='100%'height='100%'alt='"+json[i].title+"'/>" +
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

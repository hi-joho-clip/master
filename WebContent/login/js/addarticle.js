
/**
 * すべての設定で保存できるボタン（記事表示ページからできるよ）
 */
function addFullArticle() {

	// getArticle_id(article_id);//html内の<div
	// UTF-8で取得する
	var jsonParam = 'url=' + encodeURIComponent($('#article-url').val());
	console.log(jsonParam);
	// Nonceを載せる
	jsonParam = jsonParam + "&nonce=" + $('#nonce').val() + '&mode=FULL';
	//console.log("log:" + encodeURIComponent($("#search-1e").html()));
	var URL = hostURL + "/addarticle";
	var update_article = function(json) {
		if(json.flag==0){
			toastr.error(json.state);
		}else {
			location.reload();
		}
	};
	getJSON(URL, jsonParam, update_article);
}


function addArticle() {

	// getArticle_id(article_id);//html内の<div
	// UTF-8で取得する
	var jsonParam = 'url=' + encodeURIComponent($("#search-2").val());
	console.log(jsonParam);
	// Nonceを載せる
	jsonParam = jsonParam + "&nonce=" + $('#nonce').val();
	console.log("log:" + encodeURIComponent($("#search-1e").html()));
	var URL = hostURL + "/addarticle";
	var update_article = function(json) {
		if(json.flag==0){
			toastr.error(json.state);
		}else{
			console.log("表示まできてる");
			// グリッドが存在すると記事表示なの
			if ($('div.grid-sizer')[0]) {
				getOneArticle(json.article_id, addArticle_call);
				toastr.success(json.state);
			} else {
				console.log('HERE!');
				toastr.success(json.state);
			}
		}
	};
	getJSON(URL, jsonParam, update_article);
}

function addArticle_call(json) {
	console.log('art_call' + getLocalStorage('Style'));
	if (getLocalStorage('Style')=== "tile") {
		$('#search-2').val('');
		$('div#' + json.article_id).remove();
		addTileView(json);

	} else if (getLocalStorage('Style') === "list") {
		console.log('list');
		$("#search-2").val('');
		$('ol#' + json.article_id).remove();
		addListView(json);
	}
}


/**
 * 記事から読み込み
 * @param json
 */
function addListView(json) {

	var flag="";
	if(json.favflag==true){
		flag="★"+json.title;
	}else if(json.favflag==false){
		flag=json.title;
	}
	$myList = $("<ol id='" + json.article_id+ "'>"+
			"<a class='art-title' href='#' onclick='javascript:locateArticleView("+json.article_id+");'><li class='first'>"+
				"<div class='dan'>"+
					thumView(json, "100px", "100px")+
				"</div>"+
				"<div class='mawari'>"+
					"<div class='dan2'id='favtitle"+json.article_id+"'>"+
					""+flag+""+
					"</div>"+
					"<div class='dan3'>"+
					"<a  class='art-title' href='"+json.url+"' target='_blank'>"+json.url+"</a>"+
					"</div>"+
					"<input type='hidden' value='"+json.title+"' id='title"+json.article_id+"'>"+
					"<input type='hidden' value='"+json.url+"' id='url"+json.article_id+"'>"+
				"</div>"+
				"<div class='firstbuttons'>"+


						"<div id='menu2'>"+
							"<div class='remodal-bg'>"+
							"<input type='hidden' value='"+json.favflag+"' id='grobalflag"+json.article_id+"'>"+

							"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json.article_id+");return false;'>" +
							"<img src='img/trash1.png' align='right'width='30'height='30'></img>" +
							"</a>"+

							"<a href='#' data-remodal-target='sharemodal'onclick='javascript:getFriends();getArticle_id("+json.article_id+");return false;'><img src='img/share1.png' align='right'width='30'height='30'></img></a>"+

							 "<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json.article_id+");getUsingTags();return false;'>"+
							  "<img src='img/tag1.png'align='right' width='30'height='30'></img>" +
							  "</a>"+

							"<a href='#'onclick='javascript:addFavArticle("+json.article_id+");return false;'><img src='img/star1.png' align='right'width='30'height='30'></img></a>"+

							"</div>"+
						"</div>"+

				"</div>"+
			"</li></a>"+
		"</ol>");
	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	if(json.favflag==true){
		//console.log('#favtitle'+json[i].article_id);
		$('#favtitle'+json.article_id).attr('style', 'color:#FF9800');
	}//#FFEB3B
}

/**
 * 記事から読み込み
 * @param json
 */
function addTileView(json) {

	var flag="";
	if(json.favflag==true){
		flag="★"+json.title;
	}else if(json.favflag==false){
		flag=json.title;
	}
	$myList = $("<div id='" + json.article_id+ "' class='"+item[3][1] + " mosaic-block bar' title='" + json.title +"'>" +
					"<div class='mosaic-overlay'>"+

					 "<div id='link-body'><a href='#' onclick='javascript:locateArticleView("+json.article_id+");'></a></div>" +
						"<div id='menu-block'>" +
							"<div id='menu2'>"+
								"<div class='remodal-bg'>"+
								"<input type='hidden' value='"+json.favflag+"' id='grobalflag"+json.article_id+"'>"+

								"<a href='#' data-remodal-target='deletemodal'onclick='javascript:getArticle_id("+json.article_id+");return false;'>" +
								"<img src='img/trash1.png' align='right'width='42'height='42'></img>" +
								"</a>"+

								"<a href='#' data-remodal-target='sharemodal'onclick='javascript:getFriends();getArticle_id("+json.article_id+");return false;'><img src='img/share1.png' align='right'width='42'height='42'></img></a>"+

								 "<a href='#' data-remodal-target='tagmodal' onclick='javascript:getTagArticle("+json.article_id+");getUsingTags();return false;'>"+
								  "<img src='img/tag1.png'align='right' width='42'height='42'></img>" +
								  "</a>"+

								"<a href='#'onclick='javascript:addFavArticle("+json.article_id+");return false;'><img src='img/star1.png' align='right'width='42'height='42'></img></a>"+

								"</div>"+
							"</div>"+
						"</div>"+
					"</div>"+
					"<div class='mosaic-backdrop relative'>" + thumView(json, "100%", "100%")+
						"<div class='absolute'id='favtitle"+json.article_id+"'>"+flag+"<BR><a class='art-title' href='"+json.url+"'target='_blank'>"+json.url+"</a></div>" +
						"<input type='hidden' value='"+json.title+"' id='title"+json.article_id+"'>"+
						"<input type='hidden' value='"+json.url+"' id='url"+json.article_id+"'>"+
					"</div>"+
				"</div>");

	// http://www.kk1up.jp/wp-content/uploads/2015/07/201507290001-17.jpg


	$grid.prepend($myList).isotope('prepended', $myList).trigger('create');
	if (json.favflag == true) {
		// console.log('#favtitle'+json[i].article_id);
		$('#favtitle' + json.article_id).attr('style', 'color:#FF9800');
	}


	jQuery(function($){
		$('.bar').mosaic({
			animation	:	'slide'		// fade or slide
		});
    });
}

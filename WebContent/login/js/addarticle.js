

function addArticle() {

	// getArticle_id(article_id);//html内の<div
	// UTF-8で取得する
	var jsonParam = 'url=' + encodeURIComponent($("#search-1").val());
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
				// 記事IDから記事を取得して、追加する
				var username = getLocalStorage('username');
				getArticle(username, json.article_id).then(function(json) {
					if ($('#mode').val() === "tile") {

					} else if ($('#mode').val() === "list") {
						addListView(json);
					}
				});
				toastr.success(json.state);
			} else {
				toastr.success(json.state);
			}
		}
	};
	getJSON(URL, jsonParam, update_article);
}


/**
 * 記事から読み込み
 * @param json
 */
function addListView(json) {
	$myList = $("<ol id='" + json.article_id+ "'>"+
			"<a href='../login/article.html?"+json.article_id+"'><li class='first'>"+
				"<div class='dan'>"+
					thumView(json, "100px", "100px")+
				"</div>"+
				"<div class='mawari'>"+
					"<div class='dan2'id='favtitle"+json.article_id+"'>"+
					""+flag+""+
					"</div>"+
					"<div class='dan3'>"+
					"<a href='"+json.url+"' target='_blank'>"+json.url+"</a>"+
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
}
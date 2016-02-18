
//タグを追加
function addTagArticle(tag_list) {
	var arr = [];
	arr[0]=$('#article_id').val();//0番目にArticle_idを入れる。
	for(var i=0;i<tag_list.length;i++){
		arr[i+1]=tag_list.item(i).value;//1番目からタグが入る
	}
	var tag_list = "nonce="+$('#nonce').val()+"&tag_list="+encodeURIComponent( JSON.parse(JSON.stringify(arr)));
	console.log("たぐ："+tag_list);
	var URL = hostURL + "/addtagarticle";
	var tagAdd=function(json){
		if(json.flag=="0"){
			toastr.error(json.state);
		}else{
			toastr.success(json.state);
		}
	};
	getJSON(URL, tag_list, tagAdd);
}
//タグを取得
function getTagArticle(article_id) {
	getArticle_id(article_id);//html内の<div id='article_id'>にhiddenでarticle_idを持たせる
	var jsonParam = "article_id="+article_id;
	var URL = hostURL + "/gettag";
	//記事に付与されているタグをテキストフィールドに表示
	var get_tag_article_lists = function(json){
		$('#tag-it').tagit('removeAll');
		for(var i=0;i<json.length;i++){
			$('#tag-it').tagit('createTag',json[i].tag_body);
		}
	};
	getJSON(URL, jsonParam, get_tag_article_lists);
}

/**
 * オフラインでのマイリスト（検索対応）
 *
 * @param page
 * @returns {Promise}
 */
function getOffTagMyList(page) {

	return new Promise(function(resolve, reject) {

		console.log("offline" + page);
		//var username = docCookies.getItem('username');
		var username = getLocalStorage('username');


		var title = getSessionStorage('tagLists');
		$('h1.title').html(title);

		var word = '';

		if ($('#searchMode').val() === "true") {
			// マイリストの検索をしているページを出す
			word = getSessionStorage('search');
		}
		getIDEArticleTagList(username, page, word, getSessionStorage('tagLists')).then(function(json) {
			// 純粋なリストが必要
			if (getLocalStorage('Style') === 'tile') {
				get_mylists(json);
				resolve();
			} else {
				get_mylists_list(json);
				resolve();
			}
			changeViewSwitti(get_mylists, get_mylists_list);
		})['catch'](function(error) {
			console.log(error);
			reject();
		});
	});

}

/**
 * オフラインでのタグ表示
 */
function getOffTagList() {
	get_taglists(JSON.parse(getLocalStorage('tagList')));

}
//タグ一覧表示
function getTagList() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/taglist";
	getTagCache();
	getJSON(URL, jsonParam, get_taglists);
}


//更新日時が新しいタグ20件を表示
function getUsingTags(){
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/getusingtags";
	getJSON(URL, jsonParam, get_using_tags);
}
//タグを削除
function deleteTag(){

	var jsonParam = "tag_id="+$('#tag_id').val()+"&nonce="+$('#nonce').val();;// 送りたいデータ
	console.log($('#tag_id').val());
	var URL = hostURL + "/deletetag";
	var setappend=function(json){
		if(json.flag=="0"){
			toastr.error(json.state);
		}else{
			toastr.success(json.state);
		}
	};
	getJSON(URL, jsonParam, setappend);

	document.getElementById('tagtable'+$('#tag_id').val()).remove();


}
//特定のタグの記事一覧（タイル表示）
function getTagArticleList(page) {


	/*if(tag_id==0 && tag_list!=0){//tag_idが0なら、タグが複数あるリストをもとに検索をして一覧表示させる処理
		for(var i=0;i<tag_list.length;i++){
			arr[i]=tag_list.item(i).value;//1番目からタグが入る
		}
		taglists = "tag_list="+JSON.parse(JSON.stringify(arr));
		title = JSON.parse(JSON.stringify(arr));

	}else if(tag_list==0 && tag_id!=0){//tag_listが0なら、特定のタグをクリックして一覧表示させる処理
		taglists = "tag_list="+tag_id;
		title = tag_id;
	}*/
	var taglists = "tag_list="+getSessionStorage('tagLists')+"&page="+page+"&article_id="+$('#lastid').val();
	var title = getSessionStorage('tagLists');
	var func = get_mylists;
	$('h1.title').html(title);
//	if (tileView()) {
//		func = get_mylists;
//		$('div.stylebutton')
//		.html('<button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button>');
//
//		$('div#themebutton').html('<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'+
//			'<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
//		//document.getElementById('title').innerHTML = '<h1 class="title">'+title+'</h1>'+'<div style="text-align: right;"><button id="stylechange" title="リスト表示切り替え"style="visibility:hidden"><img src="img/list.png" style="visibility:visible"></button></div>';
//	} else {
//		func = get_mylists_list;
//		$('div.stylebutton')
//		.html('<button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button>');
//
//		$('div#themebutton').html('<button id="themachange" title="テーマ切り替え"onclick="javascript:changeColor()" style="visibility: hidden;">'+
//		'<img src="img/thema.png"style="visibility: visible; width: 25px;"></button>');
	//changeViewSwitti( get_mylists, get_mylists_list);

		//document.getElementById('title').innerHTML =  '<h1 class="title">'+title+'</h1>'+'<div style="text-align: right;"><button id="stylechange" title="タイル表示切り替え"style="visibility:hidden"><img src="img/tile.png" style="visibility:visible"></button></div>';

	$('#viewmode').val('2');
	setSessionStorage('viewMode', '2');
	var URL = hostURL + "/tagarticlelist";
	getJSON(URL, taglists, changeViewSwitti( get_mylists, get_mylists_list));
}
//特定のタグの記事一覧（リスト表示）
//function getTagArticleList(tag_list,tag_id) {
//	if(tag_id==0 && tag_list!=0){//tag_idが0なら、タグが複数あるリストをもとに検索をして一覧表示させる処理
//		for(var i=0;i<tag_list.length;i++){
//			arr[i]=tag_list.item(i).value;//1番目からタグが入る
//		}
//		taglists = "tag_list="+JSON.parse(JSON.stringify(arr));
//		document.getElementById('title').innerHTML='<h1>'+JSON.parse(JSON.stringify(arr))+'</h1>'+'<input type="button" id="stylechange"value="AAA">';
//	}else if(tag_list==0 && tag_id!=0){//tag_listが0なら、特定のタグをクリックして一覧表示させる処理
//		taglists = "tag_list="+tag_id;
//		document.getElementById('title').innerHTML='<h1>'+tag_id+'</h1>';
//	}
//	$('#viewmode').val('2');
//	var URL = hostURL + "/tagarticlelist";
//	getJSON(URL, taglists, get_mylists_list);
//}


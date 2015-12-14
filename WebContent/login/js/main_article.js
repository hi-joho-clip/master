/*
 * article.html(記事表示)の最初に行う処理
 * */

$(document).ready(function(){
	getViewArticle(location.search.substring(1,location.search.length));
});

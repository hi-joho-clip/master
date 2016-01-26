

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
		toastr.success(json.state);
		}
	};
	getJSON(URL, jsonParam, update_article);
}
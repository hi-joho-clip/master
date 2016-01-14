/*
 * Index.htmlの最初に行う処理
 * */


$(document).ready(function() {
		$('.isotope').isotope({
			itemSelector : '.item',
			masonry : {
				columnWidth : 100
			}
		});
});

$(document).ready(function(){
    $('.selectbox').select2({
    	placeholder: "共有したいフレンドを選択"
    });

});

$(document).ready(function() {
	 $('#tag-it').tagit({fieldName:"tags[]"});
});

	var $grid;
	$(document).ready(function() {

		$grid = $('.grid').isotope({
			itemSelector : '.grid-item',

			percentPosition : true,
			masonry : {
				columnWidth : '.grid-sizer'
			},
		});



		switch ($.cookie("viewMode")) {
		case "0"://マイリスト画面を表示しているとき
			$('.grid').empty();
			getMyList();
			break;
		case "1"://お気に入り画面を表示しているとき
			$('.grid').empty();
			getFavList();
			break;
		case "2"://特定のタグ画面を表示しているとき
			$('.grid').empty();
			console.log($.cookie("tagLists"));
			getTagArticleList(0,$.cookie("tagLists"));
			break;
		}
	});


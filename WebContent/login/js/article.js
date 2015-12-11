	$(document).ready(function() {
		$('.isotope').isotope({
			itemSelector : '.item',
			masonry : {
				columnWidth : 100
			}
		});
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
		$('.grid').on('click', '.grid-item', function() {
			getViewArticle(this.id);
		});

		switch ($.cookie("viewMode")) {
		case "0"://マイリスト画面を表示しているとき
			$('.grid').empty();
			//getMyList();
			getMyList();
			break;
		case "1"://お気に入り画面を表示しているとき
			$('.grid').empty();
			getFavList();
			break;
		case "2"://タグ画面を表示しているとき
			$('.grid').empty();
			getTagList();
			break;

		}
	});


/*function date() {

	// 現在の年数オブジェクトを4桁で生成
	var time = new Date();
	var year = time.getFullYear();
	// 1900年まで表示
	for ( var i = year; i >= 1900; i--) {
		$('#year').append('<option value="' + i + '">' + i + '</option>');
	}
	// 1～9の数字を生成
	for ( var i = 1; i <= 9; i++) {
		$('#month').append('<option value="0' + i + '">' + i + '</option>');
	}
	// 10～12の数字を生成
	for ( var i = 10; i <= 12; i++) {
		$('#month').append('<option value="' + i + '">' + i + '</option>');
	}
	// 1～9の数字を生成
	for ( var i = 1; i <= 9; i++) {
		$('#day').append('<option value="0' + i + '">' + i + '</option>');
	}
	// 10～31の数字を生成
	for ( var i = 10; i <= 31; i++) {
		$('#day').append('<option value="' + i + '">' + i + '</option>');
	}
}*/

function date() {
	// 日付範囲決定
	function calcDays() {
		$('#day').empty();
		var y = $('#year').val();
		var m = $('#month').val();

		if (m == "" || y == "") { // 年か月が選択されていない時は31日まで表示
			var last = 31;
		} else if (m == 2
				&& ((y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0)))) {
			var last = 29; // うるう年判定
		} else {
			var last = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)[m - 1];
		}

		$('#day').append('<option value="">日</option>');
		for ( var i = 1; i <= last; i++) {
			if (d == i) { // 日がすでに選択されている場合はその値が選択された状態で表示
				if(i<= 9){
					$('#day')
					.append(
							'<option value="0' + i + '" selected>' + i
									+ '</option>');
				}else{
					$('#day')
					.append(
							'<option value="' + i + '" selected>' + i
									+ '</option>');
				}
			} else {
				if(i<=9){
					$('#day')
					.append('<option value="0' + i + '">' + i + '</option>');
				}else{
					$('#day')
					.append('<option value="' + i + '">' + i + '</option>');
				}
			}
		}
	}

	var d = 0;
	$(function() {
		// 1900年～2015年まで表示
		for ( var i = 2015; i >= 1900; i--) {
			$('#year').append('<option value="' + i + '">' + i + '</option>');
		}
		// 1～9の数字を生成
		for ( var i = 1; i <= 9; i++) {
			$('#month').append('<option value="0' + i + '">' + i + '</option>');
		}
		// 10～12の数字を生成
		for ( var i = 10; i <= 12; i++) {
			$('#month').append('<option value="' + i + '">' + i + '</option>');
		}
		// 1～9の数字を生成
		for ( var i = 1; i <= 9; i++) {
			$('#day').append('<option value="0' + i + '">' + i + '</option>');
		}
		// 10～31の数字を生成
		for ( var i = 10; i <= 31; i++) {
			$('#day').append('<option value="' + i + '">' + i + '</option>');
		}

		$('#day').change(function() {
			d = $(this).val();
		});
		// 年か月が変わるごとに日数を計算
		$('#year').change(calcDays);
		$('#month').change(calcDays);
	});
}
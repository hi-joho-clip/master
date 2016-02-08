// フレンド登録者一覧画面
function getFriendList() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendlist";
	getJSON(URL, jsonParam, createFriendList);
}
/**
 * オフラインでのフレンドリスト表示
 */
function getOffFriendList() {
	createFriendList(JSON.parse(getLocalStorage('friendList')));
}

function createFriendList(json) {

	//console.log(json);

	var date_obj;
	for ( var i = 0; i < json.length; i++) {
		date_obj = new Date(json[i].created);
		date_str = date_obj.getFullYear() + "年" + (date_obj.getMonth() + 1)
				+ '月' + date_obj.getDate() + '日';

		var arrayFriend = [];
		// リクエスト中の一覧
		if (json[i].status === 2 && getLocalStorage('online') === 'true') {
			// リクエスト申請したユーザ一覧
			$('#table-body')
					.append(
							'<tr class="first"><td><h8 class="request" id="friend'
									+ json[i].friend_user_id
									+ '" >'
									+ json[i].nickname
									+ '</h8></td><td><a href="#" data-remodal-target="delete_request" class="request art-title btn btn-primary" onclick="javascript:remodalCreate('
									+ json[i].friend_user_id
									+ ');">取消</td></tr>');

		} else if (json[i].status === 3) {
			// フレンドの場合
			arrayFriend.push(json[i]);
			$('#table-body')
					.append(
							'<tr class="first"><td><h8 id="friend"'
									+ json[i].friend_user_id
									+ '">'
									+ "<a class='art-title' href='index.html'onclick='"
									+ "javascript:setSessionStorage(\"friend\",\""
									+ json[i].nickname
									+ "\");setSessionStorage(\"viewMode\",\"3\");setSessionStorage(\"shareLists\",\""
									+ +json[i].friend_user_id
									+ "\");setSessionStorage(\"shareListId\",\"" + json[i].share_id + "\")'>"
									+ json[i].nickname
									+ '</h8></td><td><a href="#" data-remodal-target="delete_friend" class="friend art-title btn btn-danger" onclick="javascript:remodalCreate('
									+ json[i].friend_user_id
									+ ');">削除</td></tr>');
		}
	}

	//console.log( JSON.stringify(arrayFriend));
	if (isSettinOnLine() == true) {
		setLocalStorage('friendList', JSON.stringify(json));
	}
	$('#friend-table').datatable({
		pageSize : 50,
		sort : [ true, true ],
		filters : [ true, false ],
		sortKey : [ '申請' ],
		filterText : '名前検索... '
	});
};







function remodalCreate(friend_user_id) {

	$('#friend_user_id').val(friend_user_id);
	// $('#row').val($('#table-body tr').index(this));
}

// リクエストを削除後の画面
function deleteRequest(friend_user_id) {
	var jsonParam = "friend_user_id=" + friend_user_id + "&nonce="
			+ $('#nonce').val();// 送りたいデータ
	console.log(jsonParam);
	var URL = hostURL + "/deleterequest";
	var setappend = function(json) {

		console.log($(this).closest('#table-body tr').index());

		if (json.flag === 1) {
			$('#friend' + json.friend_user_id).parent().parent().empty();
			// console.log('id:' + '#friend' + json.friend_user_id);
			toastr.success(json.state);
		} else {
			toastr.error(json.state);
		}
	};
	getJSON(URL, jsonParam, setappend);
	// location.reload();
}

// フレンドを削除後の画面
function deleteFriend(friend_user_id, row) {

	var jsonParam = "friend_user_id=" + friend_user_id + "&nonce="
			+ $('#nonce').val();// 送りたいデータ
	var URL = hostURL + "/deletefriend";
	var setappend = function(json) {

		// $('#friend-table').datatable('refresh');
		if (json.flag === 1) {
			$('#friend' + json.friend_user_id).parent().parent().empty();
			toastr.success(json.state);
		} else {
			toastr.error(json.state);
		}

	};
	getJSON(URL, jsonParam, setappend);
}


// // フレンド申請があると、申請通知が来る
function notice() {
	var jsonParam = null;// 送りたいデータ
	var URL = hostURL + "/friendrequest";
	var setappend = function(json) {
		if (json.length > 0) {
			console.log("申請あるよ");
			$remodal = "<h4>フレンドボックス<img src='img/friendBOX.png'></h4>";
		} else {
			console.log("申請ないよ");
			$remodal = "<h4>フレンドボックス</h4>";
		}
		$("#notice").append($remodal).trigger("create");
	};
	getJSON(URL, jsonParam, setappend);
};

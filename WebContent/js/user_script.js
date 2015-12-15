

//新規登録後
function addUser(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/adduser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//パスワード再発行
function forgotPassword(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/adduser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//ユーザ削除
function deleteUser(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/deleteuser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}


//ユーザ情報の表示
function getUserList(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//ニックネーム変更画面
function NickName(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム" + json.nickname;
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}


//ニックネーム変更した後の画面
function ChangeNickName(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/updatenickname";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//メールアドレス変更画面
function MailAddress(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList="";
	var setappend = function(json) {
		userList = "メールアドレス"+ json.mailaddress;
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//メールアドレス変更した後の画面
function ChangeMailAddress(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/updatemailaddress";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//パスワード変更した後の画面
function ChangePassword(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/updatepassword";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}


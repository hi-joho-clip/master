
//新規登録後
function addUser(){

	var jsonParam = null;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/adduser";
	var userList="";
	var setappend = function(json) {
		userList = json.ErrorMessage +"<br>";
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
function getUserList(user_id){

	var jsonParam = "user_id="+user_id;// 送りたいデータ
	console.log(jsonParam);
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>" +"メールアドレス:"+json.mailaddress+"<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//ニックネーム変更画面
function NickName(user_id){

	var jsonParam = "user_id="+user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList="";
	var setappend = function(json) {
		userList = "ニックネーム:" + json.nickname + "<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}

//メールアドレス変更画面
function MailAddress(user_id){

	var jsonParam = "user_id="+user_id;// 送りたいデータ
	var URL = "http://localhost:8080/clipMaster/viewuser";
	var userList="";
	var setappend = function(json) {
		userList = "メールアドレス:"+ json.mailaddress + "<br>";
		document.getElementById('info').innerHTML=userList;
	};
	getJSON(URL, jsonParam, setappend);
}



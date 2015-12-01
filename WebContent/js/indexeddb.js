
function checkAvailable() {

	if (KageDB.isAvailable) {

		document.getElementById("friendList").innerHTML = "利用可能です。";

		// KAGEインスタンスでDB定義
		var tutorial = new KageDB({
			name : "tutorial",
			migration : {
				1 : function(ctx, next) {
					var db = ctx.db;
					var todo = db.createObjectStore("todo", {
						keyPath : "id",
						autoIncrement : true
					});
					todo.createIndex("name", "name", {
						unique : true
					});
					next();
				}
			}
		});

		// // DB 作成
		// tutorial.tx(["todo"], function (tx, todo) {
		// todo.add({ name: "プログラミング2", priority: "E" }, "readwrite", function
		// (key) {
		// console.log("done. key = " + key);
		// });
		// });

		tutorial.tx(["todo"], function (tx, todo) {
		    todo.index("name").fetch({ el: "プロ" }, function (values) {
		        console.log("values = " + JSON.stringify(values));
		        console.log("done.");
		    });
		});

//		// 書き込み
//		tutorial.tx([ "todo" ], "readwrite", function(tx, todo) {
//			todo.put({
//				name : "プログラミング4",
//				priority : "F"
//			}, function(key) {
//				console.log("done. key = " + key);
//			});
//		});

		// 書き込み
		tutorial.tx([ "todo" ], "readwrite", function(tx, todo) {
			todo.put({
				id : 115,
				name : "プログラミングwwww",
				priority : "D"
			}, function(key) {
				console.log("done. key = " + key);
			});
		});


	} else {
		document.getElementById("friendList").innerHTML = "利用できません。";
	}

}

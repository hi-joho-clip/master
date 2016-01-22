function importJS() {
    if (! new Array().push) return false;
        var scripts = new Array(

    		//"../js/test_ajax.js",
    		"../js/lib/kagedb.js",
    		"../js/browsersupport.js",
    		"../js/lib/operative.js",
    		"../js/lib/es6-promise.min.js",
    		"../js/json-xhr.js",
    		"../js/indexeddb.js",
    		"../js/lib/cookie.js",
    		"../js/lib/store.js",
    		"../js/toppage.js"
        );
        for (var i=0; i<scripts.length; i++) {
        document.write('<script type="text/javascript" src="' +scripts[i] +'"><\/script>');
        }
    }
importJS();

//function importJS() {
//    if (! new Array().push) return false;
//        var scripts = new Array(
//    		"js/jquery/jquery-2.1.4.min.js",
//    		"js/test_ajax.js",
//    		"js/lib/kagedb.js",
//    		"js/browsersupport.js",
//    		"js/lib/operative.js",
//    		"js/lib/es6-promise.min.js",
//    		"js/json-xhr.js",
//    		"js/indexeddb.js",
//    		"js/lib/cookie.js",
//    		"js/lib/store.js",
//    		"js/toppage.js"
//        );
//        for (var i=0; i<scripts.length; i++) {
//        document.write('<script type="text/javascript" src="' +scripts[i] +'"><\/script>');
//        }
//    }
//importJS();



//ひとつの記事にarticleはひとつ。ひとつの記事にarticle_imageの中身は複数存在する
var article_json = {
		"article": [
					{ "title":"テストの冒険1" , "body":"TESTが死んだ","url":"http://test1.com"}
				],
		"article_image": [
	        		{ "image":"image01(binary)","uri":"1" },
	        		{ "image":"image02(binary)","uri":"2" },
	        		{ "image":"image03(binary)","uri":"3" },
	        		{ "image":"image04(binary)","uri":"4" }
	        	]
	};

document.write("<B>title:</B>"+article_json.article[0].title+"<B>body:</B>"+article_json.article[0].body+"<B>url:</B>"+article_json.article[0].url+"<BR>");
document.write("<B>image:</B>"+article_json.article_image[0].image+"<B>uri:</B>"+article_json.article_image[0].uri+"<BR>");

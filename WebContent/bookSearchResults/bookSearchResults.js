function getSearchData () {

	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	//ページ数を取得
	page = parameter.split('/p')[1];

	//サーチワードを取得
	search = parameter.split('/p')[0];
	q = search.split('=')[1];
	searchWord = [];

	for(var i=0; i<q.split('+').length;i++){
		searchWord.push(q.split('+')[i])
	}
	console.log(searchWord.length)
	'use strict';
	var requestQuery = {
			"searchWordLength":searchWord.length,
			"searchWord":searchWord,
			"page":page
	};
	$.ajax({
		type : 'GET',
		url : '/booksManagementSystem/BookSearchResults',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {
			// DOM操作
			console.log("OK");
		}
	});
}
//ページ読み込み時
$(document).ready(function () {
	'use strict';
	// 初期表示用
	getSearchData();
});

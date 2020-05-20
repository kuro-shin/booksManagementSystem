function Registration(){
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	syainId = parameter.split('=')[1];

	var requestQuery = {
		"bookTitle" : $('#bookTitle').val(),
		"bookAuther" : $('#bookAuther').val(),
		"bookPublisher" : $('#bookPublisher').val(),
		"bookGenre" : $("#Genre option:selected").text(),
	}

	if(!$('#bookTitle').val()||!$('#bookAuther').val()||!$('#bookPublisher').val()){
		alert("正常に入力されていません")
	}else{
		'use strict';
		$.ajax({
			type : 'POST',
			url : '/booksManagementSystem/BookRegistrationServlet',
			dataType : 'json',
			data :requestQuery,
			success : function (json) {//正常にアップデートできた際
				alert("本を登録しました。")
				location.href='../index.html'
			},
			error: function (json) { //エラーが発生した際
				alert("エラーが発生しました。")
			}

		});
	}

}

function setGenreData(genre){
	console.log(genre)
	var genreList = []
	var genreTag = "ジャンル:<select name=\"genre\" id=\"Genre\">";
	for(var i=0;i<genre.length;i++){
		genreList.push(genre[i].genreName)
		genreTag += '<option value="'+genre[i].genreName+'">'+genre[i].genreName+'</option>';
	}
	genreTag += '</select><br>'
	return genreTag;
}

function writeDocuments(){
	var genre = getGenreData();

	var inputBox = '';
	inputBox += '<form>タイトル:<input type=\"text\" placeholder=\"タイトル\" id=\"bookTitle\" required><br>'
			 + '著者名:<input type=\"text\" placeholder=\"著者名\" id=\"bookAuther\" required><br>'
			 + '出版社:<input type=\"text\" placeholder=\"出版社\" id=\"bookPublisher\" required><br>'
	inputBox += setGenreData(genre);
	inputBox += '<input type=\"button\" value=\"書籍登録\" onclick=\"Registration()\"></form>'

	$('main').html(inputBox);
}


$(document).ready(function() {

	// ログインボタンを押したときのイベント
	//$('#js-login-button').click(login);
	writeDocuments();

});

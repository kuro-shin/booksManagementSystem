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
	inputBox += '<form>タイトル:<input type=\"text\" placeholder=\"タイトル\" id=\"bookTitle\"><br>'
			 + '著者名:<input type=\"text\" placeholder=\"著者名\" id=\"bookAuther\"><br>'
			 + '出版社:<input type=\"text\" placeholder=\"出版社\" id=\"bookPublisher\"><br>'
	inputBox += setGenreData(genre);
	inputBox += '<input type=\"button\" onclick=\"bookRegistration()\" value=\"書籍登録\"></form>'

	$('main').html(inputBox);
}


$(document).ready(function() {

	// ログインボタンを押したときのイベント
	//$('#js-login-button').click(login);
	writeDocuments();

});

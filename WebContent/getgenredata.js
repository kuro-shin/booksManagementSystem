var genreId;
var genreName;

function getGenreData() {
	var requestQuery = {};
	// サーバーからデータを取得する
	var genre;
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/booksManagementSystem/GetGenreServlet',
		data : requestQuery,
		async:false,
		success : function(json) {
			genre = json;
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
	return genre;
}
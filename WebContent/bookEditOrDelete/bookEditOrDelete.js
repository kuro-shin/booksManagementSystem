var session = getSessionInformation();

function setGenreData(genre,bookGenreName){
	console.log(genre)
	var genreList = []
	var genreTag = "ジャンル:<select name=\"genre\" id=\"Genre\">";
	for(var i=0;i<genre.length;i++){
		genreList.push(genre[i].genreName)
		if(genre[i].genreName==bookGenreName){
		genreTag += '<option value="'+genre[i].genreName+'" selected>'+genre[i].genreName+'</option>';
	}else{
		genreTag += '<option value="'+genre[i].genreName+'">'+genre[i].genreName+'</option>';
	}

	}
	genreTag += '</select><br>'
	return genreTag;
}

function editDisplay() {
	 'use strict';



			var book_id = location.search.substring(3);
			//セッション持ってくるようにするときは上の方

			var requestQuery = {
					book_id : book_id
				};
				$.ajax({
					type : 'GET',
					dataType : 'json',
					url : '/booksManagementSystem/DisplayBookServlet',
					data : requestQuery,
					async: false,
					success : function(json) {
						// サーバーとの通信に成功した時の処理
						console.log(json);
						for (var i = 0; i < json.length; i++) {
							var b = json[i];

//							console.log("jsonからもらったb.book_id:"+b.bookId);
//							console.log("jsonからもらったb.bookTitle:"+b.bookTitle);
//							console.log("jsonからもらったb.bookAuther:"+b.bookAuther);
//							console.log("jsonからもらったb.bookPublisher"+b.bookPublisher);
//							console.log("jsonからもらったb.bookGenre:"+b.bookGenre);

						if(session.employeeRole=='manager'){
							var element='<form class="form-signin"><div class="form-row"><div class="col-md-12 mb-3">タイトル<input class="form-control" type="text" id="bookTitle" value="'+b.bookTitle+'"></div></div>'
								+'<div class="form-row"><div class="col-md-12 mb-3">著者<input class="form-control" type="text" id="bookAuther" value="'+b.bookAuther+'"></div></div>'
								+'<div class="form-row"><div class="col-md-12 mb-3">出版社<input class="form-control" type="text" id="bookPublisher" value="'+b.bookPublisher+'"></div></div>'
								+'<div class="form-row"><div class="col-md-12 mb-3">ジャンル<input class="form-control" type="text" id="bookGenre" value="'+b.bookGenreName+'"></div></div>'
						}

						//TO DO ジャンルをDBから持ってきて選択タブで表示できるようにする



						element += '<button class="btn btn-lg btn-info" onclick="editBook(\''+b.bookId+'\')">書籍編集</button>'
						+'<button class="btn btn-lg btn-danger" onclick="deleteBook(\''+b.bookId+'\')">書籍削除</button></form>'
						$('#editOrDelete').append(element);
						}

		},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// サーバーとの通信に失敗した時の処理
						alert('displayするときにデータの通信に失敗しました');

						console.log("XMLHttpRequest : " + XMLHttpRequest.status);
						console.log("textStatus     : " + textStatus);
						console.log("errorThrown    : " + errorThrown.message);
						console.log(errorThrown)
					}
				});
}

function deleteBook(book_id){

	var requestQuery = {
			book_id : book_id
};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/DeleteBookServlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log("削除完了");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			//alert('deleteするときにデータの通信に失敗しました');

			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
			console.log(errorThrown)
		}
	});
	$('#userInformation').empty();
	$('#isUser').empty();
	$('#isManager').empty();
	$('#editBookTable').empty();
	$('#editOrDelete').empty();
	display();// 再表示
}


function editBook(book_id){

	var requestQuery = {
			book_id : book_id,
			bookTitle : $('#bookTitle').val(),
			bookAuther : $('#bookAuther').val(),
			bookPublisher : $('#bookPublisher').val(),
			bookGenre : $('#bookGenre').val()
			//bookGenre : $('option[selected]').val()

};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/EditBookServlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log("編集完了");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			//alert('deleteするときにデータの通信に失敗しました');

			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
			console.log(errorThrown)
		}
	});
	$('#userInformation').empty();
	$('#isUser').empty();
	$('#isManager').empty();
	$('#editBookTable').empty();
	$('#editOrDelete').empty();
	editDisplay();// 再表示
}



$(document).ready(function() {
	// 初期表示用
	//getSessionInfomation();
	//executeAjax();
	editDisplay();


});
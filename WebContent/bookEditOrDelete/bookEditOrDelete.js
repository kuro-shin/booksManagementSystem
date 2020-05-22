//var session = getSessionInfomation();
var session = new Object();
session.employeeId='0001';
session.employeeName='未来太郎';
session.employeeRole='manager';
//session.employeeId='0002';
//session.employeeName='現在次郎';
//session.employeeRole='user';


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

function display() {
	 'use strict';

			if(session.employeeId!=null){
				$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
				$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">借本一覧</button>');
			}
			if(session.employeeRole='manager'){
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">書籍登録</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">延滞者一覧</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">図書管理者登録</button>');
			}

			//var book_id = location.search.substring(3);
			//セッション持ってくるようにするときは上の方
			var book_id = 'BK000193';
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
						$('#editBookTable').append('<p>タイトル：<input type="text" id="bookTitle" value="'+b.bookTitle+'"></p>');
						$('#editBookTable').append('<p>著者：<input type="text" id="bookAuther" value="'+b.bookAuther+'"></p>');
						$('#editBookTable').append('<p>出版社：<input type="text" id="bookPublisher" value="'+b.bookPublisher+'"></p>');


						$('#editBookTable').append('<p>ジャンル：<input type="text" id="bookGenre" value="'+b.bookGenreName+'"></p>');
						//$('#editBookTable').append(setGenreData(genre,b.bookGenreName));//genre一覧をどこで持ってくるのか

						}

						//TO DO ジャンルをDBから持ってきて選択タブで表示できるようにする



						$('#editOrDelete').append('<button onclick="editBook(\''+b.bookId+'\')">書籍編集</button');
						$('#editOrDelete').append('<button onclick="deleteBook(\''+b.bookId+'\')">書籍削除</button');
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
	display();// 再表示
}



$(document).ready(function() {
	// 初期表示用
	//getSessionInfomation();
	//executeAjax();
	display();


});
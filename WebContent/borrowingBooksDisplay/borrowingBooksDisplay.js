var session = getSessionInformation();

function borrwingDisplay() {
	 'use strict';

			 var requestQuery = 1;
				$.ajax({
					type : 'GET',
					dataType : 'json',
					url : '/booksManagementSystem/DisplayBorrowingBooksServlet',
					data : requestQuery,
					async: false,
					success : function(json) {
						// サーバーとの通信に成功した時の処理
						for (var i = 0; i < json.length; i++) {
						var bb = json[i];


						$('#borrowingBooksBody').append('<tr id="bb_list'+(i+1)+'">');
						$('#bb_list'+(i+1)).append('<td class="align-middle title">'+bb.title+'</td><td class="align-middle">'+bb.author+'</td><td>'

								+bb.publisher+'</td><td class="align-middle">'+bb.return_due_date+'</td>'+

								'<td class="align-middle"><button class="btn btn-danger" onclick="returnBorrowingBook(\''+bb.borrowing_book_id+'\')">返却</button></td></tr>');
						}

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// サーバーとの通信に失敗した時の処理
						alert('checkDelinquentするときにデータの通信に失敗しました');

						console.log("XMLHttpRequest : " + XMLHttpRequest.status);
						console.log("textStatus     : " + textStatus);
						console.log("errorThrown    : " + errorThrown.message);
						console.log(errorThrown)
					}
				});
}

function returnBorrowingBook(id){
	 $('#borrowingBooksBody').empty();
	 $('#userInformation').empty();
	 $('#logout').empty();
	var requestQuery = {
	q : id
};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/ReturnBorrowingBookServlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log("返却完了");
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

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/ReturnBorrowingBookServlet2',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log("返却完了");
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

	display();// 再表示
	display2();
}

function checkDelinquent() {
	 'use strict';
	 var requestQuery = 1;
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/booksManagementSystem/CheckDelinquentSevlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力

			if(json=='Delinquent')
			$('#checkDelinquent').append('返却期限を過ぎている本があります。速やかに返却してください。');

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('checkDelinquentするときにデータの通信に失敗しました');

			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
			console.log(errorThrown)
		}
	});
}



$(document).ready(function() {
	// 初期表示用
	borrwingDisplay();

	checkDelinquent();


});
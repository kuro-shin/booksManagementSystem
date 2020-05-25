var session = getSessionInformation();

function isDeliquentYMD(return_due_date){
  var dt = new Date();
  var y = dt.getFullYear();
  var m = ("00" + (dt.getMonth()+1)).slice(-2);
  var d = ("00" + dt.getDate()).slice(-2);
  var today = y + m + d ;

  var result = "<p ";
  if(return_due_date<today){
	  result += "class=\"isisDeliquentYMD\">";
  }else{
	  result += "class=\"isSafeYMD\">";
  }
  result += return_due_date;
  result += "</p>";
  return result;
}


function display() {
	 'use strict';

			if(session.employeeId!=null){
				$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
				$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">借本一覧</button>');
			}
			if(session.employeeRole==='manager'){
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">書籍登録</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">延滞者一覧</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">図書管理者登録</button>');
			}

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
								//+bb.publisher+'</td><td>'+isDeliquentYMD(bb.return_due_date)+'</td>'+

								'<td class="align-middle"><button onclick="returnBorrowingBook(\''+bb.borrowing_book_id+'\')">返却</button></td></tr>');
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
	 $('#isUser').empty();
	 $('#isManager').empty();

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
	display();// 再表示
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
	display();
	checkDelinquent();


});
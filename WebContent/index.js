//var session = getSessionInfomation();
var session = new Object();
//session.employeeId='0001';
//session.employeeName='未来太郎';
//session.employeeRole='manager';
session.employeeId='0002';
session.employeeName='現在次郎';
session.employeeRole='user';

function display() {
	 'use strict';
	// if(session.employeeId!=null){
//				alert('ログインしていません');
//				location.href='./login.html';
		//	}else{
	 if(session.employeeId!=null){
	 			$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
	 			$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
	 			$('main').append('<input type="text" placeholder="書籍を検索"><button onclick="location.href=\'http://localhost:8080/booksManagementSystem/bookSearchResult.html\'>検索</button>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">借本一覧</button>');
		if(session.employeeRole='manager'){
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">書籍登録</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">延滞者一覧</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">図書管理者登録</button>');
			}
			}

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
			var result = $.isEmptyObject(json);
			console.log(result);
			if(!result)
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
	//getSessionInfomation();
	display();
	checkDelinquent();

});
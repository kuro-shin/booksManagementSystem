//var session = getSessionInfomation();

function executeAjax() {
	 'use strict';
	 var requestQuery = 1;
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/GetLoginInfoServlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
		//	if(json=="NEEDLOGIN"){
//				alert('ログインしていません');
//				location.href='./login.html';
		//	}else{
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">借本一覧</button>');

		//	if(ROLL=='manager'){
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">書籍登録</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">延滞者一覧</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">図書管理者登録</button>');
		//	}
		//	}

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
	//executeAjax();
	checkDelinquent();

});
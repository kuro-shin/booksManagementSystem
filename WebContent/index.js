var session = getSessionInformation();

//var session = new Object();
//session.employeeId='0001';
//session.employeeName='未来太郎';
//session.employeeRole='manager';
//session.employeeId='0002';
//session.employeeName='現在次郎';
//session.employeeRole='user';

function display() {
	 'use strict';
	// if(session.employeeId!=null){
//				location.href='./login.html';
		//	}else{session.employeeId
	 console.log('session.employeeId:'+session.employeeId);
	 console.log('session.employeeName:'+session.employeeName);
	 console.log('session.employeeRole:'+session.employeeRole);
	 if(session.employeeName!=null){
	 			$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
	 			$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
	 			$('#main').append('<input type="text" id="searchWords" placeholder="書籍を検索"><button id="passSearchResult">検索</button>');
				$('#isUser').append('<button onclick="location.href=\'./request/requestDetailDisplay.html\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button onclick="location.href=\'./borrowingBooksDisplay/borrowingBooksDisplay.html\'">借本一覧</button>');
		if(session.employeeRole=='manager'){
				$('#isManager').append('<button onclick="location.href=\'./bookregistration/bookregistration.html\'">書籍登録</button>');
				$('#isManager').append('<button onclick="location.href=\'./deliquentPersonDisplay/deliquentPersonDisplay.html\'">延滞者一覧</button>');
				$('#isManager').append('<button onclick="location.href=\'./newMemberRegistration/newMemberRegistration.html\'">図書管理者登録</button>');
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
			console.log(json);
			if(json=="Delinquent")
				$('main').append('<p>返却期限を過ぎている本があります。速やかに返却してください。</p>');

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


function passSearchResult() {

	// 入力フォーム䛾値を取得
	var rawInputVal = $('#searchWords').val();
	// 特殊文字をエンコード
	var encodeVal = encodeURIComponent(rawInputVal);
	// 遷移先URL
	encodeVal= encodeVal.replace('%20','+');
	encodeVal= encodeVal.replace('%E3%80%80','+');

	var url = 'bookSearchResults/bookSearchResults.html?q='+encodeVal+'/p1';
	// 画面遷移
	location.href=url;

}


$(document).ready(function() {

	session = getSessionInformation();
	display();
	checkDelinquent();
	 $('#passSearchResult').click(passSearchResult);

});

var session = getSessionInformation();

// var session = new Object();
// session.employeeId='0001';
// session.employeeName='未来太郎';
// session.employeeRole='manager';
// session.employeeId='0002';
// session.employeeName='現在次郎';
// session.employeeRole='user';

function display() {
	 'use strict';
	// if(session.employeeId!=null){
// location.href='./login.html';
		// }else{session.employeeId
	 console.log('session.employeeId:'+session.employeeId);
	 console.log('session.employeeName:'+session.employeeName);
	 console.log('session.employeeRole:'+session.employeeRole);
	 if(session.employeeName!=null){
	 			$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
	 			$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
	 			$('#main').append('<div class="input-group">'
	 				     +' <input type="text" id="searchWords" class="form-control" placeholder="書籍名　著者名　出版社名を入力">'
	 				     + '<div class="input-group-append">'
	 				     +   '<!-- <button type="button" id="searchButton" class="findButton btn btn-secondary" onclick="passSearchResult()">書籍検索</button> -->'
	 				     +   '<input type="button" style="width: 10vw;" id="searchButton" class="findButton btn btn-secondary" value="書籍検索" onclick="passSearchResult()"></div></div>');
				$('#isUser').append('<button class="btn btn-light navButton"  onclick="location.href=\'./request/requestDisplay.html?/p1\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button class="btn btn-light navButton"  onclick="location.href=\'./borrowingBooksDisplay/borrowingBooksDisplay.html\'">借本一覧</button>');
		if(session.employeeRole=='manager'){
				$('#isManager').append('<button class="btn btn-light navButton"  onclick="location.href=\'./bookregistration/bookregistration.html\'">書籍登録</button>');
				$('#isManager').append('<button class="btn btn-light navButton" onclick="location.href=\'./deliquentPersonDisplay/deliquentPersonDisplay.html\'">延滞者一覧</button>');
				$('#isManager').append('<button class="btn btn-light navButton"  onclick="location.href=\'./newMemberRegistration/newMemberRegistration.html\'">図書管理者登録</button>');
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

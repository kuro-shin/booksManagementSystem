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

			if(session.employeeId!=null){
				$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
				$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
				$('#isUser').append('<button onclick="location.href=\'../index.html\'">TOPページへ</button>');
				$('#isUser').append('<a href="../request/requestDisplay.html?/p1"><img src="images/46090.jpg" border="0">リクエスト一覧・申請</a>');
				$('#isUser').append('<a href="../borrowingBooksDisplay/borrowingBooksDisplay.html"><img src="imag/8905.png" border="0">借本一覧</a>');

			}
			if(session.employeeRole='manager'){
				$('#isManager').append('<a href="../bookregistration/bookregistration.html"><img src="images/3291.png" border="0">書籍登録</a>');
				$('#isManager').append('<a href="../deliquentPersonDisplay/deliquentPersonDisplay.html"><img src="images/45896.png" border="0">延滞者一覧</a>');
				$('#isManager').append('<a href="../booksManagementSystem/newBookRegisration.html"><img src="images/25559.png" border="0">図書管理者登録</a>');
			}

}


$(document).ready(function() {
	// 初期表示用
	//getSessionInfomation();
	//executeAjax();
	display();

});
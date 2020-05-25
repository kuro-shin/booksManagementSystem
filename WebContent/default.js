//var session = getSessionInfomation();
var session = new Object();
//session.employeeId='0001';
//session.employeeName='未来太郎';
//session.employeeRole='manager';
session.employeeId='0002';
session.employeeName='現在次郎';
session.employeeRole='manager';

function display() {
	 'use strict';

			if(session.employeeId!=null){
				$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
				$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');

				Element = "<table class=\"table\" style=\"margin-bottom: 0;\"><tr>";
				Element += '<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../request/requestDisplay.html\'">リクエスト一覧・申請</button></td></tr>'
					+'<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../borrowingBooksDisplay/borrowingBooksDisplay.html\'">借本一覧</button></td></tr></table>'
				$('#isUser').html(Element);

			}
			if(session.employeeRole='manager'){
				Element = "";
				Element = "<table class=\"table\"><tr>";
				Element+= '<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../newBookRegisrationnewBookRegisration.html\'">書籍登録</button></td></tr>'
					+'<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../deliquentPersonDisplay/deliquentPersonDisplay.html\'">延滞者一覧</button></td></tr>'
					+'<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../newMemberRegistration/newMemberRegistration.html\'">図書管理者登録</button></td></tr></table>'

				$('#isManager').html(Element)
			}

}


$(document).ready(function() {
	// 初期表示用
	//getSessionInfomation();
	//executeAjax();
	display();

});
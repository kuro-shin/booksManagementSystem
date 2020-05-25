var session = getSessionInformation();
function logout() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
			loginRequest:"logout"
	};
	// サーバーからデータを取得する
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/booksManagementSystem/GetLoginInfoServlet',
		data : requestQuery,
		success : function(json) {
			alert("ログアウトしました");
			location.href="../login.html"

		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

function display() {
	 'use strict';

			if(session.employeeId!=null){
				$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
				$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
				$('#logout').append('<button id="logoutButton" class="btn btn-primary btn-block">ログアウト</button>');

				Element = "<table class=\"table\" style=\"margin-bottom: 0;\"><tr>";
				Element += '<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../request/requestDisplay.html?/p1\'">リクエスト一覧・申請</button></td></tr>'
					+'<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../borrowingBooksDisplay/borrowingBooksDisplay.html\'">借本一覧</button></td></tr></table>'
				$('#isUser').html(Element);

			}
			if(session.employeeRole=='manager'){
				Element = "";
				Element = "<table class=\"table\"><tr>";
				Element+= '<tr><td><button class="btn btn-light navButton" onclick="location.href=\'../bookregistration/bookregistration.html\'">書籍登録</button></td></tr>'
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
	$('#logoutButton').click(logout);

});
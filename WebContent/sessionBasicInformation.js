function getSessionInfomation() {
	 'use strict';
	 var requestQuery = 1;
	 var session = new Object();
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/booksManagementSystem/GetSessionInformationServlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			if(json.employeeId=null){
				alert('session情報が入っていません');
			}else{
			session.employeeId=json.employeeId;
			session.employeeName=json.employeeName;
			session.employeeRole=json.employeeRole;
//			console.log(USER);
//			console.log(ROLE);
//			console.log(NAME);
			return session;
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('Session情報を取得するときにデータの通信に失敗しました');
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
			console.log(errorThrown)
		}
	});
}
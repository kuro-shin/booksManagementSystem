function getSessionInformation() {
	 'use strict';
	 var requestQuery = 1;
	 var session = new Object();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/GetLoginInfoServlet',
		data : requestQuery,
		async: false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			session.employeeId=json.employeeId;
			session.employeeName=json.employeeName;
			session.employeeRole=json.employeeRole;
//			if(json.employeeId==null){
//				//alert('Session情報が入っていません');
//				//location.href='./login.html';
//			}
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
	return session;
}

//session情報は、以下を自分の使うjsの一番上に書けば取得できます

//var session = getSessionInfomation();

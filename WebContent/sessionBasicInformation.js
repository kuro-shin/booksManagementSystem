function getSessionInfomation() {
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
			USER = json.user;
			ROLL = json.roll;
			NAME = json.name;
			console.log(USER);
			console.log(ROLL);
			console.log(NAME);
			return (USER,ROLL,NAME);
			if(json.employeeId=null){
				alert('Session情報が入っていません');
			}else{
			session.employeeId='0001';
			session.employeeName='未来太郎';
			session.employeeRole='manager';
//			session.employeeId=json.employeeId;
//			session.employeeName=json.employeeName;
//			session.employeeRole=json.employeeRole;
//			console.log(session.employeeId);
//			console.log(json.employeeName);
//			console.log(json.employeeRole);
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

//session情報は、以下を自分の使うjsの一番上に書けば取得できます

//var session = getSessionInfomation();

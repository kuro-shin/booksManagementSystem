var USER;
var ROLL;
var NAME;

function getSessionInfomation() {
	 'use strict';
	 var requestQuery = 1;
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/booksManagementSystem/GetSessionInformationServlet',
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
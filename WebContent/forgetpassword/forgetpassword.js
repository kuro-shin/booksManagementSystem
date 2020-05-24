empId = "";
empName = "";
function changePassword(){
	if($('#newPasswordConfirm').val()===$('#newPassword').val()){
		if($('#newPassword').val().length>=8&&$('#newPassword').val().length<=18){
			var requestQuery = {
					newPassword : $('#newPasswordConfirm').val(),
					newPasswordConfirm :$('#newPassword').val(),
					authCode : $('#authCode').val()
			};
			$.ajax({
				type : 'POST',
				dataType:'json',
				url : '/booksManagementSystem/ChangePasswordServlet',
				data : requestQuery,
				async:false,
				success : function(json) {
					if(json==="CHANGED"){
						alert("パスワードを変更しました。")
						location.href='/booksManagementSystem/login.html';
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
		}else{
			alert("パスワードは8文字以上で入力してください。")
		}
	}else{
		alert("パスワードが一致しません")
	}

}
//function sendMail(){
//	var requestQuery = {};
//	$.ajax({
//		type : 'GET',
//		dataType:'json',
//		url : '/booksManagementSystem/CreateMail',
//		data : requestQuery,
//		async:false,
//		success : function(json) {
//			console.log(json)
//		},
//		error:function(XMLHttpRequest, textStatus, errorThrown){
//			// サーバーとの通信に失敗した時の処理
//			alert('データの通信に失敗しました');
//			console.log(errorThrown)
//		}
//	});
//}
function reSendAuthCode(){
	var requestQuery = {
			employeeId : empId
			,employeeName:empName
		};
		// サーバーからデータを取得する
		var genre;
		$.ajax({
			type : 'POST',
			dataType:'json',
			url : '/booksManagementSystem/SendAuthCode',
			data : requestQuery,
//			async:false,
			success : function(json) {
				console.log(json)
				if(json.result==="OK"){
					console.log("OK");
					Element = '<form class="form-signin"><div class="form-row"><div class="col-md-12 mb-3">';
					Element += '<label>認証コード:</label><input type=\"text\" id=\"authCode\" class="form-control"></div></div>'
						+ '<div class="form-row"><div class="col-md-12 mb-3"><label>新しいパスワード:</label><input type=\"password\" id=\"newPassword\" class="form-control"></div></div>'
						+ '<div class="form-row"><div class="col-md-12 mb-3"><label>新しいパスワード(確認):</label><input type=\"password\" id=\"newPasswordConfirm\"></div></div>'

						+ '<input type=\"button\" id=\"changePassworde\" value=\"パスワード変更\" onclick=\"changePassword()\" class="btn btn-lg btn-primary btn-block">'
						+ '<input type=\"button\" id=\"sendAuthCode\" value=\"認証コード再送信\" onclick=\"reSendAuthCode()\" class="btn btn-lg btn-primary btn-block">'
						+ '<input type=\"button\" id=\"cancel\" value=\"キャンセル\" onclick=\"location.reload()\" class="btn btn-lg btn-primary btn-block"></form>'

				$('#userInput').html(Element)
				}else{
					alert("社員IDか氏名が違います。")
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				// サーバーとの通信に失敗した時の処理
				alert('データの通信に失敗しました');
				console.log(errorThrown)
			}
		});
}
function sendAuthCode(){
		empId = $('#employeeId').val();
		empName = $('#employeeName').val();
		var requestQuery = {
			employeeId : $('#employeeId').val()
			,employeeName:$('#employeeName').val()
		};
		// サーバーからデータを取得する
		var genre;
		$.ajax({
			type : 'POST',
			dataType:'json',
			url : '/booksManagementSystem/SendAuthCode',
			data : requestQuery,
//			async:false,
			success : function(json) {
				console.log(json)
				if(json.result==="OK"){
					console.log("OK");
					Element = '<form class="form-signin"><div class="form-row"><div class="col-md-12 mb-3">';
					Element += '<label>認証コード:</label><input type=\"text\" id=\"authCode\" class="form-control"></div></div>'
						+ '<div class="form-row"><div class="col-md-12 mb-3"><label>新しいパスワード:</label><input type=\"password\" id=\"newPassword\" class="form-control"></div></div>'
						+ '<div class="form-row"><div class="col-md-12 mb-3"><label>新しいパスワード(確認):</label><input type=\"password\" id=\"newPasswordConfirm\" class="form-control"></div></div>'
						+ '<input type=\"button\" id=\"changePassworde\" value=\"パスワード変更\" onclick=\"changePassword()\" class="btn btn-lg btn-primary btn-block">'
						+ '<input type=\"button\" id=\"sendAuthCode\" value=\"認証コード再送信\" onclick=\"reSendAuthCode()\" class="btn btn-lg btn-primary btn-block">'
						+ '<input type=\"button\" id=\"cancel\" value=\"キャンセル\" onclick=\"location.reload()\" class="btn btn-lg btn-primary btn-block"></form>'


				$('#userInput').html(Element)
				}else{
					alert("社員IDか氏名が違います。")
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				// サーバーとの通信に失敗した時の処理
				alert('データの通信に失敗しました');
				console.log(errorThrown)
			}
		});

}

function displayInput(){
	Element = '<form class="form-signin"><div class="form-row"><div class="col-md-12 mb-3">';
	Element += '<label>社員ID:</label><input type=\"text\" id=\"employeeId\" class="form-control"></div></div>'
			+ '<div class="form-row"><div class="col-md-12 mb-3"><label>名前:</label><input type=\"text\" id=\"employeeName\" class="form-control"></div></div>'
			+ '<input type=\"button\" id=\"sendAuthCode\" value=\"認証コード送信\" onclick=\"sendAuthCode()\"><br>'
			+ '<input type=\"button\" id=\"cancel\" value=\"キャンセル\" onclick=\"location.href=\'/booksManagementSystem/login.html\'\"></form>'

	$('#userInput').append(Element)
}
//ページ読み込み時
$(document).ready(function () {
	'use strict';
	// 初期表示用
	displayInput();
});
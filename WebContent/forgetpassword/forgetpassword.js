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
					Element = '';
					Element += '<p>認証コード:<input type=\"text\" id=\"authCode\"></p>'
						+ '<p>新しいパスワード:<input type=\"password\" id=\"newPassword\"></p>'
						+ '<p>新しいパスワード(確認):<input type=\"password\" id=\"newPasswordConfirm\"></p>'

						+ '<input type=\"button\" id=\"changePassworde\" value=\"パスワード変更\" onclick=\"changePassword()\"><br>'
						+ '<input type=\"button\" id=\"sendAuthCode\" value=\"認証コード再送信\" onclick=\"reSendAuthCode()\"><br>'
						+ '<input type=\"button\" id=\"cancel\" value=\"キャンセル\" onclick=\"location.reload()\">'

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
					Element = '';
					Element += '<p>認証コード:<input type=\"text\" id=\"authCode\"></p>'
						+ '<p>新しいパスワード:<input type=\"password\" id=\"newPassword\"></p>'
						+ '<p>新しいパスワード(確認):<input type=\"password\" id=\"newPasswordConfirm\"></p>'
						+ '<input type=\"button\" id=\"changePassworde\" value=\"パスワード変更\" onclick=\"changePassword()\"><br>'
						+ '<input type=\"button\" id=\"sendAuthCode\" value=\"認証コード再送信\" onclick=\"reSendAuthCode()\"><br>'
						+ '<input type=\"button\" id=\"cancel\" value=\"キャンセル\" onclick=\"location.reload()\">'


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
	Element = '';
	Element += '<p>社員ID:<input type=\"text\" id=\"employeeId\"></p>'
			+ '<p>氏名:<input type=\"text\" id=\"employeeName\"></p>'
			+ '<input type=\"button\" id=\"sendAuthCode\" value=\"認証コード送信\" onclick=\"sendAuthCode()\"><br>'
			+ '<input type=\"button\" id=\"cancel\" value=\"キャンセル\" onclick=\"location.href=\'/booksManagementSystem/login.html\'\">'

	$('#userInput').append(Element)
}
//ページ読み込み時
$(document).ready(function () {
	'use strict';
	// 初期表示用
	displayInput();
});
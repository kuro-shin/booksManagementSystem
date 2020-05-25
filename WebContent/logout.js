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
$(document).ready(function() {
	// 初期表示用
	//getSessionInfomation();
	//executeAjax();
	$(logoutButton).click(logout);

});
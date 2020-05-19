
/* ログインファンクション */
function login() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		employeeId : $('#js-login-id').val()
		,password:$('#js-login-pass').val()
	};
	// サーバーからデータを取得する
	$.ajax({
		type : 'POST',
		dataType:'json',
		url : '/booksManagementSystem/LoginServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			if(json.result==="OK"){
				alert("ログイン成功")
			}else{
				alert("ユーザーIDかパスワードが間違っています");
			}
			/** localStorage01 実装ここまで part1 **/
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}
$(document).ready(function() {

	// ログインボタンを押したときのイベント
	$('#js-login-button').click(login);

});

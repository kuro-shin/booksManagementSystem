//var session = getSessionInfomation();
var role; // = session.employeeRole;
var name; // session.employeeName;
var employeeId; // = session.employeeId;
var page;
var display = function() {
	// サーバーからデータを取得する
	if (role == "manager") {
		employeeId = null;
	}
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	// ページ数を取得
	page = parameter.split('/p')[1];
	var requestQuery = {
		requestStatus : requestStatus,
		requestEmployeeId : employeeId,
		page : page
	};

	$
			.ajax({
				type : 'GET',
				dataType : 'json',
				url : '/booksManagementSystem/RequestDisplayServlet',
				data : requestQuery,
				async : false,
				success : function(json) {
					var a = '';
					if (page > 1) {
						a += '<a href="requestDisplay.html/p' + (page - 1)
								+ '">前へ</a>';
					}
					if (json.length == 21) {
						a += '<a href="requestDisplay.html/p' + (page + 1)
								+ '">次へ</a>';
					}
					$('#switch').append(a);
					var tableElemnt = '<table> <thead><tr><th>本の名前</th><th>申請者名</th><th>申請日</th><th>更新者名</th><th>更新日</th><th>ステータス</th><th>詳細</th></tr></thead><tbody>';
					for (var i = 0; i < json.length; i++) {
						var request = json[i];
						tableElemnt += '<tr> <td>' + request.requestTitle
								+ '</td><td>' + request.requestTitle + '</td>'
								+ '</td><td>' + request.requestApplicantName + '</td>'
								+ '</td><td>' + request.requestApplicantDate + '</td>'
								+ '</td><td>' + request.requestUpdaterName + '</td>'
								+ '</td><td>' + request.requestUpdateDate + '</td>'
								+ '</td><td>' + request.requestStatus + '</td>'
								//ここから
								+ '</td><td><button></button></td>'
								;
						count++;
					}

					$('#createbox').append(createElement);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
	for (var i = 1; i <= count; i++) {
		$('#edit' + i).click(editArea);
		$('#delete' + i).click(deleteEp);
	}
	$('#search').click(pull);
}
$(document).ready(function() {
	display();
});
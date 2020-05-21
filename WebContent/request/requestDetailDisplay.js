//var session = getSessionInfomation();
var role; // = session.employeeRole;
var name; // session.employeeName;
var employeeId; // = session.employeeId;
var requestStatus;
var display = function() {
	$('#requsetDetail').empty();
	// サーバーからデータを取得する
	if (role == "manager") {
	}

	var requestQuery = {
		requestId : requestId,
	};

	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/booksManagementSystem/RequestDisplayServlet',
		data : requestQuery,
		async : false,
		success : function(json) {
			var tableElemnt = '';
			var request = json[0];
			if (request.requestUpdaterName === null) {
				request.requestUpdaterName = "なし";
				request.requestUpdateDate = "未更新";
			}
			if (request.requestStatus == 1) {
				request.requestStatus = "承認";
			} else if (request.requestStatus == 2) {
				request.requestStatus = "却下";
			} else if (request.requestStatus == 3) {
				request.requestStatus = "登録済み";
			} else {
				request.requestStatus = "申請中";
			}
			tableElemnt += '<tr> <td>' + request.requestTitle + '</td><td>'
					+ request.requestApplicantName + '</td>' + '</td><td>'
					+ request.requestAuthor + '</td>' + '</td><td>'
					+ request.requestPublisher + '</td>' + '</td><td>'
					+ request.requestApplicantDate + '</td>' + '</td><td>'
					+ request.requestUpdaterName + '</td>' + '</td><td>'
					+ request.requestUpdateDate + '</td>' + '</td><td>'
					+ request.requestStatus + '</td>'
					+ '</td><td><button id="detail' + i + '" value="'
					+ request.requestId + '">詳細</button></td>';

			$('#requsetDetail').append(tableElemnt);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}
$(document).ready(function() {
	display();
})
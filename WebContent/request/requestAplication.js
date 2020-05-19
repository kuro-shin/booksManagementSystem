var session = getSessionInfomation();
var role = session.employeeRole;
var name = session.employeeName;
var employeeId = session.employeeId;
var requesutapplication = function() {
	var requesutEmployeeId = employeeId
	var requestTitle = $('#requestTitle').val();
	var requestAuthor = $('#requestAuthor').val();
	var requestPublisher = $('#requestPublisher').val();
	var requestUrl = $('#requestUrl').val();
	var requestQuery = {
			requesutEmployeeId :requesutEmployeeId,
			requestTitle :requestTitle,
			requestAuthor :requestAuthor,
			requestPublisher :requestPublisher,
			requestUrl :requestUrl,
	};

	$
			.ajax({
				type : 'POST',
				dataType : 'json',
				url : '/booksManagementSystem/RequestApplicationServlet',
				data : requestQuery,
				async : false,
				success : function(json) {
					// サーバーとの通信に成功した時の処理
					// 確認のために返却値を出力
					console.log('返却値', json);
					// 登録完了のアラート
					alert('リクエストを申請しました');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
}
$(document).ready(function() {
	$('#requesutapplicationButton').click(requesutapplication);
});/**
 *
 */
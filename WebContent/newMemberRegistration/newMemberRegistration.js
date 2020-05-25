
var session = getSessionInformation();
var role = session.employeeRole;
var name = session.employeeName;
var employeeId = session.employeeId;

console.log(role);

var memberRegist = function() {
	var registEmployeeId = $('#registEmployeeId').val();
	var registEmployeeName = $('#registEmployeeName').val();
	var registEmployeeNameKana = $('#registEmployeeNameKana').val();
	var registEmployeeEmail = $('#registEmployeeEmail').val();
	var registLoginPassword = $('#registLoginPassword').val();



	var requestQuery = {
			registEmployeeId :registEmployeeId,
			registEmployeeName :registEmployeeName,
			registEmployeeNameKana :registEmployeeNameKana,
			registEmployeeEmail :registEmployeeEmail,
			registLoginPassword :registLoginPassword,
			registLoginRole :role,
	};

	$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '/booksManagementSystem/NewMemberRegistrationServlet',
				data : requestQuery,
				async : false,
				success : function(json) {
					// サーバーとの通信に成功した時の処理
					// 登録完了のアラート
					alert('登録が完了しました');
					location.href = '../login.html';
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
}
$(document).ready(function() {
	$('#newMemberRegistrationButton').click(memberRegist);
});
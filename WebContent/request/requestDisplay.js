var session = getSessionInformation();
var role = session.employeeRole;
var name = session.employeeName;
var employeeId = session.employeeId;
var page;
var count;
var requestStatus;
var display = function() {
	$('#requestTable').empty();
	count = 0;
	// サーバーからデータを取得する
	if (role == "manager") {
		employeeId = null;
	}
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	// ページ数を取得
	var p = parameter.split('/p')[1];
	page = Number(p);

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
						a += '<a href="requestDisplay.html?/p' + (page - 1)
								+ '" id="back" class="nav-link>前へ</a>';
					}
					if (json.length == 21) {
						a += '<a href="requestDisplay.html?/p' + (page + 1)
								+ '" id="next">次へ</a>';
					}
					$('#switch').append(a);
					var tableElemnt = '<table class="table table-striped"> <thead class="thead-lignt"><tr><th>本の名前</th><th>申請者名</th><th>申請日</th><th>更新者名</th><th>更新日</th><th>ステータス</th><th>詳細</th></tr></thead><tbody>';
					for (var i = 0; i < json.length; i++) {
						var request = json[i];
						if(request.requestUpdaterName === null){
							request.requestUpdaterName = "なし";
							request.requestUpdateDate ="未更新";
						}
						if(request.requestStatus == 1){
							request.requestStatus = "承認";
						}else if(request.requestStatus == 2){
							request.requestStatus = "却下";
						}else if(request.requestStatus == 3){
							request.requestStatus = "登録済み";
						}else{
							request.requestStatus = "申請中";
						}
						if(request.requestUrl === null){
							tableElemnt += '<tr> <td>' + request.requestTitle
							+ '</td><td>' + request.requestApplicantName + '</td>'
							+ '<td>' + request.requestApplicantDate + '</td>'
							+ '<td>' + request.requestUpdaterName + '</td>'
							+ '<td>' + request.requestUpdateDate + '</td>'
							+ '<td>' + request.requestStatus + '</td>'
							+ '<td><button id="detail'+ i+'" value="'+request.requestId+'">詳細</button></td></tr>'
							;

						}else{
							tableElemnt += '<tr> <td><a href="'+request.requestUrl+'">' + request.requestTitle
							+ '</a></td><td>' + request.requestApplicantName + '</td>'
							+ '<td>' + request.requestApplicantDate + '</td>'
							+ '<td>' + request.requestUpdaterName + '</td>'
							+ '<td>' + request.requestUpdateDate + '</td>'
							+ '<td>' + request.requestStatus + '</td>'
							+ '<td><button id="detail'+ i+'" value="'+request.requestId+'">詳細</button></td></tr>'
							;

						}
						count++;
					}

					$('#requestTable').append(tableElemnt);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
	for (var i = 0; i <= count; i++) {
		$('#detail' + i).click(detail);
	}
}
var detail = function(){
	var id =$(this).attr("id");
	var requestId = $('#'+id+'').val();
	location.href = './requestDetailDisplay.html?q=' + requestId;
}
var search = function(){
	getStatus = document.form.requestStatus;
	var num = getStatus.selectedIndex;
	requestStatus = getStatus.options[num].value;
	display();
}
$(document).ready(function() {
	display();
	$('#requestStatusButton').click(search);
});
var session = getSessionInformation();
var role  = session.employeeRole;
var name = session.employeeName;
var employeeId  = session.employeeId;
var page = 1;
var requestStatus;
var reason;
var title;
var autor;
var publisher;
var display = function() {
	$('#requsetDetail').empty();
	$('#buttonBox').empty();
	// サーバーからデータを取得する

	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	var requestId = parameter.split('=')[1];
	var requestQuery = {
		requestId : requestId,
		page : page
	};

	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/booksManagementSystem/RequestDisplayServlet',
		data : requestQuery,
		async : false,
		success : function(json) {
			var tableElemnt = '';
			var boxElemnt = '';
			var request = json[0];
			if (request.requestUpdaterName === null) {
				request.requestUpdaterName = "なし";
				request.requestUpdateDate = "未更新";
			}
			if (request.requestStatus == 1) {
				boxElemnt += '<button id = "regist">登録</button>';
				request.requestStatus = "承認";
			} else if (request.requestStatus == 2) {
				boxElemnt = '<p>却下理由</p>' + '<p>'
				+ request.requestRejectReason + '</p>';
				request.requestStatus = "却下";
			} else if (request.requestStatus == 3) {
				request.requestStatus = "登録済み";
			} else {
				request.requestStatus = "申請中";
				if (role == "manager") {
					boxElemnt += '<textarea id = "reason" placeholder="却下理由を記入(却下の場合は必須)" required></textarea>'
						+ '<button id = "approval">承認</button>'
						+ '<button id = "rejection">却下</button>'
				}
			}
			$('#buttonBox').append(boxElemnt);
			title = request.requestTitle;
			autor = request.requestAuthor;
			publisher = request.requestPublisher;
			if(request.requestUrl === null){
				tableElemnt += '<tr> <td>' + request.requestTitle + '</td><td>'
				+ request.requestApplicantName + '</td>' + '<td>'
				+ request.requestAuthor + '</td>' + '<td>'
				+ request.requestPublisher + '</td>' + '<td>'
				+ request.requestApplicantDate + '</td>' + '<td>'
				+ request.requestUpdaterName + '</td>' + '<td>'
				+ request.requestUpdateDate + '</td>' + '<td>'
				+ request.requestStatus + '</td></tr>';

			}else{
				tableElemnt += '<tr> <td><a href="'+request.requestUrl+'">' + request.requestTitle + '</a></td><td>'
				+ request.requestApplicantName + '</td>' + '<td>'
				+ request.requestAuthor + '</td>' + '<td>'
				+ request.requestPublisher + '</td>' + '<td>'
				+ request.requestApplicantDate + '</td>' + '<td>'
				+ request.requestUpdaterName + '</td>' + '<td>'
				+ request.requestUpdateDate + '</td>' + '<td>'
				+ request.requestStatus + '</td></tr>';

			}


			$('#requsetDetail').append(tableElemnt);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
	$('#approval').click(approval);
	$('#rejection').click(rejection);
	$('#regist').click(regist);
}

var approval= function(){
	requestStatus = "1";
	update();
}
var rejection= function(){
	requestStatus = "2";
	reason = $('#reason').val();
	if(reason == ""){
		alert('却下理由を入力してください');
	}else{
		update();
	}
}
var regist= function(){
	requestStatus = "3";
	update();
	location.href="../bookregistration/bookregistration.html?title="+title+"&author="+autor+"&publisher="+publisher+"";
}
var update = function(){
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];
	var requestQuery = {
			requestId : parameter,
			requestUpdaterId: employeeId,
			requestStatus: requestStatus,
			requestRejectReason : reason
		};
	console.log('requestQuery', requestQuery);
	// サーバーにデータを送信する。
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/booksManagementSystem/RequestUpdateServlet2',
		data : requestQuery,
		async : false,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			alert('更新しました');
			$('#requsetDetail').empty();
			$('#buttonBox').empty();
			display();

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
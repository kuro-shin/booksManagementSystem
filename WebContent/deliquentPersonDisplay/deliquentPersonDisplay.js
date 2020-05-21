//var session = getSessionInfomation();
var session = new Object();
session.employeeId='0001';
session.employeeName='未来太郎';
session.employeeRole='manager';
//session.employeeId='0002';
//session.employeeName='現在次郎';
//session.employeeRole='user';

function display() {
	 'use strict';

			if(session.employeeId!=null){
				$('#userInformation').append('<p>社員ID:'+session.employeeId+'</p>');
				$('#userInformation').append('<p>社員名:'+session.employeeName+'</p>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">リクエスト一覧・申請</button>');
				$('#isUser').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">借本一覧</button>');
			}
			if(session.employeeRole='manager'){
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">書籍登録</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">延滞者一覧</button>');
				$('#isManager').append('<button onclick="location.href=\'http://localhost:8080/booksManagementSystem/newBookRegisration.html\'">図書管理者登録</button>');
			}

			 var requestQuery = 1;
				$.ajax({
					type : 'GET',
					dataType : 'json',
					url : '/booksManagementSystem/DeliquentPersonDisplayServlet',
					data : requestQuery,
					async: false,
					success : function(json) {
						// サーバーとの通信に成功した時の処理
						for (var i = 0; i < json.length; i++) {
						var dp = json[i];
						$('#deliquentPersonBody').append('<tr id="dp_list'+(i+1)+'">');

						$('#dp_list'+(i+1)).append('<td>'+dp.name+'</td><td>'+dp.return_due_date+'</td><td>'
								+dp.title+'</td><td>'+dp.author+'</td>'+
								'<td>'+dp.publisher+'</td></tr>');
						}


					//	$('#shain_list'+(i+1)).append('<input type="button" value="削除" id="delete'+(i+1)+'" onclick="deleteShain(\''+s.shain_id+'\')" >');



					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// サーバーとの通信に失敗した時の処理
						alert('未返却者一覧をdisplayするときにデータの通信に失敗しました');

						console.log("XMLHttpRequest : " + XMLHttpRequest.status);
						console.log("textStatus     : " + textStatus);
						console.log("errorThrown    : " + errorThrown.message);
						console.log(errorThrown)
					}
				});
}



$(document).ready(function() {
	// 初期表示用

	display();


});
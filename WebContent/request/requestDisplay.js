//var session = getSessionInfomation();
var role; //= session.employeeRole;
var name; // session.employeeName;
var employeeId; //= session.employeeId;
var display = function() {
	// サーバーからデータを取得する
	if (role == "manager") {
		employeeId = null;
	}
	var requestQuery = {
			requestStatus:requestStatus,
			requestEmployeeId:employeeId
	};

	$
			.ajax({
				type : 'GET',
				dataType : 'json',
				url : '/booksManagementSystem/RequestDisplayServlet',
				data : requestQuery,
				async : false,
				success : function(json) {
					//ここから
					var a ='<a id = ""></a>'
						if (role !== "manager") {
							var tableElemnt = '';
							for (var i = 0; i < json.length; i++) {
								var display = json[i];
								tableElemnt += '<tr> <td>' + display.syainId
										+ '</td><td>' + display.syainName
										+ '</td>';
								count++;
							}
							var editElemnt = '<button id="edit1" value="'
									+ userEmId + '">編集</button>';
							$('#display').append(tableElemnt);
							$('#editBoxMem').append(editElemnt);
						} else {

							// サーバーとの通信に成功した時の処理
							// 確認のために返却値を出力
							console.log('返却値', json);
							// 取得したデータを画面に表示する
							var tableElemnt = '';
							for (var i = 0; i < json.length; i++) {
								var display = json[i];
								tableElemnt += '<tr> <td>' + display.syainId
										+ '</td><td>' + display.syainName
										+ '</td><td><button id="edit' + (i + 1)
										+ '" value="' + display.syainId
										+ '">編集</button></td>'
										+ '<td><button id="delete' + (i + 1)
										+ '" value="' + display.syainId
										+ '">削除</button></td></tr>';
								count++;

							}
							var createElement = '<input type="text"placeholder="EMP????" id="newId"></input>'
									+ '<input type="text"placeholder="名前" id="newName"></input>'
									+ '<input type="text"placeholder="年齢" id="newAge"></input>'
									+ '<p><label><input type="radio"name="sex" value="男">男</label>'
									+ '<label><input type="radio"name="sex" value="女">女</label></p>'
									+ '<input type="text"placeholder="P?????" id="newImgId"></input>'
									+ '<input type="text"placeholder="〒〇〇〇-〇〇〇〇 住所" id="newAdress"></input>'
									+ '<input type="text"placeholder="部署Id" id="newDpId"></input>'
									+ '<button id="create">新規作成</button>';
							// HTMLに挿入
							$('#display').append(tableElemnt);
							$('#createbox').append(createElement);
						}
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

});
var session =  getSessionInformation();
function passSearchResult() {

	// 入力フォーム䛾値を取得
	var rawInputVal = $('#searchWords').val();
	// 特殊文字をエンコード
	var encodeVal = encodeURIComponent(rawInputVal);
	// 遷移先URL
	encodeVal= encodeVal.replace('%20','+');
	encodeVal= encodeVal.replace('%E3%80%80','+');

	var url = 'bookSearchResults.html?q='+encodeVal+'/p1';
	// 画面遷移
	location.href=url;

}
function Borrow(bookId){
	console.log(bookId)

	'use strict';
	var requestQuery = {
			"bookId":bookId
	};
	$.ajax({
		type : 'POST',
		url : '/booksManagementSystem/BorrowBookServlet',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {
			alert("貸出が完了しました");
			location.reload();
		}
	});

}
function getSearchData () {
	$('#userInput').empty();
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	//ページ数を取得
	var page = parameter.split('/p')[1];

	//サーチワードを取得
	search = parameter.split('/p')[0];
	q = search.split('=')[1];
	searchWord = [];

	for(var i=0; i<q.split('+').length;i++){
		searchWord.push(q.split('+')[i])
	}
	//console.log(searchWord.length)
	'use strict';
	var requestQuery = {
			"searchWordLength":searchWord.length,
			"searchWord":searchWord,
			"page":page
	};
	$.ajax({
		type : 'GET',
		url : '/booksManagementSystem/BookSearchResults',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {
			// DOM操作
			var bookLength = json.length;
			if(page!=1){
				$('#pageButtonBack').append('<a href=\'bookSearchResults.html?'+search+'/p'+(parseInt(page)-1)+'\' class="nav-link">前のページ</a>')
			}

			if(bookLength==21){
				$('#pageButtonNext').append('<a style=\"display:block; margin:0 0 0 auto;\" align=\"right\" href=\'bookSearchResults.html?'+search+'/p'+(parseInt(page)+1)+'\' class="nav-link">次のページ</a>')
				bookLength=20;

			}
			var Element = '<table id="booksData" class="table table-hover" ><thead class="thead-dark"><tr>'
				+'<th class="col-xs-1" id="Borrow">貸出</th>'
				+'<th class="col-xs-4" id="Title">書籍名</th>'
				+'<th class="col-xs-3" id="Auther">著者名</th>'
				+'<th class="col-xs-1" id="Publisher">出版社名</th>'
				+'<th class="col-xs-1" id="Genre">ジャンル</th>'
				+'<th class="col-xs-1" id="IsBorrowing">貸出状況</th>'
				+'<th class="col-xs-1" id="ReturnDueDate">返却予定日</th>'
				if(session.employeeRole=='manager'){
					Element+='<th class="col-xs-1" id="edit">編集</th></tr></thead>';
				}else{
					Element += '</tr></thead>';
				}
			for(var i=0;bookLength>i;i++){
				console.log(json[i])
				bookId = json[i].bookId
				bookTitle = json[i].bookTitle
//				if(bookTitle.length>30){
//					bookTitle = bookTitle.slice(0,50)+'......';
//				}
				bookAuther = json[i].bookAuther
				if(bookAuther.length>20){
					bookAuther = bookAuther.slice(0,20)+'......';
				}
				bookGenreName = json[i].bookGenreName
				bookIsBorrowing = json[i].bookIsBorrowing
				bookPublisher = json[i].bookPublisher
				bookReturnDueDate = json[i].returnDueDate
				if(bookIsBorrowing==0){
					Borrowing = "<font color=\"green\">配架中</font>"
					Element += '<tr>'
				}else if(bookIsBorrowing==1){
					Borrowing = "<font color=\"red\">貸出中</font>"
					Element += '<tr class="table-danger">'
				}

					if(bookIsBorrowing==0){
						//Element+='<td><input type="button" value="借りる" id="'+bookId+'" onclick=\"Borrow(this.id)\"></td>'
						Element+='<td class="align-middle"><button id="'+bookId+'" class="borrowButton btn btn-lg btn-success btn-block" onclick=\"Borrow(this.id)\">貸出</button></td>'
					}else{
						Element+='<td class="align-middle"></td>'
					}
						Element +='<td class="align-middle title">'+bookTitle+'</td>'
						if(bookAuther=="none"){
							bookAuther="";
						}
						Element += '<td class="align-middle">'+bookAuther+'</td>'
						+'<td class="align-middle">'+bookPublisher+'</td>'
						+'<td class="Genre align-middle">'+bookGenreName+'</td>'
						+'<td class="IsBorrowing align-middle">'+Borrowing+'</td>'

						if(bookIsBorrowing==1){
							Element += '<td class="ReturnDueDate align-middle">'+bookReturnDueDate+'</td>'
						}else{
							Element += '<td class="align-middle"></td>'
						}
						if(session.employeeRole=='manager'&&bookIsBorrowing!=1){
							Element+='<td class="align-middle edit"><button id=edit_'+bookId+' class="editButton btn btn-lg btn-danger btn-block" onclick=\"location.href=\'../bookEditOrDelete/bookEditOrDelete.html?q='+bookId+'\'">編集</button></td>';
						}else{
							Element+='<td class="align-middle edit"></td>';
						}
						Element+='</tr>'
			}
			Element += '</table>'
				$('#userInput').append(Element);

			console.log(json);
		}
	});
}
//ページ読み込み時
$(document).ready(function () {
	'use strict';
	// 初期表示用
	getSearchData();
});

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
				$('#pageButtonBack').append('<a href=\'bookSearchResults.html?q=/p'+(parseInt(page)-1)+'\' class="nav-link">前のページ</a>')
			}

			if(bookLength==21){
				$('#pageButtonNext').append('<a href=\'bookSearchResults.html?q=/p'+(parseInt(page)+1)+'\'>次のページ</a>')
				bookLength=20;

			}
			var Element = '<table id="booksData">'
				+'<th>書籍名</th>'
				+'<th>著者名</th>'
				+'<th>出版社名</th>'
				+'<th>ジャンル</th>'
				+'<th>貸出状況</th>'
				+'<th>貸出</th>'
				+'<th>返却予定日</th>'
			for(var i=0;bookLength>i;i++){
				console.log(json[i])
				bookId = json[i].bookId
				bookTitle = json[i].bookTitle
				bookAuther = json[i].bookAuther
				bookGenreName = json[i].bookGenreName
				bookIsBorrowing = json[i].bookIsBorrowing
				bookPublisher = json[i].bookPublisher
				bookReturnDueDate = json[i].returnDueDate
				if(bookIsBorrowing==0){
					Borrowing = "<font color=\"green\">配架中</font>"
				}else if(bookIsBorrowing==1){
					Borrowing = "<font color=\"red\">貸出中</font>"
				}
				Element += '<tr>'
						+'<td id="Title">'+bookTitle+'</td>'
						if(bookAuther=="none"){
							bookAuther="";
						}
						Element += '<td id="Auther">'+bookAuther+'</td>'
						+'<td id="Publisher">'+bookPublisher+'</td>'
						+'<td>'+bookGenreName+'</td>'
						+'<td>'+Borrowing+'</td>'
						if(bookIsBorrowing==0){
							Element+='<td><input type="button" value="借りる" id="'+bookId+'" onclick=\"Borrow(this.id)\"></td>'
						}else{
							Element+='<td></td>'
						}
						if(bookIsBorrowing==1){
							Element += '<td>'+bookReturnDueDate+'</td>'
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

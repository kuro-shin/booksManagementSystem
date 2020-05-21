package borrowingBooksDisplay;

public class BorrowingBook {

	private String title;
	private String publisher;
	private String author;
	private String return_due_date;
	private String borrowing_book_id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReturn_due_date() {
		return return_due_date;
	}

	public void setReturn_due_date(String return_due_date) {
		this.return_due_date = return_due_date;
	}

	public String getBorrowing_book_id() {
		return borrowing_book_id;
	}

	public void setBorrowing_book_id(String borrowing_book_id) {
		this.borrowing_book_id = borrowing_book_id;
	}

}

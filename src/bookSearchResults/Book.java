package bookSearchResults;

public class Book {
	private String bookId;
	private String bookTitle;
	private String bookPublisher;
	private String bookAuther;
	private String bookGenreName;
	private String bookIsBorrowing;
	private String returnDueDate;

	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getBookPublisher() {
		return bookPublisher;
	}
	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}
	public String getBookAuther() {
		return bookAuther;
	}
	public void setBookAuther(String bookAuther) {
		this.bookAuther = bookAuther;
	}
	public String getBookGenreName() {
		return bookGenreName;
	}
	public void setBookGenreName(String bookGenreName) {
		this.bookGenreName = bookGenreName;
	}
	public String getBookIsBorrowing() {
		return bookIsBorrowing;
	}
	public void setBookIsBorrowing(String bookIsBorrowing) {
		this.bookIsBorrowing = bookIsBorrowing;
	}
	public String getReturnDueDate() {
		return returnDueDate;
	}
	public void setReturnDueDate(String returnDueDate) {
		this.returnDueDate = returnDueDate;
	}

}

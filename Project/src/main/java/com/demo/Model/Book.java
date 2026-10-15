package com.demo.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Book {
	
	private String BookId;
	@NotBlank(message = "Book name is required")
	private String BookName;
	@Positive(message = "Price must be positive")
	private int Price;
	@NotBlank(message = "Author name is required")
	private String Author;
	private boolean available;
	
	public String getBookId() {
		return BookId;
	}
	public void setBookId(String bookId) {
		BookId = bookId;
	}
	public String getBookName() {
		return BookName;
	}
	public void setBookName(String bookName) {
		BookName = bookName;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	@Override
	public String toString() {
		return "Book [BookId=" + BookId + ", BookName=" + BookName + ", Price=" + Price + ", Author=" + Author
				+ ", available=" + available + "]";
	}
	public Book(String bookId, @NotBlank(message = "Book name is required") String bookName,
			@Positive(message = "Price must be positive") int price,
			@NotBlank(message = "Author name is required") String author, boolean available) {
		super();
		BookId = bookId;
		BookName = bookName;
		Price = price;
		Author = author;
		this.available = available;
	}
}
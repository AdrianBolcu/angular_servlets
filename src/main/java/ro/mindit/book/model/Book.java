package ro.mindit.book.model;


import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable{
	private int id;
	private String name;
	private String author;
	private String type;

	public Book() {
	}

	public Book(int id, String name, String author, String type) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.type = type;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String owner) {
		this.author = owner;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		return id == book.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", author='" + author + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}

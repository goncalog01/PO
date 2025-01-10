package woo;

/**
 * Class for books. A book is a product characterized by its title, author and
 * ISBN.
 */
public class Book extends Product {

    /** The book's title. */
    private String title;

    /** The book's author. */
    private String author;

    /** The book's ISBN. */
    private String isbn;

    /**
     * Constructor.
     * 
     * @param key           the book's identifier.
     * @param supplier      the book's supplier.
     * @param price         the book's initial price.
     * @param criticalLevel the book's stock critical level.
     * @param title         the book's title.
     * @param author        the book's author.
     * @param isbn          the book's ISBN.
     */
    public Book(String key, Supplier supplier, int price, int criticalLevel, String title, String author, String isbn) {
        super(key, supplier, price, criticalLevel, 3);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * Constructor.
     * 
     * @param key           the book's identifier.
     * @param supplier      the book's supplier.
     * @param price         the book's initial price.
     * @param criticalLevel the book's stock critical level.
     * @param stock         the book's initial stock
     * @param title         the book's title.
     * @param author        the book's author.
     * @param isbn          the book's ISBN.
     */
    public Book(String key, Supplier supplier, int price, int criticalLevel, int stock, String title, String author,
            String isbn) {
        super(key, supplier, price, criticalLevel, stock, 3);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * @return the book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the book's ISBN.
     */
    public String getISBN() {
        return isbn;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "BOOK" + super.toString() + getTitle() + "|" + getAuthor() + "|" + getISBN();
    }
}
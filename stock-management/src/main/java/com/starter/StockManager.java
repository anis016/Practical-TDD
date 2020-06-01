package com.starter;

import com.starter.pojo.Book;
import com.starter.service.ISBNDataService;
import com.starter.service.impl.ISBNDataServiceImpl;

/*
 * Locator Code (LC)
 * LC: Last 4 digit of ISBN + First Alphabet of Author surname + Number of words in the Title
 * Example:
 * Book: Of Mice and Men
 * Author: John Steinbeck
 * ISBN: 0140177396
 * LocatorCode: 7396J4
*/
public class StockManager {
    private ISBNDataService databaseService;
    private ISBNDataService webService;

    public void setDatabaseService(ISBNDataService databaseService) {
        this.databaseService = databaseService;
    }

    public void setWebService(ISBNDataService webService) {
        this.webService = webService;
    }

    public String getLocatorCode(String isbn) {
        Book book = databaseService.lookup(isbn);
        if (book == null) {
            book = webService.lookup(isbn);
        }

        StringBuilder locatorCode = new StringBuilder();
        locatorCode.append(book.getIsbn().substring(book.getIsbn().length() - 4));
        locatorCode.append(book.getAuthor().substring(0, 1));
        locatorCode.append(book.getTitle().split(" ").length);
        return locatorCode.toString();
    }
}

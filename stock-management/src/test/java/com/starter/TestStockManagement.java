package com.starter;

import com.starter.pojo.Book;
import com.starter.service.ISBNDataService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

public class TestStockManagement {
    final String isbn = "0140177396";
    static StockManager stockManager;
    ISBNDataService databaseService;
    ISBNDataService webService;

    @BeforeAll
    public static void beforeAll() {
        stockManager = new StockManager();
    }

    @BeforeEach
    public void beforeEach() {
        databaseService = mock(ISBNDataService.class);
        webService = mock(ISBNDataService.class);
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);
    }

    @Test
    public void testGetCorrectLocatorCodeFromDatabase() {
        when(databaseService.lookup(isbn)).
                thenReturn(new Book("Of Mice and Men", isbn, "John Steinbeck"));
        when(webService.lookup(isbn)).
                thenReturn(null);

        String actualLocatorCode = stockManager.getLocatorCode(isbn);
        String expectedLocatorCode = "7396J4";
        assertEquals(expectedLocatorCode, actualLocatorCode);
    }

    @Test
    public void testGetCorrectLocatorCodeFromWebService() {
        when(databaseService.lookup(isbn)).
                thenReturn(null);
        when(webService.lookup(isbn)).
                thenReturn(new Book("Of Mice and Men", isbn, "John Steinbeck"));

        String actualLocatorCode = stockManager.getLocatorCode(isbn);
        String expectedLocatorCode = "7396J4";
        assertEquals(expectedLocatorCode, actualLocatorCode);
    }

    @Test
    public void testDatabaseIsUseIfDataIsPresent() {
        when(databaseService.lookup(isbn)).
                thenReturn(new Book("test", isbn, "test"));

        stockManager.getLocatorCode(isbn);
        verify(databaseService, times(1)).lookup(isbn);
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void testWebServiceIsUsedIfDataIsNotPresentInDatabase() {
        when(databaseService.lookup(isbn)).
                thenReturn(null);
        when(webService.lookup(isbn)).
                thenReturn(new Book("test", isbn, "test"));

        stockManager.getLocatorCode(isbn);
        verify(databaseService, times(1)).lookup(isbn);

        // by default 1 time
        // verify(webService, times(1)).lookup(isbn);
        verify(webService).lookup(isbn);
    }
}

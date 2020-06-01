package com.starter.service;

import com.starter.pojo.Book;

public interface ISBNDataService {

    public Book lookup(String isbn);
}

package ru.geekbrains.lesson6.myproject;

import java.util.NoSuchElementException;

public class MenuItemNotFoundException extends NoSuchElementException {
    public MenuItemNotFoundException(String s) {
        super(s);
    }
}


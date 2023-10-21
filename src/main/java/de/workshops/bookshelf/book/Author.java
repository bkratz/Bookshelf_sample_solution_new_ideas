package de.workshops.bookshelf.book;

import lombok.Data;

@Data
class Author {
    private String firstname;
    private String middlename;
    private String lastname;

    @Override
    public String toString() {
        return "%s %s %s".formatted(firstname, middlename, lastname);
    }
}

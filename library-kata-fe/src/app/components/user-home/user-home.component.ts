import {Component, DestroyRef, effect, inject, OnInit} from '@angular/core';
import {BookAction, BooksComponent} from '../books/books.component';
import {BooksService} from '../../services/books.service';
import {Book} from '../../models/book';

@Component({
  selector: 'app-user-home',
  imports: [
    BooksComponent
  ],
  templateUrl: './user-home.component.html',
  styleUrl: './user-home.component.css',
  standalone: true
})
export class UserHomeComponent implements OnInit{

  booksService = inject(BooksService);

  constructor() {
    effect(() => {
      console.log('user service update',this.booksService.myBorrowedBooks());
    });
  }


  ngOnInit(): void {
    console.log('yey')
    this.booksService.updateBorrowedAndNotBorrowedLists();
  }

  get myBorrowedBooks() {
    return this.booksService.myBorrowedBooks;
  }

  get booksToBorrow() {
    return this.booksService.notBorrowedBooks;
  }

  onBorrow(book: Book) {
    this.booksService.borrowBook(book);
  }

  onReturn(book: Book) {
    this.booksService.returnBook(book);
  }

  protected readonly BookAction = BookAction;
}

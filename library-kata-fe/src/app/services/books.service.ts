import {DestroyRef, inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Book} from '../models/book';
import {AddBookData} from '../components/dialog-add-book/dialog-add-book.component';
import {forkJoin} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  http: HttpClient = inject(HttpClient);
  allBooks = signal<Book[]>([]);
  notBorrowedBooks = signal<Book[]>([]);
  myBorrowedBooks = signal<Book[]>([]);
  destroyRef = inject(DestroyRef);
  apiUrl = environment.baseUrl;

  getBooks() {
    const sub = this.http.get<Book[]>(`${this.apiUrl}/books`)
      .subscribe((resData: Book[]) => {
        this.allBooks.set(resData);
      });
    this.destroyRef.onDestroy(() => {
      sub.unsubscribe();
    });
    return sub;
  }

  addBook(data: AddBookData) {
    const sub = this.http.post<Book>(`${this.apiUrl}/books`, data)
      .subscribe(book => {
        this.getBooks();
      });
    this.destroyRef.onDestroy(() => {
      sub.unsubscribe();
    });
    return sub;
  }

  borrowBook(book: Book) {
    const sub = this.http.post<Book>(`${this.apiUrl}/books/${book.id}/borrow`, null)
      .subscribe(books => {
        this.updateBorrowedAndNotBorrowedLists();
      });
    this.destroyRef.onDestroy(() => {
      sub.unsubscribe();
    });
    return sub;
  }

  updateBorrowedAndNotBorrowedLists() {
    const sub = forkJoin([
      this.http.get<Book[]>(`${this.apiUrl}/books/borrowed`),
      this.http.get<Book[]>(`${this.apiUrl}/books/not-borrowed`)
    ]).subscribe(([borrowedBooks, notBorrowedBooks]) => {
        this.myBorrowedBooks.set(borrowedBooks);
        this.notBorrowedBooks.set(notBorrowedBooks);
      });
    this.destroyRef.onDestroy(() => {
      sub.unsubscribe();
    });
    return sub;
  }

  returnBook(book: Book) {
    const sub = this.http.post<Book>(`${this.apiUrl}/books/${book.id}/return`, null)
      .subscribe(books => {
        this.updateBorrowedAndNotBorrowedLists();
      });
    this.destroyRef.onDestroy(() => {
      sub.unsubscribe();
    })
    return sub;
  }
}

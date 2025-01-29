import {ChangeDetectionStrategy, Component, DestroyRef, inject, input, OnInit, output} from '@angular/core';
import {Book} from '../../models/book';
import {
  MatCell,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderRow,
  MatRow,
  MatTable,
  MatTableModule
} from '@angular/material/table';
import {MatIcon} from '@angular/material/icon';
import {MatButton, MatFabButton} from '@angular/material/button';
import {BooksService} from '../../services/books.service';

export enum BookAction {
  RETURN='RETURN', BORROW='BORROW'
}

@Component({
  selector: 'app-books',
  imports: [
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatTableModule,
    MatButton
  ],
  templateUrl: './books.component.html',
  styleUrl: './books.component.css',
  standalone: true,
})
export class BooksComponent implements OnInit{
  displayedColumns: string[] = [];

  bookAction = input<BookAction>();
  books = input.required<Book[]>();
  protected readonly BookAction = BookAction;
  onReturnBook = output<Book>();
  onBorrowBook = output<Book>();

  ngOnInit(): void {
    this.displayedColumns = ['title', 'description', 'author', 'publicationDate'];
    if (this.bookAction()) {
      this.displayedColumns.push('actions');
    }
  }

  onReturn(book: Book) {
    this.onReturnBook.emit(book);
  }

  onBorrow(book: Book) {
    this.onBorrowBook.emit(book);
  }
}

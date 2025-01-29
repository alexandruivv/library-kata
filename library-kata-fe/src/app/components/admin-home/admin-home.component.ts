import {Component, DestroyRef, inject, OnInit} from '@angular/core';
import {BooksService} from '../../services/books.service';
import {
  MatTableModule
} from '@angular/material/table';
import {MatIcon} from '@angular/material/icon';
import {MatFabButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {DialogAddBookComponent} from '../dialog-add-book/dialog-add-book.component';
import {BooksComponent} from '../books/books.component';

@Component({
  selector: 'app-admin-home',
  imports: [
    MatTableModule,
    MatIcon,
    MatFabButton,
    BooksComponent
  ],
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css',
  standalone: true
})
export class AdminHomeComponent implements OnInit{
  booksService = inject(BooksService);
  matDialog = inject(MatDialog);
  destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    this.booksService.getBooks();
  }

  get books() {
    return this.booksService.allBooks;
  }

  openAddDialog() {
    const dialogRef = this.matDialog.open(DialogAddBookComponent);

    const sub = dialogRef.afterClosed().subscribe(result => {
      this.booksService.addBook(result);
    })

    this.destroyRef.onDestroy(() => {
      sub.unsubscribe();
    })
  }
}

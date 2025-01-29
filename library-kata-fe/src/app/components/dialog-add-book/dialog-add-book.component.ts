import {ChangeDetectionStrategy, Component, inject, model, output} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef, MatDialogTitle
} from '@angular/material/dialog';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatInput} from '@angular/material/input';

export interface AddBookData {
  title: string;
  description: string;
  author: string;
  publicationDate: Date;
}

@Component({
  selector: 'app-dialog-add-book',
  imports: [
    MatFormField,
    MatDialogContent,
    MatDialogActions,
    MatButtonModule,
    MatFormFieldModule,
    MatInput,
    MatDialogTitle,
    FormsModule
  ],
  templateUrl: './dialog-add-book.component.html',
  styleUrl: './dialog-add-book.component.css',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogAddBookComponent {
  data: AddBookData = {
    title: '',
    description: '',
    author: '',
    publicationDate: new Date()
  }

  readonly dialogRef = inject(MatDialogRef<DialogAddBookComponent>);

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (!this.data.title || !this.data.description || !this.data.author || !this.data.publicationDate) {
      return;
    }
    this.dialogRef.close({
      ...this.data
    });
  }
}

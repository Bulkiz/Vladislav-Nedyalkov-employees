import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.css'
})

export class FileUploadComponent {
  selectedFile: File | null = null;
  errorMessage: string | null = null;
  employeeData: any[] = [];

  constructor(private http: HttpClient) {}

  onFileSelect(event: any) {
    const file = event.target.files[0];
    this.employeeData = [];
    if (file && file.type === 'text/csv') {
      this.selectedFile = file;
    } else {
      alert('Please upload a valid CSV file');
    }
  }

  uploadFile() {
    if (!this.selectedFile) {
      alert('No file selected');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.http.post<any>('http://localhost:8080/employee/upload', formData).subscribe({
      next: (response) => {
        if (response.pairProjectWorkDurations.length == 0) {
          alert('There are no pairs')
          this.employeeData = [];
        } else {
          this.employeeData = response.pairProjectWorkDurations;
        }
        this.errorMessage = null;
      },
      error: (error) => {
        alert(error.error.errorMessage);
        this.selectedFile = null;
        this.employeeData = [];
      },
    });
  }
}

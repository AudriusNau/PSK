import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Accommodation} from "../../entities/accommodation";
import {Travel} from "../../entities/travel";

@Component({
  selector: 'app-merge-travel-dialog',
  templateUrl: './merge-travel-dialog.component.html',
  styleUrls: ['./merge-travel-dialog.component.scss']
})
export class MergeTravelDialogComponent implements OnInit {
  selectedTravels: Array<Travel>

  constructor(
    private http: HttpClient,
    public dialogRef: MatDialogRef<MergeTravelDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Array<Travel>
  ) { }

  ngOnInit() {
    this.selectedTravels = this.data;
  }
  onCloseClick(): void {
    this.dialogRef.close();
  }


}

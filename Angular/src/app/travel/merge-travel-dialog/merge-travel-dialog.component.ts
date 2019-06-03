import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Accommodation} from "../../entities/accommodation";
import {Travel} from "../../entities/travel";
import {Url} from "../../http/url";
import {Router} from "@angular/router";

@Component({
  selector: 'app-merge-travel-dialog',
  templateUrl: './merge-travel-dialog.component.html',
  styleUrls: ['./merge-travel-dialog.component.scss']
})
export class MergeTravelDialogComponent implements OnInit {
  selectedTravels: Array<Travel>
  baseTravel: number
  private rules: string = String('Rules:\n 1 dates should be similar (+- 1 day)\n 2 destination should be the same\n');
  constructor(
    private http: HttpClient,
    public dialogRef: MatDialogRef<MergeTravelDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Array<Travel>,
    private router: Router
  ) { }

  ngOnInit() {
    this.selectedTravels = this.data;
  }
  onCloseClick(): void {
    this.dialogRef.close();
  }

  onMergeClick() {
    this.http.put(
      Url.get('travel/merge'),
      {baseTravelId: this.baseTravel,
        travels: this.selectedTravels.splice (this.baseTravel, 1).map(item => item.id)}
    ).subscribe(response => {
      if (response == null) {alert(' Does not match the rules\n' + this.rules); }
      else {this.onCloseClick(); }
    });
  }

}

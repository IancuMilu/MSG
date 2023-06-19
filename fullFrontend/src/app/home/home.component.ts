import { Component } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(private router: Router) {}

  goToLibrary() {
    this.router.navigate(['/library']);
  }

  goToStore() {
    this.router.navigate(['/store']);
  }
}

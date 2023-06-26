import { Component, inject } from '@angular/core';

import { AccountService } from './account/component/services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'summer-practice-2023-frontend';

  accountService = inject(AccountService);
  router = inject(Router)

  logout() {
    this.accountService.logout();
    this.goToPage("login");
  }

  toggleMenu() {
    const menu = document.querySelector('.menu-icon') as HTMLElement | null;
    const navbar = document.querySelector('.menu') as HTMLElement | null;

    navbar?.classList.toggle('active');
    menu?.classList.toggle('move'); 
  }

  goToPage(page: string) {
    this.toggleMenu();

    this.router.navigate([page]);
  }
}
import { Component, inject } from '@angular/core';

import { AccountService } from './account/component/services/account.service';
import { Router } from '@angular/router';

import { HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'summer-practice-2023-frontend';

  accountService = inject(AccountService);
  router = inject(Router)

  //Custom Scroll Bar
  @HostListener("window:scroll") onWindowScroll() {
    const scrollBar = document.getElementById('scroll-bar');

    var winScroll: number = document.body.scrollTop || document.documentElement.scrollTop;
    var height: number = document.documentElement.scrollHeight - document.documentElement.clientHeight;
    var scrolled: number = (winScroll / height) * 100;
    if (scrollBar !== null) {
      scrollBar.style.width = scrolled + '%';
    }
  };

  logout() {
    this.accountService.logout();
    localStorage.removeItem('cartGameIds');
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

  redirectToRegister(): void {
    this.router.navigate(['/register']);
  }

  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
}
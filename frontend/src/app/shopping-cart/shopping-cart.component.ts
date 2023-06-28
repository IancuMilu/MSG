import { Component, OnInit } from '@angular/core';
import { Game } from '../models/game.model';
import { GameService } from '../game/game.service';
import { Router } from '@angular/router';
import {NgForm} from "@angular/forms";
import {AccountService} from "../account/component/services/account.service";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  name: string = '';
  address: string = '';
  email: string = '';
  phone: string = '';


  cartGames: Game[] = [];

  constructor(
      private gameService: GameService,
      private router: Router,
      private accountService: AccountService
  ) {}

  ngOnInit() {
    this.getCartGames();
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form is valid. You can process the form.');

      const userId = this.accountService.getUserId();
      const gameIds = this.cartGames.map(game => game.id);

      const purchase = {
        userId: userId,
        gameIds: gameIds
      };

      // Make the request to create a purchase
      this.gameService.createPurchase(purchase).subscribe(
          response => {
            console.log('Purchase created successfully:', response);

            // Clear the cart after successful purchase
            this.cartGames = [];
            localStorage.removeItem('cartGameIds');

            // Redirect to the home page
            this.router.navigate(['/']);
          },
          error => {
            console.error('Error creating purchase:', error);
          }
      );
    } else {
      console.log('Form is invalid.');
    }
  }

  getCartGames() {
    const cartGameIds: number[] = JSON.parse(localStorage.getItem('cartGameIds') || '[]');
    cartGameIds.forEach((gameId: number) => {
      this.gameService.getGameById(gameId).subscribe({
        next: (game: Game) => {
          this.cartGames.push(game);
        },
        error: (error) => {
          console.error(error);
        }
      });
    });
  }

  getTotal() {
    return this.cartGames.reduce((total, game) => total + game.price, 0);
  }

  removeGame(index: number) {
    this.cartGames.splice(index, 1);
    localStorage.setItem('cartGameIds', JSON.stringify(this.cartGames.map(game => game.id)));
  }
}

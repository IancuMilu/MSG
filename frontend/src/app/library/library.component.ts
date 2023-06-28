import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Game } from '../models/game.model';
import { PurchasedGame } from '../models/purchased-game.model';
import { GameService } from '../game/game.service';
import { AccountService } from '../account/component/services/account.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {
  games: Game[] = [];
  private readonly TOKEN = 'token';
  purchasedGames: PurchasedGame[] = [];

  constructor(
    private router: Router,
    private gameService: GameService,
    private accountService: AccountService
  ) {}

  ngOnInit() {
    const token = localStorage.getItem(this.TOKEN);
    console.log('Token:', token);
    const userId = this.accountService.getUserId();
    if (userId) {
      console.log('Fetching games for user:', userId);
      this.getGamesForUser(userId);
    }
  }

  getGamesForUser(userId: string) {
    console.log('User ID in getGamesForUser:', userId);
    this.gameService.getGamesForUser(userId).subscribe(
      (purchasedGames: PurchasedGame[]) => {
        this.purchasedGames = purchasedGames;
        this.games = purchasedGames.map((purchasedGame: PurchasedGame) => purchasedGame.game);
        console.log('Received purchased games:', this.purchasedGames);
      },
      (error) => {
        console.error(error);
      }
    );
  }
}

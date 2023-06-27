
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Game} from '../models/game.model';
import {PurchasedGame} from '../models/purchased-game.model';
import {GameService} from '../game/game.service';
import {AccountService} from '../account/component/services/account.service';
import {defineComponents, IgcRatingComponent} from 'igniteui-webcomponents';
import {Rating} from "../models/rating.model";
import {RatingService} from "../services/rating.service";
import {FormControl} from "@angular/forms";

defineComponents(IgcRatingComponent);

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {
  games: Game[] = [];
  private readonly TOKEN = 'token';
  purchasedGames: PurchasedGame[] = [];
  ratings: Rating[] = [];
  userId : string | null = '';

  constructor(
      private router: Router,
      private gameService: GameService,
      private accountService: AccountService,
      private ratingService: RatingService
  ) {}

  ngOnInit() {
    const token = localStorage.getItem(this.TOKEN);
    console.log('Token:', token);
    this.userId = this.accountService.getUserId();
    if (this.userId) {
      console.log('Fetching games for user:', this.userId);
      const gamesForUse = this.getGamesForUser(this.userId);
      for (const game of gamesForUse) {
        this.getRatingForUserByGame(this.userId, game.id.toString());
      }
    }
  }

  getGamesForUser(userId: string): Game[] {
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
    return this.games;
  }


  getRatingForUserByGame(userId: string, gameId: string){
    this.ratingService.getRatingForGameByUser(gameId, userId).subscribe(
        (rating: Rating) => {
          this.ratings.push(rating);
          console.log('Received ratings:', this.ratings);
        },
        (error) => {
          console.error(error);
        }
    )
  }

  //const input = document.getElementByName("rating") as HTMLElement;
  ratingChanged(gameId: number) {
    console.log(gameId);
    console.log(this.userId);
    console.log(this.ratingService.getRatingForGameByUser(gameId.toString(), this.userId));
    this.ratingService.getRatingForGameByUser(gameId.toString(), this.userId).subscribe(
        (rating : Rating) => {
          console.log("rating: ", rating);
          if(rating){
            this.ratingService.updateRating(rating.id, rating);
          }
          else {
            this.ratingService.createRating(rating);
          }
        }
    );
  }
}

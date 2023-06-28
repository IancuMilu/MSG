import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game } from '../models/game.model';
import { GameService } from '../game/game.service';

@Component({
  selector: 'app-game-details',
  templateUrl: './game-details.component.html',
  styleUrls: ['./game-details.component.css']
})
export class GameDetailsComponent implements OnInit {
  game: Game | undefined;
  isStoreComponent = true;

  constructor(
    private route: ActivatedRoute,
    private gameService: GameService
  ) {}

  ngOnInit() {
    this.isStoreComponent = this.route.snapshot.data['isStoreComponent'];
    this.getGameDetails();
  }

  getGameDetails() {
    const gameId = +this.route.snapshot.params['id']; // Use the '+' operator to convert the ID to a number
  
    console.log('Game ID:', gameId); // Print the game ID to the console
    // Request for the store component
    this.gameService.getGameById(gameId).subscribe({
      next: (game: Game) => {
        this.game = game;
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
  
}

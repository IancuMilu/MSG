import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from '../models/game.model';
import { APIEndpointURLs } from '../api-endpoint-urls';
import { PurchasedGame } from '../models/purchased-game.model';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(private http: HttpClient) {}

  getGamesForUser(userId: string): Observable<PurchasedGame[]> {
    const url = APIEndpointURLs.userPurchases + userId;
    return this.http.get<PurchasedGame[]>(url);
  }

  getGameById(gameId: number): Observable<Game> {
    const url = `${APIEndpointURLs.getGameById}/${gameId}`;
    return this.http.get<Game>(url);
  }

  getGames(): Observable<Game[]> {
    const url = APIEndpointURLs.allGames;
    return this.http.get<Game[]>(url);
  }

  getPurchasedGameById(gameId: number): Observable<Game> {
    const url = `${APIEndpointURLs.purchasedGame}/${gameId}`;
    return this.http.get<Game>(url);
  }

  createPurchase(purchase: any): Observable<any> {
    const url = APIEndpointURLs.purchasedGame;
    return this.http.post(url, purchase);
  }

  deleteGame(gameId: number): Observable<void> {
    const url = `${APIEndpointURLs.getGameById}/${gameId}`;
    return this.http.delete<void>(url);
  }

  addGame(newGame: Game): Observable<Game> {
    const url = `${APIEndpointURLs.addGame}`;
    return this.http.post<Game>(url, newGame);
  }
}

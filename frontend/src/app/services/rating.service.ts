import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Rating} from "../models/rating.model";
import {APIEndpointURLs} from "../api-endpoint-urls";

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  constructor(private http: HttpClient) {}

  getRatingForGameByUser(gameId: string, userId: string | null): Observable<Rating> {
    const url = `${APIEndpointURLs.rating}game/${gameId}/user/${userId}`;
    return this.http.get<Rating>(url);
  }

  updateRating(id: number, rating: Rating) {
    const url = `${APIEndpointURLs.rating}${id}`;
    this.http.put(url, rating)
  }

  createRating(rating: Rating) {
    const url = `${APIEndpointURLs.rating}`;
    this.http.post(url, rating);
  }
}
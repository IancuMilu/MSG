import { Publisher } from './publisher.model';

export interface Game {
    id: number;
    publisherName: string;
    name: string;
    description: string;
    imageUrl: string;
    publishDate: string;
    genreNames: string[];
    price: number;
  }
  
export class APIEndpointURLs {
  private static readonly baseUrl = 'http://localhost:8080/java-api/api';

  // User
  public static readonly userUrl = APIEndpointURLs.baseUrl + '/user';
  public static readonly allUser = APIEndpointURLs.userUrl + '/user/all';
  public static readonly user = APIEndpointURLs.userUrl + '/id/';
  public static readonly myStuff = APIEndpointURLs.userUrl + '/stuff';

  // Auth
  public static readonly authUrl = APIEndpointURLs.baseUrl + '/auth';
  public static readonly registerUrl = APIEndpointURLs.authUrl + '/register';
  public static readonly loginUrl = APIEndpointURLs.authUrl + '/login';

  // Game
  public static readonly allGames = APIEndpointURLs.baseUrl + '/game/all';
  public static readonly userPurchases   = APIEndpointURLs.baseUrl + '/purchase/user/';
  public static readonly purchasedGame   = APIEndpointURLs.baseUrl + '/purchase';
  public static readonly getGameById   = APIEndpointURLs.baseUrl + '/game/';
  public static readonly addGame   = APIEndpointURLs.baseUrl + '/game';
}

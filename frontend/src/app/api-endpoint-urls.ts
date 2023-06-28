export class APIEndpointURLs {
  private static readonly baseUrl = '/java-api/api';

  // User
  public static readonly userUrl = APIEndpointURLs.baseUrl + '/user';
  public static readonly allUser = APIEndpointURLs.userUrl + '/all';
  public static readonly user = APIEndpointURLs.userUrl + '/id/';
  public static readonly myStuff = APIEndpointURLs.userUrl + '/stuff';

  // Auth
  public static readonly authUrl = APIEndpointURLs.baseUrl + '/auth';
  public static readonly registerUrl = APIEndpointURLs.authUrl + '/register';
  public static readonly loginUrl = APIEndpointURLs.authUrl + '/login';
}

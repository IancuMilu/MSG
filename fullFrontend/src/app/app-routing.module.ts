import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './account/component/services/auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './account/component/login/login.component';
import { NgModule } from '@angular/core';
import { RegisterComponent } from './account/component/register/register.component';
import { StuffListComponent } from './users/components/stuff-list/stuff-list.component';
import { UserListComponent } from './users/components/user-list/user-list.component';
import { LibraryComponent } from './library/library.component';
import { StoreComponent } from './store/store.component';
import { GameDetailsComponent } from './game-details/game-details.component';


const routes: Routes = [
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'stuff', component: StuffListComponent, canActivate: [AuthGuard] },
  { path: 'library', component: LibraryComponent },
  { path: 'store', component: StoreComponent },
  {path: 'game/:id', component: GameDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

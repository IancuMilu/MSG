import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './account/component/login/login.component';
import { NgModule } from '@angular/core';
import { RegisterComponent } from './account/component/register/register.component';
import { StuffListComponent } from './users/components/stuff-list/stuff-list.component';
import { UserListComponent } from './users/components/user-list/user-list.component';
import { LibraryComponent } from './library/library.component';
import { StoreComponent } from './store/store.component';
import { GameDetailsComponent } from './game-details/game-details.component';
import { GameService } from './game/game.service';
import { AccountService } from './account/component/services/account.service';
import {NgOptimizedImage} from "@angular/common";
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
// import function to register Swiper custom elements
import { register } from 'swiper/element/bundle';
// register Swiper custom elements
register();

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    StuffListComponent,
    LibraryComponent,
    StoreComponent,
    GameDetailsComponent,
    ShoppingCartComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        NgOptimizedImage,
    ],
  providers: [GameService, AccountService],
  bootstrap: [AppComponent],
})
export class AppModule {}

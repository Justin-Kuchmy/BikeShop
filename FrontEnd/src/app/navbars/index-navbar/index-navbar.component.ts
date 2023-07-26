import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-index-navbar',
  templateUrl: './index-navbar.component.html',
})
export class IndexNavbarComponent implements OnInit {

    searchName: String;
    get SearchName() {
        return this.searchName;
      }
      set SearchName(name: String) {
        this.searchName = name;
      }
    constructor() 
    {
        this.searchName = '';
    }
  
    ngOnInit(): void {
    }
    showMenu = false;
    signedIn = false;
    toggleHamburger(){
      this.showMenu = !this.showMenu;
    }

    LoggedIn(): boolean
    {
        if(sessionStorage.getItem('token') != null)
        {
            return true;
        }
        return false;
    }

    Logout(): void
    {
        sessionStorage.removeItem('token');
    }

}

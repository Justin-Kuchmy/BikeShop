import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles: [
]
})
export class HomeComponent implements OnInit {
    showInitialMessage: boolean = true;
    Title: string = "BikeShop CRUD Demo";
    Message: string = `&emsp;&emsp;  Welcome to my CRUD Demo website! Created with simplicity in mind, this platform utilizes MongoDB, as well as RedisDB. The Spring Backend incorporates Spring Security, providing authorization and authentication features. On the frontend, Tailwind CSS and Angular combine to deliver a visually appealing and responsive user interface.<br>`

    +`&emsp;&emsp;   Users have the ability to view products and place orders, while admins enjoy additional privileges, including viewing all customers and orders. This segregation ensures efficient workflow management and facilitates effective customer and order tracking.<br>`

    +`&emsp;&emsp;   Registration will default to a 'user' account or using Login as 'user' for the email and password for regular privledges and 'admin' for aditional privledges.`
    
  constructor()  
  { 
    if(sessionStorage.getItem('FirstLoad') === null)
    {
        sessionStorage.setItem('FirstLoad','WelcomePopUp')
    }
        
    else
    {
        this.showInitialMessage = false;
    }
  }
  closeDialog()
  {
    this.showInitialMessage = false; 
  }
  ngOnInit(): void {
  }

}

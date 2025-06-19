import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ApiService} from './core/services/api.service';
import {Categoria} from './categoria/categoria';
import {CategoriaModel} from './categoria/models/categoria-model';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Categoria],
  templateUrl: './app.html',
  standalone: true,
  styleUrl: './app.scss'
})
export class App implements OnInit {
  protected title = 'dad-front';
  // @ts-ignore
  public categorias: CategoriaModel[] = [];

  constructor(private services: ApiService) {
  }

  ngOnInit(): void {

    this.getCategories();
    //throw new Error('Method not implemented.');
    /*this.services.getProduct().subscribe(data => {
      console.log(data);
    });*/

  }

  private getCategories(): void {
    this.services.getCategory().subscribe(res => {
      this.categorias = res;

      console.log(res);
    });
  }
}

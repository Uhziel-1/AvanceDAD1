import {Component, Input, OnInit} from '@angular/core';
import {CategoriaModel} from './models/categoria-model';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-categoria',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './categoria.html',
  styleUrl: './categoria.scss'
})
export class Categoria implements OnInit {

  @Input() categorias: CategoriaModel[] = [];

  ngOnInit() {
    console.log("=======>>",this.categorias);
  }
}

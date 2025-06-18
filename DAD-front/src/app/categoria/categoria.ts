import {Component, Input} from '@angular/core';
import {CategoriaModel} from './models/categoria';

@Component({
  selector: 'app-categoria',
  imports: [],
  standalone: true,
  templateUrl: './categoria.html',
  styleUrl: './categoria.scss'
})
export class Categoria {
  @Input() categoria:CategoriaModel[] = [];
}

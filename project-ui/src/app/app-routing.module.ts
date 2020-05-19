import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewComponent} from './task-view/view.component';
import {AddComponent} from './task-add/add.component';
import {EditComponent} from './task-edit/edit.component';
import { UserComponent } from './user/user.component';
import { ProjectComponent } from './project/project.component';

const routes: Routes = [
  {path: 'view', component: ViewComponent},
  {path: 'add', component: AddComponent},
  {path: 'user', component: UserComponent},
  {path: 'project', component: ProjectComponent},
  {path: 'update', component: EditComponent},
  {path: 'update/:id', component: EditComponent},
  {path: '', component: ViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

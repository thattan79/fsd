import { DatePipe } from '@angular/common';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../model/task';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TaskService {
  http: HttpClient;
  taskHttpUrl: string = environment.apiUrl + '/task/';
  allTaskHttpUrl: string = environment.apiUrl + '/alltasks/';

  constructor(http: HttpClient, private datePipe: DatePipe) {
    this.http = http;
  }

  getAllTasks(): Promise<any> {
    return this.http.get<Task>(this.allTaskHttpUrl).toPromise().then(value => value);
  }

  getTask(id: string): Promise<any> {
    return this.http.get(this.taskHttpUrl + '' + id).toPromise().then(value => value);
  }

  getTaskByProject(id: number): Promise<any> {
    return this.http.get<Task>(this.taskHttpUrl + 'projects/' + '' + id).toPromise().then(value => value);
  }

  addTask(t: Task): Promise<any> {
    return this.http.post(this.taskHttpUrl, t).toPromise().then(value => value);
  }

  updateTask(id: number, t: Task): Promise<any> {
    return this.http.put(this.taskHttpUrl + '' + id, t).toPromise().then(value => value);
  }

  deleteTask(id: number, t: Task): Promise<any> {
    return this.http.delete(this.taskHttpUrl + '' + id).toPromise().then(value => value);
  }
}

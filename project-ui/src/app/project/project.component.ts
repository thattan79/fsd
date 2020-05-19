import { Component, OnInit } from '@angular/core';
import { Project } from '../model/project';
import { ProjectService } from '../service/project.service';
import { isNullOrUndefined } from 'util';
import * as moment from 'moment';
import { DatePipe } from '@angular/common';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  project: Project;
  projects: Project[];
  filteredProjects: Project[];
  filterByProject: string;
  users: User[];
  userName: string;
  userId: number;
  errorMsg: any;
  isUpdate: boolean;
  searchFilter: string;
  isDateChecked: boolean;

  constructor(private projectService: ProjectService,
              private userService: UserService,
              private datePipe: DatePipe) {
    this.project = new Project();
    this.project.priority = 0;
    this.isDateChecked = false;
  }

  ngOnInit() {
      this.getProjects();
  }

  getProjects(): void {
    this.projectService.getAllProjectsWithTasks().then(value => {
      this.projects = value;
      this.filteredProjects = value;
    });
  }

  update(p: Project ): void {
    this.isUpdate = true;
    this.project.id = p.id;
    this.project.project = p.project;
    this.project.startDate = p.startDate;
    this.project.endDate = p.endDate;
    this.project.priority = p.priority;
    this.project.managerId = p.managerId;
    this.getUserByProject(p.id);
  }

  suspend(p: Project): void {
    p.endDate = new Date(moment.now());
    this.projectService.updateProject(p)
      .then(
        value => {
          this.getProjects();
        }
      );
  }

  onSubmit() {
    if (!this.validateForm()) {
      return false;
    }
    this.errorMsg = '';
    this.projectService.addProject(this.project).then(
      value => {
        this.getProjects();
        this.emptyFields();
      }
    );
  }

  onUpdate() {
    if (!this.validateForm()) {
      return false;
    }
    this.errorMsg = '';
    this.projectService.updateProject(this.project).then(
      value => {
        this.getProjects();
      }
    );
    this.emptyFields();
    this.isUpdate = false;
  }

  loadUsers() {
    this.userService.getAllUsers().then(value => this.users = value);
  }

  getUserByProject(id: number): void {
    this.userService.getUserByProject(id).then(user => {
      this.userName = user.firstName + '-' + user.lastName;
    });
  }

  emptyFields() {
    this.project.project = '';
    this.project.startDate = undefined;
    this.project.endDate = undefined;
    this.project.priority = 0;
    this.project.managerId = undefined;
    this.userName = undefined;
    this.isDateChecked = false;
  }

  public validateForm() {
    const projectName = this.project.project;
    const t = new Date();
    const today = new Date(t.getFullYear(), t.getMonth(), t.getDate() );
    const endDate = new Date(this.project.endDate);
    const startDate = new Date(this.project.startDate);
    let formattedDate;
    if (isNullOrUndefined(projectName) || projectName.trim().length < 1) {
      this.errorMsg = `Project name is mandatory`;
      return false;
    }

    if (endDate < today || startDate < today) {
      formattedDate = this.formatDate(today);
      this.errorMsg = `Start or End Date should be ${formattedDate} or in the future`;
      return false;
    }
    if (endDate < startDate) {
      formattedDate = this.formatDate(startDate);
      this.errorMsg = `End Date should be greater than start date: ${formattedDate}`;
      return false;
    }
    if (this.project.endDate && !this.project.startDate) {
      this.errorMsg = `Start Date should be given when end date is given`;
      return false;
    }

    return true;
  }

  public reset() {
    this.errorMsg = '';
  }

  public formatDate(date: any) {
    return moment(date).format('DD-MM-YYYY');
  }

  onUserSelected() {
    this.users = this.users.filter(user => {
      if (user.employeeId == this.userId) {
        this.userName = user.firstName + '-' + user.lastName;
      }
  });
    this.project.managerId = this.userId;
  }

  changeChkBox(event) {
    if (event.target.checked) {
      this.isDateChecked = true;
      const today = new Date();
      const endDate = new Date().setDate(today.getDate() + 1);

      this.project.startDate = <any> this.datePipe.transform(today, 'yyyy-MM-dd');
      this.project.endDate = <any> this.datePipe.transform(endDate, 'yyyy-MM-dd');

    } else {
      this.isDateChecked = false;
      this.project.startDate = undefined;
      this.project.endDate = undefined;
    }
  }
  // Sorting in ascending order by Start Date
  sortByStartDate() {
    this.filteredProjects = this.projects.sort ((a: any, b: any) =>
      new Date(a.startDate).getTime() - new Date(b.startDate).getTime()
    );
  }
   // Sorting in ascending order by End Date
   sortByEndDate() {
    this.filteredProjects = this.projects.sort ((a: any, b: any) =>
      new Date(a.endDate).getTime() - new Date(b.endDate).getTime()
    );
  }
  // Sorting in descending order by Prority
  sortByPriority() {
    this.filteredProjects = this.projects.sort ((a: any, b: any) =>
        b.priority - a.priority
    );
  }
  // Sorting in descending order by Completed (end date in desc order)
  sortByCompleted() {
      this.filteredProjects = this.projects.filter(p => this.isProjectExpired(p));
      this.filteredProjects = this.filteredProjects.sort ((a: any, b: any) =>
          new Date(b.endDate).getTime() - new Date(a.endDate).getTime()
      );
  }

  isProjectExpired(p: Project): boolean {
    return moment(p.endDate).isBefore(moment());
  }

  resetFilter() {
    this.getProjects();
  }
}

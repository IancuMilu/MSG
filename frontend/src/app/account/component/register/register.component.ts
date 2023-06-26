import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

import { AccountService } from '../services/account.service';
import {Component} from '@angular/core';
import {style} from "@angular/animations";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  form = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
    gender: ['', Validators.required],
    admin: ['']
  });
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService
  ) {
    this.form.valueChanges.subscribe((value) => console.log(value));

    this.f.password.valueChanges.subscribe((value) => console.log(value));
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;

    // Stop here if the form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.accountService.register(this.form.value as any).subscribe({

      next: () => {
        // Reset loading state and navigate to the login page on success
        this.loading = false;
        let mess = document.getElementById("success");
        mess?.setAttribute("style","display:block");

        this.router.navigate(['/login'], { relativeTo: this.route });

      },
      error: (error) => {
        // Handle the error and reset loading state
        this.loading = false;
        // Add error handling logic here (e.g., display error message)
        console.log("Loading error (register)");
      },
    });
  }
}


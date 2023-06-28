import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, ValidatorFn, Validators} from '@angular/forms';

import {AccountService} from '../services/account.service';
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
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', Validators.required],
        //confirmPassword: ['', [Validators.required, this.passwordValidator()]],
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
        const password1 = this.form.value.password;
        const confirmPassword = this.form.value.confirmPassword;

        let passA = document.getElementById("passwordAlert");
        if (!this.verifyPasswords(password1, confirmPassword)) {
            passA?.setAttribute("style", "display:inline");
            // Passwords do not match, display an error message or perform necessary actions
            return;
        } else {
            passA?.setAttribute("style", "display:none");
        }

        this.loading = true;
        this.accountService.register(this.form.value as any).subscribe({

            next: () => {
                // Reset loading state and navigate to the login page on success
                this.loading = false;
                // let mess = document.getElementById("success");
                // mess?.setAttribute("style","display:block");
                //
                // this.router.navigate(['/login'], { relativeTo: this.route });

            },
            error: (error) => {
                // Handle the error and reset loading state
                this.loading = false;
                // Add error handling logic here (e.g., display error message)
                console.log("Loading error: ");
                console.log(error);
                let mess = document.getElementById("success");
                mess?.setAttribute("style", "display:block");

                //redirect with a delay of 2 seconds
                setTimeout(() => {
                    this.router.navigate(['/login'], {relativeTo: this.route});
                }, 2000);
            },
        });
    }

    verifyPasswords(password: string | null | undefined, confirmPassword: string | null | undefined): boolean {
        return password === confirmPassword;
    }

   /* passwordValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const password = control.get('password');
            const confirmPassword = control.get('confirmPassword');

            if (password && confirmPassword && password.value !== confirmPassword.value) {
                return { passwordMismatch: true };
            }
            return null;
        };
    }*/

}


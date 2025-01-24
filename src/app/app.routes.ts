import { Routes } from '@angular/router';
import { PromptFormComponent } from './components/prompt-form/prompt-form.component';
import { UserCasesComponent } from './components/user-cases/user-cases.component';
import { UserCaseSplitComponent } from './components/user-case-split/user-case-split.component';

export const routes: Routes = [
    { path: 'prompt-form', component: PromptFormComponent },
    { path: 'user-cases', component: UserCasesComponent },
    { path: 'user-case-split', component: UserCaseSplitComponent},
    { path: '', redirectTo: '/', pathMatch: 'full' }

];

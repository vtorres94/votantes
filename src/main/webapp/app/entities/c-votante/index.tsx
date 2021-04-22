import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CVotante from './c-votante';
import CVotanteDetail from './c-votante-detail';
import CVotanteUpdate from './c-votante-update';
import CVotanteDeleteDialog from './c-votante-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CVotanteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CVotanteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CVotanteDetail} />
      <ErrorBoundaryRoute path={match.url} component={CVotante} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CVotanteDeleteDialog} />
  </>
);

export default Routes;

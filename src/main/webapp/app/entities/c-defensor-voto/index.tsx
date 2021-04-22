import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CDefensorVoto from './defensorVoto';
import CDefensorVotoDetail from './c-defensor-voto-detail';
import CDefensorVotoUpdate from './c-defensor-voto-update';
import CDefensorVotoDeleteDialog from './c-defensor-voto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CDefensorVotoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CDefensorVotoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CDefensorVotoDetail} />
      <ErrorBoundaryRoute path={match.url} component={CDefensorVoto} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CDefensorVotoDeleteDialog} />
  </>
);

export default Routes;

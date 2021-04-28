import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CAgenda from './c-agenda';
import CAgendaDetail from './c-agenda-detail';
import CAgendaUpdate from './c-agenda-update';
import CAgendaDeleteDialog from './c-agenda-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CAgendaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CAgendaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CAgendaDetail} />
      <ErrorBoundaryRoute path={match.url} component={CAgenda} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CAgendaDeleteDialog} />
  </>
);

export default Routes;

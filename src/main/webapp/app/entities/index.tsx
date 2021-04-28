import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CVotante from './c-votante';
import CDefensorVoto from './c-defensor-voto';
import CCliente from './c-cliente';
import CAgenda from './c-agenda';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}c-votante`} component={CVotante} />
      <ErrorBoundaryRoute path={`${match.url}c-defensor-voto`} component={CDefensorVoto} />
      <ErrorBoundaryRoute path={`${match.url}c-cliente`} component={CCliente} />
      <ErrorBoundaryRoute path={`${match.url}c-agenda`} component={CAgenda} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

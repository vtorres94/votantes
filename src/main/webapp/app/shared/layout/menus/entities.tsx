import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/c-cliente">
      C Cliente
    </MenuItem>
    <MenuItem icon="asterisk" to="/c-defensor-voto">
      C Defensor Voto
    </MenuItem>
    <MenuItem icon="asterisk" to="/c-votante">
      C Votante
    </MenuItem>
    <MenuItem icon="asterisk" to="/c-agenda">
      C Agenda
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

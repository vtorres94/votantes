import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './c-defensor-voto.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICDefensorVotoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CDefensorVotoDetail = (props: ICDefensorVotoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cDefensorVotoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cDefensorVotoDetailsHeading">CDefensorVoto</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cDefensorVotoEntity.id}</dd>
          <dt>
            <span id="nombreCompleto">Nombre Completo</span>
          </dt>
          <dd>{cDefensorVotoEntity.nombreCompleto}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{cDefensorVotoEntity.nombre}</dd>
          <dt>
            <span id="segundoNombre">Segundo Nombre</span>
          </dt>
          <dd>{cDefensorVotoEntity.segundoNombre}</dd>
          <dt>
            <span id="apellidoPaterno">Apellido Paterno</span>
          </dt>
          <dd>{cDefensorVotoEntity.apellidoPaterno}</dd>
          <dt>
            <span id="segundoMaterno">Segundo Materno</span>
          </dt>
          <dd>{cDefensorVotoEntity.segundoMaterno}</dd>
          <dt>
            <span id="edad">Edad</span>
          </dt>
          <dd>{cDefensorVotoEntity.edad}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{cDefensorVotoEntity.telefono}</dd>
          <dt>
            <span id="claveElector">Clave Elector</span>
          </dt>
          <dd>{cDefensorVotoEntity.claveElector}</dd>
          <dt>
            <span id="seccionElectoral">Seccion Electoral</span>
          </dt>
          <dd>{cDefensorVotoEntity.seccionElectoral}</dd>
          <dt>
            <span id="calle">Calle</span>
          </dt>
          <dd>{cDefensorVotoEntity.calle}</dd>
          <dt>
            <span id="numExt">Num Ext</span>
          </dt>
          <dd>{cDefensorVotoEntity.numExt}</dd>
          <dt>
            <span id="colonia">Colonia</span>
          </dt>
          <dd>{cDefensorVotoEntity.colonia}</dd>
          <dt>
            <span id="cp">Cp</span>
          </dt>
          <dd>{cDefensorVotoEntity.cp}</dd>
          <dt>
            <span id="municipio">Municipio</span>
          </dt>
          <dd>{cDefensorVotoEntity.municipio}</dd>
          <dt>
            <span id="estado">Estado</span>
          </dt>
          <dd>{cDefensorVotoEntity.estado}</dd>
          <dt>
            <span id="estatus">Estatus</span>
          </dt>
          <dd>{cDefensorVotoEntity.estatus}</dd>
          <dt>
            <span id="borrado">Borrado</span>
          </dt>
          <dd>{cDefensorVotoEntity.borrado}</dd>
          <dt>Cliente</dt>
          <dd>{cDefensorVotoEntity.cliente ? cDefensorVotoEntity.cliente.cliente : ''}</dd>
        </dl>
        <Button tag={Link} to="/c-defensor-voto" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/c-defensor-voto/${cDefensorVotoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cDefensorVoto }: IRootState) => ({
  cDefensorVotoEntity: cDefensorVoto.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CDefensorVotoDetail);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './c-votante.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICVotanteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CVotanteDetail = (props: ICVotanteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cVotanteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cVotanteDetailsHeading">CVotante</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cVotanteEntity.id}</dd>
          <dt>
            <span id="nombreCompleto">Nombre Completo</span>
          </dt>
          <dd>{cVotanteEntity.nombreCompleto}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{cVotanteEntity.nombre}</dd>
          <dt>
            <span id="segundoNombre">Segundo Nombre</span>
          </dt>
          <dd>{cVotanteEntity.segundoNombre}</dd>
          <dt>
            <span id="apellidoPaterno">Apellido Paterno</span>
          </dt>
          <dd>{cVotanteEntity.apellidoPaterno}</dd>
          <dt>
            <span id="segundoMaterno">Segundo Materno</span>
          </dt>
          <dd>{cVotanteEntity.segundoMaterno}</dd>
          <dt>
            <span id="edad">Edad</span>
          </dt>
          <dd>{cVotanteEntity.edad}</dd>
          <dt>
            <span id="telefono">Telefono</span>
          </dt>
          <dd>{cVotanteEntity.telefono}</dd>
          <dt>
            <span id="claveElector">Clave Elector</span>
          </dt>
          <dd>{cVotanteEntity.claveElector}</dd>
          <dt>
            <span id="seccionElectoral">Seccion Electoral</span>
          </dt>
          <dd>{cVotanteEntity.seccionElectoral}</dd>
          <dt>
            <span id="calle">Calle</span>
          </dt>
          <dd>{cVotanteEntity.calle}</dd>
          <dt>
            <span id="numExt">Num Ext</span>
          </dt>
          <dd>{cVotanteEntity.numExt}</dd>
          <dt>
            <span id="colonia">Colonia</span>
          </dt>
          <dd>{cVotanteEntity.colonia}</dd>
          <dt>
            <span id="cp">Cp</span>
          </dt>
          <dd>{cVotanteEntity.cp}</dd>
          <dt>
            <span id="municipio">Municipio</span>
          </dt>
          <dd>{cVotanteEntity.municipio}</dd>
          <dt>
            <span id="estado">Estado</span>
          </dt>
          <dd>{cVotanteEntity.estado}</dd>
          <dt>
            <span id="estatus">Estatus</span>
          </dt>
          <dd>{cVotanteEntity.estatus}</dd>
          <dt>
            <span id="borrado">Borrado</span>
          </dt>
          <dd>{cVotanteEntity.borrado}</dd>
          <dt>Cliente</dt>
          <dd>{cVotanteEntity.cliente ? cVotanteEntity.cliente.cliente : ''}</dd>
          <dt>Defensor Voto</dt>
          <dd>{cVotanteEntity.defensorVoto ? cVotanteEntity.defensorVoto.nombreCompleto : ''}</dd>
        </dl>
        <Button tag={Link} to="/c-votante" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/c-votante/${cVotanteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cVotante }: IRootState) => ({
  cVotanteEntity: cVotante.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CVotanteDetail);

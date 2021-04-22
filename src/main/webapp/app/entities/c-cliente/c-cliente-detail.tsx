import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './c-cliente.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICClienteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CClienteDetail = (props: ICClienteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cClienteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cClienteDetailsHeading">CCliente</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cClienteEntity.id}</dd>
          <dt>
            <span id="claveCliente">Clave Cliente</span>
          </dt>
          <dd>{cClienteEntity.claveCliente}</dd>
          <dt>
            <span id="cliente">Cliente</span>
          </dt>
          <dd>{cClienteEntity.cliente}</dd>
          <dt>
            <span id="anioElectoral">Anio Electoral</span>
          </dt>
          <dd>{cClienteEntity.anioElectoral}</dd>
          <dt>
            <span id="idUsuarioCreacion">Id Usuario Creacion</span>
          </dt>
          <dd>{cClienteEntity.idUsuarioCreacion}</dd>
          <dt>
            <span id="fechaCreacion">Fecha Creacion</span>
          </dt>
          <dd>
            {cClienteEntity.fechaCreacion ? <TextFormat value={cClienteEntity.fechaCreacion} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="idUsuarioActualizacion">Id Usuario Actualizacion</span>
          </dt>
          <dd>{cClienteEntity.idUsuarioActualizacion}</dd>
          <dt>
            <span id="fechaActualizacion">Fecha Actualizacion</span>
          </dt>
          <dd>
            {cClienteEntity.fechaActualizacion ? (
              <TextFormat value={cClienteEntity.fechaActualizacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="notas">Notas</span>
          </dt>
          <dd>{cClienteEntity.notas}</dd>
          <dt>
            <span id="estatus">Estatus</span>
          </dt>
          <dd>{cClienteEntity.estatus}</dd>
          <dt>
            <span id="borrado">Borrado</span>
          </dt>
          <dd>{cClienteEntity.borrado}</dd>
        </dl>
        <Button tag={Link} to="/c-cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/c-cliente/${cClienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cCliente }: IRootState) => ({
  cClienteEntity: cCliente.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CClienteDetail);

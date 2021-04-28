import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './c-agenda.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICAgendaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CAgendaDetail = (props: ICAgendaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cAgendaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cAgendaDetailsHeading">CAgenda</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cAgendaEntity.id}</dd>
          <dt>
            <span id="direccion">Direccion</span>
          </dt>
          <dd>{cAgendaEntity.direccion}</dd>
          <dt>
            <span id="fecha">Fecha</span>
          </dt>
          <dd>{cAgendaEntity.fecha ? <TextFormat value={cAgendaEntity.fecha} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="hora">Hora</span>
          </dt>
          <dd>{cAgendaEntity.hora}</dd>
          <dt>
            <span id="encargado">Encargado</span>
          </dt>
          <dd>{cAgendaEntity.encargado}</dd>
          <dt>
            <span id="estatus">Estatus</span>
          </dt>
          <dd>{cAgendaEntity.estatus}</dd>
        </dl>
        <Button tag={Link} to="/c-agenda" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/c-agenda/${cAgendaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cAgenda }: IRootState) => ({
  cAgendaEntity: cAgenda.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CAgendaDetail);

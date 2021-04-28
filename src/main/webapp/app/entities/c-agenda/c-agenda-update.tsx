import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './c-agenda.reducer';
import { ICAgenda } from 'app/shared/model/c-agenda.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICAgendaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CAgendaUpdate = (props: ICAgendaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { cAgendaEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/c-agenda' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.fecha = convertDateTimeToServer(values.fecha);

    if (errors.length === 0) {
      const entity = {
        ...cAgendaEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="morenaApp.cAgenda.home.createOrEditLabel" data-cy="CAgendaCreateUpdateHeading">
            Create or edit a CAgenda
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cAgendaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="c-agenda-id">ID</Label>
                  <AvInput id="c-agenda-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="direccionLabel" for="c-agenda-direccion">
                  Direccion
                </Label>
                <AvField
                  id="c-agenda-direccion"
                  data-cy="direccion"
                  type="text"
                  name="direccion"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaLabel" for="c-agenda-fecha">
                  Fecha
                </Label>
                <AvInput
                  id="c-agenda-fecha"
                  data-cy="fecha"
                  type="datetime-local"
                  className="form-control"
                  name="fecha"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.cAgendaEntity.fecha)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="horaLabel" for="c-agenda-hora">
                  Hora
                </Label>
                <AvField
                  id="c-agenda-hora"
                  data-cy="hora"
                  type="text"
                  name="hora"
                  validate={{
                    maxLength: { value: 5, errorMessage: 'This field cannot be longer than 5 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="encargadoLabel" for="c-agenda-encargado">
                  Encargado
                </Label>
                <AvField
                  id="c-agenda-encargado"
                  data-cy="encargado"
                  type="text"
                  name="encargado"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="c-agenda-estatus">
                  Estatus
                </Label>
                <AvField
                  id="c-agenda-estatus"
                  data-cy="estatus"
                  type="string"
                  className="form-control"
                  name="estatus"
                  validate={{
                    max: { value: 1, errorMessage: 'This field cannot be more than 1.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/c-agenda" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  cAgendaEntity: storeState.cAgenda.entity,
  loading: storeState.cAgenda.loading,
  updating: storeState.cAgenda.updating,
  updateSuccess: storeState.cAgenda.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CAgendaUpdate);

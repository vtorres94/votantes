import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './c-cliente.reducer';
import { ICCliente } from 'app/shared/model/c-cliente.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICClienteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CClienteUpdate = (props: ICClienteUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { cClienteEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/c-cliente' + props.location.search);
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
    values.fechaCreacion = convertDateTimeToServer(values.fechaCreacion);
    values.fechaActualizacion = convertDateTimeToServer(values.fechaActualizacion);

    if (errors.length === 0) {
      const entity = {
        ...cClienteEntity,
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
          <h2 id="morenaApp.cCliente.home.createOrEditLabel" data-cy="CClienteCreateUpdateHeading">
            Create or edit a CCliente
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cClienteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="c-cliente-id">ID</Label>
                  <AvInput id="c-cliente-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="claveClienteLabel" for="c-cliente-claveCliente">
                  Clave Cliente
                </Label>
                <AvField
                  id="c-cliente-claveCliente"
                  data-cy="claveCliente"
                  type="text"
                  name="claveCliente"
                  validate={{
                    maxLength: { value: 10, errorMessage: 'This field cannot be longer than 10 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clienteLabel" for="c-cliente-cliente">
                  Cliente
                </Label>
                <AvField
                  id="c-cliente-cliente"
                  data-cy="cliente"
                  type="text"
                  name="cliente"
                  validate={{
                    maxLength: { value: 60, errorMessage: 'This field cannot be longer than 60 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="anioElectoralLabel" for="c-cliente-anioElectoral">
                  Anio Electoral
                </Label>
                <AvField
                  id="c-cliente-anioElectoral"
                  data-cy="anioElectoral"
                  type="string"
                  className="form-control"
                  name="anioElectoral"
                  validate={{
                    max: { value: 9999, errorMessage: 'This field cannot be more than 9999.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="idUsuarioCreacionLabel" for="c-cliente-idUsuarioCreacion">
                  Id Usuario Creacion
                </Label>
                <AvField
                  id="c-cliente-idUsuarioCreacion"
                  data-cy="idUsuarioCreacion"
                  type="string"
                  className="form-control"
                  name="idUsuarioCreacion"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    max: { value: 999999999999, errorMessage: 'This field cannot be more than 999999999999.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaCreacionLabel" for="c-cliente-fechaCreacion">
                  Fecha Creacion
                </Label>
                <AvInput
                  id="c-cliente-fechaCreacion"
                  data-cy="fechaCreacion"
                  type="datetime-local"
                  className="form-control"
                  name="fechaCreacion"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.cClienteEntity.fechaCreacion)}
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="idUsuarioActualizacionLabel" for="c-cliente-idUsuarioActualizacion">
                  Id Usuario Actualizacion
                </Label>
                <AvField
                  id="c-cliente-idUsuarioActualizacion"
                  data-cy="idUsuarioActualizacion"
                  type="string"
                  className="form-control"
                  name="idUsuarioActualizacion"
                  validate={{
                    max: { value: 999999999999, errorMessage: 'This field cannot be more than 999999999999.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fechaActualizacionLabel" for="c-cliente-fechaActualizacion">
                  Fecha Actualizacion
                </Label>
                <AvInput
                  id="c-cliente-fechaActualizacion"
                  data-cy="fechaActualizacion"
                  type="datetime-local"
                  className="form-control"
                  name="fechaActualizacion"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.cClienteEntity.fechaActualizacion)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="notasLabel" for="c-cliente-notas">
                  Notas
                </Label>
                <AvField
                  id="c-cliente-notas"
                  data-cy="notas"
                  type="text"
                  name="notas"
                  validate={{
                    maxLength: { value: 300, errorMessage: 'This field cannot be longer than 300 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="c-cliente-estatus">
                  Estatus
                </Label>
                <AvField
                  id="c-cliente-estatus"
                  data-cy="estatus"
                  type="string"
                  className="form-control"
                  name="estatus"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    max: { value: 1, errorMessage: 'This field cannot be more than 1.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="borradoLabel" for="c-cliente-borrado">
                  Borrado
                </Label>
                <AvField
                  id="c-cliente-borrado"
                  data-cy="borrado"
                  type="string"
                  className="form-control"
                  name="borrado"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    max: { value: 1, errorMessage: 'This field cannot be more than 1.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/c-cliente" replace color="info">
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
  cClienteEntity: storeState.cCliente.entity,
  loading: storeState.cCliente.loading,
  updating: storeState.cCliente.updating,
  updateSuccess: storeState.cCliente.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CClienteUpdate);

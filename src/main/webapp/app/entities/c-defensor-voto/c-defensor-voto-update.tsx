import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICCliente } from 'app/shared/model/c-cliente.model';
import { getEntities as getCClientes } from 'app/entities/c-cliente/c-cliente.reducer';
import { getEntity, updateEntity, createEntity, reset } from './c-defensor-voto.reducer';
import { ICDefensorVoto } from 'app/shared/model/c-defensor-voto.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICDefensorVotoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CDefensorVotoUpdate = (props: ICDefensorVotoUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { cDefensorVotoEntity, cClientes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/c-defensor-voto' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCClientes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...cDefensorVotoEntity,
        ...values,
        cliente: cClientes.find(it => it.id.toString() === values.clienteId.toString()),
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
          <h2 id="morenaApp.cDefensorVoto.home.createOrEditLabel" data-cy="CDefensorVotoCreateUpdateHeading">
            Create or edit a CDefensorVoto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cDefensorVotoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="c-defensor-voto-id">ID</Label>
                  <AvInput id="c-defensor-voto-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nombreCompletoLabel" for="c-defensor-voto-nombreCompleto">
                  Nombre Completo
                </Label>
                <AvField
                  id="c-defensor-voto-nombreCompleto"
                  data-cy="nombreCompleto"
                  type="text"
                  name="nombreCompleto"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    maxLength: { value: 60, errorMessage: 'This field cannot be longer than 60 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nombreLabel" for="c-defensor-voto-nombre">
                  Nombre
                </Label>
                <AvField
                  id="c-defensor-voto-nombre"
                  data-cy="nombre"
                  type="text"
                  name="nombre"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="segundoNombreLabel" for="c-defensor-voto-segundoNombre">
                  Segundo Nombre
                </Label>
                <AvField
                  id="c-defensor-voto-segundoNombre"
                  data-cy="segundoNombre"
                  type="text"
                  name="segundoNombre"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="apellidoPaternoLabel" for="c-defensor-voto-apellidoPaterno">
                  Apellido Paterno
                </Label>
                <AvField
                  id="c-defensor-voto-apellidoPaterno"
                  data-cy="apellidoPaterno"
                  type="text"
                  name="apellidoPaterno"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="segundoMaternoLabel" for="c-defensor-voto-segundoMaterno">
                  Segundo Materno
                </Label>
                <AvField
                  id="c-defensor-voto-segundoMaterno"
                  data-cy="segundoMaterno"
                  type="text"
                  name="segundoMaterno"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="edadLabel" for="c-defensor-voto-edad">
                  Edad
                </Label>
                <AvField
                  id="c-defensor-voto-edad"
                  data-cy="edad"
                  type="string"
                  className="form-control"
                  name="edad"
                  validate={{
                    max: { value: 999, errorMessage: 'This field cannot be more than 999.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="telefonoLabel" for="c-defensor-voto-telefono">
                  Telefono
                </Label>
                <AvField
                  id="c-defensor-voto-telefono"
                  data-cy="telefono"
                  type="text"
                  name="telefono"
                  validate={{
                    maxLength: { value: 16, errorMessage: 'This field cannot be longer than 16 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="claveElectorLabel" for="c-defensor-voto-claveElector">
                  Clave Elector
                </Label>
                <AvField
                  id="c-defensor-voto-claveElector"
                  data-cy="claveElector"
                  type="text"
                  name="claveElector"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="seccionElectoralLabel" for="c-defensor-voto-seccionElectoral">
                  Seccion Electoral
                </Label>
                <AvField
                  id="c-defensor-voto-seccionElectoral"
                  data-cy="seccionElectoral"
                  type="string"
                  className="form-control"
                  name="seccionElectoral"
                  validate={{
                    max: { value: 999999, errorMessage: 'This field cannot be more than 999999.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="calleLabel" for="c-defensor-voto-calle">
                  Calle
                </Label>
                <AvField
                  id="c-defensor-voto-calle"
                  data-cy="calle"
                  type="text"
                  name="calle"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numExtLabel" for="c-defensor-voto-numExt">
                  Num Ext
                </Label>
                <AvField
                  id="c-defensor-voto-numExt"
                  data-cy="numExt"
                  type="text"
                  name="numExt"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="coloniaLabel" for="c-defensor-voto-colonia">
                  Colonia
                </Label>
                <AvField
                  id="c-defensor-voto-colonia"
                  data-cy="colonia"
                  type="text"
                  name="colonia"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cpLabel" for="c-defensor-voto-cp">
                  Cp
                </Label>
                <AvField
                  id="c-defensor-voto-cp"
                  data-cy="cp"
                  type="string"
                  className="form-control"
                  name="cp"
                  validate={{
                    max: { value: 999999, errorMessage: 'This field cannot be more than 999999.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="municipioLabel" for="c-defensor-voto-municipio">
                  Municipio
                </Label>
                <AvField
                  id="c-defensor-voto-municipio"
                  data-cy="municipio"
                  type="text"
                  name="municipio"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estadoLabel" for="c-defensor-voto-estado">
                  Estado
                </Label>
                <AvField
                  id="c-defensor-voto-estado"
                  data-cy="estado"
                  type="text"
                  name="estado"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="c-defensor-voto-estatus">
                  Estatus
                </Label>
                <AvField
                  id="c-defensor-voto-estatus"
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
                <Label id="borradoLabel" for="c-defensor-voto-borrado">
                  Borrado
                </Label>
                <AvField
                  id="c-defensor-voto-borrado"
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
              <AvGroup>
                <Label for="c-defensor-voto-cliente">Cliente</Label>
                <AvInput id="c-defensor-voto-cliente" data-cy="cliente" type="select" className="form-control" name="clienteId">
                  <option value="" key="0" />
                  {cClientes
                    ? cClientes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.cliente}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/c-defensor-voto" replace color="info">
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
  cClientes: storeState.cCliente.entities,
  cDefensorVotoEntity: storeState.cDefensorVoto.entity,
  loading: storeState.cDefensorVoto.loading,
  updating: storeState.cDefensorVoto.updating,
  updateSuccess: storeState.cDefensorVoto.updateSuccess,
});

const mapDispatchToProps = {
  getCClientes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CDefensorVotoUpdate);

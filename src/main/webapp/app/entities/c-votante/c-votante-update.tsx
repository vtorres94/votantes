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
import { ICDefensorVoto } from 'app/shared/model/c-defensor-voto.model';
import { getEntities as getCDefensorVotos } from 'app/entities/c-defensor-voto/c-defensor-voto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './c-votante.reducer';
import { ICVotante } from 'app/shared/model/c-votante.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICVotanteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CVotanteUpdate = (props: ICVotanteUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { cVotanteEntity, cClientes, cDefensorVotos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/c-votante' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCClientes();
    props.getCDefensorVotos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...cVotanteEntity,
        ...values,
        cliente: cClientes.find(it => it.id.toString() === values.clienteId.toString()),
        defensorVoto: cDefensorVotos.find(it => it.id.toString() === values.defensorVotoId.toString()),
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
          <h2 id="morenaApp.cVotante.home.createOrEditLabel" data-cy="CVotanteCreateUpdateHeading">
            Create or edit a CVotante
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cVotanteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="c-votante-id">ID</Label>
                  <AvInput id="c-votante-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nombreCompletoLabel" for="c-votante-nombreCompleto">
                  Nombre Completo
                </Label>
                <AvField
                  id="c-votante-nombreCompleto"
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
                <Label id="nombreLabel" for="c-votante-nombre">
                  Nombre
                </Label>
                <AvField
                  id="c-votante-nombre"
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
                <Label id="segundoNombreLabel" for="c-votante-segundoNombre">
                  Segundo Nombre
                </Label>
                <AvField
                  id="c-votante-segundoNombre"
                  data-cy="segundoNombre"
                  type="text"
                  name="segundoNombre"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="apellidoPaternoLabel" for="c-votante-apellidoPaterno">
                  Apellido Paterno
                </Label>
                <AvField
                  id="c-votante-apellidoPaterno"
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
                <Label id="segundoMaternoLabel" for="c-votante-segundoMaterno">
                  Segundo Materno
                </Label>
                <AvField
                  id="c-votante-segundoMaterno"
                  data-cy="segundoMaterno"
                  type="text"
                  name="segundoMaterno"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="edadLabel" for="c-votante-edad">
                  Edad
                </Label>
                <AvField
                  id="c-votante-edad"
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
                <Label id="telefonoLabel" for="c-votante-telefono">
                  Telefono
                </Label>
                <AvField
                  id="c-votante-telefono"
                  data-cy="telefono"
                  type="text"
                  name="telefono"
                  validate={{
                    maxLength: { value: 16, errorMessage: 'This field cannot be longer than 16 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="claveElectorLabel" for="c-votante-claveElector">
                  Clave Elector
                </Label>
                <AvField
                  id="c-votante-claveElector"
                  data-cy="claveElector"
                  type="text"
                  name="claveElector"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="seccionElectoralLabel" for="c-votante-seccionElectoral">
                  Seccion Electoral
                </Label>
                <AvField
                  id="c-votante-seccionElectoral"
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
                <Label id="calleLabel" for="c-votante-calle">
                  Calle
                </Label>
                <AvField
                  id="c-votante-calle"
                  data-cy="calle"
                  type="text"
                  name="calle"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numExtLabel" for="c-votante-numExt">
                  Num Ext
                </Label>
                <AvField
                  id="c-votante-numExt"
                  data-cy="numExt"
                  type="text"
                  name="numExt"
                  validate={{
                    maxLength: { value: 30, errorMessage: 'This field cannot be longer than 30 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="coloniaLabel" for="c-votante-colonia">
                  Colonia
                </Label>
                <AvField
                  id="c-votante-colonia"
                  data-cy="colonia"
                  type="text"
                  name="colonia"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cpLabel" for="c-votante-cp">
                  Cp
                </Label>
                <AvField
                  id="c-votante-cp"
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
                <Label id="municipioLabel" for="c-votante-municipio">
                  Municipio
                </Label>
                <AvField
                  id="c-votante-municipio"
                  data-cy="municipio"
                  type="text"
                  name="municipio"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estadoLabel" for="c-votante-estado">
                  Estado
                </Label>
                <AvField
                  id="c-votante-estado"
                  data-cy="estado"
                  type="text"
                  name="estado"
                  validate={{
                    maxLength: { value: 80, errorMessage: 'This field cannot be longer than 80 characters.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="estatusLabel" for="c-votante-estatus">
                  Estatus
                </Label>
                <AvField
                  id="c-votante-estatus"
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
                <Label id="borradoLabel" for="c-votante-borrado">
                  Borrado
                </Label>
                <AvField
                  id="c-votante-borrado"
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
                <Label for="c-votante-cliente">Cliente</Label>
                <AvInput id="c-votante-cliente" data-cy="cliente" type="select" className="form-control" name="clienteId">
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
              <AvGroup>
                <Label for="c-votante-defensorVoto">Defensor Voto</Label>
                <AvInput id="c-votante-defensorVoto" data-cy="defensorVoto" type="select" className="form-control" name="defensorVotoId">
                  <option value="" key="0" />
                  {cDefensorVotos
                    ? cDefensorVotos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nombreCompleto}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/c-votante" replace color="info">
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
  cDefensorVotos: storeState.cDefensorVoto.entities,
  cVotanteEntity: storeState.cVotante.entity,
  loading: storeState.cVotante.loading,
  updating: storeState.cVotante.updating,
  updateSuccess: storeState.cVotante.updateSuccess,
});

const mapDispatchToProps = {
  getCClientes,
  getCDefensorVotos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CVotanteUpdate);

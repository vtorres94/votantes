import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
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
import { Dimmer, Divider, Header, Loader, Modal, Segment, Image, Input, Dropdown, Button, Label, Icon } from 'semantic-ui-react';
import { AUTHORITIES } from 'app/config/constants';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import NumberFormat from 'react-number-format';

export interface ICDefensorVotoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICDefensorVotoState {
  isNew: boolean;
  id?: number;
  nombreCompleto: string;
  nombre: string;
  nombreValido: boolean;
  segundoNombre: string;
  apellidoPaterno: string;
  apellidoPaternoValido: boolean;
  segundoMaterno: string;
  edad: number;
  telefono: string;
  claveElector: string;
  claveElectorValido: boolean;
  seccionElectoral?: number;
  seccionElectoralValido: boolean;
  calle: string;
  numExt: string;
  colonia: string;
  cp: number;
  municipio: string;
  estado: string;
  estatus: number;
  estatusValido: boolean;
  borrado: number;
  clienteId: number;
  clienteValido: boolean;
}

export const CDefensorVotoUpdate = (props: ICDefensorVotoUpdateProps) => {
  const [state, setState] = useState<ICDefensorVotoState>({
    isNew: !props.match.params || !props.match.params.id,
    id: props.match.params.id ? Number(props.match.params.id) : null,
    nombreCompleto: '',
    nombre: '',
    nombreValido: true,
    segundoNombre: '',
    apellidoPaterno: '',
    apellidoPaternoValido: true,
    segundoMaterno: '',
    edad: null,
    telefono: '',
    claveElector: '',
    claveElectorValido: true,
    seccionElectoral: null,
    seccionElectoralValido: true,
    calle: '',
    numExt: '',
    colonia: '',
    cp: null,
    municipio: 'VILLANUEVA',
    estado: 'ZACATECAS',
    estatus: 1,
    estatusValido: true,
    borrado: 0,
    clienteId: 1,
    clienteValido: true,
  });

  const { cClientes, loading, updating } = props;

  useEffect(() => {
    if (state.isNew) {
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

  const handleClose = () => {
    props.history.goBack();
  };

  const saveEntity = () => {
    let { nombreValido, apellidoPaternoValido, claveElectorValido, seccionElectoralValido, clienteValido, estatusValido } = state;

    (nombreValido = state.nombre.trim() !== '' ? true : false),
      (apellidoPaternoValido = state.apellidoPaterno.trim() !== '' ? true : false),
      (claveElectorValido = state.claveElector.trim() !== '' ? true : false),
      (seccionElectoralValido = state.seccionElectoral ? true : false),
      (clienteValido = state.clienteId !== null ? true : false),
      (estatusValido = state.estatus ? true : false);

    if (
      nombreValido && apellidoPaternoValido && claveElectorValido && seccionElectoralValido && props.isAdmin
        ? clienteValido
        : null && estatusValido
    ) {
      const entity = {
        ...state,
        cliente: cClientes.find(it => it.id.toString() === state.clienteId.toString()),
      };
      if (state.isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  const validateFields = () => {
    setState({
      ...state,
      nombreValido: state.nombre.trim() !== '' ? true : false,
      apellidoPaternoValido: state.apellidoPaterno.trim() !== '' ? true : false,
      claveElectorValido: state.claveElector.trim() !== '' ? true : false,
      seccionElectoralValido: state.seccionElectoral ? true : false,
      clienteValido: state.clienteId !== null ? true : false,
      estatusValido: state.estatus ? true : false,
    });
  };

  const handleChangeInput = (e, { name, value, min, max, pattern, id }) => {
    // tslint:disable-next-line:switch-default
    switch (name) {
      case 'nombre':
        setState({ ...state, nombre: value.toUpperCase(), nombreValido: value.trim() !== '' ? true : false });
        break;
      case 'apellidoP':
        setState({ ...state, apellidoPaterno: value.toUpperCase(), apellidoPaternoValido: value.trim() !== '' ? true : false });
        break;
      case 'claveE':
        setState({
          ...state,
          claveElector: value.toUpperCase(),
          claveElectorValido: value.trim() !== '' ? true : false,
        });
        break;
      case 'seccionE':
        setState({
          ...state,
          seccionElectoral: value !== null && value !== '' ? value : null,
          seccionElectoralValido: value ? true : false,
        });
        break;
      case 'cliente':
        setState({ ...state, clienteId: value !== null && value !== '' ? value : null, clienteValido: value ? true : false });
        break;
      case 'estatus':
        setState({ ...state, estatus: value, estatusValido: value ? true : false });
        break;
      default:
        break;
    }
  };

  return (
    <Modal blurring={false} centered={false} open={true} style={{ height: 'auto', position: 'relative' }} size="large">
      <Button icon="close" floated="right" onClick={handleClose} />
      <Modal.Content scrolling>
        <Modal.Description>
          <Divider horizontal>
            <Header as="h2" floated="left">
              {state.isNew ? 'Registrar defensor' : 'Actualizar defensor'}
            </Header>
          </Divider>
          <div>
            {loading ? (
              <div className="col-lg-12 mb-3">
                <Segment>
                  <Dimmer active>
                    <Loader size="massive">Cargando</Loader>
                  </Dimmer>
                  <Image src="https://react.semantic-ui.com/images/wireframe/short-paragraph.png" />
                  <Image src="https://react.semantic-ui.com/images/wireframe/short-paragraph.png" />
                  <Image src="https://react.semantic-ui.com/images/wireframe/short-paragraph.png" />
                  <Image src="https://react.semantic-ui.com/images/wireframe/short-paragraph.png" />
                  <Image src="https://react.semantic-ui.com/images/wireframe/short-paragraph.png" />
                </Segment>
              </div>
            ) : (
              <div className="row">
                <div className="col-lg-12">
                  <React.Fragment>
                    <div className="row">
                      <Label attached="top" as="a" color="blue" icon="user circle" ribbon={false} pointing="below">
                        Defensor del voto
                      </Label>
                      &nbsp;
                      {!state.isNew ? (
                        <div className="col-lg-12 col-md-12 col-sm-12">
                          <Label as="a" color="green" icon="id card outline" attached="top left">
                            ID: {props.match.params.id}
                          </Label>
                          &nbsp;
                        </div>
                      ) : null}
                      &nbsp;
                      <div className="col-lg-12 col-md-12 col-sm-12" />
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Clave elector</Label>
                        <Input
                          fluid
                          placeholder="Clave elector"
                          name="claveE"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.claveElector.toUpperCase()}
                          onChange={handleChangeInput}
                          icon="user circle"
                        />
                        {!state.claveElectorValido ? (
                          <Label basic color="red" pointing="left">
                            El campo es obligatorio
                          </Label>
                        ) : null}
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Sección electoral</Label>
                        <NumberFormat
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          style={{ width: '100%' }}
                          value={state.seccionElectoral}
                          displayType={'input'}
                          format={'# # # #'}
                          allowNegative={false}
                          customInput={Input}
                          isNumericString={true}
                          id="float-input_ded"
                          placeholder={'Sección electoral'}
                          // tslint:disable-next-line: jsx-no-lambda
                          onValueChange={values => {
                            const { formattedValue, value } = values;
                            setState({
                              ...state,
                              seccionElectoral: Number(value),
                            });
                          }}
                        />
                        {!state.seccionElectoralValido ? (
                          <Label basic color="red" pointing="left">
                            El campo es obligatorio
                          </Label>
                        ) : null}
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Nombre</Label>
                        <Input
                          fluid
                          placeholder="Nombre"
                          name="nombre"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.nombre.toUpperCase()}
                          onChange={handleChangeInput}
                          icon="user circle"
                        />
                        {!state.nombreValido ? (
                          <Label basic color="red" pointing="left">
                            El campo es obligatorio
                          </Label>
                        ) : null}
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Segundo nombre</Label>
                        <Input
                          fluid
                          placeholder="Segundo nombre"
                          name="segundoNombre"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.segundoNombre.toUpperCase()}
                          onChange={event => setState({ ...state, segundoNombre: event.target.value.toUpperCase() })}
                          icon="user circle"
                        />
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Apellido paterno</Label>
                        <Input
                          fluid
                          placeholder="Apellido paterno"
                          name="apellidoP"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.apellidoPaterno.toUpperCase()}
                          onChange={handleChangeInput}
                          icon="user circle"
                        />
                        {!state.apellidoPaternoValido ? (
                          <Label basic color="red" pointing="left">
                            El campo es obligatorio
                          </Label>
                        ) : null}
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Apellido materno</Label>
                        <Input
                          fluid
                          placeholder="Apellido materno"
                          name="apellidoM"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.segundoMaterno.toUpperCase()}
                          onChange={event => setState({ ...state, segundoMaterno: event.target.value.toUpperCase() })}
                          icon="user circle"
                        />
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        <Label>Teléfono</Label>
                        <NumberFormat
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          style={{ width: '100%' }}
                          value={state.telefono}
                          displayType={'input'}
                          format={'###-###-####'}
                          // tslint:disable-next-line: jsx-boolean-value
                          allowNegative={false}
                          customInput={Input}
                          // tslint:disable-next-line: jsx-boolean-value
                          isNumericString={true}
                          id="float-input_ded"
                          placeholder={'Teléfono'}
                          // tslint:disable-next-line: jsx-no-lambda
                          onValueChange={values => {
                            const { formattedValue, value } = values;
                            setState({
                              ...state,
                              telefono: value,
                            });
                          }}
                        />
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Calle</Label>
                        <Input
                          fluid
                          placeholder="Calle"
                          name="calle"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.calle.toUpperCase()}
                          onChange={event => setState({ ...state, calle: event.target.value.toUpperCase() })}
                          icon="user circle"
                        />
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>No. ext</Label>
                        <Input
                          fluid
                          placeholder="No. ext"
                          name="numE"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.numExt.toUpperCase()}
                          onChange={event => setState({ ...state, numExt: event.target.value.toUpperCase() })}
                          icon="user circle"
                        />
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>C.P.</Label>
                        <NumberFormat
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          style={{ width: '100%' }}
                          value={state.cp}
                          displayType={'input'}
                          format={'#####'}
                          // tslint:disable-next-line: jsx-boolean-value
                          allowNegative={false}
                          customInput={Input}
                          // tslint:disable-next-line: jsx-boolean-value
                          isNumericString={true}
                          id="float-input_ded"
                          placeholder={'C.P.'}
                          // tslint:disable-next-line: jsx-no-lambda
                          onValueChange={values => {
                            const { formattedValue, value } = values;
                            setState({
                              ...state,
                              cp: Number(value),
                            });
                          }}
                        />
                      </div>
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        &nbsp;
                        <Label>Colonia</Label>
                        <Input
                          fluid
                          placeholder="Colonia"
                          name="colonia"
                          label={{ icon: 'asterisk' }}
                          labelPosition="left corner"
                          value={state.colonia.toUpperCase()}
                          onChange={event => setState({ ...state, colonia: event.target.value.toUpperCase() })}
                          icon="user circle"
                        />
                      </div>
                      {props.isAdmin ? (
                        <div className="col-lg-6 col-md-6 col-sm-6">
                          <Label>Cliente</Label>
                          <Dropdown
                            clearable
                            placeholder="Cliente"
                            fluid
                            search
                            selection
                            name="clienteId"
                            value={state.clienteId}
                            onChange={handleChangeInput}
                            options={
                              props.cClientesList !== undefined
                                ? props.cClientesList.map(m => ({
                                    key: m.id,
                                    text: m.cliente,
                                    value: m.id,
                                  }))
                                : null
                            }
                          />
                          {!state.clienteValido ? (
                            <Label basic color="red" pointing="left">
                              El campo es obligatorio
                            </Label>
                          ) : null}
                        </div>
                      ) : null}
                      <div className="col-lg-6 col-md-6 col-sm-6">
                        <Label>Estatus</Label>
                        <br />
                        <Button.Group>
                          <Button
                            negative={state.estatus === 0}
                            // tslint:disable-next-line:jsx-no-lambda
                            onClick={() => setState({ ...state, estatus: 0 })}
                          >
                            Inactivo
                          </Button>
                          <Button.Or text="-" />
                          <Button
                            positive={state.estatus === 1}
                            // tslint:disable-next-line:jsx-no-lambda
                            onClick={() => setState({ ...state, estatus: 1 })}
                          >
                            Activo
                          </Button>
                        </Button.Group>
                        {!state.estatusValido ? (
                          <Label basic color="red" pointing="left">
                            El campo es obligatorio
                          </Label>
                        ) : null}
                      </div>
                    </div>
                    &nbsp;
                    <div className={'row'}>
                      <div className="col-lg-12 text-center">
                        <Button.Group>
                          <Button animated onClick={handleClose}>
                            <Button.Content visible>Cancelar</Button.Content>
                            <Button.Content hidden>
                              <Icon name="arrow left" />
                            </Button.Content>
                          </Button>
                          <Button.Or text="O" />
                          {/* tslint:disable-next-line: jsx-no-lambda */}
                          <Button
                            animated
                            positive
                            onClick={() => {
                              validateFields(), saveEntity();
                            }}
                          >
                            {state.isNew ? (
                              <Button.Content visible>Crear</Button.Content>
                            ) : (
                              <Button.Content visible>Guardar</Button.Content>
                            )}
                            <Button.Content hidden>
                              <Icon name="save" />
                            </Button.Content>
                          </Button>
                        </Button.Group>
                      </div>
                    </div>
                    <Divider horizontal>
                      <Header as="h4" />
                    </Divider>
                  </React.Fragment>
                </div>
              </div>
            )}
          </div>
        </Modal.Description>
      </Modal.Content>
    </Modal>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  cClientes: storeState.cCliente.entities,
  cDefensorVotoEntity: storeState.cDefensorVoto.entity,
  loading: storeState.cDefensorVoto.loading,
  updating: storeState.cDefensorVoto.updating,
  updateSuccess: storeState.cDefensorVoto.updateSuccess,
  cClientesList: storeState.cCliente.entities,
  user: storeState.userManagement.user,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
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

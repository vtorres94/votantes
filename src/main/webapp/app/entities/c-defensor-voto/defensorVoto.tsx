import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { IRootState } from 'app/shared/reducers';
import { getEntities, updateEntity } from 'app/entities/c-defensor-voto/c-defensor-voto.reducer';
import { AUTHORITIES } from 'app/config/constants';
import { getSession } from 'app/shared/reducers/authentication';
import { hasAnyAuthority } from 'app/shared/auth/private-route';

import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider from 'react-bootstrap-table2-toolkit/dist/react-bootstrap-table2-toolkit.min';
import { Header, Icon, Label, Divider, Segment, Dropdown, Button, Input, Accordion } from 'semantic-ui-react';

import { getEntities as getCClientes } from 'app/entities/c-cliente/c-cliente.reducer';
import NumberFormat from 'react-number-format';

export interface ICDefensorVotoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

// export type ICGabineteState = IPaginationBaseState;
export interface ICDefensorVotoState {
  load: boolean;
  activeIndex: number;
  viewTable: boolean;
  viewCalendar: boolean;
  filtroID: string;
  filtroClave: string;
  filtroNombre: string;
  filtroTelefono: string;
  filtroSeccion: string;
  filtroMunicipio: string;
  filtroEstado: string;
  filtroCliente: string;
  filtroEstatus: number;
}

export const CDefensorVoto = (props: ICDefensorVotoProps) => {
  const [
    state = {
      load: false,
      activeIndex: 0,
      viewTable: true,
      viewCalendar: false,
      filtroID: '',
      filtroClave: '',
      filtroNombre: '',
      filtroTelefono: '',
      filtroSeccion: '',
      filtroMunicipio: '',
      filtroEstado: '',
      filtroCliente: null,
      filtroEstatus: null,
    },
    setState,
  ] = useState<ICDefensorVotoState>();

  useEffect(() => {
    loadEntities();
    props.getCClientes();
  }, []);

  const loadEntities = () => {
    let query;
    props.isAdmin ? (query = '') : (query = '&clienteId.equals=' + props.account.clienteId);
    query = state.filtroID && state.filtroID.length > 0 ? query + '&id.in=' + state.filtroID : query;
    query = state.filtroClave && state.filtroClave.length > 0 ? query + '&claveElector.contains=' + state.filtroClave : query;
    query = state.filtroNombre && state.filtroNombre.length > 0 ? query + '&nombre.contains=' + state.filtroNombre : query;
    query = state.filtroTelefono && state.filtroTelefono.length > 0 ? query + '&telefono.contains=' + state.filtroTelefono : query;
    query = state.filtroSeccion && state.filtroSeccion.length > 0 ? query + '&seccionElectoral.contains=' + state.filtroSeccion : query;
    query = state.filtroMunicipio && state.filtroMunicipio.length > 0 ? query + '&municipio.contains=' + state.filtroMunicipio : query;
    query = state.filtroEstado && state.filtroEstado.length > 0 ? query + '&estado.contains=' + state.filtroEstado : query;
    query = state.filtroCliente != null ? query + '&clienteId.equals=' + state.filtroCliente : query;
    query = state.filtroEstatus != null ? query + '&estatus.equals=' + state.filtroEstatus : query;
    props.getEntities(query, 0, 1000, 'nombre,asc');
  };

  const limpiarCampos = event => {
    setState({
      ...state,
      ...state,
      filtroID: '',
      filtroClave: '',
      filtroNombre: '',
      filtroTelefono: '',
      filtroSeccion: '',
      filtroMunicipio: '',
      filtroEstado: '',
      filtroCliente: '',
      filtroEstatus: null,
    });
    props.isAdmin
      ? props.getEntities('', 0, 1000, 'nombre,asc')
      : props.getEntities('&clienteId.equals=' + props.account.clienteId, 0, 1000, 'nombre,asc');
  };

  const handleClick = (e, titleProps) => {
    const { activeIndex } = state;
    const { index } = titleProps;
    const newIndex = activeIndex === index ? -1 : index;
    setState({ ...state, activeIndex: newIndex });
  };

  const handleChangeInput = (e, { name, value, min, max, pattern }) => {
    switch (name) {
      case 'id':
        setState({ ...state, filtroID: value });
        break;
      case 'clave':
        setState({ ...state, filtroClave: value });
        break;
      case 'nombre':
        setState({ ...state, filtroNombre: value });
        break;
      case 'telefono':
        setState({ ...state, filtroTelefono: value });
        break;
      case 'seccion':
        setState({ ...state, filtroSeccion: value });
        break;
      case 'municipio':
        setState({ ...state, filtroMunicipio: value });
        break;
      case 'estado':
        setState({ ...state, filtroEstado: value });
        break;
      case 'clienteId':
        setState({ ...state, filtroCliente: value });
        break;
      case 'estatus':
        setState({ ...state, filtroEstatus: value });
        break;
      default:
        break;
    }
  };

  const { defensoresVotoList, match, user, account, isAdmin, cClientes } = props;
  const {
    activeIndex,
    filtroID,
    filtroClave,
    filtroNombre,
    filtroEstatus,
    filtroTelefono,
    filtroSeccion,
    filtroMunicipio,
    filtroEstado,
    filtroCliente,
  } = state;
  const estatusUnidad = [
    {
      text: 'Activo',
      value: 1,
    },
    {
      text: 'Inactivo',
      value: 0,
    },
  ];

  const columns = [
    {
      align: 'center',
      dataField: 'id',
      text: 'ID',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: () => {
        return {
          textAlign: 'center',
          width: '100px',
        };
      },
    },
    {
      align: 'center',
      dataField: 'claveElector',
      text: 'Clave de Elector',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '200px' };
      },
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '200px' };
      },
      dataField: 'nombreCompleto',
      text: 'Nombre',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '100px' };
      },
      dataField: 'telefono',
      text: 'Teléfono',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '100px' };
      },
      dataField: 'seccionElectoral',
      text: 'Sección Electoral',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '200px' };
      },
      dataField: 'calle',
      text: 'Calle',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '100px' };
      },
      dataField: 'numExt',
      text: 'No. ext',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '150px' };
      },
      dataField: 'colonia',
      text: 'Colonia',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '100px' };
      },
      dataField: 'cp',
      text: 'CP',
    },
    {
      align: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '100px' };
      },
      dataField: 'cliente.cliente',
      text: 'Cliente',
      hidden: !props.isAdmin,
    },
    {
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '150px' };
      },
      dataField: 'estatus',
      isDummyField: true,
      text: 'Estatus',
      align: 'center',
      headerAlign: 'center',
      // tslint:disable-next-line:ter-arrow-body-style
      formatter: (cellContent, row) => {
        return (
          <div>
            {row.estatus === 0 ? (
              <Label tag color="red" size="large">
                Inactivo
              </Label>
            ) : (
              <Label tag color="green" size="large">
                Activo
              </Label>
            )}
          </div>
        );
      },
    },
    {
      // tslint:disable-next-line:ter-arrow-body-style
      headerStyle: (colum, colIndex) => {
        return { textAlign: 'center', width: '200px' };
      },
      dataField: 'accion',
      isDummyField: true,
      align: 'center',
      headerAlign: 'center',
      text: 'Acciones',
      // tslint:disable-next-line:ter-arrow-body-style
      formatter: (cellContent, row) => {
        return (
          <div className="col-lg-12">
            <Button.Group fluid>
              <Button
                as={Link}
                to={`${match.url}/${row.id}`}
                circular
                icon="eye"
                title="Consultar"
                // disabled={inventarioList.length > 0 ? false : true}
                color="blue"
              />
              <Button
                as={Link}
                to={`${match.url}/votantes/${row.id}`}
                circular
                icon="list"
                title="Votantes"
                // disabled={inventarioList.length > 0 ? false : true}
                color="green"
              />
              {props.isAdmin ? (
                <Button
                  // onClick={openEstatus}
                  as={Link}
                  to={`${match.url}/${row.id}/edit`}
                  circular
                  icon="pencil"
                  title="Actualizar"
                  // disabled={inventarioList.length > 0 ? false : true}
                  color="yellow"
                />
              ) : null}
              {props.isAdmin ? (
                <Button
                  // onClick={openActualizar}
                  as={Link}
                  to={`${match.url}/${row.id}/delete`}
                  circular
                  icon="trash"
                  title="Eliminar"
                  // disabled={inventarioList.length > 0 ? false : true}
                  color="red"
                />
              ) : null}
            </Button.Group>
          </div>
        );
      },
    },
  ];

  const CustomToggleList = ({
    // tslint:disable-next-line:no-shadowed-variable
    onColumnToggle,
    toggles,
  }) => (
    <Button.Group>
      {columns
        .map(column => ({
          ...column,
          toggle: toggles[column.dataField],
        }))
        .filter(f => (!props.isAdmin ? f.dataField !== 'clienteCliente' : f))
        .map(column => (
          <Button
            type="button"
            key={column.dataField}
            className={`btn btn-warning ${column.toggle ? 'active' : ''}`}
            data-toggle="button"
            aria-pressed={column.toggle ? 'true' : 'false'}
            // tslint:disable-next-line:jsx-no-lambda
            onClick={() => onColumnToggle(column.dataField)}
          >
            {column.text}
          </Button>
        ))}
    </Button.Group>
  );

  // tslint:disable-next-line:no-shadowed-variable
  const sizePerPageRenderer = ({ options, currSizePerPage, onSizePerPageChange }) => (
    <div>
      <Button.Group>
        {options.map(option => {
          const isSelect = currSizePerPage === `${option.page}`;
          return (
            <Button
              size="mini"
              key={option.text}
              color={isSelect ? 'blue' : null}
              // tslint:disable-next-line:jsx-no-lambda
              onClick={() => onSizePerPageChange(option.page)}
            >
              {option.text}
            </Button>
          );
        })}
      </Button.Group>
    </div>
  );

  const selectRow = {
    mode: 'radio',
    clickToSelect: true,
    // onSelect: onRowSelect,
    bgColor: 'pink', // you should give a bgcolor, otherwise, you can't regonize which row has been selected
    hideSelectColumn: true, // enable hide selection column.
  };

  const options = {
    page: 1, // which page you want to show as default
    sizePerPageList: [
      {
        text: '5',
        value: 5,
      },
      {
        text: '10',
        value: 10,
      },
    ], // you can change the Dropdown clearable list for size per page
    sizePerPage: 5, // which size per page you want to locate as default
    pageStartIndex: 1, // where to start counting the pages
    paginationSize: 10, // the pagination bar size.
    prePage: '<', // Previous page button text
    nextPage: '>', // Next page button text
    firstPage: '<<', // First page button text
    lastPage: '>>', // Last page button text
    noDataText: 'No existen registros',
    sizePerPageRenderer,
    paginationPosition: 'bottom', // default is bottom, top and both is all available
    // hideSizePerPage: true > You can hide the Dropdown clearable for sizePerPage
    // alwaysShowAllBtns: true // Always show next and previous button
    // withFirstAndLast: false > Hide the going to First and Last page button
  };

  return (
    <div>
      <div className="row">
        <div className="col-lg-12">
          <React.Fragment>
            <Divider horizontal>
              <Header as="h2" floated="left">
                Defensores del voto
              </Header>
            </Divider>
            <Accordion styled fluid>
              <Accordion.Title active={activeIndex === 0} index={0} onClick={handleClick}>
                <Icon name="search" /> Buscar defensor
              </Accordion.Title>
              <Accordion.Content active={activeIndex === 0}>
                <Segment color="green" padded>
                  <Label as="a" color="green" icon="id card outline" attached="top left">
                    Criterios de búsqueda
                  </Label>
                  <div className="row">
                    <div className="col-lg-2 mb-1">
                      <Label>ID:</Label>
                      <NumberFormat
                        style={{ width: '100%' }}
                        value={state.filtroID}
                        displayType={'input'}
                        // tslint:disable-next-line: jsx-boolean-value
                        allowEmptyFormatting={true}
                        allowNegative={false}
                        customInput={Input}
                        thousandSeparator={false}
                        id="float-input_ded"
                        placeholder={'ID'}
                        // tslint:disable-next-line: jsx-no-lambda
                        onValueChange={values => {
                          const { formattedValue, value } = values;
                          setState({ ...state, filtroID: value });
                        }}
                      />
                    </div>
                    <div className="col-lg-2 mb-1">
                      <Label> Clave Elector: </Label>
                      <Input fluid placeholder="Clave" name="clave" value={filtroClave} onChange={handleChangeInput} icon="user circle" />
                    </div>
                    <div className="col-lg-2 mb-1">
                      <Label>Nombre:</Label>
                      <Input
                        fluid
                        placeholder="Nombre"
                        name="nombre"
                        value={filtroNombre}
                        onChange={handleChangeInput}
                        icon="user circle"
                      />
                    </div>
                    {props.isAdmin ? (
                      <div className="col-lg-2 mb-1">
                        <Label>Cliente:</Label>
                        <Dropdown
                          fluid
                          search
                          selection
                          clearable
                          placeholder="Cliente"
                          name="clienteId"
                          value={filtroCliente}
                          onChange={handleChangeInput}
                          options={cClientes.map(r => ({ value: r.id, key: r.id, text: r.cliente }))}
                        />
                      </div>
                    ) : null}
                    <div className="col-lg-2 mb-1">
                      <Label>Estatus:</Label>
                      <Dropdown
                        clearable
                        placeholder="Estatus"
                        fluid
                        search
                        selection
                        value={filtroEstatus}
                        name="estatus"
                        onChange={handleChangeInput}
                        options={estatusUnidad}
                      />
                    </div>
                  </div>
                  <div className="row ">
                    <div className="col-lg-12 text-right">
                      {/*  <div className="col-lg-2 mb-1"> */}
                      <br />
                      <Button.Group floated="right">
                        <Button
                          //  onClick={modalHistoria}+
                          replace
                          floated="right"
                          icon="eraser"
                          title="Limpiar"
                          // disabled={inventarioList.length > 0 ? false : true}
                          // tslint:disable-next-line:jsx-no-lambda
                          onClick={() => limpiarCampos(event)}
                          color={'grey'}
                        />
                        <Button
                          floated="right"
                          color="green"
                          icon="search"
                          title="Buscar"
                          // tslint:disable-next-line:jsx-no-lambda
                          onClick={loadEntities}
                          // disabled={clienteId != null && fechaInicio != null && fechaFin != null && proveedorId != null ? false : true}
                          // color={fechaInicio != null && fechaFin != null ? 'green' : 'grey'}
                        />
                      </Button.Group>
                    </div>
                  </div>
                </Segment>
              </Accordion.Content>
            </Accordion>
            <Divider horizontal>
              <Header as="h4" />
            </Divider>
          </React.Fragment>
        </div>
      </div>
      &nbsp;
      <div className="row" style={{ overflow: 'auto' }}>
        <React.Fragment>
          {props.isAdmin ? (
            <Button
              as={Link}
              to={`${match.url}/new`}
              circular
              icon="add"
              floated="right"
              toggle
              title="Crear"
              color="facebook"
              size="big"
            />
          ) : null}
          <ToolkitProvider
            className="table table-hover table-striped card"
            keyField="id"
            pagination={paginationFactory(options)}
            selectRow={selectRow}
            data={defensoresVotoList ? defensoresVotoList : null}
            columns={columns}
            columnToggle
          >
            {propss => (
              <div>
                <CustomToggleList {...propss.columnToggleProps} />
                <hr />
                <BootstrapTable
                  {...propss.baseProps}
                  selectRow={selectRow}
                  pagination={paginationFactory(options)}
                  // id="table-next"
                  noDataIndication="No existen registros"
                />
              </div>
            )}
          </ToolkitProvider>
        </React.Fragment>
      </div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  cClientes: storeState.cCliente.entities,
  defensoresVotoList: storeState.cDefensorVoto.entities,
  account: storeState.authentication.account,
  user: storeState.userManagement.user,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
});

const mapDispatchToProps = {
  getEntities,
  updateEntity,
  getSession,
  getCClientes,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CDefensorVoto);

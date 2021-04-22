import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './c-cliente.reducer';
import { ICCliente } from 'app/shared/model/c-cliente.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICClienteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CCliente = (props: ICClienteProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { cClienteList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="c-cliente-heading" data-cy="CClienteHeading">
        C Clientes
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new C Cliente
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cClienteList && cClienteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('claveCliente')}>
                  Clave Cliente <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cliente')}>
                  Cliente <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('anioElectoral')}>
                  Anio Electoral <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('idUsuarioCreacion')}>
                  Id Usuario Creacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaCreacion')}>
                  Fecha Creacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('idUsuarioActualizacion')}>
                  Id Usuario Actualizacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaActualizacion')}>
                  Fecha Actualizacion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('notas')}>
                  Notas <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('estatus')}>
                  Estatus <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('borrado')}>
                  Borrado <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cClienteList.map((cCliente, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cCliente.id}`} color="link" size="sm">
                      {cCliente.id}
                    </Button>
                  </td>
                  <td>{cCliente.claveCliente}</td>
                  <td>{cCliente.cliente}</td>
                  <td>{cCliente.anioElectoral}</td>
                  <td>{cCliente.idUsuarioCreacion}</td>
                  <td>
                    {cCliente.fechaCreacion ? <TextFormat type="date" value={cCliente.fechaCreacion} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{cCliente.idUsuarioActualizacion}</td>
                  <td>
                    {cCliente.fechaActualizacion ? (
                      <TextFormat type="date" value={cCliente.fechaActualizacion} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{cCliente.notas}</td>
                  <td>{cCliente.estatus}</td>
                  <td>{cCliente.borrado}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cCliente.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cCliente.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cCliente.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No C Clientes found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={cClienteList && cClienteList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ cCliente }: IRootState) => ({
  cClienteList: cCliente.entities,
  loading: cCliente.loading,
  totalItems: cCliente.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CCliente);

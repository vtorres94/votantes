import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './c-defensor-voto.reducer';
import { ICDefensorVoto } from 'app/shared/model/c-defensor-voto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICDefensorVotoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CDefensorVoto = (props: ICDefensorVotoProps) => {
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

  const { cDefensorVotoList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="c-defensor-voto-heading" data-cy="CDefensorVotoHeading">
        C Defensor Votos
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new C Defensor Voto
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cDefensorVotoList && cDefensorVotoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreCompleto')}>
                  Nombre Completo <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombre')}>
                  Nombre <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('segundoNombre')}>
                  Segundo Nombre <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apellidoPaterno')}>
                  Apellido Paterno <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('segundoMaterno')}>
                  Segundo Materno <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('edad')}>
                  Edad <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('telefono')}>
                  Telefono <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('claveElector')}>
                  Clave Elector <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('seccionElectoral')}>
                  Seccion Electoral <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('calle')}>
                  Calle <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('numExt')}>
                  Num Ext <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('colonia')}>
                  Colonia <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cp')}>
                  Cp <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('municipio')}>
                  Municipio <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('estado')}>
                  Estado <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('estatus')}>
                  Estatus <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('borrado')}>
                  Borrado <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Cliente <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cDefensorVotoList.map((cDefensorVoto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cDefensorVoto.id}`} color="link" size="sm">
                      {cDefensorVoto.id}
                    </Button>
                  </td>
                  <td>{cDefensorVoto.nombreCompleto}</td>
                  <td>{cDefensorVoto.nombre}</td>
                  <td>{cDefensorVoto.segundoNombre}</td>
                  <td>{cDefensorVoto.apellidoPaterno}</td>
                  <td>{cDefensorVoto.segundoMaterno}</td>
                  <td>{cDefensorVoto.edad}</td>
                  <td>{cDefensorVoto.telefono}</td>
                  <td>{cDefensorVoto.claveElector}</td>
                  <td>{cDefensorVoto.seccionElectoral}</td>
                  <td>{cDefensorVoto.calle}</td>
                  <td>{cDefensorVoto.numExt}</td>
                  <td>{cDefensorVoto.colonia}</td>
                  <td>{cDefensorVoto.cp}</td>
                  <td>{cDefensorVoto.municipio}</td>
                  <td>{cDefensorVoto.estado}</td>
                  <td>{cDefensorVoto.estatus}</td>
                  <td>{cDefensorVoto.borrado}</td>
                  <td>
                    {cDefensorVoto.cliente ? <Link to={`c-cliente/${cDefensorVoto.cliente.id}`}>{cDefensorVoto.cliente.cliente}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cDefensorVoto.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cDefensorVoto.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cDefensorVoto.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No C Defensor Votos found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={cDefensorVotoList && cDefensorVotoList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ cDefensorVoto }: IRootState) => ({
  cDefensorVotoList: cDefensorVoto.entities,
  loading: cDefensorVoto.loading,
  totalItems: cDefensorVoto.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CDefensorVoto);

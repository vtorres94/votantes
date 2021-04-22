import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICDefensorVoto, defaultValue } from 'app/shared/model/c-defensor-voto.model';

export const ACTION_TYPES = {
  FETCH_CDEFENSORVOTO_LIST: 'cDefensorVoto/FETCH_CDEFENSORVOTO_LIST',
  FETCH_CDEFENSORVOTO: 'cDefensorVoto/FETCH_CDEFENSORVOTO',
  CREATE_CDEFENSORVOTO: 'cDefensorVoto/CREATE_CDEFENSORVOTO',
  UPDATE_CDEFENSORVOTO: 'cDefensorVoto/UPDATE_CDEFENSORVOTO',
  PARTIAL_UPDATE_CDEFENSORVOTO: 'cDefensorVoto/PARTIAL_UPDATE_CDEFENSORVOTO',
  DELETE_CDEFENSORVOTO: 'cDefensorVoto/DELETE_CDEFENSORVOTO',
  RESET: 'cDefensorVoto/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICDefensorVoto>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CDefensorVotoState = Readonly<typeof initialState>;

// Reducer

export default (state: CDefensorVotoState = initialState, action): CDefensorVotoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CDEFENSORVOTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CDEFENSORVOTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CDEFENSORVOTO):
    case REQUEST(ACTION_TYPES.UPDATE_CDEFENSORVOTO):
    case REQUEST(ACTION_TYPES.DELETE_CDEFENSORVOTO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CDEFENSORVOTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CDEFENSORVOTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CDEFENSORVOTO):
    case FAILURE(ACTION_TYPES.CREATE_CDEFENSORVOTO):
    case FAILURE(ACTION_TYPES.UPDATE_CDEFENSORVOTO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CDEFENSORVOTO):
    case FAILURE(ACTION_TYPES.DELETE_CDEFENSORVOTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CDEFENSORVOTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CDEFENSORVOTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CDEFENSORVOTO):
    case SUCCESS(ACTION_TYPES.UPDATE_CDEFENSORVOTO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CDEFENSORVOTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CDEFENSORVOTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/c-defensor-votos';

// Actions

export const getEntities: ICrudGetAllAction<ICDefensorVoto> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CDEFENSORVOTO_LIST,
    payload: axios.get<ICDefensorVoto>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICDefensorVoto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CDEFENSORVOTO,
    payload: axios.get<ICDefensorVoto>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICDefensorVoto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CDEFENSORVOTO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICDefensorVoto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CDEFENSORVOTO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICDefensorVoto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CDEFENSORVOTO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICDefensorVoto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CDEFENSORVOTO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
